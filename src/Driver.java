import java.io.*;
import java.nio.*;
import java.util.*;
import java.lang.*;

public class Driver {
    public static void main(String[] args){
    // Cuma buat ngetes
        double det;
        Matriks M = new Matriks();
        Matriks N = new Matriks();
        Scanner in = new Scanner(System.in);
        M.bacaMatriks();
        M.tulisMatriks();
        det = M.detKofaktor();
        System.out.printf("Determinan : %.2f",det);
        System.out.println();
        N = M.makeInverse();
        N.tulisMatriks();
        
    }
}
