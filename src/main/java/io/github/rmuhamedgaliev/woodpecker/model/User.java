package io.github.rmuhamedgaliev.woodpecker.model;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class User {
    @Id
    private Long id;
    private String name;
    private String youtrackToken;

    public User() {
    }

    public User(Long id, String name, String youtrackToken) {
        this.id = id;
        this.name = name;
        this.youtrackToken = youtrackToken;
    }
}
