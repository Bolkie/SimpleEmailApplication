package com.company.gui.panel;

import com.company.gui.window.MainWindow;

import javax.swing.*;
import java.awt.*;

public class GeneratedPasswordPanel extends JPanel {
    public GeneratedPasswordPanel(char[] password, JPanel fromPanel) {
        setLayout(null);
        setSize(MainWindow.WIDTH, 320);

        int componentFullWidth = MainWindow.WIDTH - 2 * MainWindow.GAP_BORDER;
        int componentPositionX = MainWindow.GAP_BORDER;

        JLabel titleLabel = new JLabel(fromPanel.getClass().equals(RegisterPanel.class) ? "Account created" : "Password reset", SwingConstants.CENTER);
        titleLabel.setBounds(0, 0, MainWindow.WIDTH, 100);
        titleLabel.setFont(MainWindow.FONT_TITLE);
        add(titleLabel);

        JLabel textLabel_1 = new JLabel(fromPanel.getClass().equals(RegisterPanel.class) ? "Congratulations. Your account has been successfully created." : "Your password has been successfully reset.", SwingConstants.CENTER);
        textLabel_1.setBounds(componentPositionX, 100, componentFullWidth, 15);
        add(textLabel_1);

        JLabel textLabel_2 = new JLabel(fromPanel.getClass().equals(RegisterPanel.class) ? "Now you can sign in with information you provided earlier." : "Now you can sign in with new password.", SwingConstants.CENTER);
        textLabel_2.setBounds(componentPositionX, 115, componentFullWidth, 15);
        add(textLabel_2);

        JLabel textLabel_3 = new JLabel("As soon as you sign in you can change your password.", SwingConstants.CENTER);
        textLabel_3.setBounds(componentPositionX, 130, componentFullWidth, 15);
        add(textLabel_3);

        JLabel textLabel_4 = new JLabel("Here is your password:", SwingConstants.CENTER);
        textLabel_4.setBounds(componentPositionX, 145, componentFullWidth, 15);
        add(textLabel_4);

        JTextField generatedPasswordTextField = new JTextField(new String(password));
        generatedPasswordTextField.setBounds(componentPositionX, 160, componentFullWidth, 15);
        generatedPasswordTextField.setBackground(new Color(getBackground().getRGB()));
        generatedPasswordTextField.setHorizontalAlignment(SwingConstants.CENTER);
        generatedPasswordTextField.setEditable(false);
        generatedPasswordTextField.setBorder(null);
        add(generatedPasswordTextField);

        JButton switchToLoginPanelButton = new JButton("Back to login screen");
        switchToLoginPanelButton.setBounds(componentPositionX, 215, componentFullWidth, 30);
        switchToLoginPanelButton.addActionListener(e -> MainWindow.getInstance().setLoginPanelActive(this));
        add(switchToLoginPanelButton);
    }
}