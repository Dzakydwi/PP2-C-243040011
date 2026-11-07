import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * Halaman Ringkasan (Card 2) - Responsif untuk Ukuran Default & Full Screen
 *
 * Fitur Responsif:
 * - Layar Lebar / Full Screen (>= 700px): Layout 2 Kolom (Split View).
 *   Sisi kiri: pesan konfirmasi & tombol aksi.
 *   Sisi kanan: kartu tiket rincian pendaftaran.
 * - Layar Sempit / Kompak (< 700px): Layout 1 Kolom (Stacked View).
 *   Bagian konfirmasi di atas, kartu tiket di bawah dengan scroll vertikal mulus.
 * - Menerapkan Scrollable dengan getScrollableTracksViewportWidth() = true
 *   sehingga kartu tiket tidak pernah terpotong atau keluar layar baik pada ukuran default maupun full screen.
 */
public class PanelRingkasan extends JPanel {

    private final Main mainFrame;

    private JLabel lblNamaVal;
    private JLabel lblEmailVal;
    private JLabel lblUmurVal;
    private JLabel lblNimVal;
    private JLabel lblProdiVal;
    private JLabel lblGenderVal;

    private JTextArea txtKegiatanVal;
    private JLabel lblSesiVal;
    private JTextArea txtFasilitasVal;

    private JLabel lblWaktuVal;
    private JLabel lblRefNoVal;
    private JTextArea txtCatatanVal;

    public JButton btnKembali;
    public JButton btnBantuan;
    public JButton btnKeluar;

    // Komponen Layout Responsif
    private ScrollablePanel centerSplit;
    private JPanel leftPanel;
    private JPanel rightTicketWrapper;
    private boolean isStacked = false;

