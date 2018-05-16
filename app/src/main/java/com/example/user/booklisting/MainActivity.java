package com.example.user.booklisting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton search_btn = findViewById(R.id.searchButton);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText search_keyword = findViewById(R.id.searchText);
                Intent booksList = new Intent(MainActivity.this, BooksView.class);
                booksList.putExtra("search_text", search_keyword.getText().toString());
                startActivity(booksList);
            }
        });
    }
}
