
import java.io.*;
import java.nio.*;
import java.util.*;
import java.lang.*;


// How to run : javac *.java && java Driver

public class Driver {
	public static void main(String[] args) throws Exception {
		// Cuma buat ngetes
        double det;
        Matriks M = new Matriks();
        Matriks N = new Matriks();
        Matriks O = new Matriks();
        Scanner in = new Scanner(System.in);
        M.bacafileMatriks();
        M.tulisMatriks();
        M.tulisfileMatriks();
        /*M.bacaMatriks();
        M.tulisMatriks();
        /*
        det = M.detKofaktor();
        System.out.printf("Determinan : %.2f",det);
        System.out.println();
        N = M.makeInverse();
        N.tulisMatriks();
        */
        /*N = M.echelon();
        N.tulisMatriks();
        
        O = M.reducedEchelon();
        O.tulisMatriks(); */
        
	}
}
