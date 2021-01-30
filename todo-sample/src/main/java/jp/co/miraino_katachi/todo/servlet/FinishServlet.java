package jp.co.miraino_katachi.todo.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.miraino_katachi.todo.exceptions.DAOException;
import jp.co.miraino_katachi.todo.logic.FinishLogic;
import jp.co.miraino_katachi.todo.validator.ParamValidator;

@WebServlet("/finish")
public class FinishServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(FinishServlet.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.trace("Enter");

		// リクエストパラメータ取得
		String itemIDStr = request.getParameter("item_id");
		logger.debug("item_id = " + itemIDStr);

		// パラメータチェック
		String error = checkParameter(itemIDStr);
		if (error != null) {
			logger.error("パラメータチェックエラー id = " + itemIDStr);

			// エラーメッセージをリクエスト属性に保存
			request.setAttribute("message", error);

			// 項目IDがないのはありえないのでエラーページへフォワード
			request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
			return;
		}

		try {
			int itemID = Integer.parseInt(itemIDStr);
			// 完了状態更新処理
			if((new FinishLogic().execute(itemID))) {

				// 呼びだし元画面(一覧or検索)によって遷移先を変更(検索画面の場合keywordあり)
				if (request.getParameter("keyword") == null) {

					// 一覧画面へ(URLを変更したいのでリダイレクト)
					response.sendRedirect("/todo-sample/list");
				} else {

					// 検索結果画面へ(URLを変更したいのでリダイレクト)
					String keyword = request.getParameter("keyword");
					keyword = URLEncoder.encode(keyword, "utf-8");
					response.sendRedirect("/todo-sample/search?keyword=" + keyword);
				}
			} else {
				logger.error("完了状態更新失敗 id = {}", itemIDStr);

				// エラーメッセージをリクエスト属性に保存
				request.setAttribute("message", "完了状態の更新に失敗しました");

				// エラーページへフォワード
				request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
			}
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());

			// エラーメッセージをリクエスト属性に保存
			request.setAttribute("message", "作業項目IDが不正です");

			// エラーページへフォワード
			request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
		} catch (DAOException e) {
			logger.error(e.getMessage());

			// エラーメッセージをリクエスト属性に保存
			request.setAttribute("message", "完了状態の更新に失敗しました");

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
