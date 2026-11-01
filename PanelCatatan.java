import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 * Panel Kelompok 3: Catatan Khusus & Persetujuan
 * Menggunakan GridBagLayout yang responsif untuk menampung textarea dan checkbox.
 */
public class PanelCatatan extends JPanel {

    public JTextArea txtCatatan;
    public JCheckBox cbSetuju;

    public PanelCatatan() {
        setLayout(new GridBagLayout());
        setBorder(new CompoundBorder(
                new TitledBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)),
                        " Catatan Khusus & Persetujuan "),
                new EmptyBorder(8, 12, 8, 12)
        ));
        // Menetapkan ukuran minimum agar panel tidak pernah gepeng saat jendela diciutkan
        setMinimumSize(new Dimension(200, 125));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.anchor = GridBagConstraints.NORTHWEST;

        // Baris 1: Catatan (JTextArea dengan JScrollPane yang responsif)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        add(new JLabel("Catatan / Alasan :"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0; // Mengembang mengisi area vertikal

        txtCatatan = new JTextArea(4, 25);
        txtCatatan.setLineWrap(true);
        txtCatatan.setWrapStyleWord(true);
        txtCatatan.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JScrollPane scrollCatatan = new JScrollPane(txtCatatan);
        scrollCatatan.setPreferredSize(new Dimension(200, 85));
        scrollCatatan.setMinimumSize(new Dimension(150, 75));
        add(scrollCatatan, gbc);

        // Baris 2: Checkbox Persetujuan (JCheckBox)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0.0;

        cbSetuju = new JCheckBox(
                "Saya menyatakan bahwa data yang diisi adalah benar dan menyetujui ketentuan kegiatan."
        );
        cbSetuju.setOpaque(false);
        add(cbSetuju, gbc);
    }
}
