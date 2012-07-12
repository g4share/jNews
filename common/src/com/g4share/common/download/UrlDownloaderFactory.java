package com.g4share.common.download;

/**
 * User: gm
 */
public class UrlDownloaderFactory {
    public UrlDownloader Create(String url){
        return new UrlDownloader(url);
    }
}
