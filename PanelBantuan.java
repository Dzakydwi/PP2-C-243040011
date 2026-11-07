import java.awt.*;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * Halaman Bantuan (Card 3) - Responsif untuk Ukuran Default & Full Screen
 * Menampilkan panduan pengguna dengan styling modern yang selaras dengan tema ungu.
 */
public class PanelBantuan extends JPanel {

    private final Main mainFrame;
    public JButton btnKembali;
    public JButton btnKeluar;

    private static class ScrollablePanel extends JPanel implements Scrollable {
        public ScrollablePanel(LayoutManager layout) {
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
            return 64;
        }

        @Override
        public boolean getScrollableTracksViewportWidth() {
            return true;
        }

        @Override
        public boolean getScrollableTracksViewportHeight() {
            return false;
        }
    }

    public PanelBantuan(Main mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG_LIGHT);

        // Header Atas
        JPanel topHeader = new JPanel(new BorderLayout());
        topHeader.setBackground(Color.WHITE);
        topHeader.setBorder(new CompoundBorder(
                new LineBorder(UIStyle.BORDER_LIGHT, 1),
                new EmptyBorder(14, 28, 14, 28)
        ));

        JLabel lblLogo = new JLabel("PANDUAN & BANTUAN SISTEM");
        lblLogo.setIcon(PanelRingkasan.createStarBadgeIcon(14));
        lblLogo.setIconTextGap(8);
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblLogo.setForeground(UIStyle.PURPLE_PRIMARY);

        JLabel lblSub = new JLabel("Petunjuk Pengisian & Navigasi CardLayout");
        lblSub.setFont(UIStyle.FONT_REGULAR);
        lblSub.setForeground(UIStyle.TEXT_MUTED);

        topHeader.add(lblLogo, BorderLayout.WEST);
        topHeader.add(lblSub, BorderLayout.EAST);
        add(topHeader, BorderLayout.NORTH);

        // Body Content Responsif
        ScrollablePanel bodyPanel = new ScrollablePanel(new GridBagLayout());
        bodyPanel.setOpaque(false);
        bodyPanel.setBorder(new EmptyBorder(20, 24, 20, 24));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;

        // 1. Kartu Aturan Validasi
        JPanel cardValidasi = createContentCard("1. Ketentuan & Aturan Validasi Input", new String[]{
                "<b>• Nama Lengkap (Wajib):</b> Tidak boleh kosong. Jika kosong, sistem membatalkan proses, memunculkan pesan spesifik, dan kursor otomatis difokuskan ke field Nama.",
                "<b>• Email (Wajib & Format Wajar):</b> Wajib diisi sesuai pola email yang valid (contoh: <i>user@domain.com</i>). Format salah akan ditolak dan kursor difokuskan ke field Email.",
                "<b>• Umur (15–100 Tahun):</b> Harus berupa bilangan bulat dalam rentang 15 sampai 100 tahun. Input berupa teks bukan angka atau di luar rentang akan ditolak dan difokuskan ke field Umur.",
                "<b>• Pilihan Kegiatan (Wajib):</b> Pengguna wajib memilih salah satu kegiatan dari daftar dropdown selain opsi default.",
                "<b>• Persetujuan:</b> Centang pernyataan kebenaran data dan ketentuan pendaftaran."
        });
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 16, 0);
        bodyPanel.add(cardValidasi, gbc);

        // 2. Kartu Fungsi Tombol & Navigasi
        JPanel cardTombol = createContentCard("2. Fungsi Tombol Aksi & Navigasi CardLayout", new String[]{
                "<b>• Simpan / Tampilkan:</b> Memvalidasi seluruh masukan. Jika valid, data disimpan ke model dan antarmuka otomatis beralih ke Halaman Ringkasan (tiket konfirmasi).",
                "<b>• Reset Form:</b> Mengosongkan seluruh field input dan mereset status perubahan data kembali ke kondisi default.",
                "<b>• Kembali:</b> Berpindah kembali ke Halaman Formulir <i>tanpa kehilangan atau menghapus data</i> yang telah diisi sebelumnya.",
                "<b>• Keluar:</b> Menutup aplikasi. Apabila terdapat perubahan data yang belum disimpan, sistem akan menampilkan dialog konfirmasi peringatan. Pengguna dapat memilih 'Tidak' untuk membatalkan keluar.",
                "<b>• Navigasi Bebas Duplikat:</b> Seluruh navigasi (Formulir, Ringkasan, Bantuan) berlangsung di dalam satu frame yang sama via CardLayout tanpa membuka jendela baru."
        });
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 0, 0, 0);
        bodyPanel.add(cardTombol, gbc);

        JScrollPane scrollPane = new JScrollPane(bodyPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom Navigation Bar
        JPanel panelBawah = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 12));
        panelBawah.setBackground(Color.WHITE);
        panelBawah.setBorder(new LineBorder(UIStyle.BORDER_LIGHT, 1));

        btnKeluar = UIStyle.createSecondaryButton("Keluar", 90, 36);
        btnKeluar.addActionListener(new TombolKeluarListener(mainFrame));

        btnKembali = UIStyle.createPurpleButton("<- Kembali ke Formulir", 195, 36);
        btnKembali.addActionListener(e -> mainFrame.showCard(Main.CARD_FORM));

        panelBawah.add(btnKeluar);
        panelBawah.add(btnKembali);

        add(panelBawah, BorderLayout.SOUTH);
    }

    private JPanel createContentCard(String title, String[] points) {
        JPanel card = new JPanel(new BorderLayout(0, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.setColor(UIStyle.BORDER_LIGHT);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(16, 20, 16, 20));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(UIStyle.FONT_SECTION);
        lblTitle.setForeground(UIStyle.PURPLE_PRIMARY);
        card.add(lblTitle, BorderLayout.NORTH);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        for (String pt : points) {
            JLabel lbl = new JLabel("<html><body style='width: 100%;'>" + pt + "</body></html>");
            lbl.setFont(UIStyle.FONT_REGULAR);
            lbl.setForeground(UIStyle.TEXT_DARK);
            lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
            listPanel.add(lbl);
            listPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        }
        card.add(listPanel, BorderLayout.CENTER);

        return card;
    }
}
