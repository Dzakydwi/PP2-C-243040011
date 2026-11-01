import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame {

    public PanelHeader panelHeader;
    public PanelIdentitas panelIdentitas;
    public PanelKegiatan panelKegiatan;
    public PanelCatatan panelCatatan;
    public PanelTombol panelTombol;

    public JTextField txtNama;
    public JTextField txtNim;
    public JComboBox<String> cmbProdi;
    public JRadioButton rbLaki;
    public JRadioButton rbPerempuan;
    public ButtonGroup bgGender;

    public JComboBox<String> cmbKegiatan;
    public JRadioButton rbPagi;
    public JRadioButton rbSiang;
    public ButtonGroup bgSesi;
    public JCheckBox cbSertifikat;
    public JCheckBox cbKonsumsi;
    public JCheckBox cbModul;

    public JTextArea txtCatatan;
    public JCheckBox cbSetuju;

    public Main() {
        super("Formulir Pendaftaran Kegiatan Mahasiswa");
        initUI();
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(480, 420));
        getContentPane().setLayout(new BorderLayout(10, 10));

        panelHeader = new PanelHeader();
        panelIdentitas = new PanelIdentitas();
        panelKegiatan = new PanelKegiatan();
        panelCatatan = new PanelCatatan();
        panelTombol = new PanelTombol(this);

        txtNama = panelIdentitas.txtNama;
        txtNim = panelIdentitas.txtNim;
        cmbProdi = panelIdentitas.cmbProdi;
        rbLaki = panelIdentitas.rbLaki;
        rbPerempuan = panelIdentitas.rbPerempuan;
        bgGender = panelIdentitas.bgGender;

        cmbKegiatan = panelKegiatan.cmbKegiatan;
        rbPagi = panelKegiatan.rbPagi;
        rbSiang = panelKegiatan.rbSiang;
        bgSesi = panelKegiatan.bgSesi;
        cbSertifikat = panelKegiatan.cbSertifikat;
        cbKonsumsi = panelKegiatan.cbKonsumsi;
        cbModul = panelKegiatan.cbModul;

        txtCatatan = panelCatatan.txtCatatan;
        cbSetuju = panelCatatan.cbSetuju;

        getContentPane().add(panelHeader, BorderLayout.NORTH);

        ResponsivePanel panelForm = new ResponsivePanel(new GridBagLayout());
        panelForm.setBorder(new EmptyBorder(5, 15, 5, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(0, 0, 8, 0);
        panelForm.add(panelIdentitas, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(0, 0, 8, 0);
        panelForm.add(panelKegiatan, gbc);

        gbc.gridy = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        panelForm.add(panelCatatan, gbc);

        JScrollPane scrollPane = new JScrollPane(panelForm);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        getContentPane().add(scrollPane, BorderLayout.CENTER);

        getContentPane().add(panelTombol, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    static class ResponsivePanel extends JPanel implements Scrollable {
        public ResponsivePanel(LayoutManager layout) {
            super(layout);
        }

        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return getPreferredSize();
        }

        @Override
        public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
            return 16;
        }

        @Override
        public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
            return 48;
        }

        @Override
        public boolean getScrollableTracksViewportWidth() {
            return true;
        }

        @Override
        public boolean getScrollableTracksViewportHeight() {
        return getParent() != null && getParent().getHeight() >= getPreferredSize().height;
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
