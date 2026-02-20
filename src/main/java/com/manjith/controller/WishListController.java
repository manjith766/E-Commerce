package com.manjith.controller;

import com.manjith.entity.Product;
import com.manjith.entity.User;
import com.manjith.entity.Wishlist;
import com.manjith.exceptions.ProductException;
import com.manjith.service.ProductService;
import com.manjith.service.UserService;
import com.manjith.service.WishListService;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishlist")
public class WishListController {

    private final WishListService wishListService;
    private final ProductService productService;
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<Wishlist>getWishListByUserId(
            @RequestHeader("Authorization")String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Wishlist wishlist = wishListService.getWishListByUserId(user);
        return ResponseEntity.ok(wishlist);
    }

    @PostMapping("/add-product/{productId}")
    public ResponseEntity<Wishlist>addProductToWishlist(
            @PathVariable Long productId,@RequestHeader("Authorization")String jwt) throws Exception {

        Product product = productService.findProductById(productId);
        User user = userService.findUserByJwtToken(jwt);
        Wishlist updateWishList = wishListService.addProductToWishList(user,product);
        return ResponseEntity.ok(updateWishList);
    }
}
