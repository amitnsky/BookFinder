package com.google.amitnsky.bookfinder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class BookList extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Book>> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        String searched = getIntent().getCharSequenceExtra(SearchActivity.QUERY_KEY).toString();
        Bundle bundle = new Bundle(1);
        bundle.putString(SearchActivity.QUERY_KEY, searched);
        getSupportLoaderManager().initLoader(0, bundle, this).forceLoad();
    }

    private void updateUI(final ArrayList<Book> books) {
        ListView listView = (ListView) findViewById(R.id.book_list_view);
        BookAdapter adapter = new BookAdapter(getApplicationContext(), R.layout.list_item, books);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(books.get(position).getInfoLink()));
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
        if (data != null)
            updateUI(data);
    }

    //onLoaderreset set data field that is Books ArrayList null.
    @Override
    public void onLoaderReset(Loader<ArrayList<Book>> loader) {
    }
}
