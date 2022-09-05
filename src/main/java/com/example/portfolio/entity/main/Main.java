package com.example.portfolio.entity.main;


import com.example.portfolio.entity.base.BaseTimeEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Main extends BaseTimeEntity implements Serializable {

    @Id
    private String title;
}
