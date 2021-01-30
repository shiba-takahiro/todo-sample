package jp.co.miraino_katachi.todo.servlet;

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

import jp.co.miraino_katachi.todo.exceptions.DAOException;
import jp.co.miraino_katachi.todo.logic.EditActionLogic;
import jp.co.miraino_katachi.todo.validator.ParamValidator;

@WebServlet("/edit_action")
public class EditActionServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(EditActionServlet.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.trace("Enter");

		// リクエストパラメータ取得
		String itemIDStr = request.getParameter("item_id");
		String name = request.getParameter("name");
		String userIDStr = request.getParameter("user_id");
		String expireDateStr = request.getParameter("expire_date");
		String finished = request.getParameter("finished");
		logger.debug("name = " + name + ", user_id = " + userIDStr +
				", expire_date = " + expireDateStr + ", finished = " + finished);

		// パラメータチェック
		String error = checkParameter(itemIDStr, name, userIDStr, expireDateStr);
		if (error != null) {
			// エラーメッセージをリクエスト属性に保存
			request.setAttribute("message", error);

			// 編集ページへ戻す
			request.getRequestDispatcher("/edit").forward(request, response);
			return;
		}

		try {
			int itemID = Integer.parseInt(itemIDStr);
			int userID = Integer.parseInt(userIDStr);
			Date expireDate = new SimpleDateFormat("yyyy-MM-dd").parse(expireDateStr);
			// 編集処理
			boolean isSuccess =
					new EditActionLogic().execute(itemID, name, userID, expireDate, "true".equals(finished));
			if (isSuccess) {

				// 一覧画面へ(URLを変更したいのでリダイレクト)
				response.sendRedirect("/todo-sample/list");

			} else {
				logger.error("作業項目更新失敗 id = " + itemIDStr);

				// エラーメッセージをリクエスト属性に保存
				request.setAttribute("message", "作業項目の更新に失敗しました");

				// エラーページへフォワード
				request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
			}
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());

			// エラーメッセージをリクエスト属性に保存
			request.setAttribute("message", "作業項目IDまたはユーザIDが不正です");

			// エラーページへフォワード
			request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
		} catch (ParseException e) {
			logger.error(e.getMessage());

			// エラーメッセージをリクエスト属性に保存
			request.setAttribute("message", "期限を Date 型に変換できません");

			// エラーページへフォワード
			request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
		} catch(DAOException e) {
			logger.error(e.getMessage());

			// エラーメッセージをリクエスト属性に保存
			request.setAttribute("message", "作業項目の更新に失敗しました");

			// エラーページへフォワード
			request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
		}
		logger.trace("Exit");
	}

	private String checkParameter(String itemID, String name, String userID, String date) {
		// 項目 ID チェック ------
		String error = ParamValidator.validateItemID(itemID);
		if (error != null) {
			return error;
		}

		// 作業項目チェック ------
		error = ParamValidator.validateItemName(name);
		if (error != null) {
			return error;
		}

		// ユーザ名チェック ------
		error = ParamValidator.validateUserName(userID);
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
