package com.culturecam.culturecam.entities;

import com.culturecam.culturecam.imageSearchSystem.IRSearchEngine;
import com.culturecam.culturecam.imageSearchSystem.ImageSearchEngine;
import com.culturecam.culturecam.imageSearchSystem.LireSearchEngine;

public enum SearchEngines {
    IR_SEARCH_ENGINE(new IRSearchEngine()), LIRE_SOLR_ENGINE(new LireSearchEngine());

    private final ImageSearchEngine searchEngine;

    SearchEngines(ImageSearchEngine searchEngine) {
        this.searchEngine = searchEngine;
    }

    public ImageSearchEngine getSearchEngine() {
        return searchEngine;
    }
}
