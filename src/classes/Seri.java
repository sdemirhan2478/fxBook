/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;
import java.io.Serializable;

public class Seri implements Serializable{
    
    private Long seriId;
    private String seriAdi;
    private String seriAck;
    
    public Seri() {
  
    } 

    public Long getSeriId() {
        return seriId;
    }

    public void setSeriId(Long seriId) {
        this.seriId = seriId;
    }

    public String getSeriAdi() {
        return seriAdi;
    }

    public void setSeriAdi(String seriAdi) {
        this.seriAdi = seriAdi;
    }

    public String getSeriAck() {
        return seriAck;
    }

    public void setSeriAck(String seriAck) {
        this.seriAck = seriAck;
    }
    
    
    
}
