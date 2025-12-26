package org.example;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DashboardPanel extends JPanel {
    private MaterialMasterApp mainApp;

    public DashboardPanel(MaterialMasterApp app) {
        this.mainApp = app;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 40, 20, 40));

        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        JLabel title = StyleUtils.createLabel("DASHBOARD & STATISTIK", StyleUtils.FONT_HEADER);
        JButton btnBack = StyleUtils.createButton("KEMBALI", Color.BLACK);
        btnBack.addActionListener(e -> SwingUtilities.getWindowAncestor(this).dispose());

        header.add(title, BorderLayout.WEST);
        header.add(btnBack, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // GRID KARTU
        JPanel cardGrid = new JPanel(new GridLayout(2, 2, 20, 20));
        cardGrid.setBackground(Color.WHITE);
        cardGrid.setBorder(new EmptyBorder(30, 0, 30, 0));

        JPanel card1 = createInfoCard("TOTAL JENIS BARANG", Color.decode("#3498DB"));
        JLabel lblTotalBarang = createValueLabel(); card1.add(lblTotalBarang);

        JPanel card2 = createInfoCard("TOTAL NILAI ASET", Color.decode("#27AE60"));
        JLabel lblTotalAset = createValueLabel(); card2.add(lblTotalAset);

        JPanel card3 = createInfoCard("STOK MENIPIS (<5) - KLIK DETAIL", Color.decode("#E74C3C"));
        card3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card3.setToolTipText("Klik untuk melihat barang stok sedikit");
        card3.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { mainApp.openNewWindow("StokLow"); }
            public void mouseEntered(MouseEvent e) { card3.setBackground(new Color(250, 230, 230)); }
            public void mouseExited(MouseEvent e) { card3.setBackground(Color.WHITE); }
        });
        JLabel lblLowStock = createValueLabel(); card3.add(lblLowStock);

        JPanel card4 = createInfoCard("MITRA SUPPLIER", Color.decode("#8E44AD"));
        JLabel lblTotalSupplier = createValueLabel(); card4.add(lblTotalSupplier);

        cardGrid.add(card1); cardGrid.add(card2); cardGrid.add(card3); cardGrid.add(card4);
        add(cardGrid, BorderLayout.CENTER);

        JButton btnRefresh = StyleUtils.createButton("REFRESH DATA", Color.GRAY);
        btnRefresh.addActionListener(e -> refreshData(lblTotalBarang, lblTotalAset, lblLowStock, lblTotalSupplier));
        add(btnRefresh, BorderLayout.SOUTH);

        refreshData(lblTotalBarang, lblTotalAset, lblLowStock, lblTotalSupplier);
    }

    private JPanel createInfoCard(String titleText, Color barColor) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.LIGHT_GRAY, 1), new EmptyBorder(20, 20, 20, 20)));

        JPanel bar = new JPanel();
        bar.setBackground(barColor);
        bar.setMaximumSize(new Dimension(50, 5));
        bar.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title = new JLabel(titleText);
        title.setFont(new Font("Arial", Font.BOLD, 14));
        title.setForeground(Color.GRAY);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(title); card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(bar); card.add(Box.createRigidArea(new Dimension(0, 15)));
        return card;
    }

    private JLabel createValueLabel() {
        JLabel lbl = new JLabel("0");
        lbl.setFont(new Font("Arial", Font.BOLD, 36));
        lbl.setForeground(Color.DARK_GRAY);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    public void refreshData(JLabel lblTotal, JLabel lblAset, JLabel lblLow, JLabel lblSup) {
        ArrayList<Barang> list = DataManager.loadBarang();
        ArrayList<String[]> sup = DataManager.loadSupplier();

        lblTotal.setText(list.size() + " Item");
        double total = list.stream().mapToDouble(b -> b.stok * b.harga).sum();
        lblAset.setText("Rp " + StyleUtils.formatNumber(total));

        long lowStock = list.stream().filter(b -> b.stok < 5).count();
        lblLow.setText(lowStock + " Item");
        lblLow.setForeground(lowStock > 0 ? Color.RED : Color.DARK_GRAY);

        lblSup.setText(sup.size() + " Mitra");
    }
}