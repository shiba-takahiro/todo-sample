package jp.co.miraino_katachi.todo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter("/*")
public class EncodingFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// リクエストパラメータの文字コードをUTF-8に設定
		request.setCharacterEncoding("utf-8");

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

}
