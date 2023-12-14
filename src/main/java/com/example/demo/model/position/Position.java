package com.example.demo.model.position;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "position")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "position_id")
    private Long id;

    private String name;

    @Builder
    private Position(String name) {
        this.name = name;
    }
}
