package com.google.amitnsky.bookfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {
public static final String QUERY_KEY = "QUERY_KEY";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final EditText search_keyword_view = (EditText) findViewById(R.id.search_text_view);
        final Button seach_button_view = (Button) findViewById(R.id.search_button_view);
        seach_button_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchKeyword = search_keyword_view.getText().toString();
                startLoadingBooks(searchKeyword);
            }
        });
    }
    private void startLoadingBooks(String toSearch){
        Intent intent = new Intent(SearchActivity.this,BookList.class);
        intent.putExtra(QUERY_KEY,toSearch);
        startActivity(intent);
    }
}
