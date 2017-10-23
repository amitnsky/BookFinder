package com.google.amitnsky.bookfinder;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class BookList extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Book>> {
    private ListView mListView;
    private BookAdapter mAdapter;
    private TextView mLoadingStatusTextView;
    private ArrayList<Book> mBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        mListView = (ListView) findViewById(R.id.book_list_view);
        String searched = getIntent().getCharSequenceExtra(SearchActivity.QUERY_KEY).toString();
        Bundle bundle = new Bundle(1);
        bundle.putString(SearchActivity.QUERY_KEY, searched);
        //set progress bar as empty view while loading data
        mLoadingStatusTextView = (TextView) findViewById(R.id.loading_status_tv);
        if (isOnline()) {
            mListView.setEmptyView(findViewById(R.id.progress_layout));
            //init loader and forceLoad if device is online
            getSupportLoaderManager().initLoader(0, bundle, this).forceLoad();
        } else {
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar_view);
            progressBar.setVisibility(View.GONE);
            mLoadingStatusTextView.setText("No Internet Connection!!");
            mListView.setEmptyView(mLoadingStatusTextView);
        }
    }

    //helper method to check connectivity
    private boolean isOnline() {
        boolean isConnected;
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        //getActiveNetworkInfo returns null if u r not connected to any network
        if (networkInfo == null) {
            isConnected = false;
        } else {
            //isConnected should not be invoked on null object
            //so we used if else block
            isConnected = networkInfo.isConnected();
        }
        return isConnected;
    }

    /**
     * Helper method to update UI,set up adapter.
     *
     * @param mBooks arraylist
     */
    private void updateUI() {
        mAdapter = new BookAdapter(getApplicationContext(), R.layout.list_item, mBooks);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mBooks.get(position).getInfoLink()));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int id, Bundle args) {
        return new BookAsyncLoader(getApplicationContext(), args.getString(SearchActivity.QUERY_KEY));
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Book>> loader, ArrayList<Book> data) {
        if (mBooks == null) {
            mBooks = data;
            updateUI();
        }
    }

    //onLoaderreset set data field that is Books ArrayList null.
    @Override
    public void onLoaderReset(Loader<ArrayList<Book>> loader) {
        mBooks = null;
    }
}
