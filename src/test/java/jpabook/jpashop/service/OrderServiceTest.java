package jpabook.jpashop.service;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.execption.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.service.OrderService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("unit-test")
@Transactional
public class OrderServiceTest {
  
  @Autowired
  private EntityManager em;
  @Autowired
  private OrderService orderService;
  @Autowired
  private OrderRepository orderRepository;
  
  
  @Test
  public void test_order() {
    // given
    Member member = createMember();
    
    Book book = createBook("JPABOOK", 10000, 2);
    
    int orderCount = 2;
    
    // when
    Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
    
    // then
    Order getOrder = orderRepository.findOne(orderId);
    
    Assertions.assertThat(OrderStatus.ORDER).isEqualTo(getOrder.getStatus()).withFailMessage("상품 주문시 상태는 ORDER");
    Assertions.assertThat(1).isEqualTo(getOrder.getOrderItems().size()).withFailMessage("주문한 상품 종류 수가 정확해야 한다.");
    Assertions.assertThat(10000 * orderCount).isEqualTo(getOrder.getTotalPrice()).withFailMessage("주문 가격은 가격 * 수량이다.");
    Assertions.assertThat(0).isEqualTo(book.getStockQuantity()).withFailMessage("주문한 상품 종류 수가 정확해야 한다.");
  }
  
  @Test
  public void test_cancelOrder() throws Exception {
    // given
    Member member = createMember();
    Book item = createBook("ㅁㅁ", 10000, 10);
    
    int orderCount = 2;
    Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
    
    // when
    orderService.cancelOrder(orderId);
    
    // then
    Order getOrder = orderRepository.findOne(orderId);
    
    Assertions.assertThat(OrderStatus.CANCEL).isEqualTo(getOrder.getStatus()).withFailMessage("주문 취소시 상태는 CANCEL");
    Assertions.assertThat(10).isEqualTo(item.getStockQuantity()).withFailMessage("주문이 취소된 상태는 그만큼 재고가 증가해야 한다.");
  }
  
  @Test(expected = NotEnoughStockException.class)
  public void test_order_NotEnoughStockException() throws Exception {
    // given
    Member member = createMember();
    Item item = createBook("시골 JPA", 10000, 10);
    int orderCount = 11;
    
    // when
    orderService.order(member.getId(), item.getId(), orderCount);
    
    // then
    Assertions.fail("재고 수량 부족 예외가 발생");
  }
  
  private Member createMember() {
    Member member = new Member();
    member.setName("회원 1");
    member.setAddress(new Address("서울", "강가", "123-123"));
    em.persist(member);
    return member;
  }
  
  private Book createBook(String jpa_book, int price, int stockQuantity) {
    Book book = new Book();
    book.setName(jpa_book);
    book.setPrice(price);
    book.setStockQuantity(stockQuantity);
    em.persist(book);
    return book;
  }
}