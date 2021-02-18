package com.company.gui.panel;

import com.company.EmailApplication;
import com.company.dao.AccountDao;
import com.company.gui.window.MainWindow;
import com.company.model.Message;
import com.company.model.MessageType;

import javax.swing.*;
import java.util.Arrays;

public class DeleteAccountPanel extends JPanel {
    private final AccountDao accountDao;
    private final int loggedInAccountId;

    private final JLabel passwordError;
    private final JPasswordField passwordField;
    private final JCheckBox deleteAccountCheckBox;

    public DeleteAccountPanel() {
        setLayout(null);
        setSize(MainWindow.WIDTH, 580);

        accountDao = EmailApplication.accountDao;
        loggedInAccountId = EmailApplication.loggedInAccountId;

        int menuButtonWidth = 180;
        int leftSubPanelWidth = 2 * MainWindow.GAP_BORDER + menuButtonWidth;
        int rightSubPanelWidth = MainWindow.WIDTH - leftSubPanelWidth;
        int componentFullWidth = rightSubPanelWidth - MainWindow.GAP_BORDER;
        int componentPositionX = leftSubPanelWidth;

        JLabel titleLabel_1 = new JLabel("PogMail", SwingConstants.CENTER);
        titleLabel_1.setBounds(0, 0, leftSubPanelWidth, 100);
        titleLabel_1.setFont(MainWindow.FONT_TITLE);
        add(titleLabel_1);

        JButton newMessageButton = new JButton("New message");
        newMessageButton.setBounds(MainWindow.GAP_BORDER, 115, menuButtonWidth, 30);
        newMessageButton.addActionListener(e -> MainWindow.getInstance().setNewMessagePanelActive(this));
        add(newMessageButton);

        JButton receivedMessagesButton = new JButton(getUnreadMessagesCount() > 0 ? "Received messages (" + getUnreadMessagesCount() + ")" : "Received messages");        receivedMessagesButton.setBounds(MainWindow.GAP_BORDER, 160, menuButtonWidth, 30);
        receivedMessagesButton.addActionListener(e -> MainWindow.getInstance().setMessagesPanelActive(this, MessageType.RECEIVED, 0));
        add(receivedMessagesButton);

        JButton sentMessagesButton = new JButton("Sent messages");
        sentMessagesButton.setBounds(MainWindow.GAP_BORDER, 205, menuButtonWidth, 30);
        sentMessagesButton.addActionListener(e -> MainWindow.getInstance().setMessagesPanelActive(this, MessageType.SENT, 0));
        add(sentMessagesButton);

        JButton accountButton = new JButton("Personal details");
        accountButton.setBounds(MainWindow.GAP_BORDER, 250, menuButtonWidth, 30);
        accountButton.addActionListener(e -> MainWindow.getInstance().setAccountDetailsPanelActive(this));
        add(accountButton);

        JButton passwordButton = new JButton("Security");
        passwordButton.setBounds(MainWindow.GAP_BORDER, 295, menuButtonWidth, 30);
        passwordButton.addActionListener(e -> MainWindow.getInstance().setSecurityPanelActive(this));
        add(passwordButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(MainWindow.GAP_BORDER, 340, menuButtonWidth, 30);
        logoutButton.addActionListener(e -> logout());
        add(logoutButton);

        JLabel titleLabel_2 = new JLabel("Delete your account", SwingConstants.CENTER);
        titleLabel_2.setBounds(leftSubPanelWidth, 0, componentFullWidth, 100);
        titleLabel_2.setFont(MainWindow.FONT_TITLE);
        add(titleLabel_2);

        JLabel passwordLabel = new JLabel("Old password");
        passwordLabel.setBounds(componentPositionX, 100, componentFullWidth, 15);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(componentPositionX, 115, componentFullWidth, 30);
        add(passwordField);

        passwordError = new JLabel("Password error");
        passwordError.setBounds(componentPositionX, 145, componentFullWidth, 11);
        passwordError.setForeground(MainWindow.COLOR_ERROR);
        passwordError.setFont(MainWindow.FONT_NOTIFICATION);
        passwordError.setVisible(false);
        add(passwordError);

        JButton deleteAccountButton = new JButton("Delete account");
        deleteAccountButton.setBounds(componentPositionX, 190, componentFullWidth, 30);
        deleteAccountButton.addActionListener(e -> deleteAccount());
        deleteAccountButton.setEnabled(false);
        add(deleteAccountButton);

        deleteAccountCheckBox = new JCheckBox("I want to delete my account");
        deleteAccountCheckBox.setBounds(componentPositionX, 160 , componentFullWidth, 15);
        deleteAccountCheckBox.addActionListener(e -> deleteAccountButton.setEnabled(deleteAccountCheckBox.isSelected()));
        add(deleteAccountCheckBox);
    }

    private int getUnreadMessagesCount() {
        int unreadMessages = 0;

        for (Message message : accountDao.getAccount(loggedInAccountId).getInbox().getReceivedMessages()) {
            if (!message.isViewed()) {
                unreadMessages++;
            }
        }

        return unreadMessages;
    }

    private boolean isPasswordCorrect() {
        char[] password_1 = passwordField.getPassword();
        char[] password_2 = accountDao.getAccount(loggedInAccountId).getPassword();

        if (!Arrays.equals(password_1, password_2)) {
            passwordError.setText("Password is incorrect");

            return false;
        }

        return true;
    }

    private void deleteAccount() {
        if (isPasswordCorrect()) {
            accountDao.deleteAccount(loggedInAccountId);

            passwordError.setVisible(false);

            logout();
        } else {
            passwordError.setVisible(true);
        }
    }

    private void logout() {
        EmailApplication.loggedIn = false;
        EmailApplication.loggedInAccountId = null;
        MainWindow.getInstance().setLoginPanelActive(this);
    }
}