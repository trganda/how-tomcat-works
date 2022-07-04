package com.trganda;

import com.trganda.servlet.Request;
import com.trganda.servlet.Response;

import javax.servlet.Servlet;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

public class ServletProcessor {

    public void process(Request request, Response response) {
        String uri = request.getUri();
        String servletName = uri.substring(uri.lastIndexOf("/") + 1);

        try {
            URL[] urls = new URL[1];
            URLStreamHandler streamHandler = null;
            File classPath = new File(HttpServer.WEB_ROOT);
            String repository =
                    (new URL("file", null, classPath.getCanonicalPath() +
                            File.separator)).toString();
            urls[0] = new URL(null, repository, streamHandler);

            URLClassLoader loader = new URLClassLoader(urls);

            Class tServlet = loader.loadClass(servletName);
            Servlet servlet = (Servlet) tServlet.newInstance();
            servlet.service(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
