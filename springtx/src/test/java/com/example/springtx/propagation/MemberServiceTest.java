package com.example.springtx.propagation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    LogRepository logRepository;

    /**
     * memberService @Transactional : off
     * memberRepository @Transactional : on
     * logRepository @Transactional : on
     * @throws Exception
     */
    @Test
    public void outerTxOff_success() throws Exception{
        //given
        String username = "outerTxOff_success";
        //when
        memberService.joinV1(username);
        //then
        Assertions.assertTrue(memberRepository.find(username).isPresent());
        Assertions.assertTrue(logRepository.find(username).isPresent());
    }

    @Test
    public void outerTxOff_fail() throws Exception{
        //given
        String username = "로그예외_outerTxOff_success";
        //when
        memberService.joinV1(username);
        //then

        Assertions.assertTrue(memberRepository.find(username).isPresent());
        Assertions.assertTrue(logRepository.find(username).isEmpty());
    }


    /**
     * memberService @Transactional : ON
     * memberRepository @Transactional : OFF
     * logRepository @Transactional : OFF
     * @throws Exception
     */
    @Test
    public void singeTx() throws Exception{
        //given
        String username = "singeTx";
        //when
        memberService.joinV1(username);
        //then
        Assertions.assertTrue(memberRepository.find(username).isPresent());
        Assertions.assertTrue(logRepository.find(username).isPresent());
    }
    /**
     * memberService @Transactional : ON
     * memberRepository @Transactional : ON
     * logRepository @Transactional : ON
     * @throws Exception
     */
    @Test
    public void outer_tx_on_success() throws Exception{
        //given
        String username = "outer_tx_on_success";
        //when
        memberService.joinV1(username);

        Assertions.assertTrue(memberRepository.find(username).isPresent());
        Assertions.assertTrue(logRepository.find(username).isPresent());
    }

    /**
     * memberService @Transactional : ON
     * memberRepository @Transactional : ON
     * logRepository @Transactional : ON exception
     * @throws Exception
     */
    @Test
    public void outer_tx_on_fail() throws Exception{
        //given
        String username = "로그예외_outer_tx_on_fail";
        //when
        memberService.joinV1(username);

        //then 모든 데이터가 롤백된다.
        Assertions.assertTrue(memberRepository.find(username).isEmpty());
        Assertions.assertTrue(logRepository.find(username).isPresent());
    }

    /**
     * memberService @Transactional : ON
     * memberRepository @Transactional : ON
     * logRepository @Transactional : ON exception
     * @throws Exception
     */
    @Test
    public void recoverException_fail() throws Exception{
        //given
        String username = "로그예외_outer_tx_on_fail";
        //when

        assertThatThrownBy(() -> memberService.joinV2(username))
                .isInstanceOf(UnexpectedRollbackException.class);

        //then 모든 데이터가 롤백된다.
        Assertions.assertTrue(memberRepository.find(username).isEmpty());
        Assertions.assertTrue(logRepository.find(username).isEmpty());
    }

    /**
     * memberService @Transactional : ON
     * memberRepository @Transactional : ON
     * logRepository @Transactional : ON(REQUIRES_NEW) exception
     * @throws Exception
     */
    @Test
    public void recoverException_success() throws Exception{
        //given
        String username = "로그예외_recoverException_success";
        //when

        memberService.joinV2(username);


        //then 모든 데이터가 롤백된다.
        Assertions.assertTrue(memberRepository.find(username).isPresent());
        Assertions.assertTrue(logRepository.find(username).isEmpty());
    }


}