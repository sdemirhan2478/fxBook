/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxbook;

import DB.DBConnect;
import classes.Ceviri;
import classes.Dosya;
import classes.Kitap;
import classes.Seri;
import classes.TrNode;
import classes.Yayinci;
import classes.Yazar;
import java.awt.Desktop;
import java.io.*;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.Pair;
import javafx.util.StringConverter;
/*
 * @author sukru
 */
public class FxBookBean implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //LİSTELERİ DOLDUR
         setKitaplar();
         treeKitaplar();
         treeValue();
    }    
    private List<Kitap> masters;
    private Kitap selectedMaster;
    private Kitap kytMaster;
    private Yazar selectedYazar;
    private List<Yazar> yazarMap;
    private List<Seri> seriMap;
    private List<Yayinci> yayinciMap;
    private List<Ceviri> ceviriMap;
    private HashMap<Long,Long> yazarMapa = new HashMap<>();
    private HashMap<Long,Long> seriMapa = new HashMap<>();
    private HashMap<Long,Long> yayinciMapa = new HashMap<>();
    private HashMap<Long,Long> ceviriMapa = new HashMap<>();
    private Boolean ekDzn =Boolean.TRUE; //True Ekle False Düzenle
//    private HashMap<String,Long> xMap = new HashMap<>();
    private Alert msgse = new Alert(Alert.AlertType.ERROR);
    private Alert msgsi = new Alert(Alert.AlertType.INFORMATION);
    private Alert msgsc = new Alert(Alert.AlertType.CONFIRMATION);
    private String drwr="D";
    private String myKutuphane=drwr+":\\";
    private String myAdres="";
    
    @FXML private TextField fxKitapAdi = new TextField("");
    @FXML private TextArea fxAciklama = new TextArea("");
    @FXML private TextField fxYazarAdi = new TextField("");
    @FXML private TextField fxOrjinalAdi = new TextField("");
    @FXML private TextField fxSeriAdi = new TextField("");
    @FXML private TextField fxSeriNo = new TextField("");
    @FXML private TextField fxYayinciAdi = new TextField("");
    @FXML private TextField fxBaski = new TextField("");
    @FXML private TextField fxCeviriAdi = new TextField("");
    @FXML private TextField ynKitapAdi = new TextField("");
    @FXML private TextArea ynAciklama = new TextArea("");
    @FXML private ComboBox<Yazar> ynYazarCombo = new ComboBox<Yazar>();
    @FXML private TextField ynOrjinalAdi = new TextField("");
    @FXML private ComboBox<Seri> ynSeriCombo = new ComboBox<Seri>();
    @FXML private TextField ynSeriNo = new TextField("");
    @FXML private ComboBox<Yayinci> ynYayinciCombo = new ComboBox<Yayinci>();
    @FXML private TextField ynBaski = new TextField("");
    @FXML private ComboBox<Ceviri> ynCeviriCombo = new ComboBox<Ceviri>();
    @FXML private TextField ekYazarAdi = new TextField("");
    @FXML private TextArea ekYazarNot = new TextArea("");
    @FXML private TextField ekSeriAdi = new TextField("");
    @FXML private TextArea ekSeriNot = new TextArea("");
    @FXML private TextField ekYayinciAdi = new TextField("");
    @FXML private TextArea ekYayinciNot = new TextArea("");
    @FXML private TextField ekCeviriAdi = new TextField("");
    @FXML private TextArea ekCeviriNot = new TextArea("");
    @FXML private ToggleGroup tglGrp = new ToggleGroup();
    @FXML private ToggleButton ekTgl = new ToggleButton("Ekle");
    @FXML private ToggleButton dznTgl = new ToggleButton("Düzenle");
    @FXML private ToggleGroup yzrCinsGrp = new ToggleGroup();
    @FXML private ToggleButton yzrErk = new ToggleButton("Erkek");
    @FXML private ToggleButton yzrKdn = new ToggleButton("Kadın");
    @FXML private Tab tabEkle = new Tab("Ekle Düzenle");
    @FXML private Tab tabYeni = new Tab("Yeni");
    @FXML private SplitPane paneAna = new SplitPane(new Pane(), new Pane());
    @FXML private SplitPane paneCld = new SplitPane(new Pane(), new Pane());
    @FXML private TreeView <Pair<TrNode,String>> fxTreeView;
    @FXML private TableView<Dosya> fxDosyaTable = new TableView();
    @FXML private TableColumn fxDosyaColAdi = new TableColumn();
    @FXML private TableColumn fxDosyaColDgsTarihi = new TableColumn();
    @FXML private TableColumn fxDosyaColBoyut = new TableColumn();
    @FXML private TableColumn fxDosyaColBirim = new TableColumn();
    @FXML private TableColumn fxDosyaColOpen= new TableColumn();
    @FXML private ImageView fxImage = new ImageView();
    @FXML private Button ktpKayit = new Button("Kaydet");
    @FXML private Button yzrKayit = new Button("Kaydet");
    @FXML private Button seriKayit = new Button("Kaydet");
    @FXML private Button yayinciKayit = new Button("Kaydet");
    @FXML private Button cevriKayit = new Button("Kaydet");
    @FXML private Button ktpEkle = new Button("Kitap Ekle");
    @FXML private ComboBox<String> fxDrwr = new ComboBox<String>();

    public void setKitaplar() {
        seriMap=new ArrayList<>();
        yayinciMap=new ArrayList<>();
        yazarMap=new ArrayList<>();
        ceviriMap=new ArrayList<>();
        yazarMapa=new HashMap<>();   
        seriMapa=new HashMap<>();
        yayinciMapa=new HashMap<>();
        ceviriMapa=new HashMap<>();
        masters=new ArrayList<>(getKitaplar()); 
        selectedMaster=new Kitap();
        selectedYazar=new Yazar(); 
        fxDrwr.getItems().addAll("C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M");
        fxDrwr.getSelectionModel().select(drwr);
    }
    
    
    public  List<Kitap>  getKitaplar() {
        List<Kitap> bks= new ArrayList<>();
        Connection cc = null;
        ResultSet rs = null;
        PreparedStatement pStmt = null;

        Yazar y = new Yazar();
        y.setYazarId(0L);
        y.setYazarAdi("");
        y.setYazarAck("");
        y.setCinsiyet("");
        yazarMap.add(y); 
        
        Seri s = new Seri();
        s.setSeriId(0L);
        s.setSeriAdi("");
        s.setSeriAck("");
        seriMap.add(s);
              
        Yayinci yy = new Yayinci();
        yy.setYayinciId(0L);
        yy.setYayinciAdi("");
        yy.setYayinciAck("");
        yayinciMap.add(yy);
        
        Ceviri c = new Ceviri();
        c.setCeviriId(0L);
        c.setCeviriAdi("");
        c.setCeviriAck("");
        ceviriMap.add(c);
        

        
        try {
            cc = new DBConnect().getConn(); 
            String url= "SELECT id, kitap, orjadi, yazarid, yazar, yazarack, cinsiyet, seriid, seri, seriack, serino, \n" +
                        "yayinciid, yayinci, yayinciack, baski, ceviriid, ceviri, ceviriack, ack, tur \n" +
                        "FROM fx_kitap  " ;
 
            pStmt = cc.prepareStatement(url);
            rs=pStmt.executeQuery();
            while (rs.next()) {
                //***************** KİTAP ******************
                Kitap master = new Kitap();
                master.setId(rs.getLong("id"));
                master.setKitapAdi(rs.getObject("kitap")==null?"":rs.getString("kitap"));
                master.setOrjinalAdi(rs.getString("orjadi"));
                master.setAciklama(rs.getString("ack"));
                master.setTur(rs.getLong("tur"));
                //***************** YAZAR ******************
                
                master.setYazarId(rs.getLong("yazarid"));
                master.setYazarAdi(rs.getObject("yazar")==null?"":rs.getString("yazar"));
                if(rs.getLong("yazarid")>0&&yazarMapa.get(rs.getLong("yazarid"))==null){
                    yazarMapa.put(rs.getLong("yazarid"),rs.getLong("yazarid"));
                    y = new Yazar();
                    y.setYazarId(rs.getLong("yazarid"));
                    y.setYazarAdi(rs.getObject("yazar")==null?"":rs.getString("yazar"));
                    y.setYazarAck(rs.getString("yazarack"));
                    y.setCinsiyet(rs.getString("cinsiyet"));
                    yazarMap.add(y); 
                }
                //***************** SERİ ******************
                
                master.setSeriId(rs.getLong("seriid"));
                master.setSeriAdi(rs.getObject("seri")==null?"":rs.getString("seri"));
                master.setSeriNo(rs.getInt("serino"));                
                if(rs.getLong("seriid")>0&&seriMapa.get(rs.getLong("seriid"))==null){
                    seriMapa.put(rs.getLong("seriid"),rs.getLong("seriid"));
                    s = new Seri();
                    s.setSeriId(rs.getLong("seriid"));
                    s.setSeriAdi(rs.getObject("seri")==null?"":rs.getString("seri"));
                    s.setSeriAck(rs.getString("seriack"));
                    seriMap.add(s);
                }  
                
                //***************** YAYINCI ******************
                
                master.setYayinciId(rs.getLong("yayinciid"));
                master.setYayinciAdi(rs.getObject("yayinci")==null?"":rs.getString("yayinci"));
                master.setBaski(rs.getInt("baski"));
                if(rs.getLong("yayinciid")>0&&yayinciMapa.get(rs.getLong("yayinciid"))==null){
                    yayinciMapa.put(rs.getLong("yayinciid"),rs.getLong("yayinciid"));
                    yy = new Yayinci();
                    yy.setYayinciId(rs.getLong("yayinciid"));
                    yy.setYayinciAdi(rs.getObject("yayinci")==null?"":rs.getString("yayinci"));
                    yy.setYayinciAck(rs.getString("yayinciack"));
                    yayinciMap.add(yy);
                }                
                
                //***************** ÇEVİRİ ******************
                
                master.setCeviriId(rs.getLong("ceviriid"));
                master.setCeviriAdi(rs.getObject("ceviri")==null?"":rs.getString("ceviri"));
                if(rs.getLong("ceviriid")>0&&ceviriMapa.get(rs.getLong("ceviriid"))==null){
                    ceviriMapa.put(rs.getLong("ceviriid"),rs.getLong("ceviriid"));
                    c = new Ceviri();
                    c.setCeviriId(rs.getLong("ceviriid"));
                    c.setCeviriAdi(rs.getObject("ceviri")==null?"":rs.getString("ceviri"));
                    c.setCeviriAck(rs.getString("ceviriack"));
                    ceviriMap.add(c);
                }   
                bks.add(master); 
            }
             
         
            url="SELECT yazarid, yazar, yazarack, cinsiyet \n" +
                "FROM yzrcombo ";
            pStmt = cc.prepareStatement(url);
            rs=pStmt.executeQuery();
            while (rs.next()) {
                y = new Yazar();
                y.setYazarId(rs.getLong("yazarid"));
                y.setYazarAdi(rs.getObject("yazar")==null?"":rs.getString("yazar"));
                y.setYazarAck(rs.getString("yazarack"));
                y.setCinsiyet(rs.getString("cinsiyet"));
                if(rs.getLong("yazarid")>0&&yazarMapa.get(rs.getLong("yazarid"))==null){
                    yazarMapa.put(rs.getLong("yazarid"),rs.getLong("yazarid"));
                    yazarMap.add(y); 
                }
            }

            url="SELECT id, seri, seriack \n" +
                "FROM srcombo ";
            pStmt = cc.prepareStatement(url);
            rs=pStmt.executeQuery();
            while (rs.next()) {
                s = new Seri();
                s.setSeriId(rs.getLong("id"));
                s.setSeriAdi(rs.getObject("seri")==null?"":rs.getString("seri"));
                s.setSeriAck(rs.getString("seriack"));
                if(rs.getLong("id")>0&&seriMapa.get(rs.getLong("id"))==null){
                    seriMapa.put(rs.getLong("id"),rs.getLong("id"));
                    seriMap.add(s); 
                }
            }
            
            url="SELECT id, yayinci, yayinciack \n" +
                        "FROM yycombo ";
            pStmt = cc.prepareStatement(url);
            rs=pStmt.executeQuery();
            while (rs.next()) {
                yy = new Yayinci();
                yy.setYayinciId(rs.getLong("id"));
                yy.setYayinciAdi(rs.getObject("yayinci")==null?"":rs.getString("yayinci"));
                yy.setYayinciAck(rs.getString("yayinciack"));
                if(rs.getLong("id")>0&&yayinciMapa.get(rs.getLong("id"))==null){
                    yayinciMapa.put(rs.getLong("id"),rs.getLong("id"));
                    yayinciMap.add(yy); 
                }
            }
            
            url="SELECT id, ceviri, ceviriack \n" +
                        "FROM ccombo ";
            pStmt = cc.prepareStatement(url);
            rs=pStmt.executeQuery();
            while (rs.next()) {
                c = new Ceviri();
                c.setCeviriId(rs.getLong("id"));
                c.setCeviriAdi(rs.getObject("ceviri")==null?"":rs.getString("ceviri"));
                c.setCeviriAck(rs.getString("ceviriack"));
                if(rs.getLong("id")>0&&ceviriMapa.get(rs.getLong("id"))==null){
                    ceviriMapa.put(rs.getLong("id"),rs.getLong("id"));
                    ceviriMap.add(c); 
                }
            } 
            
            
            yazarMap.sort(Comparator.comparing(Yazar::getYazarAdi));
            ynYazarCombo.setItems(FXCollections.observableArrayList(yazarMap));
            ynYazarCombo.setConverter(
                new StringConverter<Yazar>() {
                    @Override
                    public String toString(Yazar object) {
                        return object.getYazarAdi();
                    }
                    @Override
                    public Yazar fromString(String string) {
                        return ynYazarCombo.getItems().stream().filter(ap -> ap.getYazarAdi().equals(string)).findFirst().orElse(new Yazar());
                    }
                }
            );
            seriMap.sort(Comparator.comparing(Seri::getSeriAdi));
            ynSeriCombo.setItems(FXCollections.observableArrayList(seriMap));
            ynSeriCombo.setConverter(
                new StringConverter<Seri>() {
                    @Override
                    public String toString(Seri object) {
                        return object.getSeriAdi();
                    }
                    @Override
                    public Seri fromString(String string) {
                        return ynSeriCombo.getItems().stream().filter(ap -> ap.getSeriAdi().equals(string)).findFirst().orElse(new Seri());
                    }
                }
            );
            yayinciMap.sort(Comparator.comparing(Yayinci::getYayinciAdi)); 
            ynYayinciCombo.setItems(FXCollections.observableArrayList(yayinciMap));
            ynYayinciCombo.setConverter(
                new StringConverter<Yayinci>() {
                    @Override
                    public String toString(Yayinci object) {
                        return object.getYayinciAdi();
                    }
                    @Override
                    public Yayinci fromString(String string) {
                        return ynYayinciCombo.getItems().stream().filter(ap -> ap.getYayinciAdi().equals(string)).findFirst().orElse(new Yayinci());
                    }
                }
            );
            ceviriMap.sort(Comparator.comparing(Ceviri::getCeviriAdi)); 
            ynCeviriCombo.setItems(FXCollections.observableArrayList(ceviriMap));
            ynCeviriCombo.setConverter(
                new StringConverter<Ceviri>() {
                    @Override
                    public String toString(Ceviri object) {
                        return object.getCeviriAdi();
                    }
                    @Override
                    public Ceviri fromString(String string) {
                        return ynCeviriCombo.getItems().stream().filter(ap -> ap.getCeviriAdi().equals(string)).findFirst().orElse(new Ceviri());
                    }
                }
            ); 
         } catch (SQLException ex) {
            Logger.getLogger(FxBook.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(rs!=null) rs.close();
                if(pStmt!=null) pStmt.close();
                if(cc!=null) cc.close();
            } catch (SQLException ex) {
                Logger.getLogger(FxBook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return bks;
    }
/***************************************************************************
    *************************TREEVİEW İŞLEMLERİ*****************************
****************************************************************************/
    public void treeKitaplar() {
     
        Connection cc = null;
        ResultSet rs = null;
        PreparedStatement pStmt = null;
        List<TrNode> trAlfMasters=new ArrayList<>();
        List<TrNode> trYzrMasters=new ArrayList<>();
        List<TrNode> trKtpMasters=new ArrayList<>();        
        try { 
            cc = new DBConnect().getConn(); 
             
            String url= "SELECT id, ustid, tur, etiket, cinsiyet \n" +
                        "FROM fx_trw ORDER BY etiket" ;
 
            pStmt = cc.prepareStatement(url);
            rs=pStmt.executeQuery();

            while (rs.next()) {
                TrNode t = new TrNode(
                    rs.getLong("id"),
                    rs.getLong("ustid"),
                    rs.getLong("tur"),
                    rs.getString("etiket"),
                    Boolean.FALSE,
                    "",
                    rs.getObject("cinsiyet")==null?"E":rs.getString("cinsiyet"),
                    ""        
                );
                switch (rs.getInt("tur")) {
                    case 1:
                        trAlfMasters.add(t);
                        break;
                    case 2:
                        trYzrMasters.add(t);    
                        break;
                    case 3:
                        trKtpMasters.add(t);
                        break;
                    default:
                        break; 
                }
            } 
   
            TreeItem<Pair<TrNode,String>> root = new TreeItem(new Pair<>(new TrNode(0L,0L,0L,"Book",Boolean.FALSE,"Book","E",""), "Book"),new ImageView(new Image(getClass().getResourceAsStream("a.png"))));
            TreeItem<Pair<TrNode,String>> alfchild, yzrchild, ktpchild;
        
            for (TrNode a :trAlfMasters) {
                // ALFABEYİ AL 
                alfchild = new TreeItem<>(new Pair<>(a, a.getEtiket()));
                root.getChildren().add(alfchild);
                a.setPkId("Book\\"+a.getEtiket());
                a.setKlsId("Book");//\\
                //xMap.put("Book|"+a.getEtiket(),a.getId());
                for (TrNode y :trYzrMasters) {
                    // YAZARI AL 
                    if(!y.getCheck()){ 
                        if(Objects.equals(y.getUstId(), a.getId())){
                            y.setCheck(Boolean.TRUE);
                            yzrchild = new TreeItem<>(new Pair<>(y, y.getEtiket()));
                            alfchild.getChildren().add(yzrchild); 
                            //xMap.put("Book|"+a.getEtiket()+"|"+y.getEtiket(),y.getId());
                            y.setPkId("Book\\"+a.getEtiket()+"\\"+y.getEtiket());
                            y.setKlsId("Book\\"+a.getEtiket()); //+"\\"
                            for (TrNode k :trKtpMasters) {
                                // KİTABI AL
                                if(!k.getCheck()){
                                    if(Objects.equals(k.getUstId(), y.getId())){
                                        k.setCheck(Boolean.TRUE);
                                        ktpchild = new TreeItem<>(new Pair<>(k, k.getEtiket()));
                                        yzrchild.getChildren().add(ktpchild); 
                                        //xMap.put("Book|"+a.getEtiket()+"|"+y.getEtiket()+"|"+k.getEtiket(),k.getId());
                                        k.setPkId("Book\\"+a.getEtiket()+"\\"+y.getEtiket()+"\\"+k.getEtiket());
                                        k.setKlsId("Book\\"+a.getEtiket()+"\\"+y.getEtiket()); //+"\\"
                                    }
                                }
                            } 
                        }
                    }
                }
            }  
            fxTreeView.setRoot(root);

         } catch (SQLException ex) {
            Logger.getLogger(FxBook.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(rs!=null) rs.close();
                if(pStmt!=null) pStmt.close();
                if(cc!=null) cc.close();
            } catch (SQLException ex) {
                Logger.getLogger(FxBook.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
  
    }

    public void treeValue() { 
        
        dznTgl.setToggleGroup(tglGrp);
        ekTgl.setToggleGroup(tglGrp);
        ekTgl.setText("Ekle");
        dznTgl.setText("Düzenle");
        tabEkle.setDisable(true);
        tabYeni.setDisable(true);
        
        fxTreeView.setCellFactory((TreeView<Pair<TrNode, String>> treeView_x) -> new TreeCell<Pair<TrNode, String>>() {
            final ImageView iconView = new ImageView();
            
            @Override protected void updateItem(final Pair<TrNode, String> pair, boolean empty) {
                super.updateItem(pair, empty);
                if (!empty && pair != null) {
                    setText(pair.getValue());
                    iconView.setImage(pair.getKey().getImage());
                    setGraphic(iconView);
                } else {
                    setText(null);
                    setGraphic(null);
                }
                setOnMouseClicked((MouseEvent event) -> {
                    if(pair==null) return;
                    if(pair.getKey()==null) return;
                    if(pair.getKey().getTur()==null) return;
                    selectedMaster=new Kitap();
                    kytMaster=new Kitap();
                    selectedYazar= new Yazar();
                    tabEkle.setDisable(true);
                    tabYeni.setDisable(true);
                    ekTgl.setSelected(false);
                    dznTgl.setSelected(false);
            
                    if(pair.getKey().getTur()==0){
                       File bFile = new File(myKutuphane+pair.getKey().getPkId());
                        if(!bFile.exists()) {
                           bFile.mkdir(); // Böyle  Bir alfabe Klasörü Yok İse oluşturuluyor
                        } 
                        if(!bFile.exists()) {
                            msgsi.setHeaderText("Klasör Oluşturulamadı Sürücü Bilgisini Kontrol Ediniz.!");
                            msgsi.showAndWait();  
                        }
                        tabYeni.setDisable(false);
                        myAdres=myKutuphane+pair.getKey().getPkId();
                        tabEkle.setDisable(true);
                        ekTgl.setSelected(true);
                        dznTgl.setSelected(false);
                        ekTgl.setDisable(true);
                        dznTgl.setDisable(true);
                        ktpEkle.setDisable(true);
                        tableDoldur(""); // tableyi boşalt
                    }else if(pair.getKey().getTur()==1){
                        File aFile = new File(myKutuphane+pair.getKey().getPkId());
                        if(!aFile.exists()) {
                           aFile.mkdir(); // Böyle  Bir alfabe Klasörü Yok İse oluşturuluyor
                        } 
                        if(!aFile.exists()) {
                            msgsi.setHeaderText("Klasör Oluşturulamadı Sürücü Bilgisini Kontrol Ediniz.!");
                            msgsi.showAndWait();  
                        }
                        tabYeni.setDisable(false);
                        tableDoldur(""); // tableyi boşalt
                    }else if(pair.getKey().getTur()==2){
                        yazarMap.stream().filter((y) -> (Objects.equals(pair.getKey().getId(), y.getYazarId()))).forEachOrdered((y) -> {selectedYazar=y;});
                        tabYeni.setDisable(false);
                        myAdres=myKutuphane+pair.getKey().getPkId();
                        tabEkle.setDisable(false);
                        ekTgl.setSelected(true);
                        dznTgl.setSelected(false);
                        ekTgl.setDisable(true);
                        dznTgl.setDisable(true);
                        ktpEkle.setDisable(true);
                        File yzrFile = new File(myKutuphane+pair.getKey().getPkId());
                        if(!yzrFile.exists()) {
                           yzrFile.mkdir(); // Böyle  Bir Yazar Klasörü Yok İse oluşturuluyor
                        } 
                        if(!yzrFile.exists()) {
                            msgsi.setHeaderText("Klasör Oluşturulamadı Sürücü Bilgisini Kontrol Ediniz.!");
                            msgsi.showAndWait();  
                        }
                        tableDoldur(myKutuphane+pair.getKey().getPkId());
                    }else if(pair.getKey().getTur()==3){
                        masters.stream().filter((k) -> (Objects.equals(pair.getKey().getId(), k.getId()))).forEachOrdered((k) -> {
                            selectedMaster=k;
                            kytMaster=k;
                            tabEkle.setDisable(false);
                            ekTgl.setSelected(false);
                            dznTgl.setSelected(true);
                            ekTgl.setDisable(false);
                            dznTgl.setDisable(false);
                            ktpEkle.setDisable(false);
                            myAdres=myKutuphane+pair.getKey().getPkId();
                            File ktpFile = new File(myKutuphane+pair.getKey().getPkId());
                            if(!ktpFile.exists()) {
                               ktpFile.mkdir(); // Böyle  Bir Kitap Klasörü Yok İse oluşturuluyor
                            } 
                            if(!ktpFile.exists()) {
                                msgsi.setHeaderText("Klasör Oluşturulamadı Sürücü Bilgisini Kontrol Ediniz.!");
                                msgsi.showAndWait();  
                            }
                            tableDoldur(myKutuphane+pair.getKey().getPkId());
                            if (event.isPrimaryButtonDown()&&event.getClickCount() == 2) {
                                try {
                                    DsyStrOku(fxDosyaTable.getSelectionModel().getSelectedItem()==null?null:fxDosyaTable.getSelectionModel().getSelectedItem().getDosyaId());
                                    System.out.println(fxDosyaTable.getSelectionModel().getSelectedItem());
                                } catch (IOException ex) {
                                    Logger.getLogger(FxBookBean.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                    }
                    
                    yzrCnsytSec();
                    tglGrpSec();
                    //clickedPair.setText("Key: " + pair.getKey().getTur() + " Value: " + pair.getValue()+ " Value: " + pair.getKey().getCinsiyet());
                });
            }
        });
    }
    
/***************************************************************************
    *************************TABLEVİEW İŞLEMLERİ*****************************
    * @param ktpAdres
****************************************************************************/
    
    public  void  tableDoldur(String ktpAdres) {
        //DecimalFormat df=new DecimalFormat("#.#");  
        File folder = new File(ktpAdres);
        File[] listOfFiles = folder.listFiles();
        String brm="Kb";
        String afrm="";
        
        List<Dosya> dosyaMap=new ArrayList<>();
        fxImage.imageProperty().set(null);
        if (listOfFiles!=null){
            for (File mdosya : listOfFiles) {
                if (mdosya.isFile()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    afrm=mdosya.getName().substring(mdosya.getName().lastIndexOf('.') + 1, mdosya.getName().length()).toLowerCase();
                    if (afrm.equalsIgnoreCase("db")||afrm.equalsIgnoreCase("jpg")||afrm.equalsIgnoreCase("jpeg")||afrm.equalsIgnoreCase("jfif")||afrm.equalsIgnoreCase("gif")||afrm.equalsIgnoreCase("png")){
                        // resim
                        if(afrm.equalsIgnoreCase("db")) break;
                        setKtpImage(mdosya);
                    }else{
                        Long b=mdosya.length();
                        Float ff=b.floatValue()/1024;
                        if (ff>1024f){
                           ff=ff/1024f; 
                            brm="Mb";
                        }
                        Dosya dd =new Dosya();
                        dd.setDosyaId(mdosya.getPath());
                        dd.setDosyaAdi(mdosya.getName());
                        dd.setDegismTarihi(sdf.format(mdosya.lastModified()));
                        dd.setDosyaBoyut(ff);
                        dd.setBoyutBrim(brm);
                        dosyaMap.add(dd);
                    }
                }
            } 
        }
        ObservableList dt = FXCollections.observableList(dosyaMap);
        fxDosyaTable.setItems(dt);  
        fxDosyaColAdi.setCellValueFactory(new PropertyValueFactory<Dosya, String>("dosyaAdi"));
        fxDosyaColDgsTarihi.setCellValueFactory(new PropertyValueFactory<Dosya, String>("degismTarihi"));
        //fxColDgsTarihi.setCellFactory((AbstractConvertCellFactory<Dosya, Date>) value -> new SimpleDateFormat("dd-MM-yyyy HH:mm").format(value));
        fxDosyaColBoyut.setCellValueFactory(new PropertyValueFactory<Dosya, Long>("dosyaBoyut"));
        fxDosyaColBoyut.setCellFactory((AbstractConvertCellFactory<Dosya, Float>) value -> new DecimalFormat("#.#").format(value));
        fxDosyaColBirim.setCellValueFactory(new PropertyValueFactory<Dosya, String>("boyutBrim"));
        fxDosyaColOpen.setCellValueFactory(new PropertyValueFactory <Button, String>("dosyaId"));
        fxDosyaTable.getColumns().setAll(fxDosyaColAdi, fxDosyaColDgsTarihi, fxDosyaColBoyut, fxDosyaColBirim, fxDosyaColOpen);
        fxDosyaTable.getSelectionModel().select(0); //
        fxDosyaTable.setDisable(false);
        fxDosyaTable.setStyle("-fx-opacity: 1.0;-fx-selection-bar: #98FB98; -fx-selection-bar-non-focused: #98FB98;"); 
  
    }

    public interface AbstractConvertCellFactory<E, T> extends Callback<TableColumn<E, T>, TableCell<E, T>> {
        @Override default TableCell<E, T> call(TableColumn<E, T> param) {
            return new TableCell<E, T>() {
                @Override protected void updateItem(T item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(convert(item));
                    }
                }
            };
        }
        String convert(T value);        
    }
    
    @FXML public void fxTableKitapSelected() throws IOException{
        
        fxDosyaTable.setOnMousePressed((MouseEvent event) -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                try {
                    DsyStrOku(fxDosyaTable.getSelectionModel().getSelectedItem()==null?null:fxDosyaTable.getSelectionModel().getSelectedItem().getDosyaId());
                } catch (IOException ex) {
                    Logger.getLogger(FxBookBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
                
            }
        });
       
        
//        ObservableList<Dosya> rowa = fxDosyaTable.getSelectionModel().getSelectedItems();         
//        DsyStrOku(rowa.get(0).getDosyaId());
//
//        fxDosyaTable.setRowFactory( tv -> {
//           TableRow<Dosya> row = new TableRow<>();
//           row.setOnMouseClicked(event -> {
//               if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
//                   Dosya rowData = row.getItem();
//                   try {
//                       DsyStrOku(rowData.getDosyaId());
//                   } catch (IOException ex) {
//                       Logger.getLogger(FxBookBean.class.getName()).log(Level.SEVERE, null, ex);
//                   }
//                   
//               }
//           });
//           return row ;
//       });
    }

    private  void DsyStrOku(String path) 
            throws IOException{
        try {
            if (path==null||path.equalsIgnoreCase(""))return;
            File f = new File(path);
            if(f.exists()) {
                String tr=(f.toPath().toString().substring(f.toPath().toString().length()-5, f.toPath().toString().length())).substring((f.toPath().toString().substring(f.toPath().toString().length()-5, f.toPath().toString().length())).indexOf("."), (f.toPath().toString().substring(f.toPath().toString().length()-5, f.toPath().toString().length())).length());
                if(tr.equalsIgnoreCase(".txt")){
                    FileReader fl = new FileReader(path);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "ISO-8859-9"));
                    //BufferedReader reader = new BufferedReader(fl);
                    String satir;
                    String metin = "";
                    while((satir = reader.readLine())!= null){
                        metin += satir + "\n";
                    }
                    fxAciklama.setText(metin);
                }else{
                    Desktop desktop = Desktop.getDesktop();
                    desktop.open(f);
                }        
            }
        } catch (IOException ex) {
            Logger.getLogger(FxBook.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setKtpImage(File mdosya){
        if (mdosya.exists()) {
           Image image = new Image(mdosya.toURI().toString(),477,555, false, false);
           fxImage.setImage(image);
           fxImage.setFitHeight(555);
           fxImage.setFitWidth(477);
        }else{
            fxImage.imageProperty().set(null);
        }
    }

    @FXML public void tglGrpSec() { 
        Yazar y = new Yazar();
        Seri sr = new Seri();
        Yayinci yy = new Yayinci();
        Ceviri c = new Ceviri();
        
        ekDzn=Boolean.TRUE;
        ekTgl.setStyle("-fx-base: lightgreen;");
        dznTgl.setStyle("-fx-base: WHITE;");
        ynKitapAdi.setText("");
        ynYazarCombo.getSelectionModel().select(y);
        ynOrjinalAdi.setText("");
        ynSeriCombo.getSelectionModel().select(sr);
        ynSeriNo.setText("");
        ynYayinciCombo.getSelectionModel().select(yy);
        ynBaski.setText("");
        ynCeviriCombo.getSelectionModel().select(c);   
        ynAciklama.setText("");

        if(tglGrp.getSelectedToggle()==ekTgl){ 
            // YENİ EKLEME İSTENİYOR İSE  selectedMaster BOŞALTILACAK
            selectedMaster=new Kitap();
            fxKitapAdi.setText(selectedMaster.getKitapAdi()==null?"":selectedMaster.getKitapAdi());
            fxYazarAdi.setText(selectedMaster.getYazarAdi()==null?"":selectedMaster.getYazarAdi());
            fxOrjinalAdi.setText(selectedMaster.getOrjinalAdi()==null?"":selectedMaster.getOrjinalAdi());
            fxSeriAdi.setText(selectedMaster.getSeriAdi()==null?"":selectedMaster.getSeriAdi());
            fxSeriNo.setText(selectedMaster.getSeriNo()==null?"":(selectedMaster.getSeriNo()==0?"":selectedMaster.getSeriNo().toString()));
            fxYayinciAdi.setText(selectedMaster.getYayinciAdi()==null?"":selectedMaster.getYayinciAdi());
            fxBaski.setText(selectedMaster.getBaski()==null?"":(selectedMaster.getBaski()==0?"":selectedMaster.getBaski().toString()));
            fxCeviriAdi.setText(selectedMaster.getCeviriAdi()==null?"":selectedMaster.getCeviriAdi());
            fxAciklama.setText(selectedMaster.getAciklama()==null?"":selectedMaster.getAciklama());
             return;
        }else{
            // KULLANICI EĞER EKRANA GİRDİ VE EKLE DÜZENLE EKLE DÜZENLE GİBİ DEVAMLI DURUM DEĞİŞTİRİYOR İSE kytMaster EN SON SEÇİLİ KAYDI HİÇ KAYBETMEDİĞİNDEN ORADAN TAZELEME YAPICAZ
            selectedMaster=kytMaster;
            fxKitapAdi.setText(selectedMaster.getKitapAdi()==null?"":selectedMaster.getKitapAdi());
            fxYazarAdi.setText(selectedMaster.getYazarAdi()==null?"":selectedMaster.getYazarAdi());
            fxOrjinalAdi.setText(selectedMaster.getOrjinalAdi()==null?"":selectedMaster.getOrjinalAdi());
            fxSeriAdi.setText(selectedMaster.getSeriAdi()==null?"":selectedMaster.getSeriAdi());
            fxSeriNo.setText(selectedMaster.getSeriNo()==null?"":(selectedMaster.getSeriNo()==0?"":selectedMaster.getSeriNo().toString()));
            fxYayinciAdi.setText(selectedMaster.getYayinciAdi()==null?"":selectedMaster.getYayinciAdi());
            fxBaski.setText(selectedMaster.getBaski()==null?"":(selectedMaster.getBaski()==0?"":selectedMaster.getBaski().toString()));
            fxCeviriAdi.setText(selectedMaster.getCeviriAdi()==null?"":selectedMaster.getCeviriAdi());
            fxAciklama.setText(selectedMaster.getAciklama()==null?"":selectedMaster.getAciklama());
        }
        

        
        if(selectedMaster.getTur()==null){
        }else if(selectedMaster.getTur()==3){
            for(Yazar xy :yazarMap){ if(Objects.equals(xy.getYazarId(), selectedMaster.getYazarId())){y = xy; break;}}
            for(Seri xs :seriMap){ if(Objects.equals(xs.getSeriId(), selectedMaster.getSeriId())){sr = xs; break;}}
            for(Yayinci xyy :yayinciMap){ if(Objects.equals(xyy.getYayinciId(), selectedMaster.getYayinciId())){yy = xyy; break;}}
            for(Ceviri xc :ceviriMap){ if(Objects.equals(xc.getCeviriId(), selectedMaster.getCeviriId())){c = xc; break;}}
            ekDzn=Boolean.FALSE;
            ekTgl.setStyle("-fx-base: WHITE;");
            dznTgl.setStyle("-fx-base: lightgreen;");
            ynKitapAdi.setText(selectedMaster.getKitapAdi()==null?"":selectedMaster.getKitapAdi());
            ynYazarCombo.getSelectionModel().select(y);
            ynOrjinalAdi.setText(selectedMaster.getOrjinalAdi()==null?"":selectedMaster.getOrjinalAdi());
            ynSeriCombo.getSelectionModel().select(sr);
            ynSeriNo.setText(selectedMaster.getSeriNo()==null?"":(selectedMaster.getSeriNo()==0?"":selectedMaster.getSeriNo().toString()));
            ynYayinciCombo.getSelectionModel().select(yy);
            ynBaski.setText(selectedMaster.getBaski()==null?"":(selectedMaster.getBaski()==0?"":selectedMaster.getBaski().toString()));
            ynCeviriCombo.getSelectionModel().select(c); 
            ynAciklama.setText(selectedMaster.getAciklama()==null?"":selectedMaster.getAciklama());
       }
       
    } 
    
    @FXML public void yzrCnsytSec() { 
        selectedYazar.setCinsiyet(selectedYazar.getCinsiyet()==null?"E":selectedYazar.getCinsiyet());
        ekYazarAdi.setText(selectedYazar.getYazarAdi()==null?"":selectedYazar.getYazarAdi());
        ekYazarNot.setText(selectedYazar.getYazarAck()==null?"":selectedYazar.getYazarAck());
        yzrErk.setStyle(selectedYazar.getCinsiyet()==null?"-fx-base: WHITE;":selectedYazar.getCinsiyet().equalsIgnoreCase("E")?"-fx-base: lightgreen;":"-fx-base: WHITE;"); 
        yzrKdn.setStyle(selectedYazar.getCinsiyet()==null?"-fx-base: WHITE;":selectedYazar.getCinsiyet().equalsIgnoreCase("K")?"-fx-base: lightgreen;":"-fx-base: WHITE;"); 
        yzrErk.setSelected(selectedYazar.getCinsiyet()==null?true:(selectedYazar.getCinsiyet().equalsIgnoreCase("E")));
        yzrKdn.setSelected(selectedYazar.getCinsiyet()==null?false:(selectedYazar.getCinsiyet().equalsIgnoreCase("K")));
    }

    @FXML public void yzrCnsytErkek() { 
        yzrErk.setStyle("-fx-base: lightgreen;"); 
        yzrKdn.setStyle("-fx-base: WHITE"); 
        yzrErk.setSelected(true);
        yzrKdn.setSelected(false);
        selectedYazar.setCinsiyet("E");
    }
    
    @FXML public void yzrCnsytKadin() { 
        yzrErk.setStyle("-fx-base: WHITE"); 
        yzrKdn.setStyle("-fx-base: lightgreen;"); 
        yzrErk.setSelected(false);
        yzrKdn.setSelected(true);
        selectedYazar.setCinsiyet("K");
    }
    

    
    @FXML private void masterKayit(){
        if(kytCheck())return;
        
        if(selectedMaster.getId()==null||selectedMaster.getId()==0){
            insrtKitapKyt();
            initialize(null, null);
        }else{
            updtKitapKyt();
        }
        msgsc.setHeaderText("Kayıt Yapıldı.!");
        msgsc.showAndWait(); 
    }
    
    public String etiketChange(String stra) {
        if (stra==null||stra.equalsIgnoreCase("")) return "";
        //System.out.println(stra.trim().replaceAll("\\s{2,}", " "));
        stra=stra.trim().replaceAll("\\s{2,}", " ");
        String[] parts =stra.split(" ");
        for(int i=0;i<parts.length;){  
                String str=parts[i].substring(0, 1);
                str=str.equals("ı")?"I":str.equals("I")?"I":str.equals("i")?"İ":str.equals("İ")?"İ":str.equals("ş")?"Ş":str.equals("Ş")?"Ş":str.equals("ğ")?"Ğ":str.equals("Ğ")?"Ğ":str.equals("ü")?"Ü":str.equals("Ü")?"Ü":str.equals("ö")?"Ö":str.equals("Ö")?"Ö":str.equals("ç")?"Ç":str.equals("Ç")?"Ç":str.toUpperCase(Locale.ENGLISH);
                String strb=(parts[i].substring(0, 1).replace(parts[i].substring(0, 1), str)+parts[i].substring(1));
                stra=i==0?strb:stra+" "+strb;
            i++;
        }
        return stra;
    }
    
    private Boolean kytCheck(){
        Boolean var=Boolean.FALSE;
        // ÖNCE KONTROLLERİ YAP
        if (ynKitapAdi.getText()==null||ynKitapAdi.getText().equalsIgnoreCase("")) {
            msgsi.setHeaderText("Kitap Adı Boş Bırakılamaz.!");
            msgsi.showAndWait();  
            var= Boolean.TRUE;
            return var;
        }

        if (ynYazarCombo.getSelectionModel().getSelectedItem().getYazarId()==null||ynYazarCombo.getSelectionModel().getSelectedItem().getYazarId()==0) {
            msgsi.setHeaderText("Yazar Adı Boş Bırakılamaz.!");
            msgsi.showAndWait();  
            var= Boolean.TRUE;
            return var;
        }
        
        if (ynSeriNo.getText()==null||ynSeriNo.getText().equalsIgnoreCase("0")||ynSeriNo.getText().equalsIgnoreCase("")) {
            ynSeriNo.setText("0");
        } else {
            if (!isNumeric(ynSeriNo.getText())){
                msgse.setHeaderText("Seri No Alanına Numeric Değer Giriniz.!");
                msgse.showAndWait();  
                var= Boolean.TRUE;
                return var;
            }
        }
        if (ynBaski.getText()==null||ynBaski.getText().equalsIgnoreCase("0")||ynBaski.getText().equalsIgnoreCase("")) {
            ynBaski.setText("0");
        } else {
            if (!isNumeric(ynBaski.getText())){
                msgse.setHeaderText("Baskı Yılı Alanına Numeric Değer Giriniz.!");
                msgse.showAndWait();  
                var= Boolean.TRUE;
                return var;
            }
        } 
        ynKitapAdi.setText(etiketChange(ynKitapAdi.getText()));
        ynOrjinalAdi.setText(etiketChange(ynOrjinalAdi.getText()));
        ynAciklama.setText(etiketChange(ynAciklama.getText()));
        // SORUN YOKSA EKRAN VERİLERİ İLE SELECTEDMASTER'İN GÜNCELLEMESİNİ YAP 
        selectedMaster.setKitapAdi(ynKitapAdi.getText());
        selectedMaster.setOrjinalAdi(ynOrjinalAdi.getText());
        selectedMaster.setYazarId(ynYazarCombo.getSelectionModel().getSelectedItem().getYazarId());
        selectedMaster.setYazarAdi(ynYazarCombo.getSelectionModel().getSelectedItem().getYazarAdi());
        selectedMaster.setSeriId(ynSeriCombo.getSelectionModel().getSelectedItem().getSeriId());
        selectedMaster.setSeriAdi(ynSeriCombo.getSelectionModel().getSelectedItem().getSeriAdi());
        selectedMaster.setSeriNo(Integer.parseInt(ynSeriNo.getText()));
        selectedMaster.setYayinciId(ynYayinciCombo.getSelectionModel().getSelectedItem().getYayinciId());
        selectedMaster.setYayinciAdi(ynYayinciCombo.getSelectionModel().getSelectedItem().getYayinciAdi());
        selectedMaster.setBaski(Integer.parseInt(ynBaski.getText()));
        selectedMaster.setCeviriId(ynCeviriCombo.getSelectionModel().getSelectedItem().getCeviriId());
        selectedMaster.setCeviriAdi(ynCeviriCombo.getSelectionModel().getSelectedItem().getCeviriAdi());
        selectedMaster.setAciklama(ynAciklama.getText());
        return var;
    }
    

    
    @FXML public void yazarComboChange() {
//        System.out.println(ynYazarCombo.getSelectionModel().getSelectedItem().getYazarId());
//        System.out.println(ynYazarCombo.getSelectionModel().getSelectedItem().getYazarAdi());
    }
    
    @FXML public void seriComboChange() {
//        System.out.println(ynSeriCombo.getSelectionModel().getSelectedItem().getSeriId());
//        System.out.println(ynSeriCombo.getSelectionModel().getSelectedItem().getSeriAdi());
    }
    
    @FXML public void yayinciComboChange() {
//        System.out.println(ynYayinciCombo.getSelectionModel().getSelectedItem().getYayinciId());
//        System.out.println(ynYayinciCombo.getSelectionModel().getSelectedItem().getYayinciAdi());
    }
    
    @FXML public void ceviriComboChange() {
//        System.out.println(ynCeviriCombo.getSelectionModel().getSelectedItem().getCeviriId());
//        System.out.println(ynCeviriCombo.getSelectionModel().getSelectedItem().getCeviriAdi());
    }

    
    
    private boolean isNumeric(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void insrtKitapKyt() {
       
        Connection cc = null;
        PreparedStatement pStmt = null;
        String generatedColumns[] = {"id"};
        
        String url= "INSERT INTO yux_trw ( ustid, tur, etiket )\n" +
                    "VALUES (?, ?, ?)";

        String urlk= "INSERT INTO kitap (id, kitap, orjadi, yazarid, seriid, serino, yayinciid, baski, ceviriid, ack )\n" +
                     "           VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {

            cc = new DBConnect().getConn();
            pStmt = cc.prepareStatement(url,generatedColumns);

            pStmt.setLong(1, selectedMaster.getYazarId());
            pStmt.setLong(2, 3);
	    pStmt.setString(3, selectedMaster.getKitapAdi());
            pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();  
            if(rs.next()){
                selectedMaster.setId(rs.getLong(1));
                pStmt = cc.prepareStatement(urlk);
                pStmt.setLong(1, selectedMaster.getId());
                pStmt.setString(2, selectedMaster.getKitapAdi());
                pStmt.setString(3, selectedMaster.getOrjinalAdi());
                pStmt.setLong(4, selectedMaster.getYazarId());
                if (selectedMaster.getSeriId()==null||selectedMaster.getSeriId()==0) {
                    pStmt.setNull(5, java.sql.Types.INTEGER);
                } else {
                   pStmt.setLong(5, selectedMaster.getSeriId());
                }
                if (selectedMaster.getSeriNo()==null||selectedMaster.getSeriNo()==0) {
                    pStmt.setNull(6, java.sql.Types.INTEGER);
                } else {
                   pStmt.setLong(6, selectedMaster.getSeriNo());
                }
                if (selectedMaster.getYayinciId()==null||selectedMaster.getYayinciId()==0) {
                    pStmt.setNull(7, java.sql.Types.INTEGER);
                } else {
                   pStmt.setLong(7, selectedMaster.getYayinciId());
                }
                if (selectedMaster.getBaski()==null||selectedMaster.getBaski()==0) {
                    pStmt.setNull(8, java.sql.Types.INTEGER);
                } else {
                   pStmt.setInt(8, selectedMaster.getBaski());
                }
                if (selectedMaster.getCeviriId()==null||selectedMaster.getCeviriId()==0) {
                    pStmt.setNull(9, java.sql.Types.INTEGER);
                } else {
                   pStmt.setLong(9, selectedMaster.getCeviriId());
                }
                pStmt.setString(10, selectedMaster.getAciklama());

                pStmt.executeUpdate(); 
                masters.add(selectedMaster);

            }        
         } catch (SQLException ex) {
            Logger.getLogger(FxBook.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(pStmt!=null) pStmt.close();
                if(cc!=null) cc.close();
            } catch (SQLException ex) {
                Logger.getLogger(FxBook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
      
    }

    public  void updtKitapKyt() {
       
        Connection cc = null;
        PreparedStatement pStmt = null;
        String url="UPDATE kitap  \n" +
                    "   SET  kitap = ?, orjadi = ?, yazarid = ?, seriid = ?, serino = ?, yayinciid = ?, baski = ?, ceviriid = ?, ack = ? \n" +
                    "   WHERE id=?";
        try {

            cc = new DBConnect().getConn();
            pStmt = cc.prepareStatement(url);

            pStmt.setString(1, selectedMaster.getKitapAdi());
            pStmt.setString(2, selectedMaster.getOrjinalAdi());
            pStmt.setLong(3, selectedMaster.getYazarId());

            if (selectedMaster.getSeriId()==null||selectedMaster.getSeriId()==0) {
                pStmt.setNull(4, java.sql.Types.INTEGER);
            } else {
               pStmt.setLong(4, selectedMaster.getSeriId());
            }
            if (selectedMaster.getSeriNo()==null||selectedMaster.getSeriNo()==0) {
                pStmt.setNull(5, java.sql.Types.INTEGER);
            } else {
               pStmt.setLong(5, selectedMaster.getSeriNo());
            }
            if (selectedMaster.getYayinciId()==null||selectedMaster.getYayinciId()==0) {
                pStmt.setNull(6, java.sql.Types.INTEGER);
            } else {
               pStmt.setLong(6, selectedMaster.getYayinciId());
            }
            if (selectedMaster.getBaski()==null||selectedMaster.getBaski()==0) {
                pStmt.setNull(7, java.sql.Types.INTEGER);
            } else {
               pStmt.setInt(7, selectedMaster.getBaski());
            }
            if (selectedMaster.getCeviriId()==null||selectedMaster.getCeviriId()==0) {
                pStmt.setNull(8, java.sql.Types.INTEGER);
            } else {
               pStmt.setLong(8, selectedMaster.getCeviriId());
            }
            pStmt.setString(9, selectedMaster.getAciklama());
	    pStmt.setLong(10, selectedMaster.getId());
            pStmt.executeUpdate();

         } catch (SQLException ex) {
            Logger.getLogger(FxBook.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(pStmt!=null) pStmt.close();
                if(cc!=null) cc.close();
            } catch (SQLException ex) {
                Logger.getLogger(FxBook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
     public void yazarKyt() {
        if(selectedYazar.getYazarId()==null||selectedYazar.getYazarId()==0){
            insrtYazar();
            initialize(null, null);
        }else{
            updtYazar();
        }
        yazarMap.sort(Comparator.comparing(Yazar::getYazarAdi));
        ynYazarCombo.setItems(FXCollections.observableArrayList(yazarMap));
        ynYazarCombo.setConverter(
            new StringConverter<Yazar>() {
                @Override
                public String toString(Yazar object) {
                    return object.getYazarAdi();
                }
                @Override
                public Yazar fromString(String string) {
                    return ynYazarCombo.getItems().stream().filter(ap -> ap.getYazarAdi().equals(string)).findFirst().orElse(new Yazar());
                }
            }
        );
     }
     
     public void updtYazar() {
        Connection cc = null;
        PreparedStatement pStmt = null;
        String url="UPDATE yazar \n" +
        "   SET yazar = ?, yazarack = ?, cinsiyet = ? \n" +
        " WHERE id =? ";
        ekYazarAdi.setText(etiketChange(ekYazarAdi.getText()));
        ekYazarNot.setText(etiketChange(ekYazarNot.getText()));
        selectedYazar.setYazarAdi(ekYazarAdi.getText());
        selectedYazar.setYazarAck(ekYazarNot.getText()); 
        try {
            cc = new DBConnect().getConn();
            pStmt = cc.prepareStatement(url);
            pStmt.setString(1, selectedYazar.getYazarAdi());
            pStmt.setString(2, selectedYazar.getYazarAck());
            pStmt.setString(3, selectedYazar.getCinsiyet());
            pStmt.setLong(4, selectedYazar.getYazarId());
            pStmt.executeUpdate();
            yazarMap.stream().filter((y) -> (Objects.equals(y.getYazarId(), selectedYazar.getYazarId()))).forEachOrdered((y) -> {y=selectedYazar;});
            msgsi.setHeaderText("Yazar Güncellemesi Tamamlandı!");
            msgsi.showAndWait();  
         } catch (SQLException ex) {
            Logger.getLogger(FxBook.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(pStmt!=null) pStmt.close();
                if(cc!=null) cc.close();
            } catch (SQLException ex) {
                Logger.getLogger(FxBook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
     }

    public void insrtYazar() {
        if (ekYazarAdi.getText()==null||ekYazarAdi.getText().equalsIgnoreCase("")) {
            msgsi.setHeaderText("Yazar Adı Boş Bırakılamaz.!");
            msgsi.showAndWait();  
            return ;
        }
        Connection cc = null;
        PreparedStatement pStmt = null;
        String generatedColumns[] = {"id"};
        ResultSet rst = null;
        String urla="SELECT id  FROM yux_trw WHERE etiket =? ";
        
        String url= "INSERT INTO yux_trw ( ustid, tur, etiket )\n" +
                    "VALUES              ( ?, ?, ? )";

        String urlk= "INSERT INTO yazar ( id, yazar, yazarack, cinsiyet )\n" +
                     "VALUES            ( ?, ?, ?, ? )";
        
        try {
            
            ekYazarAdi.setText(etiketChange(ekYazarAdi.getText()));
            ekYazarNot.setText(etiketChange(ekYazarNot.getText()));
            selectedYazar.setYazarAdi(ekYazarAdi.getText());
            selectedYazar.setYazarAck(ekYazarNot.getText());
            
            cc = new DBConnect().getConn();
            pStmt = cc.prepareStatement(urla);
            pStmt.setString(1,ekYazarAdi.getText().substring(0, 1));
            rst=pStmt.executeQuery();

            if (rst.next()) {
                pStmt = cc.prepareStatement(url,generatedColumns);
                pStmt.setLong(1, rst.getLong("id"));
                pStmt.setLong(2, 2);
                pStmt.setString(3, selectedYazar.getYazarAdi());
                pStmt.execute();
                ResultSet rs = pStmt.getGeneratedKeys();  
                if(rs.next()){
                    selectedYazar.setYazarId(rs.getLong(1));
                    pStmt = cc.prepareStatement(urlk);
                    pStmt.setLong(1, selectedYazar.getYazarId());
                    pStmt.setString(2, selectedYazar.getYazarAdi());
                    pStmt.setString(3, selectedYazar.getYazarAck());
                    pStmt.setString(4, selectedYazar.getCinsiyet());
                    pStmt.execute(); 
                }
                yazarMap.add(selectedYazar);
                msgsi.setHeaderText("Yazar Kaydı Tamamlandı!");
                msgsi.showAndWait();  
            }else{
                msgse.setHeaderText("Alfabe Kaydı Bulunamadı.!");
                msgse.showAndWait(); 
            }
            
         } catch (SQLException ex) {
            Logger.getLogger(FxBook.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(pStmt!=null) pStmt.close();
                if(cc!=null) cc.close();
                if(rst!=null) rst.close();
            } catch (SQLException ex) {
                Logger.getLogger(FxBook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
      
    }

    @FXML private void dosyaEkle() {                                          
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("View Pictures");
        File fileName = fileChooser.showOpenDialog(null);
        FileChannel source = null;
        FileChannel destination = null;
       if (fileName==null) {

       }else {
            try {
                    source = new FileInputStream(fileName).getChannel();
                    String myAdresTur=(fileName.toPath().toString().substring(fileName.toPath().toString().length()-5, fileName.toPath().toString().length())).substring((fileName.toPath().toString().substring(fileName.toPath().toString().length()-5, fileName.toPath().toString().length())).indexOf("."), (fileName.toPath().toString().substring(fileName.toPath().toString().length()-5, fileName.toPath().toString().length())).length());
                    File destFile = new File(myAdres);
                    String hdf="";
                        if(!destFile.exists()) {
                           destFile.mkdir(); // Böyle  Bir Kitap Klasörü Yok İse oluşturuluyor
                        }
                        if(!destFile.exists()) {
                            msgsi.setHeaderText("Klasör Oluşturulamadı Sürücü Bilgisini Kontrol Ediniz.!");
                            msgsi.showAndWait();  
                            return ;
                        }

                        
                        hdf=myAdres+"\\"+ynKitapAdi.getText()+myAdresTur;
                        
                        destFile = new File(hdf);
                        if(!destFile.exists()) {
                            destFile.createNewFile();
                        }else{
                            destFile = new File(dsyIsmiBelirle(myAdresTur));
                        }
                        destination = new FileOutputStream(destFile).getChannel();
                        destination.transferFrom(source, 0, source.size());
                  
                } catch (IOException ex) {
                    Logger.getLogger(FxBook.class.getName()).log(Level.SEVERE, null, ex);
                }
       }

    }     

    public String dsyIsmiBelirle(String myAdresTur) throws IOException {
        Boolean x=Boolean.TRUE;
        String hdf="";
        Integer i=1;
        try {
            while(x) {
                hdf=myAdres+"\\"+ynKitapAdi.getText()+i.toString()+myAdresTur;
                File destFile = new File(hdf);
                if(!destFile.exists()) {
                    destFile.createNewFile();
                    x=Boolean.FALSE;
                }else{
                    i++;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(FxBook.class.getName()).log(Level.SEVERE, null, ex);
        }
       return hdf;
    }
    
    @FXML private void drwUpdate() {
        drwr=fxDrwr.getSelectionModel().getSelectedItem();
        myKutuphane=drwr+":\\";
    }     
    
    public void insrtSeriKyt() {
       
        Connection cc = null;
        PreparedStatement pStmt = null;
        String generatedColumns[] = {"id"};
        
        String url= "INSERT INTO seri (seri, seriack )\n" +
                    "VALUES           (?, ?)";
        ekSeriAdi.setText(etiketChange(ekSeriAdi.getText()));
        ekSeriNot.setText(etiketChange(ekSeriNot.getText()));
        try {

            cc = new DBConnect().getConn();
            pStmt = cc.prepareStatement(url,generatedColumns);

            pStmt.setString(1, ekSeriAdi.getText());
            pStmt.setString(2, ekSeriNot.getText());
            pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();  
            if(rs.next()){
                   Seri s = new Seri();
                    s.setSeriId(rs.getLong(1));
                    s.setSeriAdi(ekSeriAdi.getText());
                    s.setSeriAck(ekSeriNot.getText());
                    seriMap.add(s);
            }    
            seriMap.sort(Comparator.comparing(Seri::getSeriAdi));
            ynSeriCombo.setItems(FXCollections.observableArrayList(seriMap));
            ynSeriCombo.setConverter(
                new StringConverter<Seri>() {
                    @Override
                    public String toString(Seri object) {
                        return object.getSeriAdi();
                    }
                    @Override
                    public Seri fromString(String string) {
                        return ynSeriCombo.getItems().stream().filter(ap -> ap.getSeriAdi().equals(string)).findFirst().orElse(new Seri());
                    }
                }
            );
            
         } catch (SQLException ex) {
            Logger.getLogger(FxBook.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(pStmt!=null) pStmt.close();
                if(cc!=null) cc.close();
            } catch (SQLException ex) {
                Logger.getLogger(FxBook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void insrtYayinciKyt() {
        Connection cc = null;
        PreparedStatement pStmt = null;
        String generatedColumns[] = {"id"};
        String url= "INSERT INTO yayinci (yayinci, yayinciack)\n" +
                    "VALUES           (?, ?)";
        ekYayinciAdi.setText(etiketChange(ekYayinciAdi.getText()));
        ekYayinciNot.setText(etiketChange(ekYayinciNot.getText()));
        try {

            cc = new DBConnect().getConn();
            pStmt = cc.prepareStatement(url,generatedColumns);

            pStmt.setString(1, ekYayinciAdi.getText());
            pStmt.setString(2, ekYayinciNot.getText());
            pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();  
            if(rs.next()){
                Yayinci yy = new Yayinci();
                yy.setYayinciId(rs.getLong(1));
                yy.setYayinciAdi(ekYayinciAdi.getText());
                yy.setYayinciAck(ekYayinciNot.getText());
                yayinciMap.add(yy);
            }       
            yayinciMap.sort(Comparator.comparing(Yayinci::getYayinciAdi)); 
            ynYayinciCombo.setItems(FXCollections.observableArrayList(yayinciMap));
            ynYayinciCombo.setConverter(
                new StringConverter<Yayinci>() {
                    @Override
                    public String toString(Yayinci object) {
                        return object.getYayinciAdi();
                    }
                    @Override
                    public Yayinci fromString(String string) {
                        return ynYayinciCombo.getItems().stream().filter(ap -> ap.getYayinciAdi().equals(string)).findFirst().orElse(new Yayinci());
                    }
                }
            ); 
         } catch (SQLException ex) {
            Logger.getLogger(FxBook.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(pStmt!=null) pStmt.close();
                if(cc!=null) cc.close();
            } catch (SQLException ex) {
                Logger.getLogger(FxBook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void insrtCeviriKyt() {
        Connection cc = null;
        PreparedStatement pStmt = null;
        String generatedColumns[] = {"id"};
        String url= "INSERT INTO ceviri (ceviri, ceviriack)\n" +
                    "VALUES           (?, ?)";
        ekCeviriAdi.setText(etiketChange(ekCeviriAdi.getText()));
        ekCeviriNot.setText(etiketChange(ekCeviriNot.getText()));
        try {

            cc = new DBConnect().getConn();
            pStmt = cc.prepareStatement(url,generatedColumns);

            pStmt.setString(1, ekCeviriAdi.getText());
            pStmt.setString(2, ekCeviriNot.getText());
            pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();  
            if(rs.next()){
                Ceviri c = new Ceviri();
                c.setCeviriId(rs.getLong(1));
                c.setCeviriAdi(ekCeviriAdi.getText());
                c.setCeviriAck(ekCeviriNot.getText());
                ceviriMap.add(c);
            }        
            ceviriMap.sort(Comparator.comparing(Ceviri::getCeviriAdi)); 
            ynCeviriCombo.setItems(FXCollections.observableArrayList(ceviriMap));
            ynCeviriCombo.setConverter(
                new StringConverter<Ceviri>() {
                    @Override
                    public String toString(Ceviri object) {
                        return object.getCeviriAdi();
                    }
                    @Override
                    public Ceviri fromString(String string) {
                        return ynCeviriCombo.getItems().stream().filter(ap -> ap.getCeviriAdi().equals(string)).findFirst().orElse(new Ceviri());
                    }
                }
            );  
         } catch (SQLException ex) {
            Logger.getLogger(FxBook.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(pStmt!=null) pStmt.close();
                if(cc!=null) cc.close();
            } catch (SQLException ex) {
                Logger.getLogger(FxBook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
 