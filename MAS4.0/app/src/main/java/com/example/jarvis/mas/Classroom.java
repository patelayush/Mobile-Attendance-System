package com.example.jarvis.mas;

// BAse class for CLassroom table

/**
 * Created by jarvis on 14/4/16.
 */
public class Classroom {
    int cid;
    String subname;
    String deptname;
    String divname;
    int sem;

    public Classroom(int cid, String subname, String deptname, String divname, int sem) {
        this.cid = cid;
        this.subname = subname;
        this.deptname = deptname;
        this.divname = divname;
        this.sem = sem;
    }
    public Classroom( String subname, String deptname, String divname, int sem) {
        this.subname = subname;
        this.deptname = deptname;
        this.divname = divname;
        this.sem = sem;
    }

    public Classroom() {
        this.cid=0;
        this.subname="";
        this.deptname="";
        this.divname="";
        this.sem=0;
    }

    public int getcid() {
        return cid;
    }

    public void setcid(int cid) {
        this.cid = cid;
    }

    public String getsubname() {
        return subname;
    }

    public void setsubname(String subname) {
        this.subname = subname;
    }

    public String getdeptname() {
        return deptname;
    }

    public void setdeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getdivname() {
        return divname;
    }

    public void setdivname(String divname) {
        this.divname = divname;
    }

    public int getSem() {
        return sem;
    }

    public void setSem(int sem) {
        this.sem = sem;
    }
}
