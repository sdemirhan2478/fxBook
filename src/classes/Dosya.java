/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;
import java.io.Serializable;
import java.sql.Date;

public class Dosya implements Serializable{
    
    private String dosyaId;
    private String dosyaAdi;
    private String degismTarihi;
    private Float dosyaBoyut;
    private String boyutBrim;
    
    public Dosya() {
  
    } 

    public String getDosyaId() {
        return dosyaId;
    }

    public void setDosyaId(String dosyaId) {
        this.dosyaId = dosyaId;
    }

    public String getDosyaAdi() {
        return dosyaAdi;
    }

    public void setDosyaAdi(String dosyaAdi) {
        this.dosyaAdi = dosyaAdi;
    }

    public String getDegismTarihi() {
        return degismTarihi;
    }

    public void setDegismTarihi(String degismTarihi) {
        this.degismTarihi = degismTarihi;
    }

    public Float getDosyaBoyut() {
        return dosyaBoyut;
    }

    public void setDosyaBoyut(Float dosyaBoyut) {
        this.dosyaBoyut = dosyaBoyut;
    }

    public String getBoyutBrim() {
        return boyutBrim;
    }

    public void setBoyutBrim(String boyutBrim) {
        this.boyutBrim = boyutBrim;
    }
    
    
}
