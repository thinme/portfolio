package com.example.portfolio.repository.board;

import com.example.portfolio.entity.data.FileUploadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

//FileUploadEntity를 저장하는 인터페이스 (JPA CrudRepository 적용)
public interface FileUploadinfoRepository extends JpaRepository<FileUploadEntity, Long> {

    //findBy 튜플을 찾겠다
    //boardSeq 컬럼에 데이터를 찾겠다
    FileUploadEntity findByBoardSeq(Long boardSeq);
}
