package com.example.jarvis.mas;

/**
 * Created by jarvis on 14/4/16.
 */
public class Student {
    int sid;
    String sname;
    boolean isPresent;
    int sroll;
    private int cid;
    boolean selected=false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Student( String sname, boolean isPresent) {
        this.sname = sname;
        this.isPresent=isPresent;
    }

    public Student(){

    }

    public Student(int sroll, String sname,int cid)
    {   this.cid=cid;
        this.sroll=sroll;
        this.sname=sname;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getSroll() {
        return sroll;
    }

    public void setSroll(int sroll) {
        this.sroll = sroll;
    }

    public String getSname() {
        return sname;
    }


    public void setSname(String sname) {
        this.sname = sname;
    }

    public void setIsPresent(boolean isPresent) {
        this.isPresent = isPresent;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
