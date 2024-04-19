package com.airbnb.service;

import com.airbnb.entity.Favourite;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface FavouriteService {
    public List<Favourite> getUserFavorites(PropertyUser user);

    public Favourite updateFavourite(boolean isFavourite, PropertyUser user, Property property);

}
