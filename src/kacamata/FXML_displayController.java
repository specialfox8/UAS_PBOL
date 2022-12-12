/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package kacamata;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author steve
 */
public class FXML_displayController implements Initializable {

    @FXML
    private TableView<Pelangganmodel> tbvpelanggan;
    @FXML
    private TableView<Kacamatamodel> tbvkacamata;
    @FXML
    private Button keluar;
    @FXML
    private Button akhir;
    @FXML
    private Button ubah;
    @FXML
    private Button hapus;
    @FXML
    private Button sesudah;
    @FXML
    private Button sebelum;
    @FXML
    private Button tambah;
    @FXML
    private Button awal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
            
    tbvpelanggan.getColumns().clear();
    tbvpelanggan.getItems().clear();
    TableColumn col=new TableColumn("No Faktur");
    col.setCellValueFactory(new PropertyValueFactory<Pelangganmodel, String>("nofaktur"));
    tbvpelanggan.getColumns().addAll(col); 
    col=new TableColumn("NamaPelanggan");
    col.setCellValueFactory(new PropertyValueFactory<Pelangganmodel, String>("namaplg"));
    tbvpelanggan.getColumns().addAll(col);
    col=new TableColumn("alamat");
    col.setCellValueFactory(new PropertyValueFactory<Pelangganmodel, String>("alamat"));
    tbvpelanggan.getColumns().addAll(col);
    col=new TableColumn("tanggal");
    col.setCellValueFactory(new PropertyValueFactory<Kacamatamodel, String>("tanggal"));
    tbvpelanggan.getColumns().addAll(col);
    
    col=new TableColumn("idkaca");
    col.setCellValueFactory(new PropertyValueFactory<Kacamatamodel, String>("idkaca"));
    tbvkacamata.getColumns().addAll(col);   
    col=new TableColumn("NoFaktur");
    col.setCellValueFactory(new PropertyValueFactory<Kacamatamodel, String>("nofaktur"));
    tbvkacamata.getColumns().addAll(col);
    col=new TableColumn("tipelensa");
    col.setCellValueFactory(new PropertyValueFactory<Kacamatamodel, String>("tipelensa"));
    tbvkacamata.getColumns().addAll(col);
    col=new TableColumn("frame");
    col.setCellValueFactory(new PropertyValueFactory<Kacamatamodel, String>("frame"));
    tbvkacamata.getColumns().addAll(col);
    col=new TableColumn("harga");
    col.setCellValueFactory(new PropertyValueFactory<Kacamatamodel, Integer>("harga"));
    tbvkacamata.getColumns().addAll(col);
    col=new TableColumn("jumlah");
    col.setCellValueFactory(new PropertyValueFactory<Kacamatamodel, Integer>("jumlah"));
    tbvkacamata.getColumns().addAll(col);
    showdata();
    }    

    private void showdata(){
        ObservableList<Pelangganmodel> data=FXMLDocumentController.dtp.Load();
        if(data!=null){ 
            tbvpelanggan.setItems(data);  
            awalkik(null);
        }else {
            Alert a=new Alert(Alert.AlertType.ERROR,"Data kosong",ButtonType.OK);
            a.showAndWait();            tbvpelanggan.getScene().getWindow().hide();;       
        }                 
    }
        

    @FXML
    private void keluarklik(ActionEvent event) {
        keluar.getScene().getWindow().hide();
    }

    @FXML
    private void akhirklik(ActionEvent event) {
        tbvpelanggan.getSelectionModel().selectLast();
        showdetil(); 
        tbvpelanggan.requestFocus();    
    }

    @FXML
    private void ubahklik(ActionEvent event) {
        Pelangganmodel s= new Pelangganmodel();       
        s=tbvpelanggan.getSelectionModel().getSelectedItem();
        try{        FXMLLoader loader=new FXMLLoader(getClass().getResource("FXML_belikaca.fxml"));    
        Parent root = (Parent)loader.load();
        FXML_belikacaController isidt=(FXML_belikacaController)loader.getController();
        isidt.execute(s);                
        Scene scene = new Scene(root);        Stage stg=new Stage();
        stg.initModality(Modality.APPLICATION_MODAL);        stg.setResizable(false);        stg.setIconified(false);        stg.setScene(scene);        stg.showAndWait();
        } catch (IOException e){   e.printStackTrace();   }
        showdata();  
        awalkik(event);
    }

    @FXML
    private void hapusklik(ActionEvent event) {
        Pelangganmodel s= new Pelangganmodel(); 
        s=tbvpelanggan.getSelectionModel().getSelectedItem();
        Alert a=new Alert(Alert.AlertType.CONFIRMATION,"Mau dihapus?",
               ButtonType.YES,ButtonType.NO);       a.showAndWait();
       if(a.getResult()==ButtonType.YES){
           if(FXMLDocumentController.dtp.delete(s.getNofaktur())){
               Alert b=new Alert(Alert.AlertType.INFORMATION,
                   "Data berhasil dihapus", ButtonType.OK); 
               b.showAndWait();
           } else {    
               Alert b=new Alert(Alert.AlertType.ERROR,    "Data gagal dihapus", ButtonType.OK);               b.showAndWait();                          }    
           showdata();      
           awalkik(event);       
       }    
    }

    @FXML
    private void sesusahklik(ActionEvent event) {
        tbvpelanggan.getSelectionModel().selectBelowCell();
        showdetil();
        tbvpelanggan.requestFocus();
    }

    @FXML
    private void sebelumklik(ActionEvent event) {
        tbvpelanggan.getSelectionModel().selectAboveCell();
        showdetil();  
        tbvpelanggan.requestFocus();
    }

    @FXML
    private void tambahklik(ActionEvent event) {
            try{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("FXML_belikaca.fxml"));    
        Parent root = (Parent)loader.load();
        Scene scene = new Scene(root);
        Stage stg=new Stage();
        stg.initModality(Modality.APPLICATION_MODAL);
        stg.setResizable(false);
        stg.setIconified(false);
        stg.setScene(scene);
        stg.show();        
        } catch (IOException e){   e.printStackTrace();   }
    }

    @FXML
    private void awalkik(ActionEvent event) {
        tbvpelanggan.getSelectionModel().selectFirst();
        showdetil();    
        tbvpelanggan.requestFocus(); 
    }
    
     private void showdetil(){
        FXMLDocumentController.dtp.getPelangganmodel().setNofaktur(
        tbvpelanggan.getSelectionModel().getSelectedItem().getNofaktur());
        ObservableList<Kacamatamodel> data=FXMLDocumentController.dtp.LoadDetil();
        if(data!=null){ tbvkacamata.setItems(data); tbvkacamata.getSelectionModel().selectFirst();
        }else {
            Alert a=new Alert(Alert.AlertType.ERROR,"Data kosong",ButtonType.OK);
            a.showAndWait();
            tbvpelanggan.getScene().getWindow().hide();;
        }                
    }
}    
