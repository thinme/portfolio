package com.example.portfolio.entity.board;

import com.example.portfolio.entity.account.Member;
import com.example.portfolio.entity.base.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board extends BaseTimeEntity implements Serializable {


    @Id
    @GeneratedValue
    private Long seq;

    @Column(length = 40, nullable = false, unique = true)
    private String title;

    @Column(length = 40, nullable = false, updatable = false)
    private String writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Member member;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<Comments> commentsList = new ArrayList<>();

    public void addComments(Comments comments){
        comments.setBoard(this);
        commentsList.add(comments);
    }

    @Setter
    @Column(nullable = false)
    @ColumnDefault("'no content'")
    private String content;

    @Setter
    @ColumnDefault("0")
    @Column(insertable = false, updatable = false)
    private Long cnt;
}
