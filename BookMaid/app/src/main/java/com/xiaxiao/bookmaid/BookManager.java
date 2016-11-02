package com.xiaxiao.bookmaid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaxi on 2016/11/2.
 */
public class BookManager {
    public void add(Book book) {
        Util.L("add book");
    }

    public void delete(Book book) {
        Util.L("delete book");
    }

    public void update(Book book) {
        Util.L("update book");
    }

    public void query(Book book) {
        Util.L("query");
    }

    public List<Book> getBooks(int type) {
        List<Book> list = new ArrayList<>();
        for (int i=0;i<40;i++) {
            Book b = new Book("book  " + i);
            list.add(b);
        }
        return list;
    }
}
