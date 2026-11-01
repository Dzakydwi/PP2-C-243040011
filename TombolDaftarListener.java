import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * Listener untuk menangani aksi tombol "Daftar Sekarang"
 * Berisi validasi kelengkapan data dan menampilkan dialog bukti pendaftaran.
 */
public class TombolDaftarListener implements ActionListener {
    private final Main mainFrame;

    public TombolDaftarListener(Main mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nama = mainFrame.txtNama.getText().trim();
        String nim = mainFrame.txtNim.getText().trim();

        // 1. Validasi Nama dan NIM
        if (nama.isEmpty() || nim.isEmpty()) {
            JOptionPane.showMessageDialog(
                    mainFrame,
                    "Mohon lengkapi Nama dan NIM terlebih dahulu!",
                    "Validasi Input",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Validasi Pilihan Kegiatan
        if (mainFrame.cmbKegiatan.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(
                    mainFrame,
                    "Silakan pilih jenis kegiatan yang ingin diikuti!",
                    "Validasi Input",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 3. Validasi Persetujuan Ketentuan
        if (!mainFrame.cbSetuju.isSelected()) {
            JOptionPane.showMessageDialog(
                    mainFrame,
                    "Anda harus menyetujui pernyataan dan ketentuan sebelum mendaftar!",
                    "Persetujuan Diperlukan",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 4. Membaca data form
        String gender = mainFrame.rbLaki.isSelected() ? "Laki-laki"
                : (mainFrame.rbPerempuan.isSelected() ? "Perempuan" : "-");
        String sesi = mainFrame.rbPagi.isSelected() ? "Pagi" : (mainFrame.rbSiang.isSelected() ? "Siang" : "-");

        StringBuilder fasilitas = new StringBuilder();
        if (mainFrame.cbSertifikat.isSelected())
            fasilitas.append("- Sertifikat Cetak\n");
        if (mainFrame.cbKonsumsi.isSelected())
            fasilitas.append("- Konsumsi\n");
        if (mainFrame.cbModul.isSelected())
            fasilitas.append("- Modul & Rekaman\n");
        if (fasilitas.length() == 0)
            fasilitas.append("- Tidak ada fasilitas tambahan\n");

        String ringkasan = String.format(
                "Pendaftaran Berhasil Dicatat!\n\n"
                        + "--- IDENTITAS ---\n"
                        + "Nama: %s\n"
                        + "NIM: %s\n"
                        + "Prodi: %s\n"
                        + "Jenis Kelamin: %s\n\n"
                        + "--- KEGIATAN ---\n"
                        + "Kegiatan: %s\n"
                        + "Sesi: %s\n"
                        + "Fasilitas:\n%s\n"
                        + "--- CATATAN ---\n"
                        + "%s",
                nama,
                nim,
                mainFrame.cmbProdi.getSelectedItem(),
                gender,
                mainFrame.cmbKegiatan.getSelectedItem(),
                sesi,
                fasilitas.toString(),
                mainFrame.txtCatatan.getText().trim().isEmpty() ? "(Tidak ada catatan)"
                        : mainFrame.txtCatatan.getText().trim());

        // 5. Tampilkan dialog bukti pendaftaran
        // JOptionPane.showMessageDialog(
        // mainFrame,
        // ringkasan,
        // "Bukti Pendaftaran",
        // JOptionPane.INFORMATION_MESSAGE
        // );
    }
}
