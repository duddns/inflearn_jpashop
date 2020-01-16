package jpabook.jpashop.repository.simplequery;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {
  
  private final EntityManager em;
  
  
  public List<OrderSimpleQueryDto> findOrderDtos() {
    return em.createQuery("SELECT new jpabook.jpashop.repository.simplequery.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address) "
        + " FROM Order o "
        + " JOIN o.member m "
        + " JOIN o.delivery d ", OrderSimpleQueryDto.class)
        .getResultList();
  }
}
