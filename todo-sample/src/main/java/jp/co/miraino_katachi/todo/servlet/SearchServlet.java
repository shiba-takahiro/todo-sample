package jp.co.miraino_katachi.todo.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.miraino_katachi.todo.dao.DAOFactory;
import jp.co.miraino_katachi.todo.dao.ItemDAO;
import jp.co.miraino_katachi.todo.entity.Item;
import jp.co.miraino_katachi.todo.exceptions.DAOException;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(SearchServlet.class);

	/**
	 * 検索
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.trace("Enter");

		// リクエストパラメータ取得
		String keyword = req.getParameter("keyword");
		logger.debug("keyword={}", keyword);

		try {
			// 検索処理
			ItemDAO itemDAO = DAOFactory.createItemDAO();
			List<Item> itemList = itemDAO.getItemListByKeyword(keyword);
			req.setAttribute("items", itemList.toArray(new Item[0]));

			// 検索結果画面へ
			req.getRequestDispatcher("/WEB-INF/jsp/search.jsp").forward(req, resp);

		} catch (DAOException e) {
			logger.error(e.getMessage());

			// エラーメッセージをリクエスト属性に保存
			req.setAttribute("message", "検索に失敗しました");

			// エラーページへフォワード
			req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, resp);
		}

		logger.trace("Exit");
	}
}
