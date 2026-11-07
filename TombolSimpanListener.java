import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Listener untuk tombol "Simpan / Tampilkan"
 * Berisi validasi ketat sesuai ketentuan tugas dengan pop-up Glassmorphism UI:
 * 1. Nama wajib diisi.
 * 2. Email berformat wajar.
 * 3. Umur berupa bilangan bulat antara 15 hingga 100.
 * 4. Pilihan kegiatan wajib dipilih.
 * Jika valid, data disimpan ke model, dikirim ke PanelRingkasan, dan pop-up sukses ditampilkan.
 */
public class TombolSimpanListener implements ActionListener {
    private final Main mainFrame;

    public TombolSimpanListener(Main mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 1. Validasi Nama Lengkap (Wajib)
        String nama = mainFrame.txtNama.getText().trim();
        if (nama.isEmpty()) {
            if (mainFrame.panelFormulir != null) mainFrame.panelFormulir.setStep(1);
            ModernDialog.showWarning(
                    mainFrame,
                    "Validasi Input",
                    "Nama lengkap wajib diisi!");
            mainFrame.txtNama.requestFocus();
            return;
        }

        // 2. Validasi Email Berformat Wajar
        String email = mainFrame.txtEmail.getText().trim();
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (email.isEmpty()) {
            if (mainFrame.panelFormulir != null) mainFrame.panelFormulir.setStep(1);
            ModernDialog.showWarning(
                    mainFrame,
                    "Validasi Input",
                    "Email wajib diisi!");
            mainFrame.txtEmail.requestFocus();
            return;
        } else if (!email.matches(emailRegex)) {
            if (mainFrame.panelFormulir != null) mainFrame.panelFormulir.setStep(1);
            ModernDialog.showWarning(
                    mainFrame,
                    "Validasi Input",
                    "Format email tidak valid!\nContoh format yang benar: nama@domain.com");
            mainFrame.txtEmail.requestFocus();
            return;
        }

        // 3. Validasi Umur Berupa Bilangan Bulat 15-100
        String umurStr = mainFrame.txtUmur.getText().trim();
        if (umurStr.isEmpty()) {
            if (mainFrame.panelFormulir != null) mainFrame.panelFormulir.setStep(1);
            ModernDialog.showWarning(
                    mainFrame,
                    "Validasi Input",
                    "Umur wajib diisi!");
            mainFrame.txtUmur.requestFocus();
            return;
        }

        int umur;
        try {
            umur = Integer.parseInt(umurStr);
        } catch (NumberFormatException ex) {
            if (mainFrame.panelFormulir != null) mainFrame.panelFormulir.setStep(1);
            ModernDialog.showWarning(
                    mainFrame,
                    "Validasi Input",
                    "Umur harus berupa angka bilangan bulat!");
            mainFrame.txtUmur.requestFocus();
            return;
        }

        if (umur < 15 || umur > 100) {
            if (mainFrame.panelFormulir != null) mainFrame.panelFormulir.setStep(1);
            ModernDialog.showWarning(
                    mainFrame,
                    "Validasi Input",
                    "Umur harus berupa bilangan bulat antara 15 sampai 100 tahun!");
            mainFrame.txtUmur.requestFocus();
            return;
        }

        // 4. Validasi Pilihan Kegiatan Wajib
        if (mainFrame.cmbKegiatan.getSelectedIndex() <= 0) {
            if (mainFrame.panelFormulir != null) mainFrame.panelFormulir.setStep(2);
            ModernDialog.showWarning(
                    mainFrame,
                    "Validasi Input",
                    "Pilihan kegiatan wajib dipilih!");
            mainFrame.cmbKegiatan.requestFocus();
            return;
        }

        // 5. Validasi Persetujuan Ketentuan
        if (!mainFrame.cbSetuju.isSelected()) {
            if (mainFrame.panelFormulir != null) mainFrame.panelFormulir.setStep(2);
            ModernDialog.showWarning(
                    mainFrame,
                    "Validasi Input",
                    "Anda harus menyetujui pernyataan dan ketentuan sebelum menyimpan pendaftaran!");
            mainFrame.cbSetuju.requestFocus();
            return;
        }

        // Jika semua valid, susun data pendaftaran
        DataPendaftaran data = new DataPendaftaran();
        data.setNama(nama);
        data.setEmail(email);
        data.setUmur(umur);
        data.setNim(mainFrame.txtNim.getText().trim().isEmpty() ? "-" : mainFrame.txtNim.getText().trim());

        String prodi = mainFrame.cmbProdi.getSelectedIndex() > 0
                ? (String) mainFrame.cmbProdi.getSelectedItem()
                : "Belum Dipilih";
        data.setProdi(prodi);

        String gender = mainFrame.rbLaki.isSelected() ? "Laki-laki"
                : (mainFrame.rbPerempuan.isSelected() ? "Perempuan" : "Tidak Disebutkan");
        data.setJenisKelamin(gender);

        data.setKegiatan((String) mainFrame.cmbKegiatan.getSelectedItem());

        String sesi = mainFrame.rbPagi.isSelected() ? "Pagi (08:30 - 12:00 WIB)"
                : (mainFrame.rbSiang.isSelected() ? "Siang (13:00 - 16:30 WIB)" : "Belum Ditentukan");
        data.setSesi(sesi);

        List<String> fasilitas = new ArrayList<>();
        if (mainFrame.cbSertifikat.isSelected()) fasilitas.add("Sertifikat Cetak");
        if (mainFrame.cbKonsumsi.isSelected()) fasilitas.add("Makan Siang / Konsumsi");
        if (mainFrame.cbModul.isSelected()) fasilitas.add("Modul Materi & Rekaman");
        if (fasilitas.isEmpty()) fasilitas.add("Tidak ada fasilitas tambahan");
        data.setFasilitas(fasilitas);

        String catatan = mainFrame.txtCatatan.getText().trim();
        data.setCatatan(catatan.isEmpty() ? "(Tidak ada catatan)" : catatan);

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        data.setTanggalWaktuDaftar(timestamp);

        // Update tampilan Halaman Ringkasan
        mainFrame.panelRingkasan.tampilkanData(data);

        // Tandai tidak ada perubahan data yang belum disimpan
        mainFrame.setUnsavedChanges(false);

        // Navigasi ke Halaman Ringkasan menggunakan CardLayout
        mainFrame.showCard(Main.CARD_RINGKASAN);

        // Tampilkan pop-up modern Glassmorphism Pendaftaran Sukses
        ModernDialog.showSuccess(
                mainFrame,
                "Pendaftaran Sukses",
                "Pendaftaran berhasil disimpan!\nMenampilkan halaman ringkasan data."
        );
    }
}
