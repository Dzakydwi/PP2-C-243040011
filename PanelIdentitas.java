import java.awt.*;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Panel Kelompok 1: Identitas Peserta
 * Desain modern 2-kolom dengan label di atas input field, styling rounded field, dan custom radio button.
 */
public class PanelIdentitas extends JPanel {

    public JTextField txtNama;
    public JTextField txtEmail;
    public JTextField txtUmur;
    public JTextField txtNim;
    public JComboBox<String> cmbProdi;
    public JRadioButton rbLaki;
    public JRadioButton rbPerempuan;
    public ButtonGroup bgGender;

    public PanelIdentitas(Main mainFrame) {
        setLayout(new GridBagLayout());
        setOpaque(false);
        setBorder(new CompoundBorder(
                new TitledBorder(new LineBorder(UIStyle.BORDER_LIGHT, 1, true),
                        " 1. Identitas Peserta ",
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

        // --- Baris 1: Label Nama & Label NIM ---
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 0.5;
        add(createFieldLabel("Nama Lengkap *"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.5;
        add(createFieldLabel("NIM"), gbc);

        // --- Baris 2: Input Nama & Input NIM ---
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.insets = new Insets(0, 8, 10, 8);
        txtNama = new JTextField();
        UIStyle.styleTextField(txtNama);
        add(txtNama, gbc);

        gbc.gridx = 1;
        txtNim = new JTextField();
        UIStyle.styleTextField(txtNim);
        add(txtNim, gbc);

        // --- Baris 3: Label Email & Label Program Studi ---
        gbc.gridy = 2;
        gbc.insets = new Insets(6, 8, 3, 8);
        gbc.gridx = 0;
        add(createFieldLabel("Email *"), gbc);

        gbc.gridx = 1;
        add(createFieldLabel("Program Studi"), gbc);

        // --- Baris 4: Input Email & ComboBox Program Studi ---
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 8, 10, 8);
        gbc.gridx = 0;
        txtEmail = new JTextField();
        UIStyle.styleTextField(txtEmail);
        add(txtEmail, gbc);

        gbc.gridx = 1;
        String[] daftarProdi = {
                "-- Pilih Program Studi --",
                "Teknik Informatika",
                "Sistem Informasi",
                "Teknik Industri",
                "Teknik Mesin",
                "Teknologi Pangan"
        };
        cmbProdi = new JComboBox<>(daftarProdi);
        UIStyle.styleComboBox(cmbProdi);
        add(cmbProdi, gbc);

        // --- Baris 5: Label Umur & Label Jenis Kelamin ---
        gbc.gridy = 4;
        gbc.insets = new Insets(6, 8, 3, 8);
        gbc.gridx = 0;
        add(createFieldLabel("Umur (15–100 Tahun) *"), gbc);

        gbc.gridx = 1;
        add(createFieldLabel("Jenis Kelamin"), gbc);

        // --- Baris 6: Input Umur & Radio Button Gender ---
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 8, 6, 8);
        gbc.gridx = 0;
        txtUmur = new JTextField();
        UIStyle.styleTextField(txtUmur);
        add(txtUmur, gbc);

        gbc.gridx = 1;
        rbLaki = new JRadioButton("Laki-laki");
        rbPerempuan = new JRadioButton("Perempuan");
        UIStyle.styleRadioButton(rbLaki);
        UIStyle.styleRadioButton(rbPerempuan);

        bgGender = new ButtonGroup();
        bgGender.add(rbLaki);
        bgGender.add(rbPerempuan);

        JPanel panelGender = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 4));
        panelGender.setOpaque(false);
        panelGender.add(rbLaki);
        panelGender.add(rbPerempuan);
        add(panelGender, gbc);

        // Listener perubahan unsaved changes
        attachChangeListeners(mainFrame);
    }

    private JLabel createFieldLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(UIStyle.FONT_LABEL);
        lbl.setForeground(UIStyle.TEXT_DARK);
        return lbl;
    }

    private void attachChangeListeners(Main mainFrame) {
        DocumentListener docListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { mainFrame.markAsModified(); }
            @Override
            public void removeUpdate(DocumentEvent e) { mainFrame.markAsModified(); }
            @Override
            public void changedUpdate(DocumentEvent e) { mainFrame.markAsModified(); }
        };

        txtNama.getDocument().addDocumentListener(docListener);
        txtEmail.getDocument().addDocumentListener(docListener);
        txtUmur.getDocument().addDocumentListener(docListener);
        txtNim.getDocument().addDocumentListener(docListener);

        cmbProdi.addItemListener(e -> mainFrame.markAsModified());
        rbLaki.addItemListener(e -> mainFrame.markAsModified());
        rbPerempuan.addItemListener(e -> mainFrame.markAsModified());
    }
}
