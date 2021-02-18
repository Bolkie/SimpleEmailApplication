package com.company.gui.panel;

import com.company.EmailApplication;
import com.company.dao.AccountDao;
import com.company.gui.window.MainWindow;
import com.company.model.Account;
import com.company.model.Message;
import com.company.model.MessageType;

import javax.swing.*;
import java.util.Arrays;
import java.util.regex.Matcher;

public class ChangePasswordPanel extends JPanel {
    private final AccountDao accountDao;
    private final int loggedInAccountId;

    private final JLabel passwordError;
    private final JLabel passwordChangedNotification;
    private final JPasswordField oldPasswordField;
    private final JPasswordField newPasswordField;
    private final JPasswordField newPasswordReTypeField;

    public ChangePasswordPanel() {
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

        JLabel titleLabel_2 = new JLabel("Change password", SwingConstants.CENTER);
        titleLabel_2.setBounds(leftSubPanelWidth, 0, componentFullWidth, 100);
        titleLabel_2.setFont(MainWindow.FONT_TITLE);
        add(titleLabel_2);

        JLabel oldPasswordLabel = new JLabel("Old password");
        oldPasswordLabel.setBounds(componentPositionX, 100, componentFullWidth, 15);
        add(oldPasswordLabel);

        oldPasswordField = new JPasswordField();
        oldPasswordField.setBounds(componentPositionX, 115, componentFullWidth, 30);
        add(oldPasswordField);

        JLabel newPasswordLabel = new JLabel("New password");
        newPasswordLabel.setBounds(componentPositionX, 160, componentFullWidth, 15);
        add(newPasswordLabel);

        newPasswordField = new JPasswordField();
        newPasswordField.setBounds(componentPositionX, 175, componentFullWidth, 30);
        add(newPasswordField);

        JLabel newPasswordReTypeLabel = new JLabel("Re-type new password");
        newPasswordReTypeLabel.setBounds(componentPositionX, 220, componentFullWidth, 15);
        add(newPasswordReTypeLabel);

        newPasswordReTypeField = new JPasswordField();
        newPasswordReTypeField.setBounds(componentPositionX, 235, componentFullWidth, 30);
        add(newPasswordReTypeField);

        passwordError = new JLabel("Password changed", SwingConstants.CENTER);
        passwordError.setBounds(componentPositionX, 272, componentFullWidth, 11);
        passwordError.setForeground(MainWindow.COLOR_ERROR);
        passwordError.setFont(MainWindow.FONT_NOTIFICATION);
        passwordError.setVisible(false);
        add(passwordError);

        passwordChangedNotification = new JLabel("Password changed", SwingConstants.CENTER);
        passwordChangedNotification.setBounds(componentPositionX, 272, componentFullWidth, 11);
        passwordChangedNotification.setForeground(MainWindow.COLOR_SUCCESS);
        passwordChangedNotification.setFont(MainWindow.FONT_NOTIFICATION);
        passwordChangedNotification.setVisible(false);
        add(passwordChangedNotification);

        JButton saveChangesButton = new JButton("Change password");
        saveChangesButton.setBounds(componentPositionX, 295, componentFullWidth, 30);
        saveChangesButton.addActionListener(e -> changePassword());
        add(saveChangesButton);
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

    private boolean isOldPasswordCorrect() {
        char[] password_1 = oldPasswordField.getPassword();
        char[] password_2 = accountDao.getAccount(loggedInAccountId).getPassword();

        if (!Arrays.equals(password_1, password_2)) {
            passwordError.setText("Old password is incorrect");

            return false;
        }

        return true;
    }

    private boolean isNewPasswordCorrect() {
        char[] password = newPasswordField.getPassword();

        Matcher hasLetter = MainWindow.LETTER.matcher(String.valueOf(newPasswordField.getPassword()));
        Matcher hasDigit = MainWindow.DIGIT.matcher(String.valueOf(newPasswordField.getPassword()));
        Matcher hasSpecial = MainWindow.SPECIAL.matcher(String.valueOf(newPasswordField.getPassword()));

        if (password.length < 8 || password.length > 25) {
            passwordError.setText("New password must have between 8 and 25 characters");

            return false;
        }

        if (!hasLetter.find() || !hasDigit.find() || !hasSpecial.find()) {
            passwordError.setText("New password must have at least one letter, digit and special character");

            return false;
        }

        return true;
    }

    private boolean areNewPasswordsEqual() {
        char[] password_1 = newPasswordField.getPassword();
        char[] password_2 = newPasswordReTypeField.getPassword();


        if (password_2.length < 8 || password_2.length > 25 || !Arrays.equals(password_1, password_2)) {
            passwordError.setText("New passwords do not match");

            return false;
        }

        return true;
    }

    private void changePassword() {
        if (isOldPasswordCorrect() && isNewPasswordCorrect() && areNewPasswordsEqual()) {
            Account account = accountDao.getAccount(loggedInAccountId);
            account.setPassword(newPasswordField.getPassword());
            accountDao.updateAccount(account);

            oldPasswordField.setText("");
            newPasswordField.setText("");
            newPasswordReTypeField.setText("");

            passwordError.setVisible(false);
            passwordChangedNotification.setVisible(true);
        } else {
            passwordError.setVisible(true);
            passwordChangedNotification.setVisible(false);
        }
    }

    private void logout() {
        EmailApplication.loggedIn = false;
        EmailApplication.loggedInAccountId = null;
        MainWindow.getInstance().setLoginPanelActive(this);
    }
}