package com.manjith.service.impl;

import com.manjith.entity.Product;
import com.manjith.entity.User;
import com.manjith.entity.Wishlist;
import com.manjith.repository.WishlistRepository;
import com.manjith.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {

    private final WishlistRepository wishlistRepository;

    @Override
    public Wishlist createWishList(User user) {
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        return wishlistRepository.save(wishlist);
    }

    @Override
    public Wishlist getWishListByUserId(User user) {
        Wishlist wishlist = wishlistRepository.findByUserId(user.getId());
        if (wishlist == null){
            wishlist=createWishList(user);
        }
        return wishlist;
    }

    @Override
    public Wishlist addProductToWishList(User user, Product product) {
        Wishlist wishlist = getWishListByUserId(user);

        if (wishlist.getProducts().contains(product)){
            wishlist.getProducts().remove(product);
        }else wishlist.getProducts().add(product);
        return wishlistRepository.save(wishlist);
    }
}
