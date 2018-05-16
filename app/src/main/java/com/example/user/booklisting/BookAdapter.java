package com.example.user.booklisting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

    /**
     * Constructs a new (@link BookAdapter)
     * @param context of the app
     * @param books is the list of books, which is the data source of the adapter
     */

    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    /**
     * Returns a new List item view that displays information about the book
     * in form of a cardView
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Check if there exists a list item view that we can reuse
        //Otherwise inflate a new list item layout
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.single_book, parent, false);
        }

        //Find the book at the given position in the list of the books
        Book currentBook = (Book) getItem(position);

        //Find the TextView with view ID title
        TextView bookTitle = (TextView) listItemView.findViewById(R.id.title);
        //Display the title of the book
        bookTitle.setText(currentBook.getmBookTitle());

        //Find the TextView with View ID author
        TextView bookAuthor = listItemView.findViewById(R.id.author);
        //Display the authors names
        bookAuthor.setText(formattedAuthor(currentBook.getmAuthors()));

        ImageView bookImage = listItemView.findViewById(R.id.image);
        Glide.with(getContext()).load(currentBook.getmImageURL())
                .into(bookImage);

        return listItemView;
    }

    private String formattedAuthor(ArrayList<String> authorsArray)
    {
        StringBuilder authors = new StringBuilder();
        if(authorsArray.size() == 1)
            authors.append(authorsArray.get(0));
        else {
            for (int i = 0; i < authorsArray.size()-1; i++){
                authors.append(authorsArray.get(i) + ", ");
            }
            authors.append(authorsArray.get(authorsArray.size() - 1));
        }
        return authors.toString();
    }
}
