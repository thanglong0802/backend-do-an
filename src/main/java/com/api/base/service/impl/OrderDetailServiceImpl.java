package com.api.base.service.impl;

import com.api.base.domain.customer.CustomerCreateRequest;
import com.api.base.domain.customer.CustomerUpdateRequest;
import com.api.base.domain.orderdetail.OrderDetailCartRequest;
import com.api.base.domain.orderdetail.OrderDetailCreateRequest;
import com.api.base.domain.orderdetail.OrderDetailResponse;
import com.api.base.entity.Cart;
import com.api.base.entity.Customer;
import com.api.base.entity.OrderDetail;
import com.api.base.entity.Product;
import com.api.base.exception.BusinessException;
import com.api.base.repository.CartRepository;
import com.api.base.repository.CustomerRepository;
import com.api.base.repository.OrderDetailRepository;
import com.api.base.repository.ProductRepository;
import com.api.base.service.OrderDetailService;
import com.api.base.utils.Utilities;
import com.api.base.utils.enumerate.OrderStatus;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository, CartRepository cartRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<OrderDetailResponse> getAll() {
        List<OrderDetail> orderDetail = orderDetailRepository.findAll();
        return Utilities.copyProperties(orderDetail, OrderDetailResponse.class);
    }

    @Override
    public OrderDetailResponse insert(OrderDetailCreateRequest request) {
        OrderDetail orderDetail = Utilities.copyProperties(request, OrderDetail.class);
        orderDetail.setOrderDetailId(generateRandomString());
        orderDetail.setStatus(OrderStatus.CHO_DUYET.getValue());
        orderDetailRepository.save(orderDetail);
        return Utilities.copyProperties(orderDetail, OrderDetailResponse.class);
    }

    @Override
    public OrderDetailResponse insertNewCart(OrderDetailCartRequest request) {
        Cart cart = Utilities.copyProperties(request.getCartCreateRequest(), Cart.class);
        cartRepository.save(cart);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderDetailId(generateRandomString());
        orderDetail.setCartId(cart.getId());
        orderDetail.setTotalPrice(cart.getTotalPrice());
        orderDetail.setNameCustomer(request.getNameCustomer());
        orderDetail.setDateOrder(LocalDateTime.now());
        orderDetail.setAddress(request.getAddress());
        orderDetail.setPhoneNumber(request.getPhoneNumber());
        orderDetail.setStatus(OrderStatus.CHO_DUYET.getValue());
        orderDetailRepository.save(orderDetail);

        Product product = productRepository.findById(request.getCartCreateRequest().getProductId()).get();
        if (ObjectUtils.allNull(product)) throw new BusinessException("Product ID not found: " + product.getId());
        product.setQuantity(product.getQuantity() - request.getCartCreateRequest().getQuantityProduct());
        productRepository.save(product);

        if (product.getQuantity() == 0) {
            product.setStatus("Hết hàng");
            productRepository.save(product);
        }

        CustomerCreateRequest customerCreateRequest = new CustomerUpdateRequest();
        customerCreateRequest.setNameCustomer(request.getNameCustomer());
        customerCreateRequest.setAddress(request.getAddress());
        customerCreateRequest.setPhoneNumber(request.getPhoneNumber());
        Customer customer = Utilities.copyProperties(customerCreateRequest, Customer.class);
        customerRepository.save(customer);

        return Utilities.copyProperties(orderDetail, OrderDetailResponse.class);
    }

    private String generateRandomString() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(5);
        int length = characters.length();

        for (int i = 0; i < 5; i++) {
            int randomIndex = random.nextInt(length);
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}
