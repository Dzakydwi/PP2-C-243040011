import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Panel Tombol Aksi di bagian bawah Card Formulir.
 * Meniru gaya tombol Image 1: Tombol Reset di sisi kiri, Bantuan/Keluar dan Simpan di sisi kanan.
 */
public class PanelTombol extends JPanel {

    public JButton btnReset;
    public JButton btnBantuan;
    public JButton btnKeluar;
    public JButton btnSimpan;

    public PanelTombol(Main mainFrame) {
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(new EmptyBorder(12, 10, 8, 10));

        // Sisi Kiri: Tombol Reset Form (bergaya text link / secondary)
        btnReset = UIStyle.createSecondaryButton("< Reset Form", 115, 36);
        btnReset.setToolTipText("Mengosongkan isian formulir");
        add(btnReset, BorderLayout.WEST);

        // Sisi Kanan: Tombol Bantuan, Keluar, dan Simpan/Tampilkan (Purple Button)
        JPanel panelKanan = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelKanan.setOpaque(false);

        btnBantuan = UIStyle.createSecondaryButton("Bantuan", 90, 36);
        btnKeluar = UIStyle.createSecondaryButton("Keluar", 85, 36);
        btnSimpan = UIStyle.createPurpleButton("Simpan / Tampilkan  >", 175, 36);

        // Event Listeners
        btnReset.addActionListener(new TombolResetListener(mainFrame));
        btnBantuan.addActionListener(e -> mainFrame.showCard(Main.CARD_BANTUAN));
        btnKeluar.addActionListener(new TombolKeluarListener(mainFrame));
        btnSimpan.addActionListener(new TombolSimpanListener(mainFrame));

        panelKanan.add(btnBantuan);
        panelKanan.add(btnKeluar);
        panelKanan.add(btnSimpan);

        add(panelKanan, BorderLayout.EAST);
    }
}
