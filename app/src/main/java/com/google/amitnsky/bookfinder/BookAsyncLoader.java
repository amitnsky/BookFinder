package com.google.amitnsky.bookfinder;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

/**
 * Created by amitnsky on 23-10-2017.
 */

public class BookAsyncLoader extends AsyncTaskLoader<ArrayList<Book>> {
    private ArrayList<Book> mBooks;
private String mSearchString;
    public BookAsyncLoader(Context context,String searchString) {
        super(context);
        mSearchString = searchString;
    }

    @Override
    protected void onStartLoading() {
        if (mBooks == null)
            forceLoad();
    }

    @Override
    public ArrayList<Book> loadInBackground() {
        QueryUtils queryUtils = new QueryUtils();
        ArrayList<Book> mBooks = queryUtils.extractInformation(mSearchString);
        return mBooks;
    }
}
