/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;
import java.io.Serializable;

public class Yayinci implements Serializable{
    
    private Long yayinciId;
    private String yayinciAdi;
    private String yayinciAck;
    
    public Yayinci() {
 
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

    public String getYayinciAck() {
        return yayinciAck;
    }

    public void setYayinciAck(String yayinciAck) {
        this.yayinciAck = yayinciAck;
    }
    
    
    
}
