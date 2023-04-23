package com.softwind.myapplication.models;

public class SavedArticles {
    private String title;
    private String link;
    private String content;
    private String pubDate;
    private String image_url;

    public SavedArticles(){/* Constructor */};

    public SavedArticles(String title, String link, String content, String pubDate, String image_url) {
        this.title = title;
        this.link = link;
        this.content = content;
        this.pubDate = pubDate;
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
