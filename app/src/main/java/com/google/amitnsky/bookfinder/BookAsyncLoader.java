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

    public BookAsyncLoader(Context context, String searchString) {
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

    //Handles a request to stop the Loader.
    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    /**
     * Handles a request to completely reset the Loader.
     */
    @Override
    protected void onReset() {
        super.onReset();
        // Ensure the loader is stopped
        onStopLoading();
        // At this point we can release the resources associated with 'apps' if needed.
        if (mBooks != null) {
            mBooks = null;
        }
    }
}
