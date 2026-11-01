# Tugas 1 - Dasar GUI Swing dan Layout
**Mata Kuliah:** IF21W0508 – Praktikum Pemrograman II  
**Nama Mahasiswa:** Dzaky  
**NIM:** 243040011  

---

## 1. Deskripsi Proyek
Aplikasi ini adalah **Formulir Pendaftaran Kegiatan Mahasiswa** berbasis desktop yang dibangun menggunakan pustaka **Java Swing**. Aplikasi dirancang dengan antarmuka yang rapi, modular, dan responsif terhadap perubahan ukuran jendela (*window resizing*).

### Komponen Swing yang Digunakan:
1. `JFrame`: Window utama aplikasi dengan penanganan `setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)`.
2. `JPanel`: Panel pembagi area dan pengelompokan (*grouping*).
3. `JLabel`: Menampilkan teks judul header, label input field, dan keterangan.
4. `JTextField`: Input teks satu baris untuk *Nama Lengkap* dan *NIM*.
5. `JComboBox`: Dropdown menu untuk memilih *Program Studi* dan *Jenis Kegiatan*.
6. `JRadioButton` & `ButtonGroup`: Pilihan opsi eksklusif untuk *Jenis Kelamin* (Laki-laki / Perempuan) dan *Sesi Waktu* (Pagi / Siang).
7. `JCheckBox`: Pilihan jamak (opsional) untuk *Fasilitas Tambahan* (Sertifikat, Konsumsi, Modul) dan *Persetujuan Ketentuan*.
8. `JTextArea` & `JScrollPane`: Input teks multi-baris untuk *Catatan Tambahan / Alasan* yang dilengkapi fitur scrollbar dan word-wrap.
9. `JButton`: Tombol aksi interaktif (*Daftar Sekarang*, *Reset Form*, dan *Keluar*).

### Layout Manager yang Digunakan (Tanpa Absolute Positioning / Null Layout):
1. **`BorderLayout`**:
   - Layout utama pada `JFrame` ContentPane.
   - `NORTH`: Panel Header / Judul Aplikasi.
   - `CENTER`: Panel formulir yang dibungkus `JScrollPane` agar konten tetap dapat diakses saat jendela berukuran kecil.
   - `SOUTH`: Panel tombol navigasi dan aksi.
2. **`GridBagLayout`**:
   - Digunakan pada setiap panel kelompok (*Identitas*, *Pilihan Kegiatan*, dan *Catatan*).
   - Memastikan label dan input tersusun presisi, rata kiri-kanan, dan fleksibel mengikuti pelebaran jendela (`weightx`, `fill = HORIZONTAL`).
3. **`FlowLayout`**:
   - Menata tombol aksi di bagian bawah secara horizontal dan rapi ke sisi kanan (`FlowLayout.RIGHT`).
   - Menata sub-komponen radio button dan checkbox.
4. **`BoxLayout` (Y_AXIS)**:
   - Menyusun panel-panel kelompok secara vertikal dari atas ke bawah dengan jarak pemisah (`Box.createRigidArea`).

### Inisialisasi:
Aplikasi diinisialisasi dan dibuka secara aman melalui thread UI resmi Java Swing yaitu **Event Dispatch Thread (EDT)**:
```java
SwingUtilities.invokeLater(new Runnable() {
    @Override
    public void run() {
        new Main().setVisible(true);
    }
});
```

---

## 2. Lingkungan Pengembangan & Spesifikasi
- **JDK Version:** OpenJDK 25 LTS (Kompatibel dengan JDK 11, 17, 21, dan 25)
- **IDE:** Antigravity IDE / Visual Studio Code / Apache NetBeans
- **Sistem Operasi:** Windows 10/11 (64-bit)

---

## 3. Langkah Build dan Run

### A. Melalui Terminal / Command Prompt:
1. Buka terminal pada folder proyek ini.
2. Lakukan kompilasi file Java:
   ```bash
   javac Main.java
   ```
3. Jalankan aplikasi:
   ```bash
   java Main
   ```

### B. Melalui IDE (VS Code / Antigravity IDE):
1. Buka folder proyek di editor.
2. Buka file `Main.java`.
3. Klik tombol **Run Java** (ikon segitiga *Play* di pojok kanan atas) atau tekan `F5` / `Ctrl + F5`.

### C. Melalui Apache NetBeans:
1. Buka project atau buat file `Main.java`.
2. Klik kanan pada file `Main.java` -> pilih **Run File** (`Shift + F6`).

---

## 4. Struktur Direktori Proyek
```text
T01_243040011/
├── Main.java                 # Kode sumber utama formulir GUI
├── README.md                 # Dokumentasi proyek dan panduan run
├── LAPORAN_RINGKAS.md        # Laporan pengujian dan bukti uji
└── screenshots/              # Tangkapan layar bukti uji responsivitas
    ├── tampilan_awal.png
    ├── tampilan_resize_besar.png
    └── tampilan_resize_kecil.png
```
