package com.santosh.android.class_app;

public class list_item3 {

    private String id;//actually subject name
    private int title;
    private String unit_;
    private String ondate;
    private String link;
    private String temporary="";


    public list_item3(String id, int title, String unit_, String ondate, String link) {
        this.id = id;
        this.title = title;
        this.unit_ = unit_;
        this.ondate = ondate;
        this.link = link;
    }
    public String getTitle3() {
        if(title == 0)
            temporary = "Course file";
        else
            temporary = "Notes";
        return temporary;
    }
    public String getUnit_() {
        return unit_;
    }
    public String getOndate() {
        return ondate;
    }
    public String getLink3() {
        return link;
    }
    public String getId3() {
        return id;
    }
}
