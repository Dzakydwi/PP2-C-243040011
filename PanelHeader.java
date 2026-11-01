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

    public PanelHeader() {
        setLayout(new BorderLayout());
        setBackground(new Color(41, 128, 185)); // Biru elegan
        setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblJudul = new JLabel("FORMULIR PENDAFTARAN KEGIATAN", JLabel.CENTER);
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblJudul.setForeground(Color.WHITE);

        JLabel lblSub = new JLabel("Praktikum Pemrograman II - Dasar GUI Swing & Layout", JLabel.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(new Color(236, 240, 241));

        add(lblJudul, BorderLayout.NORTH);
        add(lblSub, BorderLayout.SOUTH);
    }
}
