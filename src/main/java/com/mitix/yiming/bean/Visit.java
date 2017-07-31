package com.mitix.yiming.bean;

public class Visit {
    private Integer id;
    private String visitcode;
    private String visitname;
    private String extend1;
    private String extend2;

    public String getVisitname() {
        return visitname;
    }

    public void setVisitname(String visitname) {
        this.visitname = visitname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVisitcode() {
        return visitcode;
    }

    public void setVisitcode(String visitcode) {
        this.visitcode = visitcode;
    }

    public String getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1;
    }

    public String getExtend2() {
        return extend2;
    }

    public void setExtend2(String extend2) {
        this.extend2 = extend2;
    }
}
