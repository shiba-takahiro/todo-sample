package jp.co.miraino_katachi.todo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.miraino_katachi.todo.dao.DAOFactory;
import jp.co.miraino_katachi.todo.dao.UserDAO;
import jp.co.miraino_katachi.todo.entity.User;
import jp.co.miraino_katachi.todo.exceptions.DAOException;
import jp.co.miraino_katachi.todo.validator.ParamValidator;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

	/**
	 * ログイン処理
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.trace("Enter");

		String userName = request.getParameter("user_name");
		String password = request.getParameter("password");

		logger.info("user_id={}", userName);

		// パラメータチェック
		String error = checkParameter(userName, password);
		if (error != null) {
			// エラーメッセージをリクエスト属性に保存
			request.setAttribute("message", error);

			// ログインページへフォワード
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}

		try {
			// ユーザ情報の取得
			UserDAO userDAO = DAOFactory.createUserDAO();
			User user = userDAO.getUser(userName, password);

			if (user == null) {
				logger.info("ログイン失敗");

				// エラーメッセージをリクエスト属性に保存
				request.setAttribute("message", "ユーザ名またはパスワードが違います");

				// ログインページへフォワード
				request.getRequestDispatcher("index.jsp").forward(request, response);
			} else {
				logger.info("ログイン成功");

				// 取得したユーザ情報をセッションに保存
				request.getSession().setAttribute("currentUser", user);

				// 作業一覧ページへリダイレクト
				response.sendRedirect("/todo-sample/list");
			}
		} catch (DAOException e) {
			logger.error(e.getMessage());

			// エラーメッセージをリクエスト属性に保存
			request.setAttribute("message", "ログインできません");

			// エラーページへフォワード
			request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
		}

		logger.trace("Exit");
	}

	/**
	 * パラメータチェック
	 *
	 * @param userName ユーザ名
	 * @param password パスワード
	 * @return チェックエラー時には、エラーメッセージを返す。エラーがなければ null。
	 */
	private String checkParameter(String userName, String password) {

		// ユーザ名のチェック
		String error = ParamValidator.validateUserName(userName);
		if (error != null) {
			return error;
		}

		// パスワードチェック ---------------
		error = ParamValidator.validatePassword(password);
		if (error != null) {
			return error;
		}

		return null;
	}
}
