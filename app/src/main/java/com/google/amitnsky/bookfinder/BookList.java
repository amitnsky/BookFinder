package com.google.amitnsky.bookfinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class BookList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        final ArrayList<Book> books = new ArrayList<>();
        books.add(new Book("Let Us C", "Yashwant Kanetkar", 4.3f, 45, "google.com"));
        ListView listView = (ListView) findViewById(R.id.book_list_view);
        BookAdapter adapter = new BookAdapter(getApplicationContext(), R.layout.list_item, books);
        listView.setAdapter(adapter);
    }
}
