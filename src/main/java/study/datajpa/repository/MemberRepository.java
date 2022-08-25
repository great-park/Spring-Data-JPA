package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.Member;

import java.util.List;

/**
 * 어플리케이션 로딩 시점에서
 *스프링 Data JPA 해당 인터페이스를 보고서 구현 class를 만들어서 주입함
 * //@Repository 애노테이션 생략 가능
 * 컴포넌트 스캔을 스프링 데이터 JPA가 자동으로 처리
 * JPA 예외를 스프링 예외로 변환하는 과정도 자동으로 처리
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * 스프링 데이터 JPA는 메소드 이름을 분석해서 JPQL을 생성하고 실행
     * 쿼리 메소드 필터 조건
     * 스프링 데이터 JPA 공식 문서 참고: (https://docs.spring.io/spring-data/jpa/docs/current/
     * reference/html/#jpa.query-methods.query-creation)
     * 스프링 데이터 JPA가 제공하는 쿼리 메소드 기능
     * 조회: find…By ,read…By ,query…By get…By,
     * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
     * #repositories.query-methods.query-creation
     * 예:) findHelloBy 처럼 ...에 식별하기 위한 내용(설명)이 들어가도 된다.
     * COUNT: count…By 반환타입 long
     * EXISTS: exists…By 반환타입 boolean
     * 삭제: delete…By, remove…By 반환타입 long
     * DISTINCT: findDistinct, findMemberDistinctBy
     * LIMIT: findFirst3, findFirst, findTop, findTop3
     * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
     * #repositories.limit-query-result
     * > 참고: 이 기능은 엔티티의 필드명이 변경되면 인터페이스에 정의한 메서드 이름도 꼭 함께 변경해야 한다.
     * 그렇지 않으면 애플리케이션을 시작하는 시점에 오류가 발생한다.
     * > 이렇게 애플리케이션 로딩 시점에 오류를 인지할 수 있는 것이 스프링 데이터 JPA의 매우 큰 장점이다.
     */
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);
}
