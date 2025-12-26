package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RiwayatPanel extends JPanel {
    private DefaultTableModel tableModel;

    public RiwayatPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 30, 30, 30));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.add(StyleUtils.createLabel("LOG AKTIVITAS (SEMENTARA)", StyleUtils.FONT_HEADER), BorderLayout.WEST);

        JButton btnBack = StyleUtils.createButton("KEMBALI", Color.BLACK);
        btnBack.addActionListener(e -> SwingUtilities.getWindowAncestor(this).dispose());

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(Color.WHITE);
        JButton btnClear = StyleUtils.createButton("BERSIHKAN LOG", Color.RED);

        rightPanel.add(btnClear);
        rightPanel.add(btnBack);
        header.add(rightPanel, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        String[] cols = {"WAKTU", "AKTIVITAS", "KETERANGAN"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable table = new JTable(tableModel);
        StyleUtils.styleTable(table);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnClear.addActionListener(e -> {
            if(JOptionPane.showConfirmDialog(this, "Hapus log?", "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                tableModel.setRowCount(0);
        });
    }

    public void addLog(String aktivitas, String ket) {
        String time = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM")) + " " +
                LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        tableModel.insertRow(0, new Object[]{time, aktivitas, ket});
    }
}