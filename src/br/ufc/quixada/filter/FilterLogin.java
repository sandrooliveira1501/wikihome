package br.ufc.quixada.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.ufc.quixada.model.Usuario;

/**
 * Servlet Filter implementation class FilterLogin
 */
@WebFilter("/*")
public class FilterLogin implements Filter {

    /**
     * Default constructor. 
     */
    public FilterLogin() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		req.setCharacterEncoding("UTF-8");
		HttpServletResponse resp = (HttpServletResponse) response;
		String uri = req.getRequestURI();
		HttpSession session = req.getSession(false);
		String contextPath = req.getContextPath();
		

		if (uri.contains("/adm/")) {
			if (session != null) {
				Usuario user = (Usuario) session.getAttribute("usuarioAdm");
				if (user == null) {
					resp.sendRedirect(contextPath + "/login_adm.xhtml");
				} else {
					chain.doFilter(request, response);
				}
			} else {
				resp.sendRedirect(contextPath + "/login_adm.xhtml");
			}
		} else if (uri.contains("/user/")) {
			if (session != null) {
				Usuario user = (Usuario) session.getAttribute("usuario");
				if (user == null) {
					resp.sendRedirect(contextPath + "/index.xhtml");
				} else {
					chain.doFilter(request, response);
				}
			} else {
				resp.sendRedirect(contextPath + "/index.xhtml");
			}
		} else {
			chain.doFilter(request, response);
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
