package com.softwind.myapplication.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Article {
    private String title;
    private String link;
    private String[] keywords;
    private String[] creator;
    private String video_url;
    private String description;
    private String content;
    private String pubDate;
    private String image_url;
    private String source_id;
    private String[] category;
    private String[] country;
    private String language;

    public Article(String title, String link, String[] keywords, String[] creator, String video_url, String description, String content, String pubDate, String image_url, String source_id, String[] category, String[] country, String language) {
        this.title = title;
        this.link = link;
        this.keywords = keywords;
        this.creator = creator;
        this.video_url = video_url;
        this.description = description;
        this.content = content;
        this.pubDate = pubDate;
        this.image_url = image_url;
        this.source_id = source_id;
        this.category = category;
        this.country = country;
        this.language = language;
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

    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    public String[] getCreator() {
        return creator;
    }

    public void setCreator(String[] creator) {
        this.creator = creator;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getDateDiff() {
        String dateDiff;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = dateFormat.parse(this.getPubDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        long diffInMillis = System.currentTimeMillis() - date.getTime();
        long diffInSeconds = diffInMillis / 1000;
        long diffInMinutes = diffInSeconds / 60;
        long diffInHours = diffInMinutes / 60;
        long diffInDays = diffInHours / 24;

        if (diffInDays > 0) {
            dateDiff = diffInDays + " days ago";
        } else if (diffInHours > 0) {
            dateDiff = diffInHours + " hours ago";
        } else if (diffInMinutes > 0) {
            dateDiff = diffInMinutes + " minutes ago";
        } else {
            dateDiff = "just now";
        }

        return dateDiff;
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

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public String[] getCategory() {
        return category;
    }

    public void setCategory(String[] category) {
        this.category = category;
    }

    public String[] getCountry() {
        return country;
    }

    public void setCountry(String[] country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
