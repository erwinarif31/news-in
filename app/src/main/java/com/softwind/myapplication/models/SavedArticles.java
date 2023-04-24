package com.softwind.myapplication.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SavedArticles implements Parcelable {
    private String title;
    private String link;
    private String content;
    private String pubDate;
    private String image_url;
    private String category;

    public SavedArticles() {/* Constructor */}

    public SavedArticles(String title, String link, String content, String pubDate, String image_url) {
        this.title = title;
        this.link = link;
        this.content = content;
        this.pubDate = pubDate;
        this.image_url = image_url;
    }

    public SavedArticles(String title, String link, String content, String pubDate, String image_url, String category) {
        this.title = title;
        this.link = link;
        this.content = content;
        this.pubDate = pubDate;
        this.image_url = image_url;
        this.category = category;
    }

    protected SavedArticles(Parcel in) {
        title = in.readString();
        link = in.readString();
        content = in.readString();
        pubDate = in.readString();
        image_url = in.readString();
        category = in.readString();
    }

    public static final Creator<SavedArticles> CREATOR = new Creator<SavedArticles>() {
        @Override
        public SavedArticles createFromParcel(Parcel in) {
            return new SavedArticles(in);
        }

        @Override
        public SavedArticles[] newArray(int size) {
            return new SavedArticles[size];
        }
    };

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

    public String getCategory() {
        return category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(link);
        parcel.writeString(content);
        parcel.writeString(pubDate);
        parcel.writeString(image_url);
        parcel.writeString(category);
    }

    public String getDateDiff() {
        String dateDiff;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date;
        try {
            date = dateFormat.parse(this.getPubDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        assert date != null;
        long diffInMillis = System.currentTimeMillis() - date.getTime();
        long diffInSeconds = diffInMillis / 1000;
        long diffInMinutes = diffInSeconds / 60;
        long diffInHours = diffInMinutes / 60;
        long diffInDays = diffInHours / 24;

        if (diffInDays > 0) {
            dateDiff = diffInDays + " day(s) ago";
        } else if (diffInHours > 0) {
            dateDiff = diffInHours + " hours ago";
        } else if (diffInMinutes > 0) {
            dateDiff = diffInMinutes + " minutes ago";
        } else {
            dateDiff = "just now";
        }

        return dateDiff;
    }
}
