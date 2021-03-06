package jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
  
  private final ItemRepository itemRepository;
  
  
  @Transactional
  public void saveItem(Item item) {
    itemRepository.save(item);
  }
  
  /**
   * 영속성 컨텍스트가 자동 변경
   */
  @Transactional
  public void updateItem(Long itemId, String name, int price, int stockQuantity) {
    Item findItem = itemRepository.findOne(itemId);
    findItem.setPrice(price);
    findItem.setName(name);
    findItem.setStockQuantity(stockQuantity);
  }
  
  public List<Item> findAll() {
    return itemRepository.findAll();
  }
  
  public Item findOne(Long itemId) {
    return itemRepository.findOne(itemId);
  }
}
