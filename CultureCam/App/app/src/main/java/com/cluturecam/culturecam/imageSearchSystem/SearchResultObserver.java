package com.cluturecam.culturecam.imageSearchSystem;


import com.cluturecam.culturecam.entities.ImageDTO;

import java.util.List;

public interface SearchResultObserver {
    void update (List<ImageDTO> searchResults);
}
