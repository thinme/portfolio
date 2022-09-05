package com.example.portfolio.repository.board;

import com.example.portfolio.entity.board.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {


    @Query(value = "SELECT c FROM Comments c JOIN fetch c.board")
    List<Comments> findCommentsByBoard_seq(String input_board_title);
}
