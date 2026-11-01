import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Listener untuk menangani aksi tombol "Reset Form"
 * Mengembalikan seluruh field input, pilihan radio button, dan checkbox ke kondisi awal.
 */
public class TombolResetListener implements ActionListener {
    private final Main mainFrame;

    public TombolResetListener(Main mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Mengosongkan isian identitas
        mainFrame.txtNama.setText("");
        mainFrame.txtNim.setText("");
        mainFrame.cmbProdi.setSelectedIndex(0);
        mainFrame.bgGender.clearSelection();

        // Mengosongkan isian kegiatan
        mainFrame.cmbKegiatan.setSelectedIndex(0);
        mainFrame.bgSesi.clearSelection();
        mainFrame.cbSertifikat.setSelected(false);
        mainFrame.cbKonsumsi.setSelected(false);
        mainFrame.cbModul.setSelected(false);

        // Mengosongkan catatan dan checkbox persetujuan
        mainFrame.txtCatatan.setText("");
        mainFrame.cbSetuju.setSelected(false);

        // Mengarahkan kursor kembali ke input Nama
        mainFrame.txtNama.requestFocus();
    }
}