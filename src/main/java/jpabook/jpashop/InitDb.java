package jpabook.jpashop;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InitDb {
  
  private final InitService initService;
  
  
  @PostConstruct
  public void init() {
    initService.dbInit1();
    initService.dbInit2();
  }
  
  
  @Component
  @Transactional
  @RequiredArgsConstructor
  static class InitService {
    
    private final EntityManager em;
    
    public void dbInit1() {
      Member member = createMember("memberA", "서울", "강남대로", "222-222");
      em.persist(member);
      
      Book book1 = createBook("JPA1 BOOK", 10000, 100);
      em.persist(book1);
      
      Book book2 = createBook("JPA2 BOOK", 20000, 100);
      em.persist(book2);
      
      OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
      OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);
      
      Delivery delivery = createDelivery(member);
      Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
      em.persist(order);
    }
    
    public void dbInit2() {
      Member member = createMember("memberB", "경기", "남양주", "111-111");
      em.persist(member);
      
      Book book1 = createBook("SPRING1 BOOK", 30000, 200);
      em.persist(book1);
      
      Book book2 = createBook("SPRING2 BOOK", 40000, 300);
      em.persist(book2);
      
      OrderItem orderItem1 = OrderItem.createOrderItem(book1, 30000, 3);
      OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);
      
      Delivery delivery = createDelivery(member);
      Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
      em.persist(order);
    }
    
    private Member createMember(String name, String city, String street, String zipcode) {
      Member member = new Member();
      member.setName(name);
      member.setAddress(new Address(city, street, zipcode));
      return member;
    }
    
    private Book createBook(String name, int price, int stockQuantity) {
      Book book1 = new Book();
      book1.setName(name);
      book1.setPrice(price);
      book1.setStockQuantity(stockQuantity);
      return book1;
    }
    
    private Delivery createDelivery(Member member) {
      Delivery delivery = new Delivery();
      delivery.setAddress(member.getAddress());
      return delivery;
    }
  }
}
