import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Listener untuk menangani aksi tombol "Keluar"
 * Mendelegasikan pengecekan dan konfirmasi keluar ke metode konfirmasiKeluar() pada Main.
 */
public class TombolKeluarListener implements ActionListener {
    private final Main mainFrame;

    public TombolKeluarListener(Main mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mainFrame.konfirmasiKeluar();
    }
}
