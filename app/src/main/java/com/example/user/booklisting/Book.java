package com.example.user.booklisting;

import java.util.ArrayList;

/**
 * A Book object contains information related to a single book
 */

public class Book {

    /** Title of the book */
    private String mBookTitle;

    /** Name of the authors of the book */
    private ArrayList<String> mAuthors;

    /** Description of the book */
    private String mDescription;

    /** The image of the book */
    private String mImageURL;

    /**
     * Stores the price of the book
     */
    private String mPrice;

    /**
     * Creates a new Book object
     *
     * @param mBookTitle    is the title of the book.
     * @param mAuthors      are the names of authors of the book.
     * @param mDescription  is the description of the book.
     * @param mImageURL     is the book object's image Url.
     * @param mPrice        is the price of the book.
     */

    public Book(String mBookTitle, ArrayList<String> mAuthors, String mImageURL) {
        this.mBookTitle = mBookTitle;
        this.mAuthors = mAuthors;
        //this.mDescription = mDescription;
        this.mImageURL = mImageURL;
        //this.mPrice = mPrice;
    }

    /**
     * Returns the book title
     */
    public String getmBookTitle() { return mBookTitle; }
    /**
     *Returns the author name of the book
     */
    public ArrayList<String> getmAuthors() { return mAuthors; }
    /**
     *Returns the description of the book
     */
    public String getmDescription() { return mDescription; }
    /**
     *Returns the URL for the image of the book
     */
    public String getmImageURL() { return mImageURL; }
    /**
     * Returns the price of the book
     */
    public String getmPrice() { return mPrice; }
}
