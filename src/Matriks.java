import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

// Konstruktor, menghandle baca matriks dan operasi dasar matriks

class Matriks{
    double[][] M;
    int brs,kol;
    Matriks(int brs,int kol){
        M = new double[brs][kol];
        this.brs = brs;
        this.kol = kol;

    }

    void bacaMatriks(){ //Input matriks dari masukan pengguna
        int i,j;
        Scanner in = new Scanner(System.in);
        for (i = 0; i < this.brs; i++) {
            for (j = 0; j < this.kol; j++) {
                System.out.print("Input elemen matriks M["+(i+1)+"]["+(j+1)+"] : ");
                this.M[i][j] = in.nextDouble();
            }
        }
    }



}