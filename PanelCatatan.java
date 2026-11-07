import java.awt.*;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Panel Kelompok 3: Catatan Khusus & Persetujuan
 */
public class PanelCatatan extends JPanel {

    public JTextArea txtCatatan;
    public JCheckBox cbSetuju;

    public PanelCatatan(Main mainFrame) {
        setLayout(new GridBagLayout());
        setOpaque(false);
        setBorder(new CompoundBorder(
                new TitledBorder(new LineBorder(UIStyle.BORDER_LIGHT, 1, true),
                        " 3. Catatan Khusus & Persetujuan ",
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

        // Baris 1: Label Catatan
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        JLabel lblCat = new JLabel("Catatan Tambahan / Alasan Mengikuti Kegiatan");
        lblCat.setFont(UIStyle.FONT_LABEL);
        lblCat.setForeground(UIStyle.TEXT_DARK);
        add(lblCat, gbc);

        // Baris 2: TextArea Catatan dengan styling rounded dan padding
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 8, 12, 8);
        txtCatatan = new JTextArea(3, 20);
        txtCatatan.setFont(UIStyle.FONT_INPUT);
        txtCatatan.setLineWrap(true);
        txtCatatan.setWrapStyleWord(true);
        txtCatatan.setForeground(UIStyle.TEXT_DARK);
        txtCatatan.setCaretColor(UIStyle.PURPLE_PRIMARY);

        JScrollPane scrollCatatan = new JScrollPane(txtCatatan);
        scrollCatatan.setBorder(new CompoundBorder(
                new LineBorder(UIStyle.BORDER_LIGHT, 1, true),
                new EmptyBorder(6, 8, 6, 8)
        ));
        add(scrollCatatan, gbc);

        // Baris 3: Checkbox Persetujuan dengan custom check icon
        gbc.gridy = 2;
        gbc.insets = new Insets(4, 8, 4, 8);
        cbSetuju = new JCheckBox(
                "Saya menyatakan bahwa data yang diisi adalah benar dan menyetujui ketentuan pendaftaran."
        );
        UIStyle.styleCheckBox(cbSetuju);
        add(cbSetuju, gbc);

        // Listener perubahan
        txtCatatan.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { mainFrame.markAsModified(); }
            @Override
            public void removeUpdate(DocumentEvent e) { mainFrame.markAsModified(); }
            @Override
            public void changedUpdate(DocumentEvent e) { mainFrame.markAsModified(); }
        });

        cbSetuju.addItemListener(e -> mainFrame.markAsModified());
    }
}
