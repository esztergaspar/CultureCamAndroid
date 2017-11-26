package com.culturecam.culturecam.app.system;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.culturecam.culturecam.R;
import com.culturecam.culturecam.app.gui.LoadViewActivity;
import com.culturecam.culturecam.app.gui.ResultViewActivity;
import com.culturecam.culturecam.entities.ImageDTO;
import com.culturecam.culturecam.entities.SearchEngines;
import com.culturecam.culturecam.imageDeliverySystem.PhotoLibraryService;
import com.culturecam.culturecam.imageDeliverySystem.PictureRequest;
import com.culturecam.culturecam.imageSearchSystem.IRSearchEngine;
import com.culturecam.culturecam.imageSearchSystem.SearchEngine;
import com.culturecam.culturecam.imageSearchSystem.SearchResultObserver;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class ImageSearchService implements SearchResultObserver {
    private static final String TAG = "ImageSearchService";
    private List<ImageDTO> searchResult;
    private Context context;
    private static final ImageSearchService imageSearchService = new ImageSearchService();
    public static final String EXTRA_IMAGE = "image_unicorn";
    public static final String EXTRA_RESULT_LIST = "image_list";
    private SearchEngines currentSearchEngine;
    private EnumMap<SearchEngines, SearchEngine> seList = new EnumMap<SearchEngines, SearchEngine>(SearchEngines.class);

    private ImageSearchService(){
        seList.put(SearchEngines.IR_SEARCH_ENGINE, new IRSearchEngine());
        for(SearchEngine se: seList.values()) {
            se.addSearchResultObserver(this);
        }
    }

    public static ImageSearchService getInstance(){
        return imageSearchService;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    @Override
    public void update(List<ImageDTO> searchResults) {
        Log.i(TAG, "Receive new search results SWAG");
        Log.d(TAG, "DEBUG: Mocking some images");
        searchResults = new ArrayList<>();
        ImageDTO image1 = new ImageDTO();
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.full_size_render);
        image1.setImage(((BitmapDrawable)drawable).getBitmap());
        image1.setRightsURL("www.informedy.com");
        image1.setSourceURL("www.abc.de");
        searchResults.add(image1);
        searchResults.add(image1);
        searchResults.add(image1);
        searchResults.add(image1);
        searchResults.add(image1);
        searchResults.add(image1);
        searchResults.add(image1);
        searchResults.add(image1);
        Log.d(TAG,"open result view");
        Intent intent = new Intent(context, ResultViewActivity.class);
        intent.putParcelableArrayListExtra(EXTRA_RESULT_LIST, new ArrayList<Parcelable>(searchResults));
        context.startActivity(intent);
    }

    public void searchImage (Bitmap image) {
        Log.d(TAG,"open load view");
        Intent intent = new Intent(context, LoadViewActivity.class);
        intent.putExtra(EXTRA_IMAGE, image);
        context.startActivity(intent);
        Log.d(TAG,"search for Image");
        seList.get(currentSearchEngine).searchImage(image);
    }

    public List<ImageDTO> getSearchResult() {
        Log.v(TAG, "getSearchResult");

        return searchResult;
    }

    public void setCurrentSearchEngine(SearchEngines searchEngine) {
        Log.v(TAG, "set current search engine: " + searchEngine.name());
        currentSearchEngine = searchEngine;
    }
}
