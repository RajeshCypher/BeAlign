package com.example.rajeshkumarreddy.bealign;

/**
 * Created by Rajeshkumar Reddy on 03-12-2017.
 */

public class Notifications {

    String desc,title;

    public Notifications(){

    }

    public Notifications(String desc, String title) {
        this.desc = desc;
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
