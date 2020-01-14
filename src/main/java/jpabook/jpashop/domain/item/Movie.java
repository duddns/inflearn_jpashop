package jpabook.jpashop.domain.item;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;

import jpabook.jpashop.domain.Item;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorColumn(name = "M")
@Getter
@Setter
public class Movie extends Item {

  private String director;
  private String actor;
}
