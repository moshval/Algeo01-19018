import java.io.*;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.lang.*;

public class Menu {
    public static void main(String[] args ) throws Exception {
        Scanner in = new Scanner(System.in);
        Matriks M = new Matriks();
        System.out.println("------------------------------------------------");
        System.out.println("|                                              |");
        System.out.println("|                                              |");
        System.out.println("|                 KALKULATRIX                  |");
        System.out.println("|              Kalkulator Matrix               |");
        System.out.println("|           Tar diedit mau ngisi apa           |");
        System.out.println("|                                              |");
        System.out.println("|                                              |");
        System.out.println("------------------------------------------------");
        System.out.println();
        displayMenu(M);

    }

    public static void displayMenu(Matriks M) throws Exception {
        Scanner in = new Scanner(System.in);
        int inp;
        System.out.println("         MAIN MENU        ");
        System.out.println();
        System.out.println("1. Sistem Persamaan Linier");
        System.out.println("2. Determinan");
        System.out.println("3. Matriks balikan");
        System.out.println("4. Interpolasi Polinom");
        System.out.println("5. Regresi linier berganda");
        System.out.println("6. Keluar");
        System.out.println();
        System.out.println("Operasi apa yang anda inginkan? (1-6)");
        inp = in.nextInt();
        while(inp<1 || inp>6){
            System.out.println("Ulangi lagi. Pastikan pilihan integer diantara (1-6)");
            inp = in.nextInt();
        }
        if(inp==1){ //SPL
            displayMenu1(M);
        }
        else if(inp==2){ //Determinan
            displayMenu2(M);
        }
        else if(inp==3){ //Invers
            displayMenu3(M);
        }
        else if(inp==4){ //Interpolasi
            //displayMenu4(M);
        }
        else if(inp==5){ //Regresi
            //M.Regresi();
        }
        else if(inp==6){ //Keluar
            int sure;
            System.out.print("R u sure (0 / 1)?");
            sure = in.nextInt();
            while(sure!=0 && sure!=1){
                System.out.println("Ulangi lagi. Pastikan pilihan 0 atau 1");
                sure = in.nextInt();
            }
            if(sure==0){
                displayMenu(M);
            }
            else if(sure==1){
                System.out.println("Terima kasih kk");
                System.exit(0);

            }
        }
    }

    public static void displayMenu1(Matriks M) throws Exception{ //SPL
        Scanner in = new Scanner(System.in);
        int input,cho;
        System.out.println("Pilihan Metode : ");
        System.out.println("1. Metode Eliminasi Gauss ");
        System.out.println("2. Metode Eliminasi Gauss-Jordan ");
        System.out.println("3. Metode matriks balikan ");
        System.out.println("4. Kaidah Cramer ");
        System.out.print("Silakan pilih metode : ");
        input = in.nextInt();
        while(input<1 || input>4){
            System.out.println("Ulangi lagi. Pastikan pilihan integer diantara (1-4)");
            input = in.nextInt();
        }
        if(input==1||input==2||input==3||input==4){
            System.out.println("Apakah anda mau inputnya ngetik/copas sendiri (0) atau dari file text (1) ?");
            cho = in.nextInt();
            while(cho!=0 && cho!=1){
                System.out.println("Ulangi lagi. Pastikan pilihan 0 atau 1");
                cho = in.nextInt();
            }
            if(cho==0){
                M.bacaMatriks();
            }
            else if(cho==1){
                M.bacafileMatriks();
            }
            
            if(input==1){
                    //Gauss
                    M.splGauss(M);
                }
            else if(input==2){
                    //Gauss Jordan
                }
            else if(input==3){
                if(M.brs!=M.kol) System.out.println("Matriks tidak berlaku untuk operasi ini");
                else{
                    //Balikan
                }
            }
            else if(input==4){
                if(M.brs!=M.kol) System.out.println("Matriks tidak berlaku untuk operasi ini");
                else{
                    //Cramer
                }
            }
        }
        System.out.print("Apakah anda ingin tetap melakukan operasi ini(0) atau move on(1)? ");
        input = in.nextInt();
        while(input!=0 && input!=1){
            System.out.println("Ulangi lagi. Pastikan pilihan 0 atau 1");
            input = in.nextInt();
        }
        if(input==0){
            displayMenu1(M);
        }
        else if(input==1){
            displayMenu(M);
        }

    }

