package com.example.portfolio.service.board;

import com.example.portfolio.entity.account.Member;
import com.example.portfolio.entity.board.Board;
import com.example.portfolio.entity.board.Comments;
import com.example.portfolio.entity.data.FileUploadEntity;
import com.example.portfolio.repository.board.BoardRepository;
import com.example.portfolio.repository.board.CommentsRepository;
import com.example.portfolio.repository.board.FileUploadinfoRepository;
import com.example.portfolio.repository.customRepository.CustomDtoExampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepo;
    private final CommentsRepository commentsRepository;

    private final CustomDtoExampleRepository customDtoExampleRepository;

    private final FileUploadinfoRepository fileUploadinfoRepository;


    //순환참조 중단
    @Autowired
    protected BoardServiceImpl(BoardRepository boardRepo,
                               CommentsRepository commentsRepository,
                               CustomDtoExampleRepository customDtoExampleRepository,
                               FileUploadinfoRepository fileUploadinfoRepository

    ) {
        this.commentsRepository = commentsRepository;
        this.boardRepo = boardRepo;
        this.customDtoExampleRepository = customDtoExampleRepository;
        this.fileUploadinfoRepository = fileUploadinfoRepository;
    }
    @Override
    public List<Board> getBoardList(Board board) {
        return (List<Board>) boardRepo.findAll();
    }

    @Override
    public Long insertBoard(Board board) {
        return boardRepo.save(board).getSeq();
    }

    @Override
    public Board getBoard(Board board) {
        return boardRepo.findById(board.getSeq()).get();
    }

    @Override
    public void updateBoard(Board board) {
        Board findBoard = boardRepo.findById(board.getSeq()).get();
        findBoard.setTitle(board.getTitle());
        findBoard.setContent(board.getContent());
        boardRepo.save(findBoard);
    }

    @Override
    public void deleteBoard(Board board) {
        boardRepo.deleteById(board.getSeq());
    }

    @Override
    public void insertComments(Comments comments) {
        commentsRepository.save(comments);

    }

    @Override
    public List<Board> getBoardListAllBoardListByMemberId(Member member) {
        return boardRepo.findAllByMemberIdEqualsBoardWriter(member.getId());
    }

    @Override
    public List<Board> getBoardListByMemberId(Member member) {
        return boardRepo.findAllByMemberIdEqualsBoardWriter(member.getId());
    }

    @Override
    public List<Comments> getAllComments(Comments comments) {
        List<Comments> checktest = commentsRepository.findAll();
        System.out.println(checktest.size());
        for(int i =0; i<checktest.size(); i++) {
            System.out.println("-----init for-------");
            checktest.get(i).getComments_content();
        }
        return checktest;
    }

//    @Override
//    public ist<Comments> getAllComments(Comments comments) {
//        List<Comments> checktest = commentsRepository.findAll();
//        System.out.println(checktest.size());
//        for(int i =0; i<checktest.size(); i++) {
//            System.out.println("-----init for-------");
//            checktest.get(i).getComments_content();
//        }
//        return checktest;
//    }

    @Override
    public List<List<Object>> getBoardAndMemberUsersBoard() {

        return boardRepo.findAllByBoardAndMember();
    }

    @Override
    public void insertFileUploadEntity(FileUploadEntity fileUploadEntity) {
        fileUploadinfoRepository.save(fileUploadEntity);
    }

    @Override
    public FileUploadEntity getFileUploadEntity2(Long board_seq) {
        return fileUploadinfoRepository.findByBoardSeq(board_seq);
    }
}
