package com.gfike.GrimoireMaven.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Monster {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=15)
    private String name;

    @NotNull
    private boolean biped;

    @NotNull
    private boolean quadped;

    @NotNull
    private boolean swim;

    @NotNull
    private boolean fly;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Column(name="count")
    private int count;

    public Monster(String name, boolean biped, boolean quadped, boolean swim, boolean fly) {
        this.name = name;
        this.biped =biped;
        this.quadped = quadped;
        this.swim = swim;
        this.fly = fly;
    }

    public Monster(){}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isBiped() {
        return biped;
    }

    public boolean isQuadped() {
        return quadped;
    }

    public boolean isSwim() {
        return swim;
    }

    public boolean isFly() {
        return fly;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBiped(boolean biped) {
        this.biped = biped;
    }

    public void setQuadped(boolean quadped) {
        this.quadped = quadped;
    }

    public void setSwim(boolean swim) {
        this.swim = swim;
    }

    public void setFly(boolean fly) {
        this.fly = fly;
    }
}
