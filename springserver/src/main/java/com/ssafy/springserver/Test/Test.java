package com.ssafy.springserver.Test;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(name = "test")

class Test {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int number;

    @Column
    private int test;

    @Builder
    Test(int test){
        this.test = test;
    }
}
