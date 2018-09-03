/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;
import java.io.Serializable;

public class Ceviri implements Serializable{
    
    private Long ceviriId;
    private String ceviriAdi;
    private String ceviriAck;
    
    public Ceviri() {
 
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

    public String getCeviriAck() {
        return ceviriAck;
    }

    public void setCeviriAck(String ceviriAck) {
        this.ceviriAck = ceviriAck;
    }
    
}
