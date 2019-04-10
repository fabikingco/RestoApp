package com.wposs.buc.restpapp.Activitys.Adapters;

public class ListItems {

    private String mTextView1;

    private String mTextView2;

    private int mImageResourceId;

    public ListItems(String mTextView1, String mTextView2, int mImageResourceId) {
        this.mTextView1 = mTextView1;
        this.mTextView2 = mTextView2;
        this.mImageResourceId = mImageResourceId;
    }

    public String getTextView1() {
        return mTextView1;
    }

    public String getTextView2() {
        return mTextView2;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }
}
