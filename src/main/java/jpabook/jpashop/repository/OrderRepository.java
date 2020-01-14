package jpabook.jpashop.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
  
  private final EntityManager em;
  
  
  public void save(Order order) {
    em.persist(order);
  }
  
  public Order findOne(Long id) {
    return em.find(Order.class, id);
  }
  
  public List<Order> findAll(OrderSearch orderSearch) {
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<Order> queryBuilder = criteriaBuilder.createQuery(Order.class);
    Root<Order> o = queryBuilder.from(Order.class);
    Join<Object, Object> m = o.join("member", JoinType.INNER);
    
    // 주문 상태 검색
    List<Predicate> criteria = new ArrayList<>();
    if (null != orderSearch.getOrderStatus()) {
      Predicate status = criteriaBuilder.equal(o.get("status"), orderSearch.getOrderStatus());
      criteria.add(status);
    }
    
    // 회원 이름 검색
    if (StringUtils.hasText(orderSearch.getMemberName())) {
      Predicate name = criteriaBuilder.like(m.get("name"), "%" + orderSearch.getMemberName() + "%");
      criteria.add(name);
    }
    
    queryBuilder.where(criteriaBuilder.and(criteria.toArray(new Predicate[criteria.size()])));
    TypedQuery<Order> query = em.createQuery(queryBuilder).setMaxResults(1000);
    
    return query.getResultList();
  }
}
