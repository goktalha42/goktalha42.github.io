package com.tajo.memfoyy;

public class Kisi {

    private String key, k_tel, k_dogum, k_isim, k_sehir, k_soyisim, k_adres, k_yakinlik;

    public Kisi(){

    }

    public String getK_tel() {
        return k_tel;
    }

    public void setK_tel(String k_tel) {
        this.k_tel = k_tel;
    }

    public String getK_dogum() {
        return k_dogum;
    }

    public void setK_dogum(String k_dogum) {
        this.k_dogum = k_dogum;
    }

    public String getK_isim() {
        return k_isim;
    }

    public void setK_isim(String k_isim) {
        this.k_isim = k_isim;
    }

    public String getK_sehir() {
        return k_sehir;
    }

    public void setK_sehir(String k_sehir) {
        this.k_sehir = k_sehir;
    }

    public String getK_soyisim() {
        return k_soyisim;
    }

    public void setK_soyisim(String k_soyisim) {
        this.k_soyisim = k_soyisim;
    }

    public String getK_adres() {
        return k_adres;
    }

    public void setK_adres(String k_adres) {
        this.k_adres = k_adres;
    }

    public String getK_yakinlik() {
        return k_yakinlik;
    }

    public void setK_yakinlik(String k_yakinlik) {
        this.k_yakinlik = k_yakinlik;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Kisi(String key, String k_bizdekiyeri, String k_dogum, String k_isim, String k_sehir, String k_soyisim, String k_tanisma, String k_adres, String k_yakinlik) {
        this.k_adres = k_adres;
        this.k_tel = k_tel;
        this.k_dogum = k_dogum;
        this.k_isim = k_isim;
        this.k_sehir = k_sehir;
        this.k_yakinlik = k_yakinlik;
        this.k_soyisim = k_soyisim;
        this.key = key;
    }
}
