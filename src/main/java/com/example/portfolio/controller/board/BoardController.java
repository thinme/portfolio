package com.example.portfolio.controller.board;

import com.example.portfolio.entity.account.Member;
import com.example.portfolio.entity.board.Board;
import com.example.portfolio.entity.board.Comments;
import com.example.portfolio.entity.data.FileUploadEntity;
import com.example.portfolio.service.board.BoardService;
import lombok.extern.slf4j.Slf4j;
import oracle.security.crypto.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;



@Controller
@RequestMapping(path ="/board")
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @Autowired
    protected BoardController(BoardService boardService){
        this.boardService = boardService;
    }

    @GetMapping("/insertComments")
    public String insertComments(Board board, Model model){
        model.addAttribute("board",board);
        return "/board/insertComments";
    }

    @PostMapping("/insertComments")
    public String insertComments(@RequestParam("board_title")String boardTitle, Comments comments, Model model){
        boardService.insertComments(comments);
        return "redirect:/board/getBoardList";
    }

    @GetMapping("/getCommentsList")
    public String getCommentsList(Comments comments, Model model){
        List<Comments> checkCommentsList = boardService.getAllComments(comments);
        model.addAttribute("commentsList", checkCommentsList);
        return "/board/getCommentsList";
    }

    @GetMapping("/getBoardList")
    public String getBoardList(Model model, Board board){
        List<Board> boardList = boardService.getBoardList(board);
        model.addAttribute("boardList", boardList);
        return "/board/getBoardList";
    }

    //client에서 server로 이미지파일 전송(데이터 전송)
    //html form태그에 upload버튼으로 이미지 데이터 전송
    //-server는 이미지파일을 특정폴더에 저장
    //장점: 서버에 원본 이미지 파일을 저장하므로 필요할때 서버에서 바로 전달 받을 수 있음 =db에 부담감이줄어듬
    //단점: 다수의 서버에 이미지파일을 저장할 경우, 동일한 이미지 데이터 처리에대한 이슈발생= UUID를 통해 이미지 이름구분
    //-server는 이미지파일을 byte코드로 db저장
    //장점: 이미지데이터를 한곳 에 저장하고 관리
    //단점: DB에 많은 부하가 걸림, 데이터 저장 포맷의 한계.(oracle 기준으로 Blob 단위로 저장할 때 4gb한계에 이슈)

    //server에서 client로 이미지전송
    //springboot에서 URL주소를 통해 이미지를 받음(InputStream을 통해 파일을 http프로토콜에 전달하여 클라이언트에 전송)

    //@Slf4j Lombok라이브러리로 log데이터 찍음
    //info/error/debug 단위가 있고 단위마다 필터링하여 정보를 수집하고 관리 가능
    //multipartfile데이터를 수집하여 entity FileUploadEntity에 데이터저장
    //multipartfile file이 있을 때까지 작업 진행

    @GetMapping(value = "/image/{imagename}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> imgeLoading(@PathVariable("imagename")String imgname) throws IOException{
        // ResponseEntity<byte[]> 메서드리턴타입으로 이미지데이터를 송신하기 위한 객체(바이트 배열)
        // throws IOException 스트림방식으로 데이터를 전송할 때 도중에 오류가 날 경우를 찾기위해서 선언한 exception
        String path = "C:\\\\박혁진\\\\portfolio\\\\src\\\\main\\\\resources\\\\static\\\\img\\\\"+imgname;
        //File을 컴퓨터가 이해하기 위해서 Stream 배열을 만들어서 작업
        //객체(데이터 저장) String, int, double
        //Stream객체는 파일을 컴퓨터가 cpu에서 바로 읽어 들일 수 잇도록 하는 객체
        FileInputStream fis = new FileInputStream(path);
        //Buffered CPU에서 데이터를 읽어 올 때 메모리와 캐시사이에서 CPU와의 속도 차이를 줄이기 위한 중간 저장 위치
        BufferedInputStream bis = new BufferedInputStream(fis);
        //byte배열로 전환하여 ResponseEntity를 통해 클라이언트에게 데이터 전달
        //HTTP프로토콜은 바이트 단위(배열)로 데이터를 주고 받음
        byte[] imgByteArr = bis.readAllBytes();
        //ResponseEntity를 통해 http프로토콜로 클라이언트에게 데이터 전송

        //http프로토콜은 바이트배열로 데이터를 주고 받기 때문에 stream이나 버퍼를 통해 전환
        return  new ResponseEntity<byte[]>(imgByteArr, HttpStatus.OK);

    }


    @GetMapping("/insertBoard")
    public String insertBoard(){
        return "/board/insertBoard";
    }

    @PostMapping("/insertBoard")
    public String insertBoard(Board board, @Nullable@RequestParam("uploadfile")MultipartFile[] uploadfile) {
        //@Nullable@RequestParam("uploadfile")MultipartFile[] :
        //MultipartFile를 클라이언트에서 받아오고, 데이터가 없더라도 허용 (@Nullable)
        try {
            //boardService.insertBoard 메서드에서는 DB에 데이터를 저장하고 저장된 board_seq를 리턴 받음
            Long board_seq = boardService.insertBoard(board);

            List<FileUploadEntity> list = new ArrayList<>();
            for(MultipartFile file : uploadfile) {
                //MultipartFile로 클라이언트에서 온 데이터가 무결성 조건에 성립을 안하거나 메타데이터가 없거나 문제가 생길 여지를 if문으로 처리
                if(!file.isEmpty()) {
                    FileUploadEntity entity = new FileUploadEntity(null,
                            UUID.randomUUID().toString(),
                            file.getContentType(),
                            file.getName(),
                            file.getOriginalFilename(),
                            board_seq
                    );
                    //fileuploadtable에 데이터 저장
                    boardService.insertFileUploadEntity(entity);

                    list.add(entity);
                    File newFileName = new File("C:\\\\박혁진\\\\portfolio\\\\src\\\\main\\\\resources\\\\static\\\\img\\\\"+entity.getUuid()+"_"+entity.getOriginalFilename());
                    //서버에 이미지 파일 업로드(저장)
                    file.transferTo(newFileName);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }


        return "redirect:/board/getBoardList";
    }

    @GetMapping("/getBoard")
    public String getBoard(Board board, Model model) {

        FileUploadEntity fileUploadEntity = boardService.getFileUploadEntity2(board.getSeq());
        String path = "/board/image/"+fileUploadEntity.getUuid()+"_"+fileUploadEntity.getOriginalFilename();

        model.addAttribute("board", boardService.getBoard(board));
//        model.addAttribute("boardPrv", boardService.getPagesSortIndex(board));
        model.addAttribute("imgLoading", path);
//        model.addAttribute("imgLoading", path+"/filer");
        return "/board/getBoard";
    }

    @PostMapping("/updateBoard")
    public String updateBoard(Board board){
        boardService.updateBoard(board);
        return "redirect:/board/getBoard?seq="+board.getSeq();
    }

    @GetMapping("/updateBoard")
    public String updateBoardView(Board board, Model model){
        model.addAttribute("board", boardService.getBoard(board));
        return "/board/insertBoard";
    }

    @GetMapping("/deleteBoard")
    public String deleteBoard(Board board){
        boardService.deleteBoard(board);
        return "redirect:/board/getBoardList";
    }

    @GetMapping("/viewUserwriteBoard")
    public String viewUserWriteBoard(Member member, Model model){
        model.addAttribute("boardList",
                boardService.getBoardListAllBoardListByMemberId(member));
        return "/board/getboardList";
    }

    @GetMapping("/getAllUserBoardList")
    public String AllUsersBoard(Model model){
        return "index";
    }

}
