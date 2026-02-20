package com.manjith.service;

import com.manjith.entity.Product;
import com.manjith.entity.User;
import com.manjith.entity.Wishlist;

public interface WishListService {

    Wishlist createWishList(User user);
    Wishlist getWishListByUserId(User user);
    Wishlist addProductToWishList(User user, Product product);

}
