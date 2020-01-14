package jpabook.jpashop.domain.item;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;

import jpabook.jpashop.domain.Item;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorColumn(name = "A")
@Getter
@Setter
public class Album extends Item {

  private String artiest;
  private String etc;
}
