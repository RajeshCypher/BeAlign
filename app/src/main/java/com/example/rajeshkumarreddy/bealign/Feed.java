package com.example.rajeshkumarreddy.bealign;

/**
 * Created by Rajeshkumar Reddy on 29-11-2017.
 */

public class Feed {
    private String image,title,desc;

    public Feed(){

    }

    public Feed(String image, String title, String desc) {
        this.image = image;
        this.title = title;
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
