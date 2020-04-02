package com.example.snapchat.Utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Util {

    public InputStream StringToInputStream(String msg) throws IOException {
        return new ByteArrayInputStream(msg.getBytes());
    }
}
