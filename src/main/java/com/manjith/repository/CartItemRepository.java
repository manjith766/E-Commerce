package com.manjith.repository;

import com.manjith.entity.Cart;
import com.manjith.entity.CartItem;
import com.manjith.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    CartItem findByCartAndProductAndSize(Cart cart, Product product,String size);
}
