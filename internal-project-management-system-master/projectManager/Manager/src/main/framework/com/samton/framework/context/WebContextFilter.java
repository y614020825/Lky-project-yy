package com.samton.framework.context;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
/**
 * @author yangpeng
 * 当前request\response\放入本地线程
 */
@Component("webContextFilter")  
public class WebContextFilter implements Filter {  
  
    @Override
	public void init(FilterConfig filterConfig) throws ServletException {      
          
    }  
      
    @Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {  
        HttpServletRequest request = (HttpServletRequest) req;  
        HttpServletResponse response = (HttpServletResponse) resp;  
        //处理跨域请求filter
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Authentication");
        
        ServletContext servletContext = request.getSession().getServletContext();  
        WebContext.create(request, response, servletContext);  
        chain.doFilter(request, response);  
        WebContext.clear();  
    }  
  
    @Override  
    public void destroy() {  
          
    }  
  
}  