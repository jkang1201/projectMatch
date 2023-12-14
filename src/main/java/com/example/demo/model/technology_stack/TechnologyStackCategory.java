package com.example.demo.model.technology_stack;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "technology_stack_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TechnologyStackCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "technology_stack_category_id")
    private Long id;

    // 프론트엔드, 백엔드, 디자이너 등
    private String name;
}
