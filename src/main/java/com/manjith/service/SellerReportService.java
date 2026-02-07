package com.manjith.service;

import com.manjith.entity.Seller;
import com.manjith.entity.SellerReport;

public interface SellerReportService {

    SellerReport getSellerReport(Seller seller);
    SellerReport updateSellerReport(SellerReport sellerReport);

}