    public static void displayMenu2(Matriks M) throws Exception{ //DETERMINAN
        Scanner in = new Scanner(System.in);
        int input,cho;
        System.out.println("Pilihan Metode : ");
        System.out.println("1. Metode Eliminasi Gauss ");
        System.out.println("2. Metode ekspansi kofaktor-minor ");
        System.out.print("Silakan pilih metode : ");
        input = in.nextInt();
        while(input<1 || input>2){
            System.out.println("Ulangi lagi. Pastikan pilihan integer diantara (1-2)");
            input = in.nextInt();
        }
        if(input==1||input==2){
            System.out.println("Apakah anda mau inputnya ngetik/copas sendiri (0) atau dari file text (1) ?");
            cho = in.nextInt();
            while(cho!=0 && cho!=1){
                System.out.println("Ulangi lagi. Pastikan pilihan 0 atau 1");
                cho = in.nextInt();
            }
            if(cho==0){
                M.bacaMatriks();
            }
            else if(cho==1){
                M.bacafileMatriks();
            }
            
            if(M.brs!=M.kol) System.out.println("Matriks tidak berlaku untuk operasi ini");
            else{
                if(input==1){
                    //Gauss 
                    M.detGJ();
                }
                else if(input==2){
                    //Kofaktor
                    M.detKofaktor();
                    //lanjutin dong
                }
            }

        }
        System.out.print("Apakah anda ingin tetap melakukan operasi ini(0) atau move on(1)? ");
        input = in.nextInt();
        while(input!=0 && input!=1){
            System.out.println("Ulangi lagi. Pastikan pilihan 0 atau 1");
            input = in.nextInt();
        }
        if(input==0){
            displayMenu2(M);
        }
        else if(input==1){
            displayMenu(M);
        }
    }

    public static void displayMenu3(Matriks M) throws Exception{ //Invers
        Scanner in = new Scanner(System.in);
        int input,cho;
        System.out.println("Pilihan Metode : ");
        System.out.println("1. Metode Eliminasi Gauss-Jordan ");
        System.out.println("2. Metode Adjoin ");
        System.out.print("Silakan pilih metode : ");
        input = in.nextInt();
        while(input<1 || input>2){
            System.out.println("Ulangi lagi. Pastikan pilihan integer diantara (1-2)");
            input = in.nextInt();
        }
        if(input==1||input==2){
            System.out.println("Apakah anda mau inputnya ngetik/copas sendiri (0) atau dari file text (1) ?");
            cho = in.nextInt();
            while(cho!=0 && cho!=1){
                System.out.println("Ulangi lagi. Pastikan pilihan 0 atau 1");
                cho = in.nextInt();
            }
            if(cho==0){
                M.bacaMatriks();
            }
            else if(cho==1){
                M.bacafileMatriks();
            }
            
            if(M.brs!=M.kol) System.out.println("Matriks tidak berlaku untuk operasi ini");
            else{
                if(input==1){
                    //Gauss Jordan
                    //M.inverseGJ();
                }
                else if(input==2){
                    //Kofaktor
                    M.makeInverse();
                    //lanjutin dong
                }
            }

        }
        System.out.print("Apakah anda ingin tetap melakukan operasi ini(0) atau move on(1)? ");
        input = in.nextInt();
        while(input!=0 && input!=1){
            System.out.println("Ulangi lagi. Pastikan pilihan 0 atau 1");
            input = in.nextInt();
        }
        if(input==0){
            displayMenu3(M);
        }
        else if(input==1){
            displayMenu(M);
        }
    }

    public static void displayMenu4(Matriks M) throws Exception{ //Interpolasi
        Scanner in = new Scanner(System.in);
        M.gjInterpol();
        System.out.print("Apakah anda ingin tetap melakukan operasi ini(0) atau move on(1)? ");
        input = in.nextInt();
        while(input!=0 && input!=1){
            System.out.println("Ulangi lagi. Pastikan pilihan 0 atau 1");
            input = in.nextInt();
        }
        if(input==0){
            displayMenu4(M);
        }
        else if(input==1){
            displayMenu(M);
        }
    }

    public static void displayMenu5(Matriks M) throws Exception{ //Regresi Linear
        Scanner in = new Scanner(System.in);
        //M.gjInterpol();
        System.out.print("Apakah anda ingin tetap melakukan operasi ini(0) atau move on(1)? ");
        input = in.nextInt();
        while(input!=0 && input!=1){
            System.out.println("Ulangi lagi. Pastikan pilihan 0 atau 1");
            input = in.nextInt();
        }
        if(input==0){
            displayMenu5(M);
        }
        else if(input==1){
            displayMenu(M);
        }
    }

    
    

    


}

