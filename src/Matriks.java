import java.io.*;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.lang.*;

public class Matriks {
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

    public void bacafileMatriks() throws Exception{ //input matriks dari file text
        String filename;
        Scanner in = new Scanner(System.in);
        System.out.print("Masukkan nama file matriks (beserta ekstensi, contoh : matriks.txt) : ");
        filename = in.nextLine();
        filename = "../test/" + filename;
        if(Files.notExists(Paths.get(filename))) {
            System.out.println("File not found, ulangi lagi");
            this.bacafileMatriks();}
        else{
        FileReader fr = (new FileReader(filename));
        //BufferedReader br = new BufferedReader(fr);
        int elm;
        String mat = "";
        
        while((elm=fr.read()) != -1){
            //System.out.print((char) line);
            mat +=((char)elm);
        }
        mat=mat.trim();
        //System.out.print(mat);
        mat+='\n';
        if (mat.length() != 0){
            int i = 0;
            int j = 0;
            int l = 0;
            String cc ="";
            while(l<mat.length()){
                if(mat.charAt(l) == ' ') continue;
                while ((mat.charAt(l) != ' ' && mat.charAt(l) != '\n')){
                    cc+=mat.charAt(l);   
                    l++;
                }
                    this.M[i][j] = Double.parseDouble(cc);
                    j++;
                    cc= "";

                    if((mat.charAt(l) == '\n')){
                        i++;
                        this.kol = j;
                        j = 0;
                     }
                     l++;
            }
            this.brs = i;

        } 

    }
        
    }

    public void tulisfileMatriks(){ //menuliskan matriks ke file
        //Convert matrix ke string
        String mf ="";
        for (int i = 0; i < this.brs; i++) {
            for (int j = 0; j < this.kol; j++) {
                mf+=Double.toString(this.M[i][j]); 
                if(j==this.kol - 1) mf+="\n";
                else mf+=" "; }
            
        }

        //Saving to file
        String filename;
        Scanner in = new Scanner(System.in);
        System.out.print("Masukkan nama file tujuan (beserta ekstensi, contoh : matriks.txt) : ");
        filename = in.nextLine();
        filename = "../test/" + filename;
        try{
            Formatter fw = new Formatter(filename);
            fw.format("%s",mf);
            fw.close();
            System.out.println("Sukses");
        }
        catch(IOException e){
            System.out.println("Error");
            e.printStackTrace();
        }

    }

    public void tulisMatriks(){ //Menuliskan matriks ke layar
        int i,j;
        //System.out.println("Matriks anda adalah : ");
        for ( i = 0; i < this.brs; i++) {
            for ( j = 0; j < this.kol; j++) {
                System.out.printf("%.4f ",this.M[i][j] + 0.0000);
            }
            System.out.println();
        }
    }

    public void tulisfileSPL(String sol){
        //Saving to file
        String filename;
        Scanner in = new Scanner(System.in);
        System.out.print("Masukkan nama file tujuan (beserta ekstensi, contoh : matriks.txt) : ");
        filename = in.nextLine();
        filename = "../test/" + filename;
        try{
            Formatter fw = new Formatter(filename);
            fw.format("%s",sol);
            fw.close();
            System.out.println("Sukses");
        }
        catch(IOException e){
            System.out.println("Error");
            e.printStackTrace();
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
                    det+=dk.M[i][0] * (mnr.detKofaktor()) * -1.0000;
                }
            }

        }
        return det;
          
    }
    
    public Double detGJ(){ //Determinan menggunakan eliminasi Gauss
        Double det = 0.0000;
        Matriks dgj = this.copyMatriks();
        if(dgj.brs==1){
            det = dgj.M[0][0];}
        else{
            det = 1.0000;
            for (int i = 0; i < dgj.brs; i++) {
                int k = i;
                for (int j = i+1; j < dgj.kol; j++) {
                    if(Math.abs(dgj.M[j][i])>Math.abs(dgj.M[k][i])){
                        k = j;
                    }
                }
                if(dgj.M[k][i]==0.0000){
                    det = 0.0000;
                    break;
                }

                dgj.swapBrs(dgj, i, k);
                if(i!=k) det*= -1.0000;
                det*= dgj.M[i][i];
                for (int l = i+1; l < dgj.brs; l++) {
                    dgj.M[i][l] = dgj.M[i][l] / dgj.M[i][i];
                }
                for (int m = 0; m < dgj.brs;m++) {
                    if(m!=i){
                        for (int n = i+1; n < dgj.brs; n++) {
                            dgj.M[m][n] = dgj.M[m][n] - dgj.M[i][n]*dgj.M[m][i];
                        }
                    }
                }
                
            }

        }

        return det;
        

    }
    
    public Matriks makeInverse(){ // Membuat invers suatu matriks , metode adjoin , berlaku untuk matriks persegi
        Matriks inv = this.makeAdjoint();
        if(this.detKofaktor() != 0){
            if(this.brs == 1 && this.kol ==1 && this.M[0][0] != 0) inv.M[0][0] = 1/this.M[0][0];
            else{
                double perdet = this.detKofaktor();
                for (int i = 0; i < inv.brs; i++) {
                    for (int j = 0; j < inv.kol; j++) {
                        inv.M[i][j] =  inv.M[i][j] / perdet;
                    }
                }
            }
        }
        return inv;
    }
    
    
    public void swapBrs(Matriks M, int i_1, int i_2){
        int j;
        double temp;
        M.kol = this.kol;
        for (j = 0; j < this.kol; j++){
            temp = this.M[i_1][j];
            this.M[i_1][j] = this.M[i_2][j];
            this.M[i_2][j] = temp;
        }
    }
	
    
    public void kaliBrs(Matriks M, int i, double k){
        int j;
        M.kol = this.kol;
        for(j = 0; j < this.kol;j++){
            this.M[i][j] = (k)*(this.M[i][j]);
        }
    }
    
    public Matriks echelon ()
    {
    	int i,j,k;
    	int N = this.brs;
    	int O = this.kol;
    	//double[][] M1 = new double[N+1][O+1];
    	Matriks M1 = new Matriks ();
    	Matriks M2 = new Matriks ();
    	M1.brs = N+1;
    	M1.kol = O+1;
    	M2.brs = N;
    	M2.kol = O;
    	for (i = 1; i < N+1; i++) 
		{
    		for (j = 1; j < O+1; j++) 
	        {
    			M1.M[i][j] = this.M[i-1][j-1];
	        }
	    }
    	
    	i = 1;
	    j = 1;
	    while( i<=N && j<=O )
	    {
            k = i;
	        while( k<=N && M1.M[k][j]==0 ) k++;

	        if( k<=N )
	        {
	            if( k!=i ) 
	            {
	               swap(M1.M, i, k, j);
	            }

	            if( M1.M[i][j]!=1 )
	            {
	               divide(M1.M, i, j);
	            }
	            eliminateRE(M1.M, i, j);
	            i++;
	         }
	         j++;
	    }
	    
	    for ( i = 0; i < N; i++) 
		{
    		for ( j = 0; j < O; j++) 
	        {
    			M2.M[i][j] = M1.M[i+1][j+1];
	        }
	    }
	    	
	    return M2;
    }
    
    public Matriks reducedEchelon ()
    {
    	int i,j,k;
    	int N = this.brs;
    	int O = this.kol;
    	Matriks M1 = new Matriks ();
    	Matriks M2 = new Matriks ();
    	M1.brs = N+1;
    	M1.kol = O+1;
    	M2.brs = N;
    	M2.kol = O;
    	for ( i = 1; i < N+1; i++) 
		{
    		for ( j = 1; j < O+1; j++) 
	        {
    			M1.M[i][j] = this.M[i-1][j-1];
	        }
	    }
    	
    	i = 1;
	    j = 1;
	    while( i<=N && j<=O )
	    {
	        k = i;
	        while( k<=N && M1.M[k][j]==0 ) k++;
	        if( k<=N )
	        {
	            if( k!=i ) 
	            {
	               swap(M1.M, i, k, j);
	            }

	            if( M1.M[i][j]!=1 )
	            {
	               divide(M1.M, i, j);
	            }
	            eliminateRRE(M1.M, i, j);
	            i++;
	         }
	         j++;
	    }
	    for ( i = 0; i < N; i++) 
		{
    		for ( j = 0; j < O; j++) 
	        {
    			M2.M[i][j] = M1.M[i+1][j+1];
	        }
	    }
	    return M2;
    }
    	
    public void eliminateRRE (double[][] M, int i, int j)
    {
    	int n = M.length - 1;
	    int m = M[0].length - 1;
	    for(int p=1; p<=n; p++)
	    {
	       if( p!=i && M[p][j]!=0 )
	       {
	          for(int q=j+1; q<=m; q++)
	          {
	             M[p][q] -= M[p][j]*M[i][q];
	          }
	          M[p][j] = 0;
	       }
	    }
    }
    
    public void eliminateRE (double[][] M, int i, int j)
    {
    	int n = M.length - 1;
	    int m = M[0].length - 1;
	    for(int p=i; p<=n; p++)
	    {
	       if( p!=i && M[p][j]!=0 )
	       {
	          for(int q=j+1; q<=m; q++)
	          {
	             M[p][q] -= M[p][j]*M[i][q];
	          }
	          M[p][j] = 0;
	       }
	    }
    }
    
    public void divide (double[][] M, int i, int j)
    {
	    int k;  
    	int m = M[0].length - 1;
	    for(k=j+1; k<=m; k++)
	    {
	    	M[i][k] /= M[i][j];
	    }
	    M[i][j] = 1;
	}
    
    static void swap(double[][] M, int i, int k, int j)
    {
	      int m = M[0].length - 1;
	      double temp;
	      for(int q=j; q<=m; q++){
	         temp = M[i][q];
	         M[i][q] = M[k][q];
	         M[k][q] = temp;
	      }
	}
    
    public void splGaussJordan (Matriks M) 
    {
        M=M.reducedEchelon();
        System.out.println("Matriks hasil eliminasi Gauss-Jordan : ");
        M.tulisMatriks();
        System.out.println();
    	int mark = 0;
    	double sum; 
    	int i,j;
    	int N = M.brs;
    	int O = M.kol;
    	     
        // flag == 1 berarti ada solusi unik
        // flag == 2 berarti solusi parametrik
        // flag == 3 berarti tidak mempunyai solusi

        if (N == O-1) {
        	mark = 1;
        }
        for (i = 0; i < N; i++)  
        { 
            sum = 0.0000; 
            for (j = 0; j < O-2; j++)      
                sum = sum + M.M[i][j]; 
            if (sum == 0.0000 && M.M[i][O-1]==0)  
            	mark = 2;
            else if (sum != 0.0000 && M.M[i][O-1]==0)
            	mark = 3;
        }
    	
    	if (mark == 2)      
    	    System.out.println("Solusi dalam bentuk parametrik");  
    	else if (mark == 3)      
    	    System.out.println("Solusi tidak ada"); 
    	else 
    	{ 
            System.out.println("Solusi SPL Matriks anda : ");
    		for (i = 0; i < N; i++)          
    			System.out.printf("%.4f ",M.M[i][O-1]); 
    		System.out.println(" "); 
    	} 
    	
    }
    
    public void splGauss (Matriks M) 
    {
        M=M.echelon();
        System.out.println("Matriks hasil eliminasi Gauss : ");
        M.tulisMatriks();
        System.out.println();
    	double sum; 
    	int i,j;
    	int N = M.brs;
    	int O = M.kol;
    	int mark = 0;
        
        // mark == 1 berarti ada solusi unik
        // mark == 2 berarti solusi parametrik
        // mark == 3 berarti tidak mempunyai solusi

        if (N == O-1) {
        	mark = 1;
        }
        for (i = 0; i < N; i++)  
        { 
            sum = 0.0000; 
            for (j = 0; j < O-2; j++)      
                sum = sum + M.M[i][j]; 
            if (sum == 0.0000 && M.M[i][O-1]==0)  
            	mark = 2;
            else if (sum != 0.0000 && M.M[i][O-1]==0)
            	mark = 3;
        }
    	
    	if (mark == 2)      
    	    System.out.println("Solusi dalam bentuk parametrik");  
    	else if (mark == 3)      
    	    System.out.println("Solusi tidak ada"); 
    	else 
    	{ 
            System.out.println("Solusi SPL Matriks anda : ");
    		double[] tabSolusi = new double[N];
            for (i = N - 1; i >= 0; i--) 
            {
                sum = 0.0000;
                for (j = i + 1; j < O-1; j++) 
                    sum += M.M[i][j] * tabSolusi[j];
                tabSolusi[i] = (M.M[i][O-1] - sum) / M.M[i][i];
            }         
            for (i = 0; i < N; i++) 
                System.out.printf("%.4f ", tabSolusi[i]);
            System.out.println(" "); 
    	} 	
    }
    public int idxPivot(int baris){ // Mereturn indeks kolom pivot suatu baris matriks
        for (int j = 0; j < this.kol - 1; j++) {
            if(this.M[baris][j]==1) return j;
        }
        return -1;
    }

    public void solGJ(Matriks M){ //ngesolve matriks SPL Gauss/Gauss-Jordan
        Scanner in = new Scanner(System.in);
        M= M.reducedEchelon();
        int[][] tabidx = new int[150][150];
        Matriks tabsol = new Matriks();
        boolean param = false;
        boolean havesol = true;
        int count0 =0;
        int j;
        int [] pivot = new int[150]; 
        double [] Sol = new double[150];
        for (int a = 0; a < Sol.length; a++) {
            Sol[a] = -999; //idxundef
        } 
        int count;
        String sol = "";
        for (j = 0; j < M.brs; j++) {
            count0 = 0;
            for (int j2 = 0; j2 < M.kol-1; j2++) {
                if(M.M[j][j2] == 0) count0++;
            }
        if((count0 == M.kol - 1) &&(M.M[j][M.kol-1] != 0)){
            System.out.println("Sistem tidak memiliki solusi");
            havesol = false;
            break;}
        }
        if(havesol==true){
            for (int i = 0; i < M.brs; i++) {
                pivot[i] = idxPivot(i);
                count = 0;
                if(pivot[i]!=-1){
                    for (j = pivot[i]+1; j < M.kol-1; j++) {
                        if(M.M[i][j]!=0){
                            count++;
                            tabidx[i][count] = j;
                            tabsol.M[i][count] = M.M[i][j] * -1;
                        }   
                    }
                    tabidx[i][0] = count;
                    tabsol.M[i][0] = M.M[i][M.kol-1];
                }
            }
            String piv ="";
            String tabs ="";
            String index="";
            int ipar = 1;
            for (int k = 0; k < M.brs; k++) {
                if(pivot[k]!=-1){
                    ipar = 1;
                    Sol[pivot[k]] = tabsol.M[k][0];
                    piv = String.format("%d",pivot[k]+1);
                    tabs = String.format("%.4f",tabsol.M[k][0]);
                    sol += "X" + piv + " = " + tabs;
                    for (int l = 1; l <= tabidx[k][0]; l++) {
                        if(tabsol.M[k][l] > 0){
                            tabs = String.format("%.4f",tabsol.M[k][l]);
                            index = String.format("%d",tabidx[k][l]+1);
                            sol+=" + " + tabs + "X" + index;
                            ipar++;

                            }
                        else if(tabsol.M[k][l] < 0){
                            tabs = String.format("%.4f",Math.abs(tabsol.M[k][l]));
                            index = String.format("%d",tabidx[k][l]+1);
                            sol+=" - " + tabs + "X" + index;
                            ipar++;
                            
                        }

                    }
                    sol+="\n";

                }
            }

            for (int c = 0; c < M.kol-1; c++) {
                if(Sol[c]== -999){
                    param = true;
                    int tmp = c+1;
                    sol+="X" + Integer.toString(tmp)+" ";
                }
            }

            if(param){
                sol+="variabel bebas\n";
             }
           /* int cprm = 1;
            for (int d = 0; d < M.kol-1; d++) {
                if(Sol[d]== -999){
                    int tmp = d+1;
                    sol+="X" + Integer.toString(tmp)+" = "+"a"+Integer.toString(cprm)+"\n";
                    cprm++;
                }
            } */

            System.out.println("Solusi dari SPL anda adalah : ");
            System.out.println(sol);
            System.out.println("Apakah anda mau menyimpan hasil ke file? (0/1) : ");
            int pil = in.nextInt();
            while (pil!=0 && pil!=1){
                System.out.println("Ulangi lagi");
                System.out.println("Apakah anda mau menyimpan hasil ke file? (0/1) : ");
                pil = in.nextInt();
            }
            if(pil==1){
                tulisfileSPL(sol);
            }
                
        }

    }


    public void splCramer() { //Solusi SPL metode Cramer
        Scanner in = new Scanner(System.in);
        Matriks cr = new Matriks();
        cr.brs = this.brs;
        cr.kol = this.kol - 1;
        for (int i = 0; i < cr.brs; i++) {
            for (int j = 0; j < cr.kol; j++) {
                cr.M[i][j] = this.M[i][j];
            }
        }
        Double crdet = cr.detGJ();
        if (crdet == 0 || (crdet.isNaN())) {
            System.out.println("Tidak ada solusi,bisa jadi determinan = 0 atau NaN. Silakan gunakan metode lain");
        }
        else{
            System.out.println("Hasil Penyelesaian : ");
            String sol = "";
            for (int l = 0; l < cr.kol; l++) {
                Matriks cm = this.makeCramer(l);
                Double detcm = cm.detGJ();
                double valx = detcm / crdet + 0.0000;
                System.out.print("X"+(l+1)+" = ");
                System.out.printf("%.4f",valx);
                System.out.println(); 
                sol += "X"+(l+1)+" = "+Double.toString(valx)+"\n";
            }
            System.out.println("Apakah anda mau menyimpan hasil ke file? (0/1) : ");
            int pil = in.nextInt();
           while (pil!=0 && pil!=1){
                System.out.println("Ulangi lagi");
                System.out.println("Apakah anda mau menyimpan hasil ke file? (0/1) : ");
                pil = in.nextInt();
            }
            if(pil==1){
                tulisfileSPL(sol);
            }
        }
    }          


    public Matriks makeCramer(int k){ //Membuat matriks kolom Cramer
        Matriks mc = new Matriks();
        mc.brs = this.brs;
        mc.kol = this.kol - 1;
        for (int i = 0; i < mc.brs; i++) {
            for (int j = 0; j < mc.kol; j++) {
                if(j==k) mc.M[i][j] = this.M[i][this.kol - 1];
                else mc.M[i][j] = this.M[i][j];
            }
        }
        return mc;
    }
    public Double kbInterpol(){ //Membaca input interpolasi polinom dari keyboard
        Scanner in = new Scanner(System.in);
        int n;
        System.out.print("Masukkan derajat polinom interpolasi : ");
        n = in.nextInt();
        this.brs = n+1;
        this.kol = n+2;
        Double x,y;
        
        for (int i = 0; i < this.brs; i++) {
                System.out.print("Masukkan nilai (x"+i+",y"+i+") : ");
                System.out.println();
                System.out.print("x"+i+" = ");
                x = in.nextDouble();
                System.out.print("y"+i+" = ");
                y = in.nextDouble();
                Double xi = 1.0000;
                for (int j = 0; j < this.kol-1; j++) {
                    this.M[i][j] = xi;
                    xi = xi * x; 
                }
                this.M[i][this.kol-1] = y;
            }
        /*System.out.println("Matriks sistem persamaan interpolasi anda  : ");
        this.tulisMatriks(); */
        System.out.println();
        System.out.print("Masukkan nilai x yang akan ditaksir nilai fungsinya : ");
        Double xtak;
        xtak = in.nextDouble();
        return xtak;
        }

    public Double bacafileInterpol() throws Exception{ //Membaca input interpolasi dari file.txt
        Double xtak = 0.0000;
        String filename;
        Scanner in = new Scanner(System.in);
        System.out.print("Masukkan nama file pasangan titik (x,y) , (beserta ekstensi, contohnya matriks.txt) : ");
        filename = in.nextLine();
        filename = "../test/" + filename;
        if(Files.notExists(Paths.get(filename))) {
            System.out.println("File not found, ulangi lagi");
            this.bacafileInterpol();}
        else{
            FileReader fr = (new FileReader(filename));
            //BufferedReader br = new BufferedReader(fr);
            int elm;
            String mat = "";
            Matriks ip = new Matriks();
            while((elm=fr.read()) != -1){
                //System.out.print((char) line);
                mat +=((char)elm);
            }
            mat=mat.trim();
            //System.out.print(mat);
            mat+='\n';
            if (mat.length() != 0){
                int i = 0;
                int j = 0;
                int l = 0;
                String cc ="";
                while(l<mat.length()){
                    if(mat.charAt(l) == ' ') continue;
                    while ((mat.charAt(l) != ' ' && mat.charAt(l) != '\n')){
                        cc+=mat.charAt(l);   
                        l++;
                    }
                        ip.M[i][j] = Double.parseDouble(cc);
                        j++;
                        cc= "";
    
                        if((mat.charAt(l) == '\n')){
                            i++;
                            ip.kol = j;
                            j = 0;
                         }
                         l++;
                }
                ip.brs = i;

                this.brs = ip.brs;
                this.kol = this.brs + 1;
                int r = 0;
                for (int k = 0; k < this.brs; k++) {
                    Double xi = 1.0000;
                    for (int m = 0; m < this.kol - 1; m++) {
                        this.M[k][m] = xi;
                        xi = xi*ip.M[r][0];
                    }
                    this.M[k][this.kol-1] = ip.M[r][1];
                    r++;
                }
                System.out.println("Masukan anda adalah : ");
                this.tulisMatriks();
                System.out.println();
                System.out.print("Masukkan nilai x yang akan ditaksir nilai fungsinya : ");
                xtak = in.nextDouble();                
            } 
    
        }
        return xtak;
    }
        public void cramInterpol() throws Exception{ //Interpolasi with cramer, asumsi untuk setiap derajat n terdapat tepat n+1 buah titik unik
            // sehingga metode cramer valid
            // kemungkinan akan unused, dibuang sayang
            Scanner in = new Scanner(System.in);
            System.out.print("Baca dari keyboard(0) atau file(1) ? Input anda : ");
            Double xtak = 0.0000;
            int plh = in.nextInt();
            while(plh!=0 && plh!=1){
                System.out.print("Ulangi pembacaan. Baca dari keyboard(0) atau file(1) ? ");
                plh = in.nextInt();
            }
            if(plh==0) xtak = this.kbInterpol();
            else if(plh==1) xtak = this.bacafileInterpol();
            Matriks cr = new Matriks();
            cr.brs = this.brs;
            cr.kol = this.kol - 1;
            for (int i = 0; i < cr.brs; i++) {
                for (int j = 0; j < cr.kol; j++) {
                    cr.M[i][j] = this.M[i][j];
                }
            }
            Double crdet = cr.detGJ();
            if (crdet == 0 || (crdet.isNaN())) {
                System.out.println("Tidak ada solusi");
            }
            else{
                System.out.println("Hasil Penyelesaian : ");
                String sol = "P"+(this.brs - 1)+"(x) = ";
                int pang = 0;
                Double hasiltak = 0.0000;
                for (int l = 0; l < cr.kol; l++) {
                    Matriks cm = this.makeCramer(l);
                    Double detcm = cm.detGJ();
                    double valx = detcm / crdet + 0.0000;
                    System.out.print("a"+l+" = ");
                    System.out.printf("%.16f",valx);
                    hasiltak += valx * Math.pow(xtak,l);
                    System.out.println();
                    String Valx ="";
                    if(valx > 0){
                        Valx = String.format("%.16f",valx);
                        if(pang==this.brs-1 && pang!=0) sol+=" + "+Valx+"x^"+pang; 
                        else if(pang!=this.brs - 1 && pang!=0) sol += " + "+Valx+"x^"+pang;
                        else sol+=Valx; 
                    }
                    else if (valx<0){
                        Valx = String.format("%.16f",Math.abs(valx));
                        if(pang==this.brs-1 && pang!=0) sol+=" - "+Valx+"x^"+pang; 
                        else if(pang!=this.brs - 1 && pang!=0) sol += " - "+Valx+"x^"+pang;
                        else sol+="- "+Valx; 
                    }
                    pang++;
                }

                System.out.println("Polinom interpolasi : ");
                System.out.println(sol);
                System.out.printf("Hasil taksiran pada x = %.4f adalah %.16f",xtak,hasiltak);
                System.out.println();
                System.out.println("Apakah anda mau menyimpan hasil ke file? (0/1) : ");
                int pil = in.nextInt();

               while (pil!=0 && pil!=1){
                    System.out.println("Ulangi lagi");
                    System.out.println("Apakah anda mau menyimpan hasil ke file? (0/1) : ");
                    pil = in.nextInt();
                }
                if(pil==1){
                    String Hasiltak = String.format("%.16f",hasiltak);
                    sol+="\n" + "Taksiran polinom di atas pada x = "+Double.toString(xtak)+" adalah "+Hasiltak;
                    tulisfileSPL(sol);
                }
            }
        }

        public void gjInterpol() throws Exception{ //Interpolasi polinom dengan eliminasi Gauss Jordan
            Scanner in = new Scanner(System.in);
            System.out.print("Baca dari keyboard(0) atau file(1) ? Input anda : ");
            Double xtak = 0.0000;
            Matriks gji = new Matriks();
            int plh = in.nextInt();
            while(plh!=0 && plh!=1){
                System.out.print("Ulangi pembacaan. Baca dari keyboard(0) atau file(1) ? ");
                plh = in.nextInt();
            }
            if(plh==0) xtak = this.kbInterpol();
            else if(plh==1) xtak = this.bacafileInterpol();
            
            boolean havesol = true;
            gji = this.reducedEchelon();
            for (int i = 0; i < this.brs; i++) {
                if(gji.M[i][this.kol-1]!=0){
                    havesol = false;
                    for (int j = 0; j < this.kol - 1; j++) {
                        if(gji.M[i][j] != 0){
                            havesol = true;
                            break;
                        }
                    }
                }
            }
            if(!havesol){
                System.out.println("Tidak dapat dibentuk polinom dari titik-titik tersebut");   
            }
            else{
                System.out.println("Hasil Penyelesaian : ");
                String sol = "P"+(this.brs - 1)+"(x) = ";
                int pang = 0;
                Double hasiltak = 0.0000;
                for (int l = 0; l < this.brs; l++) {
                    Double valx = gji.M[l][this.kol - 1];
                    System.out.print("a"+l+" = ");
                    System.out.printf("%.16f",valx);
                    hasiltak += valx * Math.pow(xtak,l);
                    System.out.println();
                    String Valx = "";
                    if(valx >= 0){
                        Valx = String.format("%.16f",valx);
                        if(pang==this.brs-1 && pang!=0) sol+=" + "+Valx+"x^"+pang; 
                        else if(pang!=this.brs - 1 && pang!=0) sol += " + "+Valx+"x^"+pang;
                        else sol+=Valx; 
                    }
                    else if (valx<0){
                        Valx = String.format("%.16f",Math.abs(valx));
                        if(pang==this.brs-1 && pang!=0) sol+=" - "+Valx+"x^"+pang; 
                        else if(pang!=this.brs - 1 && pang!=0) sol += " - "+Valx+"x^"+pang;
                        else sol+="- "+Valx; 
                    }
                    pang++;
                }

                System.out.println("Polinom interpolasi : ");
                System.out.println(sol);
                System.out.printf("Hasil taksiran pada x = %.4f adalah %.16f",xtak,hasiltak);
                System.out.println();
                System.out.println("Apakah anda mau menyimpan hasil ke file? (0/1) : ");
                int pil = in.nextInt();

               while (pil!=0 && pil!=1){
                    System.out.println("Ulangi lagi");
                    System.out.println("Apakah anda mau menyimpan hasil ke file? (0/1) : ");
                    pil = in.nextInt();
                }
                if(pil==1){
                    String Hasiltak = String.format("%.16f",hasiltak);
                    sol+="\n" + "Taksiran polinom di atas pada x = "+Double.toString(xtak)+" adalah "+Hasiltak;
                    tulisfileSPL(sol);
                }

            }

        }




        public Matriks seperate_main_Augmented(){ // Memisahkan augmented matriks
            Matriks main = new Matriks();
            int i,j;
            main.brs = this.brs;
            main.kol = this.kol - 1;
    
            for (i = 0; i < main.brs; i++) {
                for (j = 0; j < main.kol; j++) {
                    main.M[i][j] = this.M[i][j];
                }
            }
            return main;
        }
    
        public Matriks seperate_minor_Augmented(){ // Memisahkan augmented matriks
            Matriks minor = new Matriks();
            int i,j;
            minor.brs = this.brs;
            minor.kol = 1;
    
            for (i = 0; i < minor.brs; i++) {
                minor.M[i][0] = this.M[i][(this.kol)-1];
            }
            return minor;
        }
    
        public double kaliKol(int j1, int j2){ // Mengalikan elemen kolom j1 dengan elemen kolom j2 untuk semua baris
            int i;
            double count = 0;
            for(i = 0; i < this.brs;i++){
                count += (this.M[i][j1])*(this.M[i][j2]);
            }
            return(count);
        }
        public double jumKol(int j){ // Menjumlahkan elemen pada kolom j
            int i;
            double count = 0;
            for(i = 0; i < this.brs;i++){
                count += (this.M[i][j]);
            }
            return(count);
        }
    
        public Matriks multiple(Matriks M1, Matriks M2){ // Mengalikan dua buah matriks
            int i,j,k;
            Matriks M3 = new Matriks();
            M3.brs = M1.brs;
            M3.kol = M2.kol;
    
            for(i=0; i < M3.brs;i++){
                for(j=0; j < M3.kol ;j++){
                    M3.M[i][j] = 0;
                    for(k=0; k < M1.kol; k++){
                        M3.M[i][j] += (M1.M[i][k])*(M2.M[k][j]);
                    }
                }
            }
            return M3;
        }

        public void Matriks_SPLInv(){ // Membentuk matriks solusi SPL dengan metode invers
            Scanner in = new Scanner(System.in);
            Matriks A = new Matriks();
            Matriks B = new Matriks();
            Matriks K = new Matriks();
            Matriks Sol = new Matriks();
            String sol = "";
            A = this.seperate_main_Augmented();
            B = this.seperate_minor_Augmented();
            K = A.makeInverse();
            System.out.println("Matriks hasil metode invers : ");
            K.tulisMatriks();
            Sol = multiple(K,B);
            for (int i = 0; i < Sol.brs; i++) {
                int idx = i+1;
                sol+="X"+Integer.toString(idx)+" = "+Double.toString(Sol.M[i][0])+"\n"; 
            }
            System.out.println("Hasil penyelesaian : ");
            System.out.println(sol);

            System.out.println("Apakah anda mau menyimpan hasil ke file? (0/1) : ");
            int pil = in.nextInt();
            while (pil!=0 && pil!=1){
                System.out.println("Ulangi lagi");
                System.out.println("Apakah anda mau menyimpan hasil ke file? (0/1) : ");
                pil = in.nextInt();
            }
            if(pil==1){
                tulisfileSPL(sol);
            }



        }

        public Matriks Matriks_regresi(){ // Membentuk matriks regresi
            Matriks reg = new Matriks();
            reg.brs = this.kol;
            reg.kol = (this.kol) + 1;

            int i,j;
            // Mengisi matriks utama regresi
            for (i = 0; i < reg.brs; i++) {
                for (j = 0; j < reg.kol; j++) {
                    reg.M[0][0] = this.brs;
                    if (j == 0){
                        if (i > 0) {
                            reg.M[i][j] = this.jumKol((i-1));
                        }
                    }else if (i == 0) {
                        if (j >0) {
                            reg.M[i][j] = this.jumKol((j-1));
                        }
                    }else if(i != 0 && j != 0){
                        reg.M[i][j] = this.kaliKol((i-1),(j-1));
                    }

                }
            }
            // Mengisi sisanya yaitu kolom terakhir
            for (i = 0; i < reg.brs; i++) {
                reg.M[0][reg.kol] = this.jumKol(((this.kol)-1));
                if (i > 0){
                    reg.M[i][reg.kol] = this.kaliKol((i-1),((this.kol)-1));
                }
            }
            return reg;
        }

        public Matriks Result_regresi_inv(){ // Membentuk matriks solusi koefisien regresi
            Matriks A = new Matriks();
            Matriks B = new Matriks();
            Matriks C = new Matriks();
            Matriks K = new Matriks();

            A = this.Matriks_regresi();
            B = A.seperate_main_Augmented();
            C = A.seperate_minor_Augmented();
            K = B.makeInverse();

            return multiple(K,C);
        }

        public Matriks Result_regresi_gauss(){ // Membentuk matriks eselon dari matriks regresi
            Matriks A = new Matriks();
            Matriks B = new Matriks();

            A = this.Matriks_regresi();
            B = A.echelon();

            return B;
        }

        public double Compute_regresi_inv(){
            Matriks M = new Matriks();
            int N;
            int i,j;
            Matriks x = new Matriks();
            x.brs = 1;
            x.kol = (this.kol); // elemen x[0][0] diisi 1 setelah indeks tersebut baru input parameter x nya

            Scanner in = new Scanner(System.in);
            N = in.nextInt();
            M.brs = 20;
            M.kol = N+1;

            for (i = 0; i < M.brs; i++) {
                for (j = 0; j < M.kol; j++) {
                    M.M[i][j] = in.nextDouble();
                }
            }

            Matriks koef_b = M.Result_regresi_inv();

            x.M[0][0] = 1;
            for (j = 1; j < x.kol; j++) {
                x.M[0][j] = in.nextDouble();
            }

            double count = 0;
            for(i = 0; i < x.brs ; i++){
                for(j = 0; j < x.kol ; j++){
                    count += (x.M[i][j])*(koef_b.M[j][i]);
                }
            }

            return count;
        }

        public boolean Is_identity(Matriks M){
            int i,j;
            boolean Identity = (M.brs == M.kol);
            if (Identity) {
                for (i = 0; i < M.brs; i++) {
                    for (j = 0; j < M.kol; j++) {
                        Identity = Identity && ((i != j) ? (M.M[i][j] == 0) : (M.M[i][j] == 1) );
                    }
                }
            }
            return Identity;
        }

        public Matriks Merged_Identity(){
            int i,j;
            Matriks Merged = new Matriks();
            Merged.brs = this.brs;
            Merged.kol = 2*(this.kol);

            // Mengcopy matriks
            for (i = 0; i < Merged.brs; i++) {
                for (j = 0; j < this.kol; j++) {
                    Merged.M[i][j] = this.M[i][j];
                }
            }

            // Menggabungkan dengan matriks identitas

            for (i = 0; i < Merged.brs; i++) {
                for (j = this.kol; j < Merged.kol; j++) {
                    if ((j-i) == this.kol){
                        Merged.M[i][j] = 1;
                    }
                    else{
                        Merged.M[i][j] = 0;
                    }
                }
            }

            return Merged;
        }

        public Matriks Invers_gauss(){
        	int i,j;
        	int N = this.brs;
        	int O = this.kol;
        	Matriks M1 = new Matriks ();
        	M1.brs = N;
        	M1.kol = O/2;
        	
        	for ( i = 0; i < N; i++) 
    		{
        		for ( j = 0; j < O/2; j++) 
    	        {
        			M1.M[i][j] = this.M[i][j+(O/2)];
    	        }
    	    }
    	    return M1;
        }

    public Matriks kbRegresi(){ //Membaca input interpolasi polinom dari keyboard
        Scanner in = new Scanner(System.in);
        int N,brs;
        int i,j;
        Matriks M = new Matriks();
        System.out.print("Masukkan jumlah peubah x : ");
        N = in.nextInt();
        System.out.print("Masukkan banyak baris  : ");
        brs = in.nextInt();
        this.brs = brs;
        this.kol = N+1;
        for (i = 0; i < this.brs; i++) {
            for (j = 0; j < this.kol; j++) {
                this.M[i][j] = in.nextDouble();
            }
        }
        System.out.println();
        Matriks x = new Matriks();
        x.brs = 1;
        x.kol = (this.kol); // elemen x[0][0] diisi 1 setelah indeks tersebut baru input parameter x nya
        System.out.print("Masukkan peubah x yang akan ditaksir nilainya : ");
        System.out.println();
        x.M[0][0] = 1;
        for (j = 1; j < x.kol; j++) {
            x.M[0][j] = in.nextDouble();
        }
        return x;
    }

    public Matriks bacafileRegresi() throws Exception{ //Membaca input interpolasi dari file.txt
        String filename;
        Scanner in = new Scanner(System.in);
        Matriks x = new Matriks();
        System.out.print("Masukkan nama file regresi , (beserta ekstensi, contohnya matriks.txt) : ");
        filename = in.nextLine();
        filename = "../test/" + filename;
        if(Files.notExists(Paths.get(filename))) {
            System.out.println("File not found, ulangi lagi");
            this.bacafileInterpol();}
        else{
            FileReader fr = (new FileReader(filename));
            //BufferedReader br = new BufferedReader(fr);
            int elm;
            String mat = "";
            Matriks reg = new Matriks();
            while((elm=fr.read()) != -1){
                //System.out.print((char) line);
                mat +=((char)elm);
            }
            mat=mat.trim();
            //System.out.print(mat);
            mat+='\n';
            if (mat.length() != 0){
                int i = 0;
                int j = 0;
                int l = 0;
                String cc ="";
                while(l<mat.length()){
                    if(mat.charAt(l) == ' ') continue;
                    while ((mat.charAt(l) != ' ' && mat.charAt(l) != '\n')){
                        cc+=mat.charAt(l);
                        l++;
                    }
                    reg.M[i][j] = Double.parseDouble(cc);
                    j++;
                    cc= "";

                    if((mat.charAt(l) == '\n')){
                        i++;
                        reg.kol = j;
                        j = 0;
                    }
                    l++;
                }
                reg.brs = i;

                this.brs = reg.brs;
                this.kol = reg.kol;
                for (i = 0; i < this.brs; i++) {
                    for (j = 0; j < this.kol; j++) {
                        this.M[i][j] = reg.M[i][j];
                    }
                }
                System.out.println();
                System.out.print("Masukkan peubah x yang akan ditaksir nilainya : ");
                x.brs = 1;
                x.kol = (this.kol); // elemen x[0][0] diisi 1 setelah indeks tersebut baru input parameter x nya
                System.out.println();
                x.M[0][0] = 1;
                for (j = 1; j < x.kol; j++) {
                    x.M[0][j] = in.nextDouble();
                }
            }
        }
        return x;
    }
    public void Regresi() throws Exception{ //Interpolasi with kofaktor, asumsi untuk setiap derajat n terdapat tepat n+1 buah titik unik
        // sehingga metode cramer valid , namun tidak berlaku untuk titik yang mengandung x = 0
        Scanner in = new Scanner(System.in);
        int i,j;
        String sol = "";
        Matriks x = new Matriks();
        Matriks koef_b = new Matriks();
        System.out.print("Baca dari keyboard(0) atau file(1) ? Input Anda : ");
        Double xtak = 0.0000;
        int plh = in.nextInt();
        while(plh!=0 && plh!=1){
            System.out.print("Ulangi pembacaan. Baca dari keyboard(0) atau file(1) ? ");
            plh = in.nextInt();
        }
        if(plh==0) {
            x = this.kbRegresi();
        }
        else if(plh==1) {
            x = this.bacafileRegresi();
        }

        System.out.print("Mau menggunakan metode invers(0) atau gauss(1) ? Input Anda ");

        int choice = in.nextInt();
        while(plh!=0 && plh!=1){
            System.out.print("Ulangi pembacaan. Mau menggunakan metode invers(0) atau gauss(1) ? ");
            choice = in.nextInt();
        }
        if(choice==0) {
            koef_b = this.Result_regresi_inv();
            koef_b.tulisMatriks();
        }
        else if(choice==1) {
            koef_b = this.Result_regresi_gauss();
            boolean havesol = true;
            koef_b = koef_b.reducedEchelon();
            for ( i = 0; i < koef_b.brs; i++) {
                if(koef_b.M[i][koef_b.kol-1]!=0){
                    havesol = false;
                    for ( j = 0; j < this.kol - 1; j++) {
                        if(koef_b.M[i][j] != 0){
                            havesol = true;
                            break;
                        }
                    }
                }
            }
            koef_b.tulisMatriks();
        }
        double count = 0;
        for(i = 0; i < x.brs ; i++){
            for(j = 0; j < x.kol ; j++){
                count += (x.M[i][j])*(koef_b.M[j][i]);
            }
        }
        System.out.printf("Hasil taksiran regresi linier berganda adalah %.4f",count);
        System.out.println();
        System.out.println("Apakah anda mau menyimpan hasil ke file? (0/1) : ");
        int pil = in.nextInt();

        while (pil!=0 && pil!=1){
            System.out.println("Ulangi lagi");
            System.out.println("Apakah anda mau menyimpan hasil ke file? (0/1) : ");
            pil = in.nextInt();
        }
        if(pil==1){
            sol+="\n" + "Hasil taksiran regresi linier berganda dari data tersebut adalah = "+Double.toString(count);
            tulisfileSPL(sol);
        }
    }

}
