/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package kacamata;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author steve
 */
public class FXML_belikacaController implements Initializable {

    @FXML
    private Button hitung;
    @FXML
    private Button hapus;
    @FXML
    private Button keluar;
    @FXML
    private TextField txtidjual;
    @FXML
    private ComboBox<String> tipekaca;
    @FXML
    private TextField jumlah;
    @FXML
    private TextField pelanggan;
    @FXML
    private TextField txttotal;
    @FXML
    private Button simpan;
    private Label lbljumlah;

    
    private dbpelanggan dt= new dbpelanggan();
    private dbkacamata dt2= new dbkacamata();
    private Kacamatamodel km= new Kacamatamodel();
    
    @FXML
    private DatePicker dtptanggal;
    @FXML
    private TextField tipeframe;
    @FXML
    private TableView<Kacamatamodel> tbvdetil;
    @FXML
    private TextField idkaca;
    @FXML
    private TextField alamat;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        tipekaca.setItems(FXCollections.observableArrayList(
        "Single Vision","Bifokal","Progressive","Office Lenses","Fatigue Free Lenses"));
           
        
//        dt.getPelangganmodel().clear();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        dtptanggal.setValue(LocalDate.parse(formatter.format(LocalDate.now()),formatter));    
        tbvdetil.getColumns().clear();
        
        tbvdetil.getItems().clear();
        TableColumn col=new TableColumn("Nofaktur");
        col.setCellValueFactory(new PropertyValueFactory<Pelangganmodel, String>("Nofaktur"));
        
        tbvdetil.getColumns().addAll(col);
        col=new TableColumn("ID Kaca");
        col.setCellValueFactory(new PropertyValueFactory<Pelangganmodel, String>("idkaca"));
        
        tbvdetil.getColumns().addAll(col);
        col=new TableColumn("Tipe lensa");
        col.setCellValueFactory(new PropertyValueFactory<Pelangganmodel, String>("tipelensa"));
        
        tbvdetil.getColumns().addAll(col);
        col=new TableColumn("Frame");
        col.setCellValueFactory(new PropertyValueFactory<Pelangganmodel, String>("frame"));
              
        tbvdetil.getColumns().addAll(col);
        col=new TableColumn("Alamat");
        col.setCellValueFactory(new PropertyValueFactory<Pelangganmodel, String>("alamat"));
        
        tbvdetil.getColumns().addAll(col);
        col=new TableColumn("Jumlah");
        col.setCellValueFactory(new PropertyValueFactory<Pelangganmodel, Integer>("jumlah"));
        
        tbvdetil.getColumns().addAll(col);
        col=new TableColumn("Total harga");
        col.setCellValueFactory(new PropertyValueFactory<Pelangganmodel, Integer>("harga"));
        tbvdetil.getColumns().addAll(col);
    }    

    @FXML
    private void hitungklik(ActionEvent event) {
       
        int lns;
        switch(km.getHarga()){
            case 0 : lns=150000;break;
            case 1 : lns=150000;break;
            case 2 : lns=100000;break;
            case 3 : lns=150000;break;
            case 4 : lns=250000;break;
            case 5 : lns=120000;break;
            default: lns=0;
        }  
          
        km.setJumlah(Integer.parseInt(jumlah.getText()));
        int bayar= (lns)*(km.getJumlah());
        txttotal.setText(String.valueOf(lns));
        

      int hsl= lns*(km.getJumlah());
      txttotal.setText(String.valueOf(hsl)); 
      
      Kacamatamodel tmp=new Kacamatamodel();
      Pelangganmodel jl=new Pelangganmodel();
        
        tmp.setNofaktur(txtidjual.getText());
        tmp.setIdkaca(idkaca.getText());
        jl.setNamaplg(pelanggan.getText());
//        tmp.setTipelensa(tipekaca.getText());
        jl.setAlamat(alamat.getText());
        tmp.setHarga(Integer.parseInt(txttotal.getText()));
        tmp.setFrame(tipeframe.getText());
        tmp.setJumlah(Integer.parseInt(jumlah.getText()));
        if(dt.getKacamatamodel().get(txtidjual.getText()) ==null){
           dt.setKacamatamodel(tmp);
                    tbvdetil.getItems().add(tmp);
        }  else {
                int p=-1;
                for(int i=0;i<tbvdetil.getItems().size();i++){
                    if(tbvdetil.getItems().get(i).getNofaktur().equalsIgnoreCase(
                        txtidjual.getText()))
                        p=i; 
                }
                if(p>=0) tbvdetil.getItems().set(p, tmp);
                dt.getKacamatamodel().remove(txtidjual.getText());
                dt.setKacamatamodel(tmp);
            if(p>=0){
            tbvdetil.getItems().set(p, tmp);
            dt.getKacamatamodel().remove(txtidjual.getText());            
            dt.setKacamatamodel(tmp);
            hitungtotal();
        }
        }
        hapusklik(event);
    }

    @FXML
    private void hapusklik(ActionEvent event) {
        txtidjual.setText("");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        dtptanggal.setValue(LocalDate.parse(formatter.format(LocalDate.now()),formatter));
        idkaca.setText("");
        pelanggan.setText("");
        tipekaca.getEditor().clear();
        tipeframe.setText("");
        jumlah.setText("");
        alamat.setText("");
        txttotal.setText("");
        txtidjual.requestFocus();
    }

    @FXML
    private void keluarklik(ActionEvent event) {
        keluar.getScene().getWindow().hide();
    }

    boolean editdata=false;
    
    @FXML
    private void simpanklik(ActionEvent event) {
        dt.getPelangganmodel().setNofaktur(txtidjual.getText());
        dt.getPelangganmodel().setTanggal(Date.valueOf(dtptanggal.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        dt.getPelangganmodel().setNamaplg(pelanggan.getText());

        
        if(dt.saveall()){
        Alert a=new Alert(Alert.AlertType.INFORMATION,"Data berhasil disimpan ",ButtonType.OK);
               a.showAndWait(); 
        } else {
               Alert a=new Alert(Alert.AlertType.ERROR,"Data gagal disimpan ",ButtonType.OK);
               a.showAndWait();             
        }
        hapusklik(event);
    }
    
    public void execute(Pelangganmodel p){
        if(!p.getNofaktur().isEmpty()){
          editdata=true;
          txtidjual.setText(p.getNofaktur());
          pelanggan.setText(p.getNamaplg());
          txtidjual.setEditable(false);
          pelanggan.requestFocus();
        }
     }
    
    private void tbvdetilklik(MouseEvent event) {
        Kacamatamodel tmp=tbvdetil.getSelectionModel().getSelectedItem();
        if(tmp!=null){
        txtidjual.setText(tmp.getNofaktur());
        idkaca.setText(tmp.getIdkaca());
        
        jumlah.setText(String.valueOf(tmp.getJumlah()));
        txttotal.setText(String.valueOf(tmp.getHarga()));
        }
    }
    
    private void hitungtotal(){
        int total=0;
        for(int i=0;i<tbvdetil.getItems().size();i++){
        Kacamatamodel dtl=tbvdetil.getItems().get(i);
        total+=dtl.getHarga();
        txttotal.setText(String.valueOf(total));
        }
    }

    
}
