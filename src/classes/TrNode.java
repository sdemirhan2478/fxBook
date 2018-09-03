/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;
import java.io.Serializable;
import javafx.scene.image.Image;

public class TrNode implements Serializable{
    private Long id;
    private Long ustId;
    private Long tur;
    private String etiket;
    private  Boolean check;
    private String pkId; //seçili dosya adresi
    private Image image;
    private String cinsiyet;
    private String klsId; //seçili dosyanın klasörü
    
    public TrNode(Long id, Long ustId, Long tur, String etiket, Boolean check, String pkId, String cinsiyet, String klsId ) {
        this.id = id;
        this.ustId = ustId;
        this.tur = tur;
        this.etiket = etiket;
        this.check = check;
        this.pkId = pkId;
        this.cinsiyet = cinsiyet;
        this.klsId = klsId;
    } 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUstId() {
        return ustId;
    }

    public void setUstId(Long ustId) {
        this.ustId = ustId;
    }

    public String getEtiket() {
        return etiket;
    }

    public void setEtiket(String etiket) {
        this.etiket = etiket;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public String getPkId() {
        return pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId;
    }

    public Long getTur() {
        return tur;
    }

    public void setTur(Long tur) {
        this.tur = tur;
    }

    public String getCinsiyet() {
        return cinsiyet;
    }

    public void setCinsiyet(String cinsiyet) {
        this.cinsiyet = cinsiyet;
    }
    
    public Image getImage() {
      if (image == null) {
          
            if (null==tur){
                image = new Image(getClass().getResourceAsStream("b.png"));
            }else switch (tur.intValue()) {
              case 0:
                  image = new Image(getClass().getResourceAsStream("a.png"));
                  break;
              case 1:
                  image = new Image(getClass().getResourceAsStream("a.png"));
                  break;
              case 2:
                  image = cinsiyet.equalsIgnoreCase("E")? new Image(getClass().getResourceAsStream("e.png")):new Image(getClass().getResourceAsStream("k.png"));
                  break;
              case 3:
                  image = new Image(getClass().getResourceAsStream("b.png"));
                  break;
              default:
                  image = new Image(getClass().getResourceAsStream("b.png"));
                  break;
          }
      }
      return image;
    }

    public String getKlsId() {
        return klsId;
    }

    public void setKlsId(String klsId) {
        this.klsId = klsId;
    }
    
  @Override
  public String toString() {
    return "TrNode{"+"id = " + id +'}';
  }    
}
