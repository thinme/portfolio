package com.example.portfolio.repository.account;

import com.example.portfolio.entity.account.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {




    @Query(value = "select m from Member m where m.email = :email_1 or m.id = :id_1")
    Member findMemberByEmailOrId(String email_1, String id_1);

    //(ID는 중복가능한 구조에서)Id값을 매개변수로 넣고, 아이디 생성날짜가 가장 최신인 것
    @Query(value = "select m from Member m where m.id = :id_1 order by m.createDate DESC")
    Member findFirstById(String id_1);

    @Query(value = "SELECT m FROM Member m JOIN fetch m.boardList WHERE m.id = :memberId")
    List<Member> findAllByMemberIdEqualsBoardWriter(String memberId);



}