import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Listener untuk menangani aksi tombol "Reset Form"
 * Mengembalikan seluruh field input, pilihan combo box, radio button, dan checkbox ke kondisi awal.
 * Juga mengembalikan langkah formulir kembali ke Langkah 1 (Identitas).
 */
public class TombolResetListener implements ActionListener {
    private final Main mainFrame;

    public TombolResetListener(Main mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mainFrame.setResetting(true);
        try {
            // Mengosongkan isian identitas
            mainFrame.txtNama.setText("");
            mainFrame.txtEmail.setText("");
            mainFrame.txtUmur.setText("");
            mainFrame.txtNim.setText("");
            mainFrame.cmbProdi.setSelectedIndex(0);
            mainFrame.bgGender.clearSelection();

            // Mengosongkan isian kegiatan
            mainFrame.cmbKegiatan.setSelectedIndex(0);
            mainFrame.bgSesi.clearSelection();
            mainFrame.cbSertifikat.setSelected(false);
            mainFrame.cbKonsumsi.setSelected(false);
            mainFrame.cbModul.setSelected(false);

            // Mengosongkan catatan dan persetujuan
            mainFrame.txtCatatan.setText("");
            mainFrame.cbSetuju.setSelected(false);

            // Kembalikan ke Langkah 1 (Identitas Peserta)
            if (mainFrame.panelFormulir != null) {
                mainFrame.panelFormulir.setStep(1);
            }
        } finally {
            mainFrame.setResetting(false);
            mainFrame.setUnsavedChanges(false);
        }

        // Mengarahkan kursor kembali ke input Nama
        mainFrame.txtNama.requestFocus();
    }
}
