import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;


public class PanelIdentitas extends JPanel {

    public JTextField txtNama;
    public JTextField txtNim;
    public JComboBox<String> cmbProdi;
    public JRadioButton rbLaki;
    public JRadioButton rbPerempuan;
    public ButtonGroup bgGender;

    public PanelIdentitas() {
        setLayout(new GridBagLayout());
        setBorder(new CompoundBorder(
                new TitledBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)), " Identitas Peserta "),
                new EmptyBorder(8, 12, 8, 12)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        add(new JLabel("Nama Lengkap :"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtNama = new JTextField();
        add(txtNama, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        add(new JLabel("NIM :"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtNim = new JTextField();
        add(txtNim, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        add(new JLabel("Program Studi :"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        String[] daftarProdi = {
                "-- Pilih Program Studi --",
                "Teknik Informatika",
                "Sistem Informasi",
                "Teknik Industri",
                "Teknik Mesin",
                "Teknologi Pangan"
        };
        cmbProdi = new JComboBox<>(daftarProdi);
        add(cmbProdi, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        add(new JLabel("Jenis Kelamin :"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        rbLaki = new JRadioButton("Laki-laki");
        rbPerempuan = new JRadioButton("Perempuan");

        bgGender = new ButtonGroup();
        bgGender.add(rbLaki);
        bgGender.add(rbPerempuan);

        JPanel panelGender = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelGender.setOpaque(false);
        panelGender.add(rbLaki);
        panelGender.add(rbPerempuan);
        add(panelGender, gbc);
    }
}
