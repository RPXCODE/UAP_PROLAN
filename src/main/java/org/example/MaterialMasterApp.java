package org.example;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class MaterialMasterApp extends JFrame {

    public static RiwayatPanel riwayatPanelShared;
    private boolean isAdmin;

    public MaterialMasterApp(boolean isAdminRole) {
        this.isAdmin = isAdminRole;

        setTitle("MaterialMaster - " + (isAdmin ? "ADMIN MODE" : "GUEST MODE"));
        setSize(1100, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        if(riwayatPanelShared == null) {
            riwayatPanelShared = new RiwayatPanel();
        }

        GradientPanel content = new GradientPanel(Color.decode("#2C3E50"), Color.decode("#000000"));
        content.setLayout(new BorderLayout());
        content.setBorder(new EmptyBorder(40, 60, 40, 60));

        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("GUDANG MATERIAL MASTER");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel(isAdmin ? "Selamat Datang, Administrator" : "Selamat Datang, Tamu (Hanya Lihat)");
        subtitle.setForeground(isAdmin ? Color.GREEN : Color.YELLOW);
        subtitle.setFont(new Font("Arial", Font.BOLD, 16));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(title);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(subtitle);
        content.add(headerPanel, BorderLayout.NORTH);

        JPanel menuGrid = new JPanel(new GridLayout(2, 3, 30, 30));
        menuGrid.setOpaque(false);
        menuGrid.setBorder(new EmptyBorder(40, 0, 40, 0));

        menuGrid.add(createMenuBtn("DASHBOARD", "Dash"));
        menuGrid.add(createMenuBtn("DATA STOK", "Stok"));
        menuGrid.add(createMenuBtn("INPUT BARANG", "Input"));
        menuGrid.add(createMenuBtn("DATA SUPPLIER", "Supplier"));
        menuGrid.add(createMenuBtn("LOG AKTIVITAS", "History"));
        menuGrid.add(new JLabel(""));

        content.add(menuGrid, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);

        JButton btnLogout = StyleUtils.createButton("LOGOUT / KELUAR", Color.RED);
        btnLogout.setPreferredSize(new Dimension(180, 45));
        btnLogout.addActionListener(e -> {
            new LoginScreen().setVisible(true);
            this.dispose();
        });
        bottomPanel.add(btnLogout);

        content.add(bottomPanel, BorderLayout.SOUTH);

        add(content, BorderLayout.CENTER);
    }

    private JButton createMenuBtn(String text, String type) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setBackground(Color.WHITE);
        btn.setForeground(Color.decode("#2C3E50"));
        btn.setFocusPainted(false);
        btn.setBorder(new CompoundBorder(new LineBorder(Color.WHITE, 2), new EmptyBorder(10, 10, 10, 10)));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) { btn.setBackground(Color.decode("#ECF0F1")); }
            public void mouseExited(MouseEvent evt) { btn.setBackground(Color.WHITE); }
        });

        btn.addActionListener(e -> openNewWindow(type));
        return btn;
    }

    public void openNewWindow(String type) {
        JFrame frame = new JFrame();
        frame.setSize(1200, 750);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = null;

        switch (type) {
            case "Dash":
                frame.setTitle("Dashboard");
                panel = new DashboardPanel(this);
                break;
            case "Stok":
                frame.setTitle("Stok Barang");
                panel = new StokPanel(isAdmin, false);
                break;
            case "StokLow":
                frame.setTitle("PERINGATAN: STOK MENIPIS");
                panel = new StokPanel(isAdmin, true);
                break;
            case "Input":
                frame.setTitle("Input Barang");
                panel = new InputPanel(isAdmin);
                break;
            case "Supplier":
                frame.setTitle("Supplier");
                panel = new SupplierPanel(isAdmin);
                break;
            case "History":
                frame.setTitle("Log Aktivitas");
                panel = riwayatPanelShared;
                break;
        }

        if (panel != null) {
            frame.add(panel);
            frame.setVisible(true);
        }
    }

    public static void logActivity(String aktivitas, String ket) {
        if(riwayatPanelShared != null) riwayatPanelShared.addLog(aktivitas, ket);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginScreen().setVisible(true));
    }
}