package com.santosh.android.class_app;

public class list_item2 {
    private String date = "";
    private String title = "";
    private String description = "";
    private String img = "";
    private String source = "";

    public list_item2(String date, String title, String description, String img, String source, int category) {
        this.date = date;
        this.title = title;
        this.description = description;
        this.img = img;
        this.source = source;
        this.category = category;
    }

    private int category = 0;
   /* 0 - general
    1 - fests
    2 - exam/transport
    3 - placement*/

    public String getDate2() {
        return date;
    }
    public String getTitle2() {
        return title;
    }
    public String getDescription2() {
        return description;
    }
    public String getImg() {
        return img;
    }
    public int getCategory() {
        return category;
    }
    public String getSource() {
        return source;
    }
}
