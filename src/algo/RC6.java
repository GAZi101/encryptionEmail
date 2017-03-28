package algo;


import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


public class RC6 {
    private int w=32, r=20;
    private int Pw=0xb7e15163, Qw=0x9e3779b9;
    private int[] S;
   
/*public static void main(String[]args){
     appRC6 x=new appRC6();
     x.mulai();
}

void mulai(){
    try {
        String password="tonnyaray";
       
        String path=System.getProperty("user.dir")+"\\tesdoc\\";
        System.out.println("path="+path);       
        String pathFile=path+"listing_pc.docx";
       
        File fpathFile=new File(pathFile);
        String nf=fpathFile.getName();//tesdoc.doc
        String absnf=fpathFile.getParent();//nama folder
      
        appRC6 x=new appRC6();
        byte[]doc2Byte=x.doc2Byte(pathFile);
        byte[]passByte=x.STB2(password);
       
        String nfEn=absnf+"\\EN-"+nf;
        byte[] encrypted = x.encrypt(doc2Byte,passByte);//x.encrypt(kalisidoc, password);    
        x.saveByte(encrypted,nfEn);
       
        //+++++++++++++++++++++++++++++++++++++
        //Baca FIle Dekrip
        String passworddek="tonnyaray";
       
//        String pilihfile="";//D:\foldrer/a.doc
//        String saveke="";//D:\skripsi\
//        File ff=new File(pilihfile);
//        String NFs=ff.getName();
//        pathFileEn=saveke+"\\EN-"+NFs;
       
        String pathFileEn=nfEn;
        File fpathFileDek=new File(pathFileEn);
        String nfdec=fpathFileDek.getName();
        String absnfdec=fpathFileDek.getParent();
    
        appRC6 xx=new appRC6();
        byte[]doc2ByteDek=xx.doc2Byte2(pathFileEn);
         byte[]passByteDek=x.STB2(passworddek);
        
        String nfDek=absnfdec+"\\DEK-"+nfdec;

        byte[]doc2ByteXX=xx.decrypt(doc2ByteDek,passByteDek);
        xx.saveByte(doc2ByteXX,nfDek); 
       
    } catch (Exception ex) {
        Logger.getLogger(appRC6.class.getName()).log(Level.SEVERE, null, ex);
    }
}
 

String BTS(byte[]result){
            String str = Base64.encode(result);//encodeBase64String
            return str;
        }

byte [] STB2(String s){
            char [] x = s.toCharArray();
            byte [] hs = new byte[x.length];
            for (int i = 0;i<x.length;i++){
                hs [i] = (byte) (int)x[i];
            }
            return hs;
 }

byte[] STB3 (String s) throws Base64DecodingException{
            byte [] hs = Base64.decode(s);
            return hs;
     }
 
*/
//    private String filename;
//    private String directory;
//    private String fileNameEncrypt;
//    private String extension;
 
//    public void setFile(String directory, String filename, String extens){
//        this.fileNameEncrypt = directory + "/" + "en." + filename + "." + extens;
//        System.out.println("fileNameEncrypt="+fileNameEncrypt);
//    }
 
    private int[] convBytesWords(byte[] key, int u, int c) {
        int[] tmp = new int[c];
        for (int i = 0; i < tmp.length; i++)
            tmp[i] = 0;
     
        for (int i = 0, off = 0; i < c; i++)
            tmp[i] = ((key[off++] & 0xFF)) | ((key[off++] & 0xFF) << 8)
                    | ((key[off++] & 0xFF) << 16) | ((key[off++] & 0xFF) << 24);
     
        return tmp;
    }
 
 
    //penjadwalan kunci RC 6 mencampurkan aray S dengan L
    private int[] generateSubkeys(byte[] key) {
        int u = w / 8;
        int c = key.length / u;
        int t = 2 * r + 4;
     
        int[] L = convBytesWords(key, u, c);
     
        int[] S = new int[t];
        S[0] = Pw;
        for (int i = 1; i < t; i++)
            S[i] = S[i - 1] + Qw;
     
        int A = 0;
        int B = 0;
        int k = 0, j = 0;
     
        int v = 3 * Math.max(c, t);
     
        for (int i = 0; i < v; i++) {
            A = S[k] = rotl((S[k] + A + B), 3);
            B = L[j] = rotl(L[j] + A + B, A + B);
            k = (k + 1) % t;
            j = (j + 1) % c;
        }
        return S;
    }
 
