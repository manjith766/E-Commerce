package com.manjith.controller;

import com.manjith.entity.*;
import com.manjith.exceptions.SellerException;
import com.manjith.responce.PaymentLinkResponse;
import com.manjith.service.*;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OderController {

    private final OrderService orderService;
    private final UserService userService;
    private final CartService cartService;
    private final SellerReportService sellerReportService;
    private final SellerService sellerService;

    @PostMapping
    public ResponseEntity<PaymentLinkResponse> createOrderHandler(
            @RequestBody Address shippingAddress,
            @RequestParam PaymentMethod paymentMethod,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.findUserCart(user);
        Set<Order> orders =
                orderService.createOrder(user, shippingAddress, cart);

//            PaymentOrder paymentOrder = paymentService.createOrder(user, orders);

        PaymentLinkResponse response = new PaymentLinkResponse();

//            if (paymentMethod.equals(PaymentMethod.RAZORPAY)) {
//
//                PaymentLink payment =
//                        paymentService.createRazorpayPaymentLink(
//                                user,
//                                paymentOrder.getAmount(),
//                                paymentOrder.getId()
//                        );
//
//                String paymentUrl = payment.get("short_url");
//                String paymentLinkId = payment.get("id");
//
//                response.setPayment_link_url(paymentUrl);
//                response.setPayment_link_id(paymentLinkId);
//
//                paymentOrder.setPaymentLinkId(paymentLinkId);
//                paymentOrderRepository.save(paymentOrder);
//
//            }
//            else {
//
//                String paymentUrl =
//                        paymentService.createStripePaymentLink(
//                                user,
//                                paymentOrder.getAmount(),
//                                paymentOrder.getId()
//                        );
//
//                response.setPayment_link_url(paymentUrl);
//            }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>>usersOrderHistoryHandler(@RequestHeader("Authorization")String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Order>orders= orderService.userOrderHistory(user.getId());
        return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(
            @PathVariable Long orderId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Order order = orderService.findOrderById(orderId);

        return ResponseEntity.ok(order);
    }

    @GetMapping("/item/{orderItemId}")
    public ResponseEntity<OrderItem> getOrderItemById(
            @PathVariable Long orderItemId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        OrderItem orderItem =
                orderService.getOrderItemById(orderItemId);

        return ResponseEntity.ok(orderItem);
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(
            @PathVariable Long orderId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Order order = orderService.cancelOrder(orderId, user);

        Seller seller = sellerService.getSellerById(order.getSellerId());

        SellerReport report = sellerReportService.getSellerReport(seller);

        report.setCanceledOrders(report.getCanceledOrders() + 1);
        report.setTotalRefunds(
                report.getTotalRefunds() + order.getTotalSellingPrice()
        );

        sellerReportService.updateSellerReport(report);

        return ResponseEntity.ok(order);
    }
}
