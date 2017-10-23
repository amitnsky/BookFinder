package com.google.amitnsky.bookfinder;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

/**
 * Created by amitnsky on 23-10-2017.
 */

public class BookAsyncLoader extends AsyncTaskLoader<ArrayList<Book>> {
    private ArrayList<Book> mBooks;

    public BookAsyncLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        if (mBooks == null)
            forceLoad();
    }

    @Override
    public ArrayList<Book> loadInBackground() {
        QueryUtils queryUtils = new QueryUtils();
        ArrayList<Book> mBooks = queryUtils.extractInformation("Five Point Someone");
        return mBooks;
    }
}
