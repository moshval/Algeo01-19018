
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
        System.out.println("Matriks anda adalah : ");
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
    
    public Matriks makeInverse(){ // Membuat invers suatu matriks , metode adjoin , berlaku untuk matriks persegi
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
	    	//look for a non-zero entry in col j at or below row i
	        k = i;
	        while( k<=N && M1.M[k][j]==0 ) k++;

	        // if such an entry is found at row k
	        if( k<=N )
	        {
	        	//  if k is not i, then swap row i with row k
	            if( k!=i ) 
	            {
	               swap(M1.M, i, k, j);
	            }

	            // if A[i][j] is not 1, then divide row i by A[i][j]
	            if( M1.M[i][j]!=1 )
	            {
	               divide(M1.M, i, j);
	            }

	            // eliminate all other non-zero entries from col j by subtracting from each
	            // row (other than i) an appropriate multiple of row i
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
    	//double[][] M1 = new double[N+1][O+1];
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
	    	//look for a non-zero entry in col j at or below row i
	        k = i;
	        while( k<=N && M1.M[k][j]==0 ) k++;

	        // if such an entry is found at row k
	        if( k<=N )
	        {
	        	//  if k is not i, then swap row i with row k
	            if( k!=i ) 
	            {
	               swap(M1.M, i, k, j);
	            }

	            // if A[i][j] is not 1, then divide row i by A[i][j]
	            if( M1.M[i][j]!=1 )
	            {
	               divide(M1.M, i, j);
	            }

	            // eliminate all other non-zero entries from col j by subtracting from each
	            // row (other than i) an appropriate multiple of row i
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
    	int mark = 0;
    	double sum; 
    	int i,j;
    	int N = this.brs;
    	int O = this.kol;
    	     
        // flag == 1 berarti ada solusi unik
        // flag == 2 berarti solusi parametrik
        // flag == 3 berarti tidak mempunyai solusi

        if (N == O-1) {
        	mark = 1;
        }
        for (i = 0; i < N; i++)  
        { 
            sum = 0; 
            for (j = 0; j < O-2; j++)      
                sum = sum + this.M[i][j]; 
            if (sum == 0 && this.M[i][O-1]==0)  
            	mark = 2;
            else if (sum != 0 && this.M[i][O-1]==0)
            	mark = 3;
        }
    	
    	if (mark == 2)      
    	    System.out.println("Solusi dalam bentuk parametrik");  
    	else if (mark == 3)      
    	    System.out.println("Solusi tidak ada"); 
    	else 
    	{ 
    		for (i = 0; i < N; i++)          
    			System.out.print(this.M[i][N] +" "); 
    		System.out.println(" "); 
    	} 
    	
    }
    
    public void splGauss (Matriks M) 
    {
    	M=M.echelon();
    	double sum; 
    	int i,j;
    	int N = this.brs;
    	int O = this.kol;
    	int mark = 0;
        
        // mark == 1 berarti ada solusi unik
        // mark == 2 berarti solusi parametrik
        // mark == 3 berarti tidak mempunyai solusi

        if (N == O-1) {
        	mark = 1;
        }
        for (i = 0; i < N; i++)  
        { 
            sum = 0; 
            for (j = 0; j < O-2; j++)      
                sum = sum + this.M[i][j]; 
            if (sum == 0 && this.M[i][O-1]==0)  
            	mark = 2;
            else if (sum != 0 && this.M[i][O-1]==0)
            	mark = 3;
        }
    	
    	if (mark == 2)      
    	    System.out.println("Solusi dalam bentuk parametrik");  
    	else if (mark == 3)      
    	    System.out.println("Solusi tidak ada"); 
    	else 
    	{ 
    		double[] tabSolusi = new double[N];
            for (i = N - 1; i >= 0; i--) 
            {
                sum = 0.0;
                for (j = i + 1; j < O-1; j++) 
                    sum += this.M[i][j] * tabSolusi[j];
                tabSolusi[i] = (this.M[i][O-1] - sum) / this.M[i][i];
            }         
            for (i = 0; i < N; i++) 
                System.out.printf("%.2f ", tabSolusi[i]);
            System.out.println(" "); 
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
        Double crdet = cr.detKofaktor();
        if (crdet == 0 || (crdet.isNaN())) {
            System.out.println("Tidak ada solusi");
        }
        else{
            System.out.println("Hasil Penyelesaian : ");
            String sol = "";
            for (int l = 0; l < cr.kol; l++) {
                Matriks cm = this.makeCramer(l);
                Double detcm = cm.detKofaktor();
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
                System.out.println();
                System.out.print("Masukkan nilai x yang akan ditaksir nilai fungsinya : ");
                xtak = in.nextDouble();                
            } 
    
        }
        return xtak;
    }
        public void cramInterpol() throws Exception{ //Interpolasi with kofaktor, asumsi untuk setiap derajat n terdapat tepat n+1 buah titik unik
            // sehingga metode cramer valid , namun tidak berlaku untuk titik yang mengandung x = 0
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
            Double crdet = cr.detKofaktor();
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
                    Double detcm = cm.detKofaktor();
                    double valx = detcm / crdet + 0.0000;
                    System.out.print("a"+l+" = ");
                    System.out.printf("%.4f",valx);
                    hasiltak += valx * Math.pow(xtak,l);
                    System.out.println();
                    if(valx > 0.0000){
                        if(pang==this.brs-1 && pang!=0) sol+=" + "+Double.toString(valx)+"x^"+pang; 
                        else if(pang!=this.brs - 1 && pang!=0) sol += " + "+Double.toString(valx)+"x^"+pang;
                        else sol+=Double.toString(valx); 
                    }
                    else{
                        if(pang==this.brs-1 && pang!=0) sol+=" "+Double.toString(valx)+"x^"+pang; 
                        else if(pang!=this.brs - 1 && pang!=0) sol += " "+Double.toString(valx)+"x^"+pang;
                        else sol+=Double.toString(valx); 
                    }
                    pang++;
                }

                System.out.println("Polinom interpolasi : ");
                System.out.println(sol);
                System.out.printf("Hasil taksiran pada x = %.2f adalah %.4f",xtak,hasiltak);
                System.out.println();
                System.out.println("Apakah anda mau menyimpan hasil ke file? (0/1) : ");
                int pil = in.nextInt();

               while (pil!=0 && pil!=1){
                    System.out.println("Ulangi lagi");
                    System.out.println("Apakah anda mau menyimpan hasil ke file? (0/1) : ");
                    pil = in.nextInt();
                }
                if(pil==1){
                    sol+="\n" + "Taksiran polinom di atas pada x = "+Double.toString(xtak)+" adalah "+Double.toString(hasiltak);
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

        public Matriks Matriks_SPLInv(){ // Membentuk matriks solusi SPL dengan metode invers

            Matriks A = new Matriks();
            Matriks B = new Matriks();
            Matriks K = new Matriks();
    
            A = this.seperate_main_Augmented();
            B = this.seperate_minor_Augmented();
            K = A.makeInverse();
            K.tulisMatriks();

            return multiple(K,B);
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

}
