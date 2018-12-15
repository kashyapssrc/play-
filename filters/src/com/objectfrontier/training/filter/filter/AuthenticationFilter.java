package com.objectfrontier.training.filter.filter;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.objectfrontier.training.filter.exception.AppException;
import com.objectfrontier.training.filter.exception.ExceptionCodes;

public class AuthenticationFilter implements Filter{

    @Override
    public void destroy() { }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        log("\n%s", "authentication filter begins");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);

        if(Objects.isNull(session)) {
            throw new AppException(ExceptionCodes.SESSION_INVALID);
        } else {
            chain.doFilter(request, response);
        }
        log("\n%s", "authentication filter ends");
    }

    @Override
    public void init(FilterConfig config) throws ServletException { }

    public static void log(String format, Object args) {
        System.out.format(format, args);
    }
}
