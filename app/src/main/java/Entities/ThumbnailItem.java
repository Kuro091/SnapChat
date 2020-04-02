package Entities;

import android.graphics.Bitmap;

import com.zomato.photofilters.imageprocessors.Filter;

public class ThumbnailItem extends com.zomato.photofilters.utils.ThumbnailItem {
    public String filterName;
    public Bitmap image;
    public Filter filter;

    public ThumbnailItem() {
        image = null;
        filter = new Filter();
    }
}