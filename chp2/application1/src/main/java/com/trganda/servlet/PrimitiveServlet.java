package com.trganda.servlet;

import javax.servlet.*;
import java.io.IOException;

public class PrimitiveServlet implements Servlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println(this.getClass().getName() + " init");
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        System.out.println(this.getClass().getName() + " start service");
    }

    @Override
    public String getServletInfo() {
        return this.getClass().getName();
    }

    @Override
    public void destroy() {
        System.out.println(this.getClass().getName() + " destroy");
    }
}
