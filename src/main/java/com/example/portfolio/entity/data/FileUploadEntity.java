package com.example.portfolio.entity.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadEntity {

    @Id
    @GeneratedValue
    private Long id;

    private  String uuid;
    private  String contentType;
    private  String name;
    private  String originalFilename;
    private  Long boardSeq;

}
