package com.trganda;

import com.trganda.servlet.Request;
import com.trganda.servlet.Response;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";
    public static final String SHUTDOWN = "/SHUTDOWN";

    private boolean shutdown = false;

    public void await() {
        int port = 8080;

        ServerSocket socketServer = null;
        try {
            socketServer = new ServerSocket(port, 0, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        while(!shutdown) {
            try {
                Socket conn = socketServer.accept();
                InputStream in = conn.getInputStream();
                OutputStream out = conn.getOutputStream();

                com.trganda.servlet.Request request = new com.trganda.servlet.Request(in);
                request.parse();

                com.trganda.servlet.Response response = new com.trganda.servlet.Response(out);
                response.setRequest(request);

                // check if this is a request for a servlet or
                // a static resource
                if (request.getUri().startsWith("/servlet/")) {
                    ServletProcessor servletProcessor = new ServletProcessor();
                    servletProcessor.process(request, response);
                } else {
                    StaticResourceProcessor staticResourceProcessor = new StaticResourceProcessor();
                    staticResourceProcessor.process(request, response);
                }

                conn.close();
                shutdown = request.getUri().equals(SHUTDOWN);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    public static void main(String[] args) {

        HttpServer httpServer = new HttpServer();
        httpServer.await();
    }
}
