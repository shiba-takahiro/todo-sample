package jp.co.miraino_katachi.todo_sample.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.miraino_katachi.todo_sample.dao.DAOFactory;
import jp.co.miraino_katachi.todo_sample.dao.ItemDAO;
import jp.co.miraino_katachi.todo_sample.entity.Item;
import jp.co.miraino_katachi.todo_sample.exceptions.DAOException;
import jp.co.miraino_katachi.todo_sample.validator.ParamValidator;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(DeleteServlet.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.trace("Enter");

		// リクエストパラメータ取得
		String itemIDStr = request.getParameter("item_id");
		logger.debug("item_id = " + itemIDStr);

		// パラメータチェック
		String error = checkParameter(itemIDStr);
		if (error != null) {
			// エラーメッセージをリクエスト属性に保存
			request.setAttribute("message", error);

			// 項目IDがないのはありえないのでエラーページへフォワード
			request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
			return;
		}

		try {
			int itemID = Integer.parseInt(itemIDStr);
			// 削除対象アイテム取得
			ItemDAO itemDAO = DAOFactory.createItemDAO();
			Item item = itemDAO.getItem(itemID);
			request.setAttribute("item", item);

			// 削除画面へ
			request.getRequestDispatcher("/WEB-INF/jsp/delete.jsp").forward(request, response);

		} catch (NumberFormatException e) {
			logger.error(e.getMessage());

			// エラーメッセージをリクエスト属性に保存
			request.setAttribute("message", "作業項目IDが不正です");

			// エラーページへフォワード
			request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
		} catch(DAOException e) {
			logger.error(e.getMessage());

			// エラーメッセージをリクエスト属性に保存
			request.setAttribute("message", "削除画面を開けませんでした");

			// エラーページへフォワード
			request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
		}

		logger.trace("Exit");
	}

	private String checkParameter(String itemID) {
		// 項目IDチェック -----------------
		return ParamValidator.validateItemID(itemID);
	}

}
