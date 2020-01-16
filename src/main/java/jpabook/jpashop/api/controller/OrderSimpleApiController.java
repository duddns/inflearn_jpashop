package jpabook.jpashop.api.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.simplequery.OrderSimpleQueryDto;
import jpabook.jpashop.repository.simplequery.OrderSimpleQueryRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

  private final OrderRepository orderRepository;
  private final OrderSimpleQueryRepository orderSimpleQueryRepository;
  
  
  @GetMapping("/api/v1/simple-orders")
  public List<Order> findOrdersV1() {
    List<Order> all = orderRepository.findAll(new OrderSearch());
    for (Order order : all) {
      order.getMember().getName(); // Lazy 강제 초기화
      order.getDelivery().getAddress(); // Lazy 강제 초기화
    }
    return all;
  }
  
  @GetMapping("/api/v2/simple-orders")
  public List<SimpleOrderDto> findOrdersV2() {
    List<Order> orders = orderRepository.findAll(new OrderSearch());
    
    // 1 + N 문제 발생!
    List<SimpleOrderDto> result = orders.stream()
        .map(o -> new SimpleOrderDto(o))
        .collect(Collectors.toList());
    
    return result;
  }
  
  @GetMapping("/api/v3/simple-orders")
  public List<SimpleOrderDto> findOrdersV3() {
    List<Order> orders = orderRepository.findAllWithMemberDelivery();
    
    List<SimpleOrderDto> result = orders.stream()
        .map(o -> new SimpleOrderDto(o))
        .collect(Collectors.toList());
    
    return result;
  }
  
  @GetMapping("/api/v4/simple-orders")
  public List<OrderSimpleQueryDto> findOrdersV4() {
    return orderSimpleQueryRepository.findOrderDtos();
  }
  
  @Data
  @AllArgsConstructor
  static class SimpleOrderDto {
    
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    
    
    public SimpleOrderDto(Order order) {
      this.orderId = order.getId();
      this.name = order.getMember().getName(); // Lazy 초기화
      this.orderDate = order.getOrderDate();
      this.orderStatus = order.getStatus();
      this.address = order.getDelivery().getAddress(); // Lazy 초기화
    }
  }
}
