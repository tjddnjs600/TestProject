package com.example.firstday;

public class Voca {
    private String kor;
    private String eng;

    public String getKor() {
        return kor;
    }

    public void setKor(String kor) {
        this.kor = kor;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public Voca(String eng, String kor) {

        this.kor = kor;
        this.eng = eng;
    }


}