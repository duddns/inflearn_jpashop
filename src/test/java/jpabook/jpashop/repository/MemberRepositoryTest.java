package jpabook.jpashop.repository;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("unit-test")
@Transactional
public class MemberRepositoryTest {
  
  @Autowired
  private MemberRepository memberRepository;
  
  
  @Test
  @Rollback(false) // 해당 옵션을 통해 롤백을 억제할 수 있다.
  public void test() throws Exception {
    // given
    Member member = new Member();
    member.setName("memberA");
    
    // when
    Long savedId = memberRepository.save(member);
    Member findMember = memberRepository.findOne(savedId);
    
    // then
    Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
    Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
    Assertions.assertThat(findMember).isEqualTo(member);
    
    // 위의 두 코드는 모두 true이다. 같은 영속성 컨텍스트에서 비교했기 때문이다.
    System.out.println("findMember == member : " + (findMember == member));
  }
}
