package jp.co.miraino_katachi.todo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(LogoutServlet.class);

	/**
	 * ログアウト処理
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.trace("Enter");

		// セッションに保存しているユーザ情報を消す
		request.getSession().setAttribute("currentUser", null);

		// ログインページへリダイレクト
		response.sendRedirect("/todo-sample/index.jsp");

		logger.trace("Exit");
	}

}
