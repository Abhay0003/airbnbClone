package com.airbnb.service;

import com.airbnb.entity.Favourite;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.FavouriteRepository;
import com.airbnb.repository.PropertyRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class FavouriteServiceImpl implements FavouriteService {

    private FavouriteRepository favouriteRepository;
    private PropertyRepository propertyRepository;

    public FavouriteServiceImpl(FavouriteRepository favouriteRepository, PropertyRepository propertyRepository) {
        this.favouriteRepository = favouriteRepository;
        this.propertyRepository = propertyRepository;
    }


    @Override
    public List<Favourite> getUserFavorites(PropertyUser user) {
        return favouriteRepository.findByPropertyUser(user);
    }

    public Favourite updateFavourite(boolean isFavourite, PropertyUser user, Property property) {
        Favourite existingFavourite = favouriteRepository.findByPropertyUserAndProperty(user, property);
        existingFavourite.setIsFavourite(isFavourite);
        return favouriteRepository.save(existingFavourite);
    }
}
