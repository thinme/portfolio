package com.example.portfolio.service.board;

import com.example.portfolio.entity.account.Member;
import com.example.portfolio.entity.board.Board;
import com.example.portfolio.entity.board.Comments;
import com.example.portfolio.entity.data.FileUploadEntity;

import java.util.List;

public interface BoardService {

    List<Board> getBoardList(Board board);

    Long insertBoard(Board board);

    Board getBoard(Board board);

    void updateBoard(Board board);

    void deleteBoard(Board board);

    void insertComments(Comments comments);

    List<Board> getBoardListAllBoardListByMemberId(Member member);

    List<Board> getBoardListByMemberId(Member member);



    List<Comments> getAllComments(Comments comments);

    List<List<Object>> getBoardAndMemberUsersBoard();

    void insertFileUploadEntity(FileUploadEntity fileUploadEntity);

    FileUploadEntity getFileUploadEntity2(Long board_seq);
}
