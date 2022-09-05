package com.example.portfolio.entity.account;

import com.example.portfolio.entity.base.BaseTimeEntity;
import com.example.portfolio.entity.board.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member extends BaseTimeEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long seq;

    @Column(length = 40, nullable = false, unique = true)
    private String id;

    @BatchSize(size=100)
    @OneToMany(mappedBy = "member")
    private List<Board> boardList = new ArrayList<>();

    private String password;

    private String email;
}
