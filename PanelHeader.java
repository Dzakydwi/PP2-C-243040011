import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Panel Header Judul Aplikasi (Bagian Atas / NORTH)
 */
public class PanelHeader extends JPanel {

    private JLabel lblJudul;
    private JLabel lblSub;

    public PanelHeader() {
        this("FORMULIR PENDAFTARAN KEGIATAN", "Praktikum Pemrograman II - Form, Ringkasan, & Bantuan");
    }

    public PanelHeader(String judul, String subjudul) {
        setLayout(new BorderLayout());
        setBackground(new Color(41, 128, 185)); // Biru elegan
        setBorder(new EmptyBorder(12, 20, 12, 20));

        lblJudul = new JLabel(judul, JLabel.CENTER);
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblJudul.setForeground(Color.WHITE);

        lblSub = new JLabel(subjudul, JLabel.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(new Color(236, 240, 241));

        add(lblJudul, BorderLayout.NORTH);
        add(lblSub, BorderLayout.SOUTH);
    }

    public void setTitle(String judul, String subjudul) {
        lblJudul.setText(judul);
        lblSub.setText(subjudul);
    }
}
