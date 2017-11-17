package com.culturecam.culturecam.imageSearchSystem;


import com.culturecam.culturecam.entities.ImageDTO;

import java.util.List;

public interface SearchResultObserver {
    void update (List<ImageDTO> searchResults);
}
