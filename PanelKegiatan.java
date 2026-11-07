import java.awt.*;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * Panel Kelompok 2: Pilihan Kegiatan
 * Penataan modern untuk dropdown kegiatan, opsi sesi waktu dengan radio button custom,
 * dan fasilitas tambahan dengan custom checkbox.
 */
public class PanelKegiatan extends JPanel {

    public JComboBox<String> cmbKegiatan;
    public JRadioButton rbPagi;
    public JRadioButton rbSiang;
    public ButtonGroup bgSesi;
    public JCheckBox cbSertifikat;
    public JCheckBox cbKonsumsi;
    public JCheckBox cbModul;

    public PanelKegiatan(Main mainFrame) {
        setLayout(new GridBagLayout());
        setOpaque(false);
        setBorder(new CompoundBorder(
                new TitledBorder(new LineBorder(UIStyle.BORDER_LIGHT, 1, true),
                        " 2. Pilihan Kegiatan ",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        UIStyle.FONT_SECTION,
                        UIStyle.PURPLE_PRIMARY),
                new EmptyBorder(12, 16, 14, 16)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 3, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Baris 1: Label Jenis Kegiatan
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        JLabel lblKeg = new JLabel("Pilih Jenis Kegiatan *");
        lblKeg.setFont(UIStyle.FONT_LABEL);
        lblKeg.setForeground(UIStyle.TEXT_DARK);
        add(lblKeg, gbc);

        // Baris 2: ComboBox Kegiatan
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 8, 12, 8);
        String[] daftarKegiatan = {
                "-- Pilih Kegiatan --",
                "Seminar Nasional: Tren AI & Machine Learning 2026",
                "Workshop: Desain Antarmuka Modern dengan Java Swing",
                "Pelatihan: Cloud Computing & Microservices",
                "Bootcamp: Fullstack Web Development",
                "Lomba: Competitive Programming Championship"
        };
        cmbKegiatan = new JComboBox<>(daftarKegiatan);
        UIStyle.styleComboBox(cmbKegiatan);
        add(cmbKegiatan, gbc);

        // Baris 3: Label Sesi & Label Fasilitas
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(6, 8, 4, 8);
        gbc.gridx = 0;
        gbc.weightx = 0.5;
        JLabel lblSesi = new JLabel("Pilih Sesi Waktu");
        lblSesi.setFont(UIStyle.FONT_LABEL);
        lblSesi.setForeground(UIStyle.TEXT_DARK);
        add(lblSesi, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.5;
        JLabel lblFas = new JLabel("Fasilitas Tambahan");
        lblFas.setFont(UIStyle.FONT_LABEL);
        lblFas.setForeground(UIStyle.TEXT_DARK);
        add(lblFas, gbc);

        // Baris 4: Panel Sesi (Custom Radio) & Panel Fasilitas (Custom Checkbox)
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 8, 6, 8);
        gbc.gridx = 0;
        rbPagi = new JRadioButton("Pagi (08:30 - 12:00 WIB)");
        rbSiang = new JRadioButton("Siang (13:00 - 16:30 WIB)");
        UIStyle.styleRadioButton(rbPagi);
        UIStyle.styleRadioButton(rbSiang);

        bgSesi = new ButtonGroup();
        bgSesi.add(rbPagi);
        bgSesi.add(rbSiang);

        JPanel panelSesi = new JPanel(new GridLayout(0, 1, 0, 8));
        panelSesi.setOpaque(false);
        panelSesi.add(rbPagi);
        panelSesi.add(rbSiang);
        add(panelSesi, gbc);

        gbc.gridx = 1;
        cbSertifikat = new JCheckBox("Sertifikat Cetak");
        cbKonsumsi = new JCheckBox("Makan Siang / Konsumsi");
        cbModul = new JCheckBox("Modul Materi & Rekaman");
        UIStyle.styleCheckBox(cbSertifikat);
        UIStyle.styleCheckBox(cbKonsumsi);
        UIStyle.styleCheckBox(cbModul);

        JPanel panelFasilitas = new JPanel(new GridLayout(0, 1, 0, 8));
        panelFasilitas.setOpaque(false);
        panelFasilitas.add(cbSertifikat);
        panelFasilitas.add(cbKonsumsi);
        panelFasilitas.add(cbModul);
        add(panelFasilitas, gbc);

        // Listener perubahan
        cmbKegiatan.addItemListener(e -> mainFrame.markAsModified());
        rbPagi.addItemListener(e -> mainFrame.markAsModified());
        rbSiang.addItemListener(e -> mainFrame.markAsModified());
        cbSertifikat.addItemListener(e -> mainFrame.markAsModified());
        cbKonsumsi.addItemListener(e -> mainFrame.markAsModified());
        cbModul.addItemListener(e -> mainFrame.markAsModified());
    }
}
