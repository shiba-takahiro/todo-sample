package jp.co.miraino_katachi.todo_sample.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.miraino_katachi.todo_sample.exceptions.DAOException;
import jp.co.miraino_katachi.todo_sample.logic.AddActionLogic;
import jp.co.miraino_katachi.todo_sample.validator.ParamValidator;

@WebServlet("/add_action")
public class AddActionServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(AddActionServlet.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.trace("Enter");

		// リクエストパラメータ取得
		String name = request.getParameter("name");
		String userIDStr = request.getParameter("user_id");
		String expireDateStr = request.getParameter("expire_date");
		logger.debug("name = " + name + ", user_id = " + userIDStr + ", expire_date " + expireDateStr);

		// パラメータチェック
		String error = checkParameter(name, userIDStr, expireDateStr);
		if (error != null) {
			// エラーメッセージをリクエスト属性に保存
			request.setAttribute("message", error);

			// 登録ページへ戻す
			request.getRequestDispatcher("/add").forward(request, response);
			return;
		}

		try {
			int userID = Integer.parseInt(userIDStr);
			Date expireDate = new SimpleDateFormat("yyyy-MM-dd").parse(expireDateStr);

			// 登録処理
			boolean isSuccess =
					new AddActionLogic().execute(name, userID, expireDate);
			if (isSuccess) {

				// 一覧画面へ(URLを変更したいのでリダイレクト)
				response.sendRedirect("/todo-sample/list");

			} else {
				logger.error("作業項目登録失敗");

				// エラーメッセージをリクエスト属性に保存
				request.setAttribute("message", "作業登録に失敗しました");

				// エラーページへフォワード
				request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
			}
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());

			// エラーメッセージをリクエスト属性に保存
			request.setAttribute("message", "ユーザIDが不正です");

			// エラーページへフォワード
			request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
		} catch (ParseException e) {
			logger.error(e.getMessage());

			// エラーメッセージをリクエスト属性に保存
			request.setAttribute("message", "期限を Date 型に変換できません");

			// エラーページへフォワード
			request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
		} catch (DAOException e) {
			logger.error(e.getMessage());

			// エラーメッセージをリクエスト属性に保存
			request.setAttribute("message", "作業登録に失敗しました");

			// エラーページへフォワード
			request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
		}
		logger.trace("Exit");
	}

	private String checkParameter(String name, String userID, String date) {

		// 作業項目チェック ------
		String error = ParamValidator.validateItemName(name);
		if (error != null) {
			return error;
		}

		// ユーザIDチェック ------
		error = ParamValidator.validateUserID(userID);
		if (error != null) {
			return error;
		}

		// 期限チェック ------
		error = ParamValidator.validateExpireDate(date);
		if (error != null) {
			return error;
		}

		return null;
	}
}
