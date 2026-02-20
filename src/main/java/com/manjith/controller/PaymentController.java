package com.manjith.controller;

import com.manjith.entity.*;
import com.manjith.responce.ApiResponse;
import com.manjith.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

    @RestController
    @RequiredArgsConstructor
    @RequestMapping("/api/payment")
    public class PaymentController {

        private final PaymentService paymentService;
        private final UserService userService;
        private final SellerService sellerService;
        private final SellerReportService sellerReportService;
        private final OrderService orderService;
        private final TransactionService transactionService;


        @GetMapping("/{paymentId}")
        public ResponseEntity<ApiResponse> paymentSuccessHandler(
                @PathVariable String paymentId,
                @RequestParam String paymentLinkId,
                @RequestHeader("Authorization") String jwt
        ) throws Exception {

            // Validate user
            User user = userService.findUserByJwtToken(jwt);

            //  Get payment order
            PaymentOrder paymentOrder =
                    paymentService.getPaymentOrderByPaymentId(paymentLinkId);

            //  Proceed payment
            boolean paymentSuccess = paymentService.proceedPaymentOrder(
                    paymentOrder,
                    paymentId,
                    paymentLinkId
            );

            // If payment success → update seller report
            if (paymentSuccess) {

                for (Order order : paymentOrder.getOrders()) {

                    transactionService.createTransaction(order);

                    Seller seller = sellerService.getSellerById(order.getSellerId());
                    SellerReport report = sellerReportService.getSellerReport(seller);
                    report.setTotalOrders(report.getTotalOrders() + 1);
                    report.setTotalEarnings(report.getTotalEarnings() + order.getTotalSellingPrice());
                    report.setTotalSales(report.getTotalSales() + order.getOrderItems().size());
                    sellerReportService.updateSellerReport(report);
                }
            }

            //  Return response
            ApiResponse res = new ApiResponse();
            res.setMessage("Payment successful");

            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }
    }
