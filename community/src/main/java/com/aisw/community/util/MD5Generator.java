package com.aisw.community.util;

import lombok.ToString;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@ToString
public class MD5Generator {

    private String fileName;

    public MD5Generator(String originFileName) throws UnsupportedEncodingException, NoSuchAlgorithmException{
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        md5.update(originFileName.getBytes(StandardCharsets.UTF_8));

        byte[] md5Hash = md5.digest();
        StringBuilder hexMD5Hash = new StringBuilder();
        for(byte b : md5Hash){
            String hexString = String.format("%02x", b);
            hexMD5Hash.append(hexString);
        }

        fileName = hexMD5Hash.toString();
    }
}
