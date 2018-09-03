/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;
import java.io.Serializable;
import java.sql.Date;

public class Kitap implements Serializable{
    private Long id;            
    private String kitapAdi; 
    private String orjinalAdi; 
    private Long yazarId;
    private String yazarAdi;
    private Long seriId;
    private String seriAdi;
    private Integer seriNo;
    private Long yayinciId;
    private String yayinciAdi;
    private Integer baski; 
    private Long ceviriId;
    private String ceviriAdi;
    private String aciklama;
    private Date ektarih;
    private Long tur;
    
    public Kitap(){
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKitapAdi() {
        return kitapAdi;
    }

    public void setKitapAdi(String kitapAdi) {
        this.kitapAdi = kitapAdi;
    }

    public String getOrjinalAdi() {
        return orjinalAdi;
    }

    public void setOrjinalAdi(String orjinalAdi) {
        this.orjinalAdi = orjinalAdi;
    }

    public Long getYazarId() {
        return yazarId;
    }

    public void setYazarId(Long yazarId) {
        this.yazarId = yazarId;
    }

    public String getYazarAdi() {
        return yazarAdi;
    }

    public void setYazarAdi(String yazarAdi) {
        this.yazarAdi = yazarAdi;
    }

    public Long getSeriId() {
        return seriId;
    }

    public void setSeriId(Long seriId) {
        this.seriId = seriId;
    }

    public String getSeriAdi () {
        return seriAdi;
    }

    public void setSeriAdi(String seriAdi) {
        this.seriAdi = seriAdi;
    }

    public Integer getSeriNo() {
        return seriNo;
    }

    public void setSeriNo(Integer seriNo) {
        this.seriNo = seriNo;
    }

    public Long getYayinciId() {
        return yayinciId;
    }

    public void setYayinciId(Long yayinciId) {
        this.yayinciId = yayinciId;
    }

    public String getYayinciAdi() {
        return yayinciAdi;
    }

    public void setYayinciAdi(String yayinciAdi) {
        this.yayinciAdi = yayinciAdi;
    }

    public Integer getBaski() {
        return baski;
    }

    public void setBaski(Integer baski) {
        this.baski = baski;
    }

    public Long getCeviriId() {
        return ceviriId;
    }

    public void setCeviriId(Long ceviriId) {
        this.ceviriId = ceviriId;
    }

    public String getCeviriAdi() {
        return ceviriAdi;
    }

    public void setCeviriAdi(String ceviriAdi) {
        this.ceviriAdi = ceviriAdi;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public Date getEktarih() {
        return ektarih;
    }

    public void setEktarih(Date ektarih) {
        this.ektarih = ektarih;
    }

    public Long getTur() {
        return tur;
    }

    public void setTur(Long tur) {
        this.tur = tur;
    }
    
}
