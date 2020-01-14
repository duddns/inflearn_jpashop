package jpabook.jpashop.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.service.MemberService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("unit-test")
@Transactional
public class MemberServiceTest {
  
  @Autowired
  private MemberService memberService;
  @Autowired
  private MemberRepository memberRepository;
  
  
  @Test
  public void test_join() throws Exception {
    // given
    Member member = new Member();
    member.setName("kim");
    
    // when
    Long savedId = memberService.join(member);
    
    // then
    Assertions.assertThat(member).isEqualTo(memberRepository.findOne(savedId));
  }
  
  @Test(expected = IllegalStateException.class)
  public void test_join_duplicate() throws Exception {
    // given
    Member member1 = new Member();
    member1.setName("kim1");
    Member member2 = new Member();
    member2.setName("kim1");
    
    // when
    memberService.join(member1);
    memberService.join(member2); // 예외 발생
    
    // then
    Assertions.fail("예외가 발생해야 한다.");
  }
}