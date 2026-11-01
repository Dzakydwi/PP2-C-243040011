import java.awt.*;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Panel Tombol Aksi di bagian bawah (Bagian Bawah / SOUTH)
 * Menggunakan FlowLayout
 */
public class PanelTombol extends JPanel {

    public JButton btnReset;
    public JButton btnKeluar;
    public JButton btnDaftar;

    public PanelTombol(Main mainFrame) {
        setLayout(new FlowLayout(FlowLayout.RIGHT, 12, 12));
        setBorder(new EmptyBorder(5, 10, 10, 15));

        btnReset = new JButton("Reset Form");
        btnReset.setPreferredSize(new Dimension(110, 32));

        btnKeluar = new JButton("Keluar");
        btnKeluar.setPreferredSize(new Dimension(90, 32));

        // Tombol Daftar Sekarang dengan pewarnaan yang kompatibel di seluruh sistem operasi
        btnDaftar = new JButton("Daftar Sekarang") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(new Color(31, 97, 141));
                } else if (getModel().isRollover()) {
                    g2.setColor(new Color(52, 152, 219));
                } else {
                    g2.setColor(new Color(41, 128, 185));
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnDaftar.setContentAreaFilled(false);
        btnDaftar.setFocusPainted(false);
        btnDaftar.setForeground(Color.WHITE);
        btnDaftar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnDaftar.setPreferredSize(new Dimension(140, 32));

        // Event Handling dihubungkan ke masing-masing listener
        btnDaftar.addActionListener(new TombolDaftarListener(mainFrame));
        btnReset.addActionListener(new TombolResetListener(mainFrame));
        btnKeluar.addActionListener(new TombolKeluarListener(mainFrame));

        add(btnReset);
        add(btnKeluar);
        add(btnDaftar);
    }
}
