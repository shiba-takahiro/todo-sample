package jp.co.miraino_katachi.todo_sample.servlet;

import java.io.IOException;
import java.util.List;

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

@WebServlet("/list")
public class ListServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(ListServlet.class);

	/**
	 * 作業一覧表示
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.trace("Enter");

		try {
			// アイテム一覧取得
			ItemDAO itemDAO = DAOFactory.createItemDAO();
			List<Item> itemList = itemDAO.getItemList();
			req.setAttribute("items", itemList.toArray(new Item[0]));

			// 一覧画面へ
			req.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(req, resp);

		} catch (DAOException e) {
			logger.error(e.getMessage());

			// エラーメッセージをリクエスト属性に保存
			req.setAttribute("message", "作業一覧を表示できません");

			// エラーページへフォワード
			req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, resp);
		}

		logger.trace("Exit");
	}

}
