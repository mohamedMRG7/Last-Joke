package com.example.moham.lastjoke.user;

import java.io.Serializable;

/**
 * Created by moham on 1/29/2018.
 */

public class UserJokes implements Serializable{

    private String username;
    private String email;
    private String userUniq_id;
    private String userIcon;
    private String joke;
    private String language;
    private int happy_num;
    private int sad_num;



    public UserJokes(String username, String email, String userUniq_id, String userIcon, String joke,  int happy_num, int sad_num) {
        this.username = username;
        this.email = email;
        this.userUniq_id = userUniq_id;
        this.userIcon = userIcon;
        this.joke = joke;
        this.happy_num = happy_num;
        this.sad_num = sad_num;

    }


    public UserJokes() {
    }

    public UserJokes(String displayName, String email) {
        this.username=displayName;
        this.email=email;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserUniq_id() {
        return userUniq_id;
    }

    public void setUserUniq_id(String userUniq_id) {
        this.userUniq_id = userUniq_id;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    public int getHappy_num() {
        return happy_num;
    }

    public void setHappy_num(int happy_num) {
        this.happy_num = happy_num;
    }

    public int getSad_num() {
        return sad_num;
    }

    public void setSad_num(int sad_num) {
        this.sad_num = sad_num;
    }

    public String getLanguage() {return language;}

    public void setLanguage(String language) {this.language = language;}
}
