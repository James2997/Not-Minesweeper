package com.mobileappdev.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;

public class ImageProcessing {
    public static HashMap<String, Bitmap> processSprites(Bitmap original) {
        HashMap<String, Bitmap> sprites = new HashMap<String, Bitmap>();

        int height = original.getHeight()/4;
        int width = original.getWidth()/4;

        sprites.put("empty",    Bitmap.createBitmap(original, width * 0,0, width, height));
        sprites.put("one",      Bitmap.createBitmap(original, width * 1,0, width, height));
        sprites.put("two",      Bitmap.createBitmap(original, width * 2,0, width, height));
        sprites.put("three",    Bitmap.createBitmap(original, width * 3,0, width, height));
        sprites.put("four",     Bitmap.createBitmap(original, width * 0,height * 1, width, height));
        sprites.put("five",     Bitmap.createBitmap(original, width * 1,height * 1, width, height));
        sprites.put("six",      Bitmap.createBitmap(original, width * 2,height * 1, width, height));
        sprites.put("seven",    Bitmap.createBitmap(original, width * 3,height * 1, width, height));
        sprites.put("eight",    Bitmap.createBitmap(original, width * 0,height * 2, width, height));
        sprites.put("unpressed",Bitmap.createBitmap(original, width * 1,height * 2, width, height));
        sprites.put("bomb_hit", Bitmap.createBitmap(original, width * 2,height * 2, width, height));
        sprites.put("flag",     Bitmap.createBitmap(original, width * 3,height * 2, width, height));
        sprites.put("not_bomb", Bitmap.createBitmap(original, width * 0,height * 3, width, height));
        sprites.put("question", Bitmap.createBitmap(original, width * 1,height * 3, width, height));
        sprites.put("bomb",     Bitmap.createBitmap(original, width * 2,height * 3, width, height));

        return sprites;
    }
}