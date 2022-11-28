package com.mobileappdev.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;

public class ImageProcessing {
    public static HashMap<String, Bitmap> processSprites() {
        Bitmap original = BitmapFactory.decodeFile("@drawable/minesweeper");
        HashMap<String, Bitmap> sprites = new HashMap<String, Bitmap>();

        return sprites;
    }
}
