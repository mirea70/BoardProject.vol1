package com.fastcampus.ch4.domain;

import java.util.Date;
import java.util.Objects;

public class CommentDto {
    private Integer cno;
    private Integer bno;
    private Integer pcno;
    private String comment;
    private String Commenter;
    private Date reg_date;
    private Date up_date;

    public CommentDto(){}
    public CommentDto(Integer bno, Integer pcno, String comment, String commenter) {
        this.bno = bno;
        this.pcno = pcno;
        this.comment = comment;
        Commenter = commenter;
    }

    public Integer getCno() {
        return cno;
    }

    public void setCno(Integer cno) {
        this.cno = cno;
    }

    public Integer getBno() {
        return bno;
    }

    public void setBno(Integer bno) {
        this.bno = bno;
    }

    public Integer getPcno() {
        return pcno;
    }

    public void setPcno(Integer pcno) {
        this.pcno = pcno;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommenter() {
        return Commenter;
    }

    public void setCommenter(String commenter) {
        Commenter = commenter;
    }

    public Date getReg_date() {
        return reg_date;
    }

    public void setReg_date(Date reg_date) {
        this.reg_date = reg_date;
    }

    public Date getUp_date() {
        return up_date;
    }

    public void setUp_date(Date up_date) {
        this.up_date = up_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommentDto)) return false;
        CommentDto that = (CommentDto) o;
        return Objects.equals(cno, that.cno) && Objects.equals(bno, that.bno) && Objects.equals(pcno, that.pcno) && Objects.equals(comment, that.comment) && Objects.equals(Commenter, that.Commenter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cno, bno, pcno, comment, Commenter);
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "cno=" + cno +
                ", bno=" + bno +
                ", pcno=" + pcno +
                ", comment='" + comment + '\'' +
                ", Commenter='" + Commenter + '\'' +
                ", reg_date=" + reg_date +
                ", up_date=" + up_date +
                '}';
    }
}
