package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.reopsitory.MemberRepository;
import hello.jdbc.reopsitory.ex.MemberRepositoryV4_2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import java.sql.DriverManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
class MemberServiceV4Test333 {

    @Autowired
    DataSource dataSource;

    @Value("spring.datasource.url")
    String str;




    @Test
    void find() {
        System.out.println(dataSource.getClass().getName());
        System.out.println(str);
    }

}