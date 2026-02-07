package com.manjith.service;

import com.manjith.entity.Cart;
import com.manjith.entity.CartItem;
import com.manjith.entity.Product;
import com.manjith.entity.User;


public interface CartService {

    public CartItem addCartItem(
            User user,
            Product product,
            String size,
            int quantity
            );
    public Cart findUserCart(User user);
}
