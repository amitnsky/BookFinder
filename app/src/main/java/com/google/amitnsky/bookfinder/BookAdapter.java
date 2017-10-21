package com.google.amitnsky.bookfinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by amitnsky on 21-10-2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {
    private TextView mTitleTV;
    private TextView mAuthorTV;
    private TextView mRatingTV;
    private TextView mRatingCountTV;

    public BookAdapter(Context context, int resource, ArrayList<Book> objects) {
        super(context, resource, objects);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView = convertView;
        //for first case it might be null
        if (rootView == null) {
            rootView = LayoutInflater.from(getContext()).
                    inflate(R.layout.list_item, parent, false);
        }
        //find corresponding views
        mTitleTV = (TextView) rootView.findViewById(R.id.title_text_view);
        mAuthorTV = (TextView) rootView.findViewById(R.id.author_text_view);
        mRatingTV = (TextView) rootView.findViewById(R.id.rating_text_view);
        mRatingCountTV = (TextView) rootView.findViewById(R.id.rating_count_text_view);
        //set corresponding views at root
        final Book currentBook = getItem(position);
        mTitleTV.setText(currentBook.getTitle());
        mAuthorTV.setText(currentBook.getAuthor());
        mRatingCountTV.setText(Integer.toString(currentBook.getRatingCount()));
        mRatingTV.setText(Float.toString(currentBook.getRating()));
        //return rootView finally
        return rootView;
    }
}
