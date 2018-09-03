/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;
import java.io.Serializable;

public class Yazar implements Serializable{
    private Long yazarId;              
    private String yazarAdi;
    private String yazarAck;
    private String cinsiyet;
    
    public Yazar(){
        
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

    public String getYazarAck() {
        return yazarAck;
    }

    public void setYazarAck(String yazarAck) {
        this.yazarAck = yazarAck;
    }

    public String getCinsiyet() {
        return cinsiyet;
    }

    public void setCinsiyet(String cinsiyet) {
        this.cinsiyet = cinsiyet;
    }
    
    
}
