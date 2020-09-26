
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
    	int N = this.brs;
    	int O = this.kol;
    	int max;
        for (int k = 0; k < N; k++) 
        {
            /** find pivot row **/
            max = k;
            for (int i = k + 1; i < N; i++) 
                if (Math.abs(this.M[i][k]) > Math.abs(this.M[max][k])) 
                    max = i;
 
            /** swap row in M matrix **/    
            double[] temp = this.M[k]; 
            this.M[k] = this.M[max]; 
            this.M[max] = temp;
 
            /** pivot within A and B **/
            for (int i = k + 1; i < N; i++) 
            {
                double factor = this.M[i][k] / this.M[k][k];
                for (int j = k; j < O; j++) 
                	this.M[i][j] -= factor * this.M[k][j];
            }
        }
        
        double[] divided = new double [N];
		for (int i=0; i < N; i++) 
		{
			divided[i]=this.M[i][i];
		}
        for (int i = 0; i < N; i++)
           {
               for (int j = 0; j < O; j++) 
               {
            	   this.M[i][j]=this.M[i][j]/divided[i];
               }   
           }
        
        return (copyMatriks());
    }
    
    public Matriks reducedEchelon ()
    {
    	int N = this.brs;
    	int O = this.kol;
    	int max;
        for (int k = 0; k < N; k++) 
        {
            /** find pivot row **/
            max = k;
            for (int i = k + 1; i < N; i++) 
                if (Math.abs(this.M[i][k]) > Math.abs(this.M[max][k])) 
                    max = i;
 
            /** swap row in A matrix **/    
            double[] temp = this.M[k]; 
            this.M[k] = this.M[max]; 
            this.M[max] = temp;
 
            /** pivot within A and B **/
            for (int i = k + 1; i < N; i++) 
            {
                double factor = this.M[i][k] / this.M[k][k];
                for (int j = k; j < O; j++)
                	this.M[i][j] -= factor * this.M[k][j];
            }
        }
        
        double[] divided = new double [N];
		for (int i=0; i < N; i++) 
		{
			divided[i]=this.M[i][i];
		}
        for (int i = 0; i < N; i++)
           {
               for (int j = 0; j < O; j++) 
               {
            	   this.M[i][j]=this.M[i][j]/divided[i];
               }   
           }
        
        int mark=0;
        for (int j=0; j<O; j++)
        {
        	if (this.M[N-1][j]==1)
        		mark=j;
        }
        for (int j=mark; j>0; j=j-1)
		{
			double divided1 = this.M[j][j];
			for (int i=j-1; i>=0; i=i-1)
			{
				double factor = this.M[i][j]/divided1;
				for (int l=O-1; l>i;l=l-1)
				{
					this.M[i][l] -= factor * this.M[j][l];
				}
			}
		}
        
        return (copyMatriks());
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
        public void cramInterpol() throws Exception{ //Interpolasi with kofaktor, asumsi untuk setiap derajat n terdapat tepat n+1 buah titik
            // sehingga metode cramer valid
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
                    if(pang==this.brs-1 && pang!=0) sol+="("+Double.toString(valx)+"x^"+pang+")"; 
                    else if(pang!=this.brs - 1 && pang!=0) sol += "("+Double.toString(valx)+"x^"+pang+") + ";
                    else sol+="("+Double.toString(valx)+")"+" + ";
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
                    sol+="\n" + "Taksiran pada x = "+Double.toString(xtak)+" adalah "+Double.toString(hasiltak);
                    tulisfileSPL(sol);
                }
            }
        }
        

}