    /**
     * Panel yang mengimplementasikan Scrollable agar lebar selalu mengikuti viewport
     */
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
            return true; // Menyesuaikan lebar layar (mencegah overflow horizontal)
        }

        @Override
        public boolean getScrollableTracksViewportHeight() {
            return false;
        }
    }

    /**
     * Ikon bintang grafis anti-glitch kotak kosong
     */
    public static Icon createStarBadgeIcon(int size) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UIStyle.PURPLE_PRIMARY);
                int cx = x + size / 2;
                int cy = y + size / 2;
                int r = size / 2;
                int rIn = r / 3;
                int[] px = {cx, cx + rIn, cx + r, cx + rIn, cx, cx - rIn, cx - r, cx - rIn};
                int[] py = {cy - r, cy - rIn, cy, cy + rIn, cy + r, cy + rIn, cy, cy - rIn};
                g2.fillPolygon(px, py, 8);
                g2.dispose();
            }

            @Override
            public int getIconWidth() { return size; }

            @Override
            public int getIconHeight() { return size; }
        };
    }

    public PanelRingkasan(Main mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG_LIGHT);

        // Header tipis atas
        JPanel topHeader = new JPanel(new BorderLayout());
        topHeader.setBackground(Color.WHITE);
        topHeader.setBorder(new CompoundBorder(
                new LineBorder(UIStyle.BORDER_LIGHT, 1),
                new EmptyBorder(12, 28, 12, 28)
        ));

        JLabel lblLogo = new JLabel("SISTEM PENDAFTARAN MAHASISWA");
        lblLogo.setIcon(createStarBadgeIcon(14));
        lblLogo.setIconTextGap(8);
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblLogo.setForeground(UIStyle.PURPLE_PRIMARY);

        JLabel lblStepTitle = new JLabel("Langkah 3 dari 3: Konfirmasi Selesai");
        lblStepTitle.setFont(UIStyle.FONT_REGULAR);
        lblStepTitle.setForeground(UIStyle.TEXT_MUTED);

        topHeader.add(lblLogo, BorderLayout.WEST);
        topHeader.add(lblStepTitle, BorderLayout.EAST);
        add(topHeader, BorderLayout.NORTH);

        // Inisialisasi Sisi Kiri & Sisi Kanan
        initLeftPanel();
        initRightTicketWrapper();

        // Area Tengah Responsif
        centerSplit = new ScrollablePanel(new GridBagLayout());
        centerSplit.setOpaque(false);
        centerSplit.setBorder(new EmptyBorder(20, 24, 20, 24));

        // Terapkan layout awal (2 kolom)
        applySplitLayout(false);

        // Wrapper agar pada layar sangat besar (Full Screen) konten tetap proporsional dan terpusat
        ScrollablePanel contentCenteringWrapper = new ScrollablePanel(new GridBagLayout());
        contentCenteringWrapper.setOpaque(false);

        GridBagConstraints gbcCenter = new GridBagConstraints();
        gbcCenter.gridx = 0;
        gbcCenter.gridy = 0;
        gbcCenter.weightx = 1.0;
        gbcCenter.weighty = 1.0;
        gbcCenter.fill = GridBagConstraints.BOTH;
        gbcCenter.anchor = GridBagConstraints.NORTH;
        contentCenteringWrapper.add(centerSplit, gbcCenter);

        // Listener dinamis untuk mendeteksi perubahan ukuran jendela (Resize Responsif)
        contentCenteringWrapper.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = contentCenteringWrapper.getWidth();
                if (width <= 0) return;
                boolean shouldStack = width < 700;
                if (shouldStack != isStacked) {
                    isStacked = shouldStack;
                    applySplitLayout(isStacked);
                }
            }
        });

        JScrollPane scrollSummary = new JScrollPane(contentCenteringWrapper);
        scrollSummary.setBorder(null);
        scrollSummary.setOpaque(false);
        scrollSummary.getViewport().setOpaque(false);
        scrollSummary.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollSummary.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollSummary.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollSummary, BorderLayout.CENTER);
    }

    /**
     * Membangun panel sisi kiri (Pesan Konfirmasi & Tombol Aksi)
     */
    private void initLeftPanel() {
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);

        // Icon badge atas (Alignment kiri)
        JPanel iconBadge = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        iconBadge.setOpaque(false);
        iconBadge.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblIcon = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UIStyle.PURPLE_LIGHT);
                g2.fillOval(0, 0, 48, 48);
                g2.setColor(UIStyle.PURPLE_PRIMARY);
                g2.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                int[] xPoints = {15, 22, 33};
                int[] yPoints = {25, 32, 17};
                g2.drawPolyline(xPoints, yPoints, 3);
                g2.dispose();
            }
        };
        lblIcon.setPreferredSize(new Dimension(50, 50));
        iconBadge.add(lblIcon);
        leftPanel.add(iconBadge);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 16)));

        // Judul Utama (Bold besar)
        JLabel lblTitle1 = new JLabel("Pendaftaran Berhasil");
        lblTitle1.setFont(UIStyle.FONT_BIG_TITLE);
        lblTitle1.setForeground(UIStyle.TEXT_DARK);
        lblTitle1.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTitle2 = new JLabel("Dikonfirmasi!");
        lblTitle2.setFont(UIStyle.FONT_BIG_TITLE);
        lblTitle2.setForeground(UIStyle.TEXT_DARK);
        lblTitle2.setAlignmentX(Component.LEFT_ALIGNMENT);

        leftPanel.add(lblTitle1);
        leftPanel.add(lblTitle2);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));

        // Deskripsi dengan JTextArea agar membungkus kata secara otomatis tanpa memaksa lebar berlebih
        JTextArea txtDesc = new JTextArea(
                "Terima kasih telah mendaftar kegiatan ini. Data pendaftaran Anda telah berhasil diverifikasi dan tersimpan dengan aman ke dalam sistem."
        );
        txtDesc.setFont(UIStyle.FONT_REGULAR);
        txtDesc.setForeground(UIStyle.TEXT_MUTED);
        txtDesc.setLineWrap(true);
        txtDesc.setWrapStyleWord(true);
        txtDesc.setEditable(false);
        txtDesc.setOpaque(false);
        txtDesc.setFocusable(false);
        txtDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(txtDesc);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 22)));

        // Tombol Aksi Utama
        btnKembali = UIStyle.createPurpleButton("<- Kembali ke Formulir", 210, 40);
        btnKembali.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnKembali.addActionListener(e -> {
            mainFrame.showCard(Main.CARD_FORM);
            if (mainFrame.panelFormulir != null) {
                mainFrame.panelFormulir.setStep(2);
            }
        });

        // Tombol Aksi Sekunder (Bantuan & Keluar)
        JPanel panelSecBtns = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        panelSecBtns.setOpaque(false);
        panelSecBtns.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnBantuan = UIStyle.createSecondaryButton("Bantuan", 95, 34);
        btnBantuan.addActionListener(e -> mainFrame.showCard(Main.CARD_BANTUAN));

        btnKeluar = UIStyle.createSecondaryButton("Keluar", 85, 34);
        btnKeluar.addActionListener(new TombolKeluarListener(mainFrame));

        panelSecBtns.add(btnBantuan);
        panelSecBtns.add(btnKeluar);

        leftPanel.add(btnKembali);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 4)));
        leftPanel.add(panelSecBtns);
        leftPanel.add(Box.createVerticalGlue());
    }

    /**
     * Membangun panel sisi kanan (Kartu Status & Kartu Detail Tiket)
     */
    private void initRightTicketWrapper() {
        rightTicketWrapper = new JPanel(new BorderLayout(0, 12));
        rightTicketWrapper.setOpaque(false);

        // 1. Kotak Status Atas
        JPanel statusCard = new JPanel(new BorderLayout()) {
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
        statusCard.setOpaque(false);
        statusCard.setBorder(new EmptyBorder(14, 18, 14, 18));

        JPanel statusTextPanel = new JPanel(new GridLayout(0, 1, 0, 2));
        statusTextPanel.setOpaque(false);
        JLabel lblStatusTitle = new JLabel("STATUS REGISTRASI");
        lblStatusTitle.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblStatusTitle.setForeground(UIStyle.TEXT_MUTED);

        JLabel lblStatusBold = new JLabel("Terdaftar Resmi");
        lblStatusBold.setFont(new Font("Segoe UI", Font.BOLD, 19));
        lblStatusBold.setForeground(UIStyle.TEXT_DARK);

        statusTextPanel.add(lblStatusTitle);
        statusTextPanel.add(lblStatusBold);

        JLabel checkBadge = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UIStyle.SUCCESS_BG);
                g2.fillOval(0, 0, 36, 36);
                g2.setColor(UIStyle.SUCCESS_GREEN);
                g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                int[] xPoints = {11, 16, 25};
                int[] yPoints = {18, 23, 13};
                g2.drawPolyline(xPoints, yPoints, 3);
                g2.dispose();
            }
        };
        checkBadge.setPreferredSize(new Dimension(38, 38));

        statusCard.add(statusTextPanel, BorderLayout.CENTER);
        statusCard.add(checkBadge, BorderLayout.EAST);
        rightTicketWrapper.add(statusCard, BorderLayout.NORTH);

        // 2. Kotak Detail Tiket
        JPanel detailsCard = new JPanel(new BorderLayout(0, 8)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.setColor(UIStyle.BORDER_LIGHT);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);

                // Efek pinggiran tiket / gerigi bawah (Ticket Scallop Effect)
                int radius = 6;
                int y = getHeight() - 1;
                g2.setColor(UIStyle.BG_LIGHT);
                for (int x = 12; x < getWidth() - 12; x += 16) {
                    g2.fillOval(x, y - radius, radius * 2, radius * 2);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        detailsCard.setOpaque(false);
        detailsCard.setBorder(new EmptyBorder(16, 18, 20, 18));

        JLabel lblDetailHead = new JLabel("Detail Pendaftaran");
        lblDetailHead.setFont(UIStyle.FONT_SECTION);
        lblDetailHead.setForeground(UIStyle.TEXT_DARK);
        lblDetailHead.setBorder(new EmptyBorder(0, 0, 8, 0));
        detailsCard.add(lblDetailHead, BorderLayout.NORTH);

        // Baris-baris Tiket
        JPanel rowsPanel = new JPanel(new GridBagLayout());
        rowsPanel.setOpaque(false);
        GridBagConstraints rgbc = new GridBagConstraints();
        rgbc.insets = new Insets(3, 2, 3, 2);
        rgbc.anchor = GridBagConstraints.NORTHWEST;

        lblWaktuVal = new JLabel("-");
        lblRefNoVal = new JLabel("-");
        lblNamaVal = new JLabel("-");
        lblEmailVal = new JLabel("-");
        lblUmurVal = new JLabel("-");
        lblNimVal = new JLabel("-");
        lblProdiVal = new JLabel("-");
        lblGenderVal = new JLabel("-");

        txtKegiatanVal = new JTextArea("-");
        txtKegiatanVal.setFont(UIStyle.FONT_LABEL);
        txtKegiatanVal.setForeground(UIStyle.TEXT_DARK);
        txtKegiatanVal.setLineWrap(true);
        txtKegiatanVal.setWrapStyleWord(true);
        txtKegiatanVal.setEditable(false);
        txtKegiatanVal.setOpaque(false);
        txtKegiatanVal.setFocusable(false);

        lblSesiVal = new JLabel("-");

        txtFasilitasVal = new JTextArea("-");
        txtFasilitasVal.setFont(UIStyle.FONT_REGULAR);
        txtFasilitasVal.setForeground(UIStyle.TEXT_DARK);
        txtFasilitasVal.setLineWrap(true);
        txtFasilitasVal.setWrapStyleWord(true);
        txtFasilitasVal.setEditable(false);
        txtFasilitasVal.setOpaque(false);
        txtFasilitasVal.setFocusable(false);

        txtCatatanVal = new JTextArea("-");
        txtCatatanVal.setFont(UIStyle.FONT_REGULAR);
        txtCatatanVal.setForeground(UIStyle.TEXT_DARK);
        txtCatatanVal.setLineWrap(true);
        txtCatatanVal.setWrapStyleWord(true);
        txtCatatanVal.setEditable(false);
        txtCatatanVal.setOpaque(false);
        txtCatatanVal.setFocusable(false);

        int r = 0;
        addTicketRow(rowsPanel, rgbc, r++, "Waktu Simpan", lblWaktuVal);
        addTicketRow(rowsPanel, rgbc, r++, "No. Referensi", lblRefNoVal);
        addTicketRow(rowsPanel, rgbc, r++, "Nama Lengkap", lblNamaVal);
        addTicketRow(rowsPanel, rgbc, r++, "Email", lblEmailVal);
        addTicketRow(rowsPanel, rgbc, r++, "Umur", lblUmurVal);
        addTicketRow(rowsPanel, rgbc, r++, "NIM", lblNimVal);
        addTicketRow(rowsPanel, rgbc, r++, "Program Studi", lblProdiVal);
        addTicketRow(rowsPanel, rgbc, r++, "Jenis Kelamin", lblGenderVal);
        addTicketRow(rowsPanel, rgbc, r++, "Kegiatan", txtKegiatanVal);
        addTicketRow(rowsPanel, rgbc, r++, "Sesi Waktu", lblSesiVal);
        addTicketRow(rowsPanel, rgbc, r++, "Fasilitas", txtFasilitasVal);

        JLabel lblStatusTag = new JLabel("Sukses Terdaftar");
        lblStatusTag.setFont(UIStyle.FONT_LABEL);
        lblStatusTag.setForeground(UIStyle.SUCCESS_GREEN);
        lblStatusTag.setIcon(UIStyle.createCheckmarkIcon(14));
        lblStatusTag.setIconTextGap(5);
        addTicketRow(rowsPanel, rgbc, r++, "Status", lblStatusTag);

        detailsCard.add(rowsPanel, BorderLayout.CENTER);
        rightTicketWrapper.add(detailsCard, BorderLayout.CENTER);
    }

    /**
     * Mengatur tata letak responsif antara 2 Kolom (Split View) dan 1 Kolom (Stacked View)
     */
    private void applySplitLayout(boolean stacked) {
        centerSplit.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weighty = 1.0;

        if (stacked) {
            // Tata Letak 1 Kolom: Atas - Bawah (untuk layar sempit / kompak)
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(0, 0, 24, 0);
            centerSplit.add(leftPanel, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = 1.0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(0, 0, 0, 0);
            centerSplit.add(rightTicketWrapper, gbc);
        } else {
            // Tata Letak 2 Kolom: Kiri - Kanan (Split View untuk default & Full Screen)
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0.42;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(0, 0, 0, 20);
            centerSplit.add(leftPanel, gbc);

            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.weightx = 0.58;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(0, 0, 0, 0);
            centerSplit.add(rightTicketWrapper, gbc);
        }

        centerSplit.revalidate();
        centerSplit.repaint();
    }

    private void addTicketRow(JPanel parent, GridBagConstraints gbc, int row, String label, Component valComp) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.34;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel lbl = new JLabel(label);
        lbl.setFont(UIStyle.FONT_REGULAR);
        lbl.setForeground(UIStyle.TEXT_MUTED);
        parent.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.66;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        if (valComp instanceof JLabel) {
            ((JLabel) valComp).setFont(UIStyle.FONT_LABEL);
            ((JLabel) valComp).setForeground(UIStyle.TEXT_DARK);
        }
        parent.add(valComp, gbc);
    }

    public void tampilkanData(DataPendaftaran data) {
        lblNamaVal.setText(data.getNama());
        lblEmailVal.setText(data.getEmail());
        lblUmurVal.setText(data.getUmur() + " Tahun");
        lblNimVal.setText(data.getNim());
        lblProdiVal.setText(data.getProdi());
        lblGenderVal.setText(data.getJenisKelamin());

        txtKegiatanVal.setText(data.getKegiatan());
        lblSesiVal.setText(data.getSesi());

        StringBuilder sbFasilitas = new StringBuilder();
        for (String f : data.getFasilitas()) {
            sbFasilitas.append(f).append(", ");
        }
        String fasStr = sbFasilitas.toString();
        if (fasStr.endsWith(", ")) {
            fasStr = fasStr.substring(0, fasStr.length() - 2);
        }
        txtFasilitasVal.setText(fasStr.isEmpty() ? "-" : fasStr);

        lblWaktuVal.setText(data.getTanggalWaktuDaftar());
        lblRefNoVal.setText("REG-" + (data.getNim().equals("-") ? "2026" : data.getNim()) + "-" + (int) (Math.random() * 9000 + 1000));
        txtCatatanVal.setText(data.getCatatan());
    }
}