    //penggeseran bit kekiri
    private int rotl(int val, int pas) {
        return (val << pas) | (val >>> (32 - pas));
    }
 
    //penggeseran bit ke kanan
    private int rotr(int val, int pas) {
        return (val >>> pas) | (val << (32-pas));
    }
 
 
    //memecah blok cipertext kedalam 4 register
    private byte[] decryptBloc(byte[] input) {
        byte[] tmp = new byte[input.length];
        int t,u;
        int aux;
        int[] data = new int[input.length/4];
     
        for(int i =0;i<data.length;i++)
            data[i] = 0;
        int off = 0;
     
        for(int i=0;i<data.length;i++){
            data[i] = ((input[off++]&0xff))|
                    ((input[off++]&0xff) << 8) |
                    ((input[off++]&0xff) << 16) |
                    ((input[off++]&0xff) << 24);
        }
     
        int A = data[0],B = data[1],C = data[2],D = data[3];
     
        C = C - S[2*r+3];
        A = A - S[2*r+2];
        for(int i = r;i>=1;i--) {
            aux = D;
            D = C;
            C = B;
            B = A;
            A = aux;
         
            u = rotl(D*(2*D+1),5);
            t = rotl(B*(2*B + 1),5);
            C = rotr(C-S[2*i + 1],t) ^ u;
            A = rotr(A-S[2*i],u) ^ t;
        }
     
        D = D - S[1];
        B = B - S[0];
     
        data[0] = A;data[1] = B;data[2] = C;data[3] = D;
     
        for(int i = 0;i<tmp.length;i++) {
            tmp[i] = (byte)((data[i/4] >>> (i%4)*8) & 0xff);
        }
        return tmp;
    }
 
    //memecah blok plaintext kedalam 4 register
    private byte[] encryptBloc(byte[] input) {
        byte[] tmp = new byte[input.length];
        int t,u;
        int aux;
        int[] data = new int[input.length/4];
        for(int i =0;i<data.length;i++)
            data[i] = 0;
        int off = 0;
        for(int i=0;i<data.length;i++) {
            data[i] = ((input[off++]&0xff))|
                    ((input[off++]&0xff) << 8) |
                    ((input[off++]&0xff) << 16) |
                    ((input[off++]&0xff) << 24);
        }
     
        int A = data[0],B = data[1],C = data[2],D = data[3];
     
        B = B + S[0];
        D = D + S[1];
        for(int i = 1;i<=r;i++) {
            t = rotl(B*(2*B+1),5);
            u = rotl(D*(2*D+1),5);
            A = rotl(A^t,u)+S[2*i];
            C = rotl(C^u,t)+S[2*i+1];
         
            aux = A;
            A = B;
            B = C;
            C = D;
            D = aux;
        }
        A = A + S[2*r+2];
        C = C + S[2*r+3];
     
        data[0] = A;data[1] = B;data[2] = C;data[3] = D;
     
        for(int i = 0;i<tmp.length;i++) {
            tmp[i] = (byte)((data[i/4] >>> (i%4)*8) & 0xff);
        }
        return tmp;
    }
 
    //proses padding awal dalam penjadwalan kunci
    private static  byte[] paddingKey(byte[] key){
        int l=0;
        if(key.length==1){
            l=3;
        }
        else
            l=key.length%4;
        byte[]key2=new byte[key.length+l];
     
        for(int i=0;i<key2.length;i++){
            if(i<key.length){
                key2[i]=key[i];
            }
            else{
                key2[i]=0;
            }
        }
        return key2;
    }
 
    //fungsi untuk melakukan enkripsi RC6
    public  byte[] encrypt(byte[] data, byte[] key) { 
        byte[] bloc = new byte[16];
        key = paddingKey(key);
        S = generateSubkeys(key);

        int lenght = 16 - data.length % 16;
        byte[] padding = new byte[lenght];
        padding[0] = (byte) 0x80;

        for (int i = 1; i < lenght; i++)
                padding[i] = 0;
        int count = 0;
        byte[] tmp = new byte[data.length+lenght];

        int i;
        for(i=0;i<data.length+lenght;i++) {
            if(i>0 && i%16 == 0) {
                bloc = encryptBloc(bloc);
                System.arraycopy(bloc, 0, tmp, i-16, bloc.length);
            }

            if (i < data.length)
                bloc[i % 16] = data[i];
            else {             
                bloc[i % 16] = padding[count];
                count++;
                if(count>lenght-1) count = 1;
            }
        }
        bloc = encryptBloc(bloc);
        System.arraycopy(bloc, 0, tmp, i - 16, bloc.length);
        return tmp;              
    }

