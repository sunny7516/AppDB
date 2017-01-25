package com.example.tacademy.dbdownload;

// 전화나 문자의 최신 전번을 저장하는 데이터 모델

public class LastCallModel {
    String uid, name, tel, regdate;
    int idx, type;

    String[] param;
    public String[] getParam(){
        param =  new String[]{uid, name, tel, type+"", regdate};
        return param;
    }

    public LastCallModel(int idx, String uid, String name, String tel, int type, String regdate) {
        this.idx = idx;
        this.uid = uid;
        this.name = name;
        this.tel = tel;
        this.type = type;
        this.regdate = regdate;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
