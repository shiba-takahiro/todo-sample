package jp.co.miraino_katachi.todo.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

@WebServlet("/add")
public class AddServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(AddServlet.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.trace("Enter");

		try {
			// ユーザプルダウンメニュー用のユーザリスト取得
			UserDAO userDAO = DAOFactory.createUserDAO();
			List<User> userList = userDAO.getUsers();

			// 取得したユーザリストをリクエストに保存
			request.setAttribute("users",  userList.toArray(new User[0]));

			// 現在の日付をyyyy-MM-ddフォーマットでリクエストに保存
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
			request.setAttribute("today", fmt.format(new Date()));

			// 作業登録画面へ
			request.getRequestDispatcher("/WEB-INF/jsp/add.jsp").forward(request, response);

		} catch(DAOException e) {
			logger.error(e.getMessage());

			// エラーメッセージをリクエスト属性に保存
			request.setAttribute("message", "作業登録画面を開けませんでした");

			// エラーページへフォワード
			request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
		}

		logger.trace("Exit");
	}

}
