package com.example.springtx.propagation;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Log {
    @Id @GeneratedValue
    private Long id;
    private String message;

    public Log(String message) {
        this.message = message;
    }
}
