/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kacamata;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author steve
 */
public class dbpelanggan {
 private Pelangganmodel dt=new Pelangganmodel ();
  private HashMap<String,Kacamatamodel> dt2=new HashMap<String,Kacamatamodel>();

    public Pelangganmodel  getPelangganmodel() {
        return dt;
    }

    public void setPelangganmodel (Pelangganmodel  s) {
        dt = s;
    }
    
    
    public HashMap<String,Kacamatamodel> getKacamatamodel(){
        return(dt2);
    }
    
    public void setKacamatamodel(Kacamatamodel d){
        dt2.put(d.getNofaktur(), d);
    }
    
        public ObservableList<Pelangganmodel >  Load() {
         try {  
            ObservableList<Pelangganmodel> tableData=FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery(
                    "Select * from pelanggan ");
            int i = 1; 
            while (rs.next()) { 
                Pelangganmodel d=new Pelangganmodel();
                d.setNofaktur(rs.getString("NoFaktur"));
                d.setNamaplg(rs.getString("namaplg"));
                d.setAlamat(rs.getString("Alamat"));
                d.setTanggal(rs.getDate("tanggal"));
                tableData.add(d);            
                i++;          
            }
            con.tutupKoneksi();    
            return tableData;
        } catch (Exception e) {  
            e.printStackTrace(); 
            return null;       
        }   
  }
        public ObservableList<Kacamatamodel >  LoadDetil() {
          try {
            ObservableList<Kacamatamodel> tableData=FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            dt2.clear();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("Select * from kacamata");
            int i = 1;
            while (rs.next()) {
                Kacamatamodel d=new Kacamatamodel();
                d.setNofaktur(rs.getString("nofaktur"));
                d.setIdkaca(rs.getString("idkaca"));
                d.setJumlah(rs.getInt("jumlah"));
                d.setTipelensa(rs.getString("tipelensa"));
                d.setFrame(rs.getString("tipeframe"));
                d.setHarga(rs.getInt("harga"));
                tableData.add(d);
                setKacamatamodel(d);
                i++;
            }
            con.tutupKoneksi();
            return tableData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        }
   
public boolean delete(String nomor) {
    boolean berhasil = false; 
    Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.dbKoneksi.setAutoCommit(false); 
            con.preparedStatement=con.dbKoneksi.prepareStatement("delete from subjual where Nofaktur = ?");
            con.preparedStatement.setString(1, nomor); 
            con.preparedStatement.executeUpdate();
            con.preparedStatement = con.dbKoneksi.prepareStatement("delete from jual where Nofaktur = ?");
            con.preparedStatement.setString(1, nomor);  
            con.preparedStatement.executeUpdate();
            con.dbKoneksi.commit();  berhasil = true;  
        } catch (Exception e) { 
           e.printStackTrace();   
        } finally {    
            con.tutupKoneksi(); 
            return berhasil;  
        }   
}

  public boolean saveall() {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.dbKoneksi.setAutoCommit(false); // membuat semua perintah menjadi 1 transaksi
            con.preparedStatement = con.dbKoneksi.prepareStatement(
            "delete from penlanggan where nofaktur=?");
            con.preparedStatement.setString(1, getPelangganmodel().getNofaktur());
            con.preparedStatement.executeUpdate();           
            con.preparedStatement = con.dbKoneksi.prepareStatement(
            "insert into pelanggan (nofaktur,namaplg,alamat,tanggal) values (?,?,?,?)");
            con.preparedStatement.setString(1, getPelangganmodel().getNofaktur());
            con.preparedStatement.setString(2, getPelangganmodel().getNamaplg());
            con.preparedStatement.setString(3, getPelangganmodel().getAlamat());
            con.preparedStatement.setDate(4, getPelangganmodel().getTanggal());
                        con.preparedStatement.executeUpdate();
            con.preparedStatement = con.dbKoneksi.prepareStatement(
            "delete from kacamata where nofaktur=?");
            con.preparedStatement.setString(1, getPelangganmodel().getNofaktur());
            
            con.preparedStatement.executeUpdate();           
            for(Kacamatamodel sm:dt2.values()){
               con.preparedStatement = con.dbKoneksi.prepareStatement("insert into kacamata (nofaktur,idkaca,tipelensa,frame,harga, jumlah) values (?,?,?,?,?,?)");
               con.preparedStatement.setString(1, sm.getNofaktur());
               con.preparedStatement.setString(2, sm.getIdkaca());
               con.preparedStatement.setString(1, sm.getTipelensa());
               con.preparedStatement.setString(2, sm.getFrame());
               con.preparedStatement.setInt(3, sm.getHarga());
               con.preparedStatement.setInt(3, sm.getJumlah());
               con.preparedStatement.executeUpdate();
            }
            con.dbKoneksi.commit(); //semua perintah di transaksi dikerjakan
            berhasil = true;
        } catch (Exception e) {
            e.printStackTrace();
            berhasil = false;
        } finally {
            con.tutupKoneksi();
            return berhasil;
        }
    }
  
 public Pelangganmodel getdata(String nomor) {
        Pelangganmodel tmp=new Pelangganmodel();
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery(
                    "select * from pelanggan p inner join kacamata k on p.nofaktur=k.nofaktur  where nofaktur = '" + nomor +"'");
            while (rs.next()) {
                tmp.setNofaktur(rs.getString("nofaktur"));
                tmp.setNamaplg(rs.getString("namaplg"));
                tmp.setAlamat(rs.getString("Alamat"));
                tmp.setTanggal(rs.getDate("tanggal"));

            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tmp;    }

 public int validasi(String nomor) {
        int val = 0;
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery(
                    "select count(*) as jml from pelanggan where nofaktur = '" +
                            nomor +"'");
            while (rs.next()) {
                val = rs.getInt("jml");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }
}
