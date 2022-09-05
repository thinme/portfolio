package com.example.portfolio.repository.board;

import com.example.portfolio.entity.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query(value = "SELECT b FROM Board b INNER JOIN Member m ON b.writer = m.id WHERE m.id = :memberId")
    List<Board> findAllByMemberIdEqualsBoardWriter(String memberId);

    @Query(value = "SELECT b FROM Board b JOIN b.member m")
    List<List<Object>> findAllByBoardAndMember();
}

