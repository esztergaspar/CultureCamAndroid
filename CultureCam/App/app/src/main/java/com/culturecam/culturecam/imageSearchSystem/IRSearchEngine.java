package com.culturecam.culturecam.imageSearchSystem;

import android.graphics.Bitmap;
import com.culturecam.culturecam.entities.ImageDTO;

import java.util.ArrayList;
import java.util.List;


public class IRSearchEngine extends SearchEngine {

    @Override
    public void searchImage(Bitmap image) {
        //TODO
        returnSearchresults(new ArrayList<ImageDTO>());
    }
}
