package jpabook.jpashop.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import jpabook.jpashop.domain.Item;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
  
  private final EntityManager em;
  
  
  public void save(Item item) {
    if (null == item.getId()) {
      em.persist(item);
    } else {
      em.merge(item);
    }
  }
  
  public Item findOne(Long id) {
    return em.find(Item.class, id);
  }
  
  public List<Item> findAll() {
    return em.createQuery("SELECT i FROM Item i", Item.class)
        .getResultList();
  }
}
