import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Adapter kompatibilitas TombolDaftarListener ke TombolSimpanListener.
 */
public class TombolDaftarListener implements ActionListener {
    private final TombolSimpanListener simpanListener;

    public TombolDaftarListener(Main mainFrame) {
        this.simpanListener = new TombolSimpanListener(mainFrame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        simpanListener.actionPerformed(e);
    }
}
