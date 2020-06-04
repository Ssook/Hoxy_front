package org.techtown.hoxy.waste;

import android.graphics.Bitmap;

public class Item  {
    Bitmap image;
    String title;



    int fee;

   Bitmap getImage() {
       return this.image;
   }
    String getTitle() {
        return this.title;
    }
    public int getFee() {
        return fee;
    }

    public Item( String title, int fee, Bitmap image) {
        this.image = image;
        this.title = title;
        this.fee = fee;
    }
}
