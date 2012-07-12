package com.g4share.common.download;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * User: gm
 */
public class UrlDownloader {
    private String url;

    public UrlDownloader(String url) {
        this.url = url;
    }

    //http://jexp.ru/index.php/Java_Tutorial/Network/SocketChannel
    public byte[] downLoad() throws IOException {
        URL streamUrl = new URL(url);
        InputStream stream =  streamUrl.openStream();
        List<byte[]> data = new ArrayList<>();

        int length;
        int totalSize = 0;
        int buffSize = 1024;

        while (true){
            byte[] buffer = new byte[buffSize];
            length = stream.read(buffer);

            //break if no more data
            if (length == -1)   break;

            totalSize += length;
            if (length != buffSize){
                //trim array
                byte[] lastBuff = new byte[length];
                System.arraycopy(buffer, 0, lastBuff, 0, length);
                buffer = lastBuff;
            }

            data.add(buffer);
        }

        //copy to byte array
        int pos = 0;
        byte[] dataArray = new byte[totalSize];
        while (!data.isEmpty()) {
            byte[] buffer = data.get(0);

            System.arraycopy(buffer, 0, dataArray, pos, buffer.length);
            pos += buffer.length;

            data.remove(0);
        }

        return dataArray;
    }
}
