package com.manjith.repository;

import com.manjith.entity.Coupon;
import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository  extends JpaRepository<Coupon,Long> {

    Coupon findByCode(String code);

}
