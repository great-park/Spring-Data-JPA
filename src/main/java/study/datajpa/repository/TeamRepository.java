package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.List;

/**
 * 어플리케이션 로딩 시점에서
 *스프링 Data JPA 해당 인터페이스를 보고서 구현 class를 만들어서 주입함
 * //@Repository 애노테이션 생략 가능
 * 컴포넌트 스캔을 스프링 데이터 JPA가 자동으로 처리
 * JPA 예외를 스프링 예외로 변환하는 과정도 자동으로 처리
 */
public interface TeamRepository extends JpaRepository<Team, Long> {
}
