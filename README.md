# Tugas Besar 1 IF 2123 Aljabar Linier dan  Geometri 
Sistem Persamaan Linier, Determinan, dan Aplikasinya\
Semester  I Tahun 2020/2021

# Disusun oleh kelompok 16, yang terdiri dari :
1. 13519018 - Mohammad Sheva Almeyda Sofjan
2. 13519069 - Muhammad Fikri N.
3. 13519091 - Mohammad Yahya Ibrahim

# Deskripsi Singkat : 
Program yang dapat mengkalkulasikan Sistem Persamaan Linier , Determinan, Invers Matriks, Interpolasi Polinom, dan Regresi Linear Berganda
dengan memanfaatkan properti matriks.\
Program dibuat dengan bahasa Java (JRE 15).\
Masukan dapat berupa input dari keyboard maupun file text.\
Untuk mengkalkulasikan masukan, terdapat berbagai metode tiap operasinya, di antaranya :
1. Operasi Sistem Persamaan Linier : Metode eliminasi Gauss, eliminasi Gauss-Jordan, matriks balikan, dan kaidah Cramer.
2. Operasi Determinan : Metode reduksi baris dan ekspansi kofaktor-minor.
3. Operasi Invers : Metode eliminasi Gauss-Jordan(reduksi baris) dan adjoin.
4. Operasi Interpolasi Polinom : Metode eliminasi Gauss-Jordan dan kaidah Cramer.
5. Operasi Regresi Linier Berganda : Metode eliminasi Gauss dan invers matriks.

# Cara menjalankan :
Pastikan JRE sudah terpasang di perangkat anda.\
Cara memastikan (untuk OS Windows) : Jalankan terminal (cmd) -> ketik "java -version" (tanpa tanda kutip).\
Cara menjalankan program (untuk OS Windows):
1. Versi 1, langsung run bytecode (bila sudah terdapat file .class di folder bin)\
    Jalankan terminal(cmd)\
    Pastikan sudah berada di folder "Algeo01-19018", jika belum silahkan change directory menggunakan perintah "cd" (tanpa tanda kutip).\
    Lalu, pada cmd ketik(masukkan perintah) : 
    ```
    cd bin
    Java Menu
    ```

2. Versi 2, membuat bytecode dari file .java \
    Jalankan terminal(cmd)\
    Pastikan sudah berada di folder "Algeo01-19018", jika belum silahkan change directory menggunakan perintah "cd" (tanpa tanda kutip).\
    Lalu, pada cmd ketik(masukkan perintah) :
    ```
    cd src
    javac *.java && java Menu
    ```
Catatan : akhiri tiap perintah dengan Enter\
Ketika sudah dijalankan, anda akan "disambut" oleh tampilan berikut :
```
------------------------------------------------
|                                              |
|                                              |
|                 KALKULATRIX                  |
|              Kalkulator Matrix               |
|            IF19 -018- -069- -091-            |
|                                              |
|                                              |
------------------------------------------------


-------------------MAIN MENU--------------------
| 1. Sistem Persamaan Linier                   |
| 2. Determinan                                |
| 3. Matriks balikan                           |
| 4. Interpolasi Polinom                       |
| 5. Regresi linier berganda                   |
| 6. Keluar                                    |
------------------------------------------------

Operasi apa yang anda inginkan? (1-6)
```
Yang dapat anda lakukan selanjutnya adalah dengan memasukkan perintah anda kepada program menggunakan keyboard.\
Selamat Menikmati!

