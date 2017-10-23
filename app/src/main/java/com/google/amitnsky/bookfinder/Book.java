package com.google.amitnsky.bookfinder;

/**
 * Created by amitnsky on 19-10-2017.
 */

public class Book {
    String mTitle;
    String mAuthor;
    float mRating;
    int mRatingCount;
    String mInfoLink;

    public Book(String mTitle, String mAuthor, float mRating, int mRatingCount, String mInfoLink) {
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mRating = mRating;
        this.mRatingCount = mRatingCount;
        this.mInfoLink = mInfoLink;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public float getRating() {
        return mRating;
    }

    public int getRatingCount() {
        return mRatingCount;
    }

    public String getInfoLink() {
        return mInfoLink;
    }


}
