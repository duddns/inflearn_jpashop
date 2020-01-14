package jpabook.jpashop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {
  
  @Id
  @GeneratedValue
  @Column(name = "member_id")
  private Long id;
  
  private String name;
  
  @Embedded
  private Address address;
  
  @OneToMany(mappedBy = "member") // 나는 주인이 아니다. 읽기 전용이 되는 것이다.
  private List<Order> orders = new ArrayList<Order>();
}
