import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 * Panel Kelompok 2: Pilihan Kegiatan
 * Menggunakan GridBagLayout untuk penataan pilihan kegiatan, sesi, dan fasilitas.
 */
public class PanelKegiatan extends JPanel {

    public JComboBox<String> cmbKegiatan;
    public JRadioButton rbPagi;
    public JRadioButton rbSiang;
    public ButtonGroup bgSesi;
    public JCheckBox cbSertifikat;
    public JCheckBox cbKonsumsi;
    public JCheckBox cbModul;

    public PanelKegiatan() {
        setLayout(new GridBagLayout());
        setBorder(new CompoundBorder(
                new TitledBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)), " Pilihan Kegiatan "),
                new EmptyBorder(8, 12, 8, 12)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.anchor = GridBagConstraints.WEST;

        // Baris 1: Pilihan Kegiatan (JComboBox)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        add(new JLabel("Jenis Kegiatan :"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        String[] daftarKegiatan = {
                "-- Pilih Kegiatan --",
                "Seminar Nasional: Tren AI & Machine Learning 2026",
                "Workshop: Desain Antarmuka Modern dengan Java Swing",
                "Pelatihan: Cloud Computing & Microservices",
                "Bootcamp: Fullstack Web Development",
                "Lomba: Competitive Programming Championship"
        };
        cmbKegiatan = new JComboBox<>(daftarKegiatan);
        add(cmbKegiatan, gbc);

        // Baris 2: Sesi Kehadiran (JRadioButton + ButtonGroup)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST; // Label rata atas
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        add(new JLabel("Sesi Waktu :"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        rbPagi = new JRadioButton("Pagi (08:30 - 12:00 WIB)");
        rbSiang = new JRadioButton("Siang (13:00 - 16:30 WIB)");
        rbPagi.setOpaque(false);
        rbSiang.setOpaque(false);

        bgSesi = new ButtonGroup();
        bgSesi.add(rbPagi);
        bgSesi.add(rbSiang);

        // Disusun vertikal menggunakan GridLayout agar kedua sesi selalu tampil lengkap dan tidak pernah terpotong saat resize
        JPanel panelSesi = new JPanel(new GridLayout(0, 1, 0, 2));
        panelSesi.setOpaque(false);
        panelSesi.add(rbPagi);
        panelSesi.add(rbSiang);
        add(panelSesi, gbc);

        // Baris 3: Fasilitas Tambahan (JCheckBox)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTHWEST; // Label rata atas
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        add(new JLabel("Fasilitas Tambahan :"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        cbSertifikat = new JCheckBox("Sertifikat Cetak");
        cbKonsumsi = new JCheckBox("Makan Siang / Konsumsi");
        cbModul = new JCheckBox("Modul Materi & Rekaman");
        cbSertifikat.setOpaque(false);
        cbKonsumsi.setOpaque(false);
        cbModul.setOpaque(false);

        // Disusun vertikal menggunakan GridLayout agar seluruh opsi fasilitas (3 opsi) selalu tampil lengkap saat di-resize
        JPanel panelFasilitas = new JPanel(new GridLayout(0, 1, 0, 2));
        panelFasilitas.setOpaque(false);
        panelFasilitas.add(cbSertifikat);
        panelFasilitas.add(cbKonsumsi);
        panelFasilitas.add(cbModul);
        add(panelFasilitas, gbc);
    }
}
