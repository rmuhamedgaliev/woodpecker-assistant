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

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYoutrackToken(String youtrackToken) {
        this.youtrackToken = youtrackToken;
    }

    //added Getters for user
    public Long getId() { return id; }

    public String getName() { return name; }

    public String getYoutrackToken() { return youtrackToken; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", youtrackToken='" + youtrackToken + '\'' +
                '}';
    }
}
