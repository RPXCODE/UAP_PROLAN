package org.example;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;

public class InputPanel extends JPanel {
    public InputPanel(boolean isAdmin) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 40, 20, 40));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.add(StyleUtils.createLabel("FORM INPUT BARANG MASUK", StyleUtils.FONT_HEADER), BorderLayout.WEST);

        JButton btnBack = StyleUtils.createButton("KEMBALI", Color.BLACK);
        btnBack.addActionListener(e -> SwingUtilities.getWindowAncestor(this).dispose());
        header.add(btnBack, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        if (!isAdmin) {
            JLabel lblDenied = new JLabel("MODE TAMU: DILARANG MENAMBAH DATA.", SwingConstants.CENTER);
            lblDenied.setFont(new Font("Arial", Font.BOLD, 18));
            lblDenied.setForeground(Color.RED);
            add(lblDenied, BorderLayout.CENTER);
            return;
        }

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(new LineBorder(Color.BLACK, 1));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); gbc.fill = GridBagConstraints.HORIZONTAL;

        int r = 0;
        JTextField txtNama = addForm(form, gbc, r++, "NAMA BARANG:");

        gbc.gridx = 0; gbc.gridy = r; form.add(StyleUtils.createLabel("KATEGORI:", StyleUtils.FONT_LABEL), gbc);
        String[] kats = {"SEMEN", "CAT", "KAYU", "BESI", "KERAMIK", "PIPA", "ALAT", "LAINNYA"};
        JComboBox<String> cmbKategori = new JComboBox<>(kats);
        gbc.gridx = 1; form.add(cmbKategori, gbc); r++;

        JTextField txtStok = addForm(form, gbc, r++, "STOK AWAL:");
        JTextField txtSatuan = addForm(form, gbc, r++, "SATUAN (SAK/KG):");
        JTextField txtHarga = addForm(form, gbc, r++, "HARGA SATUAN (RP):");

        JButton btnSimpan = StyleUtils.createButton("SIMPAN DATA", Color.BLUE);
        gbc.gridx = 1; gbc.gridy = r; form.add(btnSimpan, gbc);

        add(form, BorderLayout.CENTER);

        btnSimpan.addActionListener(e -> {
            try {
                if (txtNama.getText().isEmpty()) throw new Exception("Nama wajib diisi!");
                ArrayList<Barang> list = DataManager.loadBarang();
                list.add(new Barang(txtNama.getText(), (String) cmbKategori.getSelectedItem(),
                        Integer.parseInt(txtStok.getText()), txtSatuan.getText(), Double.parseDouble(txtHarga.getText())));
                DataManager.saveBarang(list);
                MaterialMasterApp.logActivity("INPUT", "Masuk: " + txtNama.getText());
                JOptionPane.showMessageDialog(this, "Berhasil Disimpan!");
                txtNama.setText(""); txtStok.setText(""); txtHarga.setText(""); txtSatuan.setText("");
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage()); }
        });
    }
    private JTextField addForm(JPanel p, GridBagConstraints gbc, int r, String lbl) {
        gbc.gridx = 0; gbc.gridy = r; p.add(StyleUtils.createLabel(lbl, StyleUtils.FONT_LABEL), gbc);
        JTextField f = StyleUtils.createTextField();
        gbc.gridx = 1; p.add(f, gbc);
        return f;
    }
}