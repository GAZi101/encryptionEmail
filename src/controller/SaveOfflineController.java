/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import enkripsi.Enkrip;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import koneksidb.ConnectionDB;

/**
 *
 * @author Yuddi
 */
public class SaveOfflineController {
    private final Enkrip enkrip = new Enkrip();   
    ConnectionDB cdb = new ConnectionDB();
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public String pesan = "";
    
    public Object[][] listInbox;
    public Object[][] listSent;
    
    public String userID,password;
    public String tanggal;
    
    
    public boolean simpanInbox(){
        boolean adaKesalahan = false;
        Connection koneksi;
        
         if((koneksi = cdb.getConnection()) != null){
            int jumlahSimpan = 0;
            boolean simpan = false;
            String SQLStatement;
            Statement sta;
            try{
                SQLStatement = "delete from inbox where userid='"+userID+"' and date='"+sdf2.format(sdf.parse(tanggal))+"'"; 
                sta = koneksi.createStatement();
                sta.executeUpdate(SQLStatement);
            } catch (SQLException ex){
                adaKesalahan = true;
                pesan = ex.getMessage();
            } catch (ParseException ex) {
                Logger.getLogger(SaveOfflineController.class.getName()).log(Level.SEVERE, null, ex);
            }
                try{
                    simpan = true;
                    SQLStatement = "INSERT into inbox(userid, status, fromaddress, subject, content, date) values ('" +
                                userID + "','" + listInbox[0][0] + "','" + listInbox[0][1] + "','" + listInbox[0][2] + "','" + listInbox[0][3] + "','" + sdf2.format(sdf.parse(listInbox[0][4].toString())) + "')"; 
                    sta = koneksi.createStatement();
                    jumlahSimpan += sta.executeUpdate(SQLStatement);    
                    
                    sta.close();
                    koneksi.close();
                } catch (SQLException ex){
                    adaKesalahan = true;
                    pesan = ex.getMessage();
                } catch (ParseException ex) {
                    Logger.getLogger(SaveOfflineController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            if (simpan) {
                if (jumlahSimpan < 1){
                    adaKesalahan = true; 
                    pesan = "Gagal menyimpan data pesan masuk";
                }
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n"+cdb.getPesanKesalahan();
        }
        return !adaKesalahan;
    }
    
    public boolean simpanSent(){
        boolean adaKesalahan = false;
        Connection koneksi;
        
         if((koneksi = cdb.getConnection()) != null){
            int jumlahSimpan = 0;
            boolean simpan = false;
            String SQLStatement;
            Statement sta;
            try{
                SQLStatement = "delete from sent where userid='"+userID+"' and date='"+sdf2.format(sdf.parse(tanggal))+"'"; 
                sta = koneksi.createStatement();
                sta.executeUpdate(SQLStatement);
            } catch (SQLException ex){
                adaKesalahan = true;
                pesan = ex.getMessage();
            } catch (ParseException ex) {
                Logger.getLogger(SaveOfflineController.class.getName()).log(Level.SEVERE, null, ex);
            }
                try{
                    simpan = true;
                    SQLStatement = "INSERT into sent(userid, status, fromaddress, subject, content, date) values ('" +
                                userID + "','" + listSent[0][0] + "','" + listSent[0][1] + "','" + listSent[0][2] + "','" + listSent[0][3] + "','" + sdf2.format(sdf.parse(listSent[0][4].toString())) + "')"; 
                    sta = koneksi.createStatement();
                    jumlahSimpan += sta.executeUpdate(SQLStatement);    
                    
                    sta.close();
                    koneksi.close();
                } catch (SQLException ex){
                    adaKesalahan = true;
                    pesan = ex.getMessage();
                } catch (ParseException ex) {
                    Logger.getLogger(SaveOfflineController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            if (simpan) {
                if (jumlahSimpan < 1){
                    adaKesalahan = true; 
                    pesan = "Gagal menyimpan data pesan terkirim";
                }
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n"+cdb.getPesanKesalahan();
        }
        return !adaKesalahan;
    }
    
    public boolean simpanUser(){
        boolean adaKesalahan = false;
        Connection koneksi;
        
         if((koneksi = cdb.getConnection()) != null){
            int jumlahSimpan = 0;
            boolean simpan = false;
            String SQLStatement;
            Statement sta;
            try{
                SQLStatement = "delete from user where userid='"+userID+"'"; 
                sta = koneksi.createStatement();
                sta.executeUpdate(SQLStatement);
            } catch (SQLException ex){
                adaKesalahan = true;
                pesan = ex.getMessage();
            }
                try{
                    simpan = true;
                    System.out.println("userid="+userID);
                    System.out.println("Password="+password);
                    SQLStatement = "INSERT into user(userid, password) values ('" + userID + "','" + enkrip.enkripText2(password, "budiluhur") + "')"; 
                    sta = koneksi.createStatement();
                    jumlahSimpan += sta.executeUpdate(SQLStatement);    
                    
                    sta.close();
                    koneksi.close();
                } catch (SQLException ex){
                    adaKesalahan = true;
                    pesan = ex.getMessage();
                }
                
            if (simpan) {
                if (jumlahSimpan < 1){
                    adaKesalahan = true; 
                    pesan = "Gagal menyimpan data pengguna";
                }
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n"+cdb.getPesanKesalahan();
        }
        return !adaKesalahan;
    }
    
    public boolean bacaUser(String userid){
        boolean adaKesalahan = false;	
        Connection koneksi; 
        this.userID = "";
        this.password = "";
        
        if ((koneksi = cdb.getConnection()) != null){
            Statement sta;
            ResultSet rset;
                    
            try {
                System.out.println(userID);
                String SQLStatemen = "select * from user where userid='"+userid+"'";
                sta = koneksi.createStatement();
                rset = sta.executeQuery(SQLStatemen);
                
                rset.next();
                if (rset.getRow()>0){
                    this.userID = rset.getString("userid"); 
//                    this.password = enkrip.deskripText(rset.getString("password"), "budiluhur"); 
                    System.out.println("bacauser="+password);
                    
                } else {
                    adaKesalahan = true;
                    pesan = "user \""+userid+"\" tidak ditemukan\n"+enkrip.enkripText2(userid, "budiluhur");
                }
                
                sta.close();
                rset.close();
                koneksi.close();
            } catch (SQLException ex){
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel goods\n"+ex;
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n"+cdb.getPesanKesalahan();
        }
        
        return !adaKesalahan;
    }
    
    public boolean bacaDataInbox(String userID){
        boolean adaKesalahan = false;
        Connection connection;
        listInbox = new Object[0][0];
        
        if ((connection = cdb.getConnection()) != null){
            String SQLStatemen;
            Statement sta;
            ResultSet rSet;
            
            try {
                SQLStatemen = "select * from inbox where userid='"+userID+"' order by CONCAT(date) DESC";
                sta = connection.createStatement();
                rSet = sta.executeQuery(SQLStatemen);
                
                rSet.next();
                rSet.last();
                listInbox = new Object[rSet.getRow()][5];
                
                if (rSet.getRow()>0){
                    rSet.first();
                    int i=0;
                    do {
                        listInbox[i] = new Object[]{rSet.getString("status"),Boolean.FALSE, rSet.getString("fromaddress"), rSet.getString("subject"), rSet.getString("content"), sdf.format(sdf2.parse(rSet.getString("date")))};
                        i++;
                    } while (rSet.next());
                }
                
                sta.close();
                rSet.close();
                connection.close();
            } catch (SQLException ex){
                adaKesalahan = true;
                pesan = "Tidak dapat membaca data pesan masuk\n"+ex.getMessage();
            } catch (ParseException ex) {
                Logger.getLogger(SaveOfflineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n"+cdb.getPesanKesalahan();
        }
        if(adaKesalahan)
        System.out.println(pesan);
        return !adaKesalahan;
    }
    
    public boolean bacaDataSent(String userID){
        boolean adaKesalahan = false;
        Connection connection;
        listSent = new Object[0][0];
        
        if ((connection = cdb.getConnection()) != null){
            String SQLStatemen;
            Statement sta;
            ResultSet rSet;
            
            try {
                SQLStatemen = "select * from sent where userid='"+userID+"' order by CONCAT(date) DESC";
                sta = connection.createStatement();
                rSet = sta.executeQuery(SQLStatemen);
                
                rSet.next();
                rSet.last();
                listSent = new Object[rSet.getRow()][5];
                
                if (rSet.getRow()>0){
                    rSet.first();
                    int i=0;
                    do {
                        listSent[i] = new Object[]{rSet.getString("status"),Boolean.FALSE, rSet.getString("fromaddress"), rSet.getString("subject"), rSet.getString("content"), sdf.format(sdf2.parse(rSet.getString("date")))};
                        i++;
                    } while (rSet.next());
                }
                
                sta.close();
                rSet.close();
                connection.close();
            } catch (SQLException ex){
                adaKesalahan = true;
                pesan = "Tidak dapat membaca data pesan terkirim\n"+ex.getMessage();                
            } catch (ParseException ex) {
                Logger.getLogger(SaveOfflineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n"+cdb.getPesanKesalahan();
        }
        if(adaKesalahan)
        System.out.println(pesan);
        return !adaKesalahan;
    }
    
    public boolean hapus(String Folder,String tgl){
        boolean adaKesalahan = false;	
        Connection connection; 
        
        if ((connection = cdb.getConnection()) != null){
            int jumlahHapus;
            Statement sta;
            
            try {
                String SQLStatemen = "delete from "+Folder+" where date='"+sdf2.format(sdf.parse(tgl))+"'";
                sta = connection.createStatement();
                jumlahHapus = sta.executeUpdate(SQLStatemen);
                if (jumlahHapus < 1){
                    pesan = "pesan pada "+tgl+" tidak ditemukan";
                    System.out.println(pesan);
                    adaKesalahan = true;
                }
                sta.close();
                connection.close();
            } catch (SQLException ex){
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel '"+Folder+"'\n"+ex;
            } catch (ParseException ex) {
                Logger.getLogger(SaveOfflineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n"+cdb.getPesanKesalahan();
        }
        return !adaKesalahan;
    }
    
}
