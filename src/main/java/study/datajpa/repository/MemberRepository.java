package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
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
     * 스프링 데이터 JPA 공식 문서 참고: (https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation)
     * > 참고: 이 기능은 엔티티의 필드명이 변경되면 인터페이스에 정의한 메서드 이름도 꼭 함께 변경해야 한다.
     * 그렇지 않으면 애플리케이션을 시작하는 시점에 오류가 발생한다.
     * > 이렇게 애플리케이션 로딩 시점에 오류를 인지할 수 있는 것이 스프링 데이터 JPA의 매우 큰 장점이다.
     */
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    /**
     * 실행할 메서드에 정적 쿼리를 직접 작성하므로 이름 없는 Named 쿼리라 할 수 있음
     * JPA Named 쿼리처럼 애플리케이션 실행 시점에 문법 오류를 발견할 수 있음(매우 큰 장점!)
     */
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();


    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) " +
            "from Member m join m.team t")
    List<MemberDto> findMemberDto();
}
