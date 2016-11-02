package com.xiaxiao.bookmaid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    ImageView searchBook_img;
    ImageView addBook_img;
    EditText edit_et;
    ListView listview;
    Button have_btn;
    Button buy_btn;

    BookManager bookManager;
    BookAdapter bookAdapter;
    List books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        bookManager = new BookManager();
        books = bookManager.getBooks(0);
        bookAdapter = new BookAdapter(this, books, 0);
        listview.setAdapter(bookAdapter);
    }

    public void initViews() {
        searchBook_img = (ImageView) findViewById(R.id.search_img);
        addBook_img = (ImageView) findViewById(R.id.add_img);
        edit_et = (EditText) findViewById(R.id.edit_et);
        listview = (ListView) findViewById(R.id.listview);
        have_btn = (Button) findViewById(R.id.have_btn);
        buy_btn = (Button) findViewById(R.id.buy_btn);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id) {
            case R.id.search_img:
                break;
            case R.id.add_img:
                break;
            case R.id.have_btn:
                break;
            case R.id.buy_btn:
                break;
            default:
                break;
        }

    }
}
