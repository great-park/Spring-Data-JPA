package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    //컬렉션 파라미터 바인딩
    @Query("select m from Member m where m.username in :names")
    List<Member> findNyNames(@Param("names") List<String> name);

    //스프링 데이터 JPA는 유연한 반환 타입 지원
//    List<Member> findByUsername(String name); //컬렉션
//    Member findByUsername(String name); //단건
//    Optional<Member> findByUsername(String name); //단건 Optional

    /**
     * 조회 결과가 많거나 없으면?
     * 컬렉션
     * 결과 없음: 빈 컬렉션 반환
     * 단건 조회
     * 결과 없음: null 반환
     * 결과가 2건 이상: javax.persistence.NonUniqueResultException 예외 발생
     * > 참고: 단건으로 지정한 메서드를 호출하면 스프링 데이터 JPA는 내부에서 JPQL의
     * Query.getSingleResult() 메서드를 호출한다. 이 메서드를 호출했을 때 조회 결과가 없으면
     * javax.persistence.NoResultException 예외가 발생하는데 개발자 입장에서 다루기가 상당히
     * 불편하다. 스프링 데이터 JPA는 단건을 조회할 때 이 예외가 발생하면 예외를 무시하고 대신에 null 을
     * 반환한다.
     */


    // 페이징 - 반환 타입이 Page
    Page<Member> findByAge(int age, Pageable pageable);
}
