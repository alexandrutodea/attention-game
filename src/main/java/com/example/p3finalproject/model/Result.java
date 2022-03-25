package com.example.p3finalproject.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Result implements Comparable<Result> {

    @Id @GeneratedValue
    private int id;
    @ManyToOne
    private Player player;
    private long score;
    LocalDateTime dateTime;

    public Result(Player player, long score) {
        this.player = player;
        this.score = score;
        this.dateTime = LocalDateTime.now();
    }

    public Result() {

    }

    public Player getUser() {
        return player;
    }

    public void setUser(Player player) {
        this.player = player;
    }

    public String getUsername() {
        return this.player.getUsername();
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getTime() {
        return dateTime.toString().split("\\.")[0].replace("T", " ");
    }

    @Override
    public int compareTo(Result other) {
        return Long.compare(this.score, other.score);
    }

    @Override
    public String toString() {
        return "Result{" +
               "user=" + player +
               ", score=" + score +
               '}';
    }
}
