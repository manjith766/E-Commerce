package com.manjith.service.impl;

import com.manjith.entity.Cart;
import com.manjith.entity.Coupon;
import com.manjith.entity.User;
import com.manjith.repository.CartRepository;
import com.manjith.repository.CouponRepository;
import com.manjith.repository.UserRepository;
import com.manjith.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    @Override
    public Cart applyCoupon(String code, double orderValue, User user) throws Exception {

        Coupon coupon = couponRepository.findByCode(code);

        if (coupon == null)
            throw new Exception("Coupon not valid");

        if (!coupon.isActive())
            throw new Exception("Coupon is not active");

        if (LocalDate.now().isBefore(coupon.getValidityStartDate()) ||
                LocalDate.now().isAfter(coupon.getValidityEndDate()))
            throw new Exception("Coupon expired");

        if (orderValue < coupon.getMinimumOrderValue())
            throw new Exception("Minimum order value should be " + coupon.getMinimumOrderValue());

        if (user.getUsedCoupons().contains(coupon))
            throw new Exception("Coupon already used");

        Cart cart = cartRepository.findByUserId(user.getId());

        double discount =
                (cart.getTotalSellingPrice() * coupon.getDiscountPercentage()) / 100;

        cart.setTotalSellingPrice(cart.getTotalSellingPrice() - discount);
        cart.setCouponCode(code);

        user.getUsedCoupons().add(coupon);
        userRepository.save(user);

        return cartRepository.save(cart);
    }
    @Override
    public Cart removeCoupon(String code, User user) throws Exception {

        Coupon coupon = couponRepository.findByCode(code);

        if (coupon == null)
            throw new Exception("Coupon not found");

        Cart cart = cartRepository.findByUserId(user.getId());

        double discount =
                (cart.getTotalSellingPrice() * coupon.getDiscountPercentage()) / 100;

        cart.setTotalSellingPrice(cart.getTotalSellingPrice() + discount);
        cart.setCouponCode(null);

        return cartRepository.save(cart);
    }

    @Override
    public Coupon findCouponById(Long id) throws Exception {
        return couponRepository.findById(id)
                .orElseThrow(() -> new Exception("Coupon not found"));
    }

    @Override
    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Override
    public List<Coupon> findAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    public void deleteCoupon(Long id) throws Exception {
        findCouponById(id);
        couponRepository.deleteById(id);
    }
}
