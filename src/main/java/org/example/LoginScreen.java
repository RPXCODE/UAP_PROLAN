package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginScreen extends JFrame {

    public LoginScreen() {
        setTitle("Login - Material Master");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        GradientPanel bg = new GradientPanel(Color.decode("#1CB5E0"), Color.decode("#000046"));
        bg.setLayout(new GridBagLayout());

        JPanel loginBox = new JPanel();
        loginBox.setLayout(new BoxLayout(loginBox, BoxLayout.Y_AXIS));
        loginBox.setBackground(new Color(255, 255, 255, 240));
        loginBox.setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel lblTitle = new JLabel("LOGIN SYSTEM");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitle.setForeground(new Color(0, 0, 70));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField txtUser = StyleUtils.createTextField();
        JPasswordField txtPass = new JPasswordField();
        StyleUtils.stylePasswordField(txtPass);

        JButton btnLogin = StyleUtils.createButton("LOGIN ADMIN", Color.decode("#000046"));
        JButton btnGuest = StyleUtils.createButton("MASUK SEBAGAI TAMU", Color.decode("#1CB5E0"));

        loginBox.add(lblTitle);
        loginBox.add(Box.createRigidArea(new Dimension(0, 30)));
        loginBox.add(StyleUtils.createLabel("Username:", StyleUtils.FONT_LABEL));
        loginBox.add(txtUser);
        loginBox.add(Box.createRigidArea(new Dimension(0, 10)));
        loginBox.add(StyleUtils.createLabel("Password:", StyleUtils.FONT_LABEL));
        loginBox.add(txtPass);
        loginBox.add(Box.createRigidArea(new Dimension(0, 20)));
        loginBox.add(btnLogin);
        loginBox.add(Box.createRigidArea(new Dimension(0, 10)));
        loginBox.add(btnGuest);

        bg.add(loginBox);
        add(bg);

        btnLogin.addActionListener(e -> {
            String user = txtUser.getText();
            String pass = new String(txtPass.getPassword());
            if (user.equals("admin") && pass.equals("admin")) {
                new MaterialMasterApp(true).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Username/Password Salah! (admin/admin)");
            }
        });

        btnGuest.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Mode Tamu: Anda hanya bisa melihat data.");
            new MaterialMasterApp(false).setVisible(true);
            this.dispose();
        });
    }
}