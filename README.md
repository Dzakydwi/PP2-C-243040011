Tugas 2 - Event Handling, Validasi Input, dan Navigasi CardLayout
Mata Kuliah: IF21W0508 – Praktikum Pemrograman II  
Nama Mahasiswa: Dwi Dzaky Wibowo
NIM: 243040011  

1. Deskripsi Proyek
Proyek ini merupakan pengembangan lanjutan dari Tugas 1 menjadi aplikasi Pendaftaran Kegiatan Mahasiswa yang Interaktif berbasis Java Swing. Aplikasi ini mengimplementasikan:
1. Navigasi Multi-Halaman dengan CardLayout: Mengelola 3 halaman utama (Formulir, Ringkasan, dan Bantuan) di dalam satu jendela (`JFrame`) tanpa membuka pop-up atau jendela duplikat.
2. Event-Driven Architecture: Menerapkan event handling pada aksi tombol:
   - Simpan / Tampilkan: Memvalidasi seluruh masukan pengguna, menyimpan data ke model `DataPendaftaran`, menampilkan ke halaman Ringkasan, dan memindahkan card aktif.
   - Reset Form: Mengosongkan form ke kondisi awal dan mereset status perubahan data.
   - Kembali: Berpindah kembali ke Halaman Formulir “tanpa kehilangan data” yang telah diinput sebelumnya.
   - Keluar: Menutup aplikasi dengan konfirmasi jika terdapat perubahan yang belum disimpan (dirty state), serta mendukung pembatalan konfirmasi keluar.
3. Validasi Input Ketat:
   - Nama Lengkap: Wajib diisi (tidak boleh kosong atau hanya spasi).
   - Email: Wajib diisi dengan format email yang wajar/valid (`regex`).
   - Umur: Wajib berupa bilangan bulat dalam rentang 15 sampai 100 tahun.
   - Pilihan Kegiatan: Wajib memilih opsi kegiatan yang valid (bukan opsi default).
   - Jika terdapat input yang salah, proses penyimpanan dibatalkan (tidak diproses), sistem menampilkan dialog pesan kesalahan yang spesifik, dan kontrol yang salah otomatis mendapatkan fokus kursor (`requestFocus()`).
4. Pencegahan Kehilangan Data (Unsaved Changes Confirmation):
   - Aplikasi secara otomatis mendeteksi perubahan data input (dirty checking).
   - Jika pengguna menekan tombol "Keluar" atau tombol silang jendela (X) saat ada data yang belum disimpan, sistem akan menampilkan dialog konfirmasi peringatan. Pengguna dapat memilih `Tidak` untuk membatalkan keluar.
2. Struktur Halaman & Komponen (CardLayout)

Nama Card | Komponen Kelas | Deskripsi 
`CARD_FORM` | `PanelFormulir.java` | Menampung formulir pendaftaran lengkap (Identitas, Kegiatan, Catatan, Persetujuan) dan tombol aksi (Bantuan, Reset, Keluar, Simpan/Tampilkan). |
`CARD_RINGKASAN` | `PanelRingkasan.java` | Menampilkan ringkasan lengkap data yang telah disimpan (Identitas, Kegiatan, Catatan, Timestamp), dilengkapi tombol Kembali dan Keluar. |
`CARD_BANTUAN` | `PanelBantuan.java` | Menampilkan petunjuk pengisian, daftar aturan validasi, dan penjelasan fungsi setiap tombol aplikasi. |


3. Lingkungan Pengembangan & Spesifikasi
- JDK Version: OpenJDK 25 LTS (Kompatibel dengan JDK 11, 17, 21, dan 25)
- IDE: Visual Studio Code dan Antigravity IDE
- Sistem Operasi: Windows 10/11 (64-bit)


4. Langkah Build dan Run

A. Melalui Terminal / Command Prompt:
1. Buka terminal pada folder proyek ini:
   ```bash
   cd "d:\praktikum smstr 5\Praktikum Pemograman II"
   ```
2. Kompilasi seluruh file Java:
   ```bash
   javac .java
   ```
3. Jalankan aplikasi utama:
   ```bash
   java Main
   ```

B. Menjalankan Pengujian Otomatis (9 Skenario Bagian 2):
Untuk memverifikasi secara langsung seluruh kriteria pengujian tugas:
```bash
java TestSkenarioTugas2
```

C. Menghasilkan Tangkapan Layar Aplikasi:
```bash
java GenerateScreenshots
```

D. Melalui IDE (VS Code / Apache NetBeans):
1. Buka folder proyek di editor/IDE pilihan.
2. Buka file `Main.java`.
3. Klik tombol Run Java atau tekan `F5` / `Ctrl + F5` (atau `Shift + F6` di NetBeans).

---

5. Struktur Direktori Proyek
```text
T02_243040011/
├── DataPendaftaran.java          # Model data untuk menampung data pendaftaran
├── Main.java                     # Main class, frame utama & pengatur CardLayout
├── PanelHeader.java              # Komponen header judul dan subjudul
├── PanelFormulir.java            # Halaman Formulir (Card 1)
├── PanelIdentitas.java           # Form identitas (Nama, Email, Umur, NIM, Prodi, Gender)
├── PanelKegiatan.java            # Form kegiatan (Jenis Kegiatan, Sesi, Fasilitas)
├── PanelCatatan.java             # Form catatan tambahan dan checkbox persetujuan
├── PanelTombol.java              # Panel tombol aksi halaman formulir
├── PanelRingkasan.java           # Halaman Ringkasan data pendaftaran (Card 2 - Modern Ticket Receipt)
├── PanelBantuan.java             # Halaman Bantuan dan panduan validasi (Card 3)
├── ModernDialog.java             # Pop-up kustom bertema Glassmorphism UI (Validasi, Konfirmasi, Sukses)
├── UIStyle.java                  # Konstanta gaya visual, warna ungu, font, dan tombol modern
├── TombolSimpanListener.java     # Event handler tombol Simpan / Tampilkan & validasi
├── TombolResetListener.java      # Event handler tombol Reset form
├── TombolKeluarListener.java     # Event handler tombol Keluar & konfirmasi
├── TombolDaftarListener.java     # Adapter listener untuk kompatibilitas Tugas 1
├── TestSkenarioTugas2.java       # Program pengujian otomatis seluruh skenario Bagian 2
├── GenerateScreenshots.java      # Utilitas penangkap layar aplikasi
├── README.md                     # Dokumentasi panduan teknis dan build/run
├── LAPORAN_RINGKAS.md            # Laporan pengujian dan bukti uji 9 skenario
└── screenshots/                  # Berkas tangkapan layar antarmuka & pop-up
    ├── 01_halaman_formulir.png
    ├── 02_halaman_ringkasan.png
    ├── 03_halaman_bantuan.png
    ├── 04_popup_validasi_input.png
    ├── 05_popup_konfirmasi_keluar.png
    └── 06_popup_pendaftaran_sukses.png
```


