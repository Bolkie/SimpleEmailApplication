package com.company.gui.panel;

import com.company.EmailApplication;
import com.company.dao.AccountDao;
import com.company.gui.window.MainWindow;
import com.company.model.Account;

import javax.swing.*;
import java.awt.*;

public class PasswordRecoveryPanel extends JPanel {
    private final AccountDao accountDao;

    private final JLabel passwordRecoveryError;
    private final JTextField usernameTextField;
    private final JTextField securityQuestionAnswerTextField;
    private final JComboBox<String> securityQuestionComboBox;

    public PasswordRecoveryPanel() {
        setLayout(null);
        setSize(MainWindow.WIDTH, 430);

        accountDao = EmailApplication.accountDao;

        int componentFullWidth = MainWindow.WIDTH - 2 * MainWindow.GAP_BORDER;
        int componentPositionX = MainWindow.GAP_BORDER;

        JLabel panelTitleLabel = new JLabel("Password recovery", SwingConstants.CENTER);
        panelTitleLabel.setBounds(0, 0, MainWindow.WIDTH, 100);
        panelTitleLabel.setFont(MainWindow.FONT_TITLE);
        add(panelTitleLabel);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(componentPositionX, 100, componentFullWidth, 15);
        add(usernameLabel);

        usernameTextField = new JTextField();
        usernameTextField.setBounds(componentPositionX, 115, componentFullWidth, 30);
        add(usernameTextField);

        JLabel securityQuestionLabel = new JLabel("Security question");
        securityQuestionLabel.setBounds(componentPositionX, 160, componentFullWidth, 15);
        add(securityQuestionLabel);

        securityQuestionComboBox = new JComboBox<>(MainWindow.SECURITY_QUESTIONS);
        securityQuestionComboBox.setBounds(componentPositionX, 175, componentFullWidth, 30);
        securityQuestionComboBox.setFont(MainWindow.FONT_TEXT_PLAIN);
        securityQuestionComboBox.setBackground(Color.WHITE);
        add(securityQuestionComboBox);

        JLabel securityQuestionAnswerLabel = new JLabel("Enter your answer here");
        securityQuestionAnswerLabel.setBounds(componentPositionX, 220, componentFullWidth, 15);
        add(securityQuestionAnswerLabel);

        securityQuestionAnswerTextField = new JTextField();
        securityQuestionAnswerTextField.setBounds(componentPositionX, 235, componentFullWidth, 30);
        add(securityQuestionAnswerTextField);

        passwordRecoveryError = new JLabel("Username, security question or security question answer is incorrect", SwingConstants.CENTER);
        passwordRecoveryError.setBounds(componentPositionX, 272, componentFullWidth, 11);
        passwordRecoveryError.setFont(MainWindow.FONT_NOTIFICATION);
        passwordRecoveryError.setForeground(MainWindow.COLOR_ERROR);
        passwordRecoveryError.setVisible(false);
        add(passwordRecoveryError);

        JButton resetPasswordButton = new JButton("Reset password");
        resetPasswordButton.setBounds(componentPositionX, 295, componentFullWidth, 30);
        resetPasswordButton.addActionListener(e -> resetPassword());
        add(resetPasswordButton);

        JButton switchToRegisterPanelButton = new JButton("Return to login screen");
        switchToRegisterPanelButton.setBounds(componentPositionX, 345, componentFullWidth, 15);
        switchToRegisterPanelButton.setBackground(new Color(getBackground().getRGB()));
        switchToRegisterPanelButton.setBorder(null);
        switchToRegisterPanelButton.addActionListener(e -> MainWindow.getInstance().setLoginPanelActive(this));
        add(switchToRegisterPanelButton);
    }

    private void resetPassword() {
        String username = usernameTextField.getText();
        String securityQuestion = securityQuestionComboBox.getSelectedItem().toString();
        String securityQuestionAnswer = securityQuestionAnswerTextField.getText();

        for (Account account : accountDao.getAllAccounts()) {
            if ((username.equalsIgnoreCase(account.getUsername()) || username.equalsIgnoreCase(account.getUsername() + "@" + account.getDomain())) && securityQuestion.equals(account.getSecurityQuestion()) && securityQuestionAnswer.equalsIgnoreCase(account.getSecurityQuestionAnswer())) {
                char[] password = MainWindow.generateRandomPassword(8);
                accountDao.getAccount(account.getId()).setPassword(password);
                MainWindow.getInstance().setGeneratedPasswordPanelActive(this, password);

                passwordRecoveryError.setVisible(false);

                return;
            }
        }

        passwordRecoveryError.setVisible(true);
    }
}