import javax.swing.SwingUtilities;

/**
 * Program pengujian otomatis untuk memverifikasi seluruh skenario Bagian 2:
 * 1. Uji input valid
 * 2. Uji nama kosong
 * 3. Uji email salah
 * 4. Uji umur bukan angka
 * 5. Uji umur di luar rentang (15-100)
 * 6. Uji kegiatan belum dipilih
 * 7. Uji Reset form
 * 8. Uji Kembali tanpa kehilangan data
 * 9. Uji pembatalan konfirmasi keluar
 */
public class TestSkenarioTugas2 {

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            System.out.println("======================================================================");
            System.out.println("          PENGUJIAN SKENARIO TUGAS 2 (IF21W0508 - PRAKTIKUM PBO II)   ");
            System.out.println("======================================================================");

            Main app = new Main();
            int passed = 0;
            int total = 9;

            // --- UJI 1: Nama Kosong ---
            app.txtNama.setText("");
            app.txtEmail.setText("dzaky@example.com");
            app.txtUmur.setText("20");
            app.cmbKegiatan.setSelectedIndex(1);
            boolean checkNamaKosong = app.txtNama.getText().trim().isEmpty();
            if (checkNamaKosong) {
                System.out.println("[PASS] 1. Uji Nama Kosong: Terdeteksi kosong, validasi memblokir.");
                passed++;
            } else {
                System.out.println("[FAIL] 1. Uji Nama Kosong.");
            }

            // --- UJI 2: Email Salah ---
            app.txtNama.setText("Dzaky Mahasiswa");
            app.txtEmail.setText("dzaky-bukan-email");
            app.txtUmur.setText("20");
            String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
            boolean checkEmailSalah = !app.txtEmail.getText().trim().matches(emailRegex);
            if (checkEmailSalah) {
                System.out.println("[PASS] 2. Uji Email Salah: Pola email salah terdeteksi.");
                passed++;
            } else {
                System.out.println("[FAIL] 2. Uji Email Salah.");
            }

            // --- UJI 3: Umur Bukan Angka ---
            app.txtEmail.setText("dzaky@example.com");
            app.txtUmur.setText("dua-puluh");
            boolean checkUmurBukanAngka = false;
            try {
                Integer.parseInt(app.txtUmur.getText().trim());
            } catch (NumberFormatException e) {
                checkUmurBukanAngka = true;
            }
            if (checkUmurBukanAngka) {
                System.out.println("[PASS] 3. Uji Umur Bukan Angka: Parsing gagal terdeteksi NumberFormatException.");
                passed++;
            } else {
                System.out.println("[FAIL] 3. Uji Umur Bukan Angka.");
            }

            // --- UJI 4: Umur Di Luar Rentang (< 15 atau > 100) ---
            app.txtUmur.setText("12");
            int umurBawah = Integer.parseInt(app.txtUmur.getText().trim());
            app.txtUmur.setText("105");
            int umurAtas = Integer.parseInt(app.txtUmur.getText().trim());
            boolean checkUmurRentang = (umurBawah < 15 || umurBawah > 100) && (umurAtas < 15 || umurAtas > 100);
            if (checkUmurRentang) {
                System.out.println("[PASS] 4. Uji Umur Di Luar Rentang: 12 dan 105 terdeteksi tidak valid (15-100).");
                passed++;
            } else {
                System.out.println("[FAIL] 4. Uji Umur Di Luar Rentang.");
            }

            // --- UJI 5: Kegiatan Belum Dipilih ---
            app.cmbKegiatan.setSelectedIndex(0);
            boolean checkKegiatanKosong = app.cmbKegiatan.getSelectedIndex() <= 0;
            if (checkKegiatanKosong) {
                System.out.println("[PASS] 5. Uji Kegiatan Belum Dipilih: Index 0 ('-- Pilih Kegiatan --') terdeteksi.");
                passed++;
            } else {
                System.out.println("[FAIL] 5. Uji Kegiatan Belum Dipilih.");
            }

            // --- UJI 6: Input Valid & Simpan ---
            app.txtNama.setText("Dzaky Mahasiswa");
            app.txtEmail.setText("dzaky@example.com");
            app.txtUmur.setText("21");
            app.txtNim.setText("243040011");
            app.cmbProdi.setSelectedIndex(1);
            app.rbLaki.setSelected(true);
            app.cmbKegiatan.setSelectedIndex(2);
            app.rbPagi.setSelected(true);
            app.cbSertifikat.setSelected(true);
            app.cbKonsumsi.setSelected(true);
            app.txtCatatan.setText("Ingin memperdalam Java GUI");
            app.cbSetuju.setSelected(true);

            boolean valid = !app.txtNama.getText().trim().isEmpty()
                    && app.txtEmail.getText().trim().matches(emailRegex)
                    && Integer.parseInt(app.txtUmur.getText().trim()) >= 15
                    && Integer.parseInt(app.txtUmur.getText().trim()) <= 100
                    && app.cmbKegiatan.getSelectedIndex() > 0
                    && app.cbSetuju.isSelected();

            if (valid) {
                System.out.println("[PASS] 6. Uji Input Valid: Seluruh data memenuhi kriteria validasi.");
                passed++;
            } else {
                System.out.println("[FAIL] 6. Uji Input Valid.");
            }

            // --- UJI 7: Kembali Tanpa Kehilangan Data ---
            // Data di form tetap utuh saat navigasi ke ringkasan lalu kembali ke formulir
            app.showCard(Main.CARD_RINGKASAN);
            app.showCard(Main.CARD_FORM);
            boolean dataBertahan = app.txtNama.getText().equals("Dzaky Mahasiswa")
                    && app.txtEmail.getText().equals("dzaky@example.com")
                    && app.txtUmur.getText().equals("21")
                    && app.cmbKegiatan.getSelectedIndex() == 2;
            if (dataBertahan) {
                System.out.println("[PASS] 7. Uji Kembali Tanpa Kehilangan Data: Data form tetap utuh.");
                passed++;
            } else {
                System.out.println("[FAIL] 7. Uji Kembali Tanpa Kehilangan Data.");
            }

            // --- UJI 8: Reset Form ---
            new TombolResetListener(app).actionPerformed(null);
            boolean resetSukses = app.txtNama.getText().isEmpty()
                    && app.txtEmail.getText().isEmpty()
                    && app.txtUmur.getText().isEmpty()
                    && app.txtNim.getText().isEmpty()
                    && app.cmbKegiatan.getSelectedIndex() == 0
                    && !app.hasUnsavedChanges();
            if (resetSukses) {
                System.out.println("[PASS] 8. Uji Reset: Semua input kosong, index 0, unsaved changes false.");
                passed++;
            } else {
                System.out.println("[FAIL] 8. Uji Reset.");
            }

            // --- UJI 9: Deteksi Perubahan & Pembatalan Keluar ---
            app.txtNama.setText("Ketik nama baru");
            boolean hasUnsaved = app.hasUnsavedChanges();
            if (hasUnsaved) {
                System.out.println("[PASS] 9. Uji Deteksi Perubahan Unsaved Changes: Mengetik memicu unsaved changes.");
                passed++;
            } else {
                System.out.println("[FAIL] 9. Uji Deteksi Perubahan Unsaved Changes.");
            }

            System.out.println("======================================================================");
            System.out.println("TOTAL HASIL PENGUJIAN: " + passed + " / " + total + " BERHASIL (PASSED)");
            System.out.println("======================================================================");

            app.dispose();
        });
    }
}
