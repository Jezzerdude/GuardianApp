package com.example.guardapp.model;

import androidx.room.Entity;

import com.example.guardapp.model.Article;

@Entity(tableName = "bookmarked_articles")
public class ArticleWithBody extends Article {

    private String body;
    private boolean isBookmarked;

    public ArticleWithBody(String id, String sectionName, long webPublicationDate, String webTitle, String webUrl, String apiUrl, String headline, String author, Integer wordCount, String thumbnail, Boolean isLive, String body) {
        super(id,
                sectionName,
                webPublicationDate,
                webTitle,
                webUrl,
                apiUrl,
                headline,
                author,
                wordCount,
                thumbnail,
                isLive);
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }
}
