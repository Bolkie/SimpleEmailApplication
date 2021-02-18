package com.company.gui.panel;

import com.company.EmailApplication;
import com.company.dao.AccountDao;
import com.company.gui.window.MainWindow;
import com.company.model.Account;
import com.company.model.MessageType;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class LoginPanel extends JPanel {
    private final AccountDao accountDao;

    private final JLabel loginError;
    private final JTextField usernameTextField;
    private final JPasswordField passwordField;

    public LoginPanel() {
        setLayout(null);
        setSize(MainWindow.WIDTH, 370);

        accountDao = EmailApplication.accountDao;

        int buttonWidth = 110;
        int componentFullWidth = MainWindow.WIDTH - 2 * MainWindow.GAP_BORDER;
        int componentPositionX_1 = MainWindow.GAP_BORDER;
        int componentPositionX_2 = componentPositionX_1 + componentFullWidth - buttonWidth;

        JLabel panelTitleLabel = new JLabel("Sign in to your account", SwingConstants.CENTER);
        panelTitleLabel.setBounds(0, 0, MainWindow.WIDTH, 100);
        panelTitleLabel.setFont(MainWindow.FONT_TITLE);
        add(panelTitleLabel);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(componentPositionX_1, 100, componentFullWidth, 15);
        add(usernameLabel);

        usernameTextField = new JTextField();
        usernameTextField.setBounds(componentPositionX_1, 115, componentFullWidth, 30);
        add(usernameTextField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(componentPositionX_1, 160, componentFullWidth, 15);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(componentPositionX_1, 175, componentFullWidth, 30);
        add(passwordField);

        JButton recoverPasswordButton = new JButton("Recover password");
        recoverPasswordButton.setBounds(componentPositionX_2, 205, buttonWidth, 15);
        recoverPasswordButton.setBackground(new Color(getBackground().getRGB()));
        recoverPasswordButton.setBorder(null);
        recoverPasswordButton.addActionListener(e -> MainWindow.getInstance().setPasswordRecoveryPanelActive(this));
        add(recoverPasswordButton);

        loginError = new JLabel("Username or password is incorrect", SwingConstants.CENTER);
        loginError.setBounds(componentPositionX_1, 212, componentFullWidth, 11);
        loginError.setFont(MainWindow.FONT_NOTIFICATION);
        loginError.setForeground(MainWindow.COLOR_ERROR);
        loginError.setVisible(false);
        add(loginError);

        JButton logInButton = new JButton("Sign in");
        logInButton.setBounds(componentPositionX_1, 235, componentFullWidth, 30);
        logInButton.addActionListener(e -> signIn());
        add(logInButton);

        JButton switchToRegisterPanelButton = new JButton("No account? Click here");
        switchToRegisterPanelButton.setBounds(componentPositionX_1, 285, componentFullWidth, 15);
        switchToRegisterPanelButton.setBackground(new Color(getBackground().getRGB()));
        switchToRegisterPanelButton.setBorder(null);
        switchToRegisterPanelButton.addActionListener(e -> MainWindow.getInstance().setRegisterPanelActive(this));
        add(switchToRegisterPanelButton);
    }

    private void signIn() {
        String username = usernameTextField.getText();
        char[] password = passwordField.getPassword();

        for (Account account : accountDao.getAllAccounts()) {
            if ((username.equalsIgnoreCase(account.getUsername()) || username.equalsIgnoreCase(account.getFullUsername())) && Arrays.equals(password, account.getPassword())) {
                EmailApplication.loggedIn = true;
                EmailApplication.loggedInAccountId = account.getId();
                MainWindow.getInstance().setMessagesPanelActive(this, MessageType.RECEIVED, 0);

                loginError.setVisible(false);

                return;
            }
        }

        loginError.setVisible(true);
    }
}