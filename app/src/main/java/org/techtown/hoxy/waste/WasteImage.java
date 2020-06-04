package org.techtown.hoxy.waste;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class WasteImage {

    public static ArrayList<Bitmap> bitmaps = new ArrayList<>();

   /* public WasteImage(Bitmap bitmap) {
        bitmaps = new ArrayList<>();
        bitmaps.add(bitmap);
    }*/

    public static ArrayList<Bitmap> getBitmaps() {
        return bitmaps;
    }

    public static void setBitmaps(Bitmap bitmap) {
        WasteImage.bitmaps.add(bitmap);
    }
}
