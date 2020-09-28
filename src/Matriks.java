
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

}
