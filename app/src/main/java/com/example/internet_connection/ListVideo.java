package com.example.internet_connection;

import java.net.URL;

public class ListVideo {
    String nameVideo;
    String urlVideo;

    public ListVideo(String nameVideo, String urlVideo) {
        this.nameVideo = nameVideo;
        this.urlVideo = urlVideo;
    }

    public String getNameVideo() {
        return nameVideo;
    }

    public void setNameVideo(String nameVideo) {
        this.nameVideo = nameVideo;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }
}
