package algeo1;
import java.util.Scanner;

public class Driver {
	public static void main(String[] args) {
		// Cuma buat ngetes
        double det;
        Matriks M = new Matriks();
        Matriks N = new Matriks();
        Matriks O = new Matriks();
        Scanner in = new Scanner(System.in);
        M.bacaMatriks();
        M.tulisMatriks();
        /*
        det = M.detKofaktor();
        System.out.printf("Determinan : %.2f",det);
        System.out.println();
        N = M.makeInverse();
        N.tulisMatriks();
        */
        N = M.echelon();
        N.tulisMatriks();
        
        O = M.reducedEchelon();
        O.tulisMatriks();
        
	}
}
