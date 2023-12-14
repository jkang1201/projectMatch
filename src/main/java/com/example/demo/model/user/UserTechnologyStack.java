package com.example.demo.model.user;

import com.example.demo.model.technology_stack.TechnologyStack;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_technology_stack")
public class UserTechnologyStack {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_technology_stack_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technology_stack_id")
    private TechnologyStack technologyStack;

    @Builder
    private UserTechnologyStack(User user, TechnologyStack technologyStack) {
        this.user = user;
        this.technologyStack = technologyStack;
    }
}
