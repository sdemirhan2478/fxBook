/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;
import java.io.Serializable;

public class Adres implements Serializable{
    private Long id;            
    private String format;   
    private Long boyut;
    private String kitapFormat;
    private String kitapAdres;
    
    
    public Adres() {
        
    } 
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Long getBoyut() {
        return boyut;
    }

    public void setBoyut(Long boyut) {
        this.boyut = boyut;
    }

    public String getKitapFormat() {
        return kitapFormat;
    }

    public void setKitapFormat(String kitapFormat) {
        this.kitapFormat = kitapFormat;
    }

    public String getKitapAdres() {
        return kitapAdres;
    }

    public void setKitapAdres(String kitapAdres) {
        this.kitapAdres = kitapAdres;
    }
    
    
}
