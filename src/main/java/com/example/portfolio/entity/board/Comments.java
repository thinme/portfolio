package com.example.portfolio.entity.board;

import com.example.portfolio.entity.base.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Comments extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long seq;

    private String comments_content;

    @Transient
    private String board_title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_title" ,referencedColumnName = "title")
    private Board board;

    public Comments(String comments_content, Board board) {
        this.comments_content = comments_content;
        this.board = board;
    }
}
