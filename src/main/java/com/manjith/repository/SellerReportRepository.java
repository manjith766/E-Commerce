package com.manjith.repository;

import com.manjith.entity.SellerReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerReportRepository extends JpaRepository<SellerReport,Long> {
    SellerReport findSellerId(Long sellerId);
}
