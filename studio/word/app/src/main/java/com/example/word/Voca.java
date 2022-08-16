package com.example.word;

public class Voca {
    int idx;
    String eng;
    String kor;

    public Voca(String eng, String kor){
        this.eng = eng;
        this.kor = kor;
    }

    public Voca(int idx, String eng, String kor) {
        this.idx = idx;
        this.eng = eng;
        this.kor = kor;
    }
}
