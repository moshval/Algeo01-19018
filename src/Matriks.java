import java.io.*;
import java.nio.*;
import java.util.*;
import java.lang.*;

// Konstruktor, menghandle baca matriks dan operasi dasar matriks

public class Matriks{
    double[][] M = new double[150][150]; // inisialisasi ukuran max 150 x 150
    int brs,kol;
    Matriks(){
        for (int i = 0; i < 150; i++) {
            for (int j = 0; j < 150; j++) {
                this.M[i][j] = -999; //inisialisasi idxUndef 
            }
        }
        this.brs = 0;
        this.kol = 0;

    }

    public void bacaMatriks(){ //Input matriks dari masukan pengguna
        int i,j;
        Scanner in = new Scanner(System.in);
        System.out.print("Input jumlah baris : ");
        this.brs = in.nextInt();
        System.out.print("Input jumlah kolom : ");
        this.kol = in.nextInt();
        System.out.println("Input elemen matriks : ");
        for (i = 0; i < this.brs; i++) {
            for (j = 0; j < this.kol; j++) {
                this.M[i][j] = in.nextDouble();
            }
        }
    }

    public void tulisMatriks(){ //Menuliskan matriks ke layar
        int i,j;
        System.out.println("Matriks anda adalah : ");
        for ( i = 0; i < this.brs; i++) {
            for ( j = 0; j < this.kol; j++) {
                System.out.printf("%.4f ",this.M[i][j] + 0.000);
            }
            System.out.println();
        }
    }

    public Matriks copyMatriks(){ // mengcopy matrikss
        int i,j;
        Matriks cp = new Matriks();
        cp.brs = this.brs;
        cp.kol = this.kol;
        for (i = 0; i < cp.brs; i++) {
            for (j = 0; j < cp.kol; j++) {
                cp.M[i][j] = this.M[i][j];
            }
        }
        return cp;

    }

    public Matriks transposeMatriks(){ // membuat transpose matriks
        Matriks tp = new Matriks();
        tp.brs = this.kol;
        tp.kol = this.brs;
        for (int i = 0; i < this.brs; i++) {
            for (int j = 0; j < this.kol; j++) {
                tp.M[i][j] = this.M[j][i];
            }
        }
        return tp;
    }


    public Matriks makeKofaktor(){ //membuat matriks kofaktor
        Matriks kf = new Matriks();
        kf.brs = this.brs;
        kf.kol = this.kol;
        double konst;
        for (int i = 0; i < kf.brs; i++) {
            if(i%2==0){
                konst = 1.0;
            }
            else{
                konst = -1.0;
            }
            for (int j = 0; j < kf.kol; j++) {
                kf.M[i][j] = konst * ((this.makeMinor(i, j)).detKofaktor());
                konst = -1.0*konst;
            }
        }
        return kf;
    }

    public Matriks makeMinor(int b, int k){ //Membuat minor matriks di setiap b,k untuk b,k indeks baris,kolom matriks
        Matriks mno = new Matriks();
        mno.brs = this.brs - 1;
        mno.kol = this.kol - 1;
        int ik = 0;
        for (int i = 0; i < this.brs; i++) {
            if(i!=b){
                int jk = 0;
                for (int j = 0; j < this.kol; j++) {
                    if(j!=k){
                        mno.M[ik][jk] = this.M[i][j];
                        jk++;
                    }
                }
                ik++;

            }
        }
        return mno;
    }

    public Matriks makeAdjoint(){ // Membuat adjoin suatu matriks dengan mentranspos kofaktor
        return (this.makeKofaktor()).transposeMatriks();
    }

    public double detKofaktor(){ //menghitung determinan matriks dengan ekspansi kofaktor-minor
        double det = 0;
        int i,ii,ik,ji,jk;
        Matriks dk = this.copyMatriks();
        if (dk.brs == 1) {
            det = dk.M[0][0];
        }
        else{
            for (i = 0; i < dk.brs; i++) {
                Matriks mnr = new Matriks();
                mnr.brs = dk.brs - 1;
                mnr.kol = dk.kol - 1;
                ik = 0;
                for (ii = 0; ii < dk.brs; ii++) {
                    if(ii!=i){
                        jk = 0;
                        for (ji = 1; ji < dk.kol; ji++) {
                            mnr.M[ik][jk] = dk.M[ii][ji];
                            jk++;
                        }
                        ik++;
                    }
                }
                if(i%2 == 0){
                    det+=dk.M[i][0] * (mnr.detKofaktor());
                }
                else{
                    det+=dk.M[i][0] * (mnr.detKofaktor()) * -1;
                }
            }

        }
        return det;
          
    }
    
    public Matriks makeInverse(){ // Membuat invers suatu matriks , metode adjoin
        Matriks inv = this.makeAdjoint();
        if(this.detKofaktor() != 0){
            double perdet = this.detKofaktor();
            for (int i = 0; i < inv.brs; i++) {
                for (int j = 0; j < inv.kol; j++) {
                    inv.M[i][j] =  inv.M[i][j] / perdet;
                }
            }
        }
        return inv;
    }









}
