package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.reopsitory.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


/**
 * 트랜잭션 - 파라미터 연동 , 풀을 고려한 종료
 */
@RequiredArgsConstructor
@Slf4j
public class MemberServiceV2 {
    private final DataSource dataSource;
    private final MemberRepositoryV2 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Connection con = dataSource.getConnection();
        try {
            con.setAutoCommit(false);
            //비즈니스 로직
            bizLogic(con, toId, fromId, money);
            con.commit(); //성공시 커밋
        } catch (Exception e) {
            con.rollback(); //실패시 롤백
            throw new IllegalStateException();
        } finally {
            if (con != null) {
                try {
                    release(con);
                } catch (Exception e) {
                    log.error("error", e);
                }
            }

        }


    }

    private void bizLogic(Connection con, String toId, String fromId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(fromId, con);
        Member toMember = memberRepository.findById(toId, con);

        memberRepository.update(con, fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(con, toId, toMember.getMoney() + money);
    }

    private void release(Connection con) throws SQLException {
        con.setAutoCommit(true);
        con.close();
    }

    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException();
        }
    }


}


