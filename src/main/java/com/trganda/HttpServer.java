package com.trganda;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Objects;

public class HttpServer {

    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";
    public static final String SHUTDOWN = "/SHUTDOWN";

    private boolean shutdown = false;

    public void await() throws IOException {
        int port = 8080;

        ServerSocket socketServer = new ServerSocket(port, 0, InetAddress.getByName("127.0.0.1"));

        while(!shutdown) {
            Socket conn = socketServer.accept();
            InputStream in = conn.getInputStream();
            OutputStream out = conn.getOutputStream();

            Request request = new Request(in);
            request.parse();
            String uri = request.getUri();

            Response response = new Response(out);
            response.setRequest(request);
            response.sendStaticResource();

            if (Objects.equals(uri, SHUTDOWN)) {
                shutdown = true;
            }
        }
    }

    public static void main(String[] args) throws IOException {

        HttpServer httpServer = new HttpServer();
        httpServer.await();
    }
}