    //fungsi untuk melakukan dekripsi RC6
    public  byte[] decrypt(byte[] data, byte[] key) {
        byte[] tmp = new byte[data.length];
        byte[] bloc = new byte[16];
        key = paddingKey(key);
        S = generateSubkeys(key);

        int i;
        for(i=0;i<data.length;i++) {
            if(i>0 && i%16 == 0) {
                bloc = decryptBloc(bloc);
                System.arraycopy(bloc, 0, tmp, i-16, bloc.length);
            }

            if (i < data.length)
                bloc[i % 16] = data[i];
        }

        bloc = decryptBloc(bloc);
        System.arraycopy(bloc, 0, tmp, i - 16, bloc.length);

        tmp = deletePadding(tmp);
        return tmp;
    }

    //proses penghilangan padding pada key
    private byte[] deletePadding(byte[] input) {
        int count = 0;

        int i = input.length - 1;
        while (input[i] == 0) {
                count++;
                i--;
        }

        byte[] tmp = new byte[input.length - count - 1];
        System.arraycopy(input, 0, tmp, 0, tmp.length);
        return tmp;
    }
     
    public static String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
                int v = b[i] & 0xff;
                if (v < 16) {
                        sb.append('0');
                }
                sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }

//    public byte[] encrypt(String content, byte[] bytes) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    public String decrypt(byte[] bcs, String kunci) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
   


private String byteArrayToHexString(String b) {
        StringBuffer sb = new StringBuffer(b.length() * 2);
        for (int i = 0; i < b.length(); i++) {
            char[]bb=b.toCharArray();////
            int v = bb[i] & 0xff;
            if (v < 16) {
                    sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }

/*   byte[] doc2Byte2(String NF){  
            File f = new File(NF);
            byte[] bit = new byte[(int)f.length()];
            try{
                FileInputStream fis = new FileInputStream(f);
                fis.read(bit);
                fis.close();
            }catch(Exception exc){}
           
            //hasil = DatatypeConverter.printBase64Binary(bit);
   return bit;        
   }
           
   
    byte[] doc2Byte(String NF){
        Path path = Paths.get(NF);
        byte[] data=null;
        try {
            data = Files.readAllBytes(path);
        } catch (IOException ex) {
            Logger.getLogger(RC6.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
   
   String byte2String(byte[]data){
      String gab="";
        for(int i=0; i < data.length; i++) {
            String al = String.valueOf(data[i]& 0xff ); // & 0xff unsigned integer
             int ii=Integer.parseInt(al);
             char c=(char)ii;
             gab=gab+c;
         }
        return gab;
   }
   
     private void saveString(String dataString,String NF) {
          try {
           String content = dataString;
           File file = new File(NF);
           System.out.println(NF);
           if (!file.exists()) {
               file.createNewFile();
           }
          
           FileWriter fw = new FileWriter(file.getAbsoluteFile());
           BufferedWriter bw = new BufferedWriter(fw);
           bw.write(content);
           bw.close();
           System.out.println("File Berhasil di simpan ! lihat di : "+NF);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
     private void saveByte(byte[] databyte,String NF)  {
        try {
            Path path = Paths.get(NF);
            Files.write(path, databyte); //creates, overwrites
            System.out.println("File Berhasil di dekripsi ! lihat di : " + NF);
        } catch (IOException ex) {
            Logger.getLogger(RC6.class.getName()).log(Level.SEVERE, null, ex);
        }
     }  
     String byte2String2(byte[]data){
    String gab="";
        for(int i=0; i < data.length; i++) {
              char c=(char)(int)data[i];
              gab=gab+c;
         }
        return gab;
}

byte[] string2Byte(String data){
    char[]ar=data.toCharArray();
    byte[]bit=new byte[ar.length];
        for(int i=0; i < ar.length; i++) {
              int c=(int)ar[i];
              bit[i]=(byte)c;
         }
        return bit;
}*/
}
