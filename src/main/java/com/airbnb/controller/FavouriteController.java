package com.airbnb.controller;

import com.airbnb.entity.Favourite;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.FavouriteRepository;
import com.airbnb.service.FavouriteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/favourite")
public class FavouriteController {

    private FavouriteRepository favouriteRepository;

    private FavouriteService favouriteService;

    public FavouriteController(FavouriteRepository favouriteRepository, FavouriteService favouriteService) {
        this.favouriteRepository = favouriteRepository;
        this.favouriteService = favouriteService;
    }

    @PostMapping
    public ResponseEntity<Favourite> addFavourite(

            @RequestBody Favourite favourite,
            @AuthenticationPrincipal PropertyUser user
            ){
        favourite.setPropertyUser(user);
        Favourite savedFavourite = favouriteRepository.save(favourite);
        return new ResponseEntity<>(savedFavourite, HttpStatus.CREATED);
    }

    @GetMapping("/userFavourites")
    public ResponseEntity<List<Favourite>> getUserFavourites(@AuthenticationPrincipal PropertyUser user){
        List<Favourite> userFavorites = favouriteService.getUserFavorites(user);
        return new ResponseEntity<>(userFavorites, HttpStatus.OK);
    }

    @PutMapping("/updateFavorites")
    public ResponseEntity<Favourite> updateFavourite(boolean isFavourite,
                                                     @AuthenticationPrincipal PropertyUser user,
                                                     Property property) {
        Favourite updatedFavourite = favouriteService.updateFavourite(isFavourite, user, property);
        return new ResponseEntity<>(updatedFavourite,HttpStatus.OK);
    }
}
