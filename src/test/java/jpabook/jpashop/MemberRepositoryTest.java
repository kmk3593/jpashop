package jpabook.jpashop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

// @RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    // @Autowired
    // MemberRepository memberRepository;

    @Test
    @Transactional
    public void testMember() throws Exception {

        // given
        // Member member = new Member();
        // member.setUsername("memberA");

        // when
        // Long savedId = memberRepository.save(member);
        // Member findMember = memberRepository.find(savedId);

        // then
        // Assersions.assertThat(findMember.getId()).isEqualTo(member.getId());
        // Assersions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

    }

}
