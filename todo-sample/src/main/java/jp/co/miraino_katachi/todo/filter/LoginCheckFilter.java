package jp.co.miraino_katachi.todo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter({
	"/list",
	"/finish",
	"/search",
	"/add",    "/add_action",
	"/delete", "/delete_action",
	"/edit",   "/edit_action"
})
/**
 *
 * ログインチェックを行うFilterクラス
 * 未ログインでのアクセスはすべてログインページへリダイレクトする
 *
 */
public class LoginCheckFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest)request).getSession();

		if(session == null || session.getAttribute("currentUser") == null) {
			// セッション未確立 or ログインユーザ情報がなければログインページへ
			((HttpServletResponse)response).sendRedirect("/todo-sample/index.jsp");
		} else {
			// ログイン後はリクエストどおりに
			chain.doFilter(request, response);
		}
	}
}
