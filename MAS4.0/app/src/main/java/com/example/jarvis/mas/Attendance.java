package com.example.jarvis.mas;
// Base class for Attendance table



/**
 * Created by jarvis on 14/4/16.
 */
public class Attendance {
    int aid;
    String dateTime;
    int isPresent;
    int csid;

    public Attendance(int aid) {
        this.aid = aid;
    }

    public Attendance(int aid, String dateTime, int isPresent, int csid) {
        this.aid = aid;
        this.dateTime = dateTime;
        this.isPresent = isPresent;
        this.csid = csid;
    }

    public Attendance() {
        dateTime="";
        isPresent=0;
        csid=0;
    }
    public Attendance( String dateTime, int isPresent, int csid) {
        this.dateTime = dateTime;
        this.isPresent = isPresent;
        this.csid = csid;
    }

    public int getaid() {
        return aid;
    }

    public void setaid(int aid) {
        this.aid = aid;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int isPresent() {
        return isPresent;
    }

    public void setIsPresent(int isPresent) {
        this.isPresent = isPresent;
    }

    public int getCSid() {
        return csid;
    }

    public void setCSid(int csid) {
        this.csid = csid;
    }
}
