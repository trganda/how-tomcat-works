package com.trganda;

import java.io.IOException;
import java.io.InputStream;

public class Request {

    private String uri;
    private InputStream in;

    public Request(InputStream in) {
        this.in = in;
    }

    public void parse() {
        byte[] buffer = new byte[2048];
        StringBuffer stringBuffer = new StringBuffer();

        try {
            int cnt = in.read(buffer);
            if (cnt != -1) {
                for (int i = 0; i < cnt; i++) {
                    stringBuffer.append((char)buffer[i]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String request = stringBuffer.toString();
        int startIdx = request.indexOf(" ");
        int endIdx = -1;
        if (startIdx != -1) {
            endIdx = request.indexOf(" ", startIdx + 1);
            if (endIdx > startIdx) {
                uri = request.substring(startIdx + 1, endIdx);
            }
        }
    }

    public String getUri() {
        return uri;
    }
}
