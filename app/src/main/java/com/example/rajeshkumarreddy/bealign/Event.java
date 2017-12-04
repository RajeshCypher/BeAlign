package com.example.rajeshkumarreddy.bealign;

/**
 * Created by Rajeshkumar Reddy on 03-12-2017.
 */

public class Event {
    private String date,desc,link,title;

    public Event(){

    }

    public Event(String date, String desc, String link, String title) {
        this.date = date;
        this.desc = desc;
        this.link = link;
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
