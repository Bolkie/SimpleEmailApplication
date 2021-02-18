package com.company.gui.panel;

import com.company.EmailApplication;
import com.company.dao.AccountDao;
import com.company.gui.window.MainWindow;
import com.company.model.Account;
import com.company.model.Message;
import com.company.model.MessageType;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class ChangeSecurityQuestionPanel extends JPanel {
    private final AccountDao accountDao;
    private final int loggedInAccountId;

    private final JLabel securityQuestionError;
    private final JLabel passwordError;
    private final JLabel securityQuestionChangedNotification;
    private final JTextField securityQuestionAnswerTextField;
    private final JPasswordField passwordField;
    private final JComboBox<String> securityQuestionComboBox;

    public ChangeSecurityQuestionPanel() {
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

        JLabel titleLabel_2 = new JLabel("Change security question", SwingConstants.CENTER);
        titleLabel_2.setBounds(leftSubPanelWidth, 0, componentFullWidth, 100);
        titleLabel_2.setFont(MainWindow.FONT_TITLE);
        add(titleLabel_2);

        JLabel securityQuestionLabel = new JLabel("Security question");
        securityQuestionLabel.setBounds(componentPositionX, 100, componentFullWidth, 15);
        add(securityQuestionLabel);

        securityQuestionComboBox = new JComboBox<>(MainWindow.SECURITY_QUESTIONS);
        securityQuestionComboBox.setBounds(componentPositionX, 115, componentFullWidth, 30);
        securityQuestionComboBox.setFont(MainWindow.FONT_TEXT_PLAIN);
        securityQuestionComboBox.setBackground(Color.WHITE);
        for (int i = 0; i < MainWindow.SECURITY_QUESTIONS.length; i++) {
            if (accountDao.getAccount(loggedInAccountId).getSecurityQuestion().equals(MainWindow.SECURITY_QUESTIONS[i])) {
                securityQuestionComboBox.setSelectedIndex(i);
            }
        }
        add(securityQuestionComboBox);

        JLabel securityQuestionAnswerLabel = new JLabel("Enter your answer here");
        securityQuestionAnswerLabel.setBounds(componentPositionX, 160, componentFullWidth, 15);
        add(securityQuestionAnswerLabel);

        securityQuestionAnswerTextField = new JTextField(accountDao.getAccount(loggedInAccountId).getSecurityQuestionAnswer());
        securityQuestionAnswerTextField.setBounds(componentPositionX, 175, componentFullWidth, 30);
        add(securityQuestionAnswerTextField);

        securityQuestionError = new JLabel("Security question answer error");
        securityQuestionError.setBounds(componentPositionX, 205, componentFullWidth, 11);
        securityQuestionError.setForeground(MainWindow.COLOR_ERROR);
        securityQuestionError.setFont(MainWindow.FONT_NOTIFICATION);
        securityQuestionError.setVisible(false);
        add(securityQuestionError);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(componentPositionX, 220, componentFullWidth, 15);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(componentPositionX, 235, componentFullWidth, 30);
        add(passwordField);

        passwordError = new JLabel("Password error");
        passwordError.setBounds(componentPositionX, 265, componentFullWidth, 11);
        passwordError.setForeground(MainWindow.COLOR_ERROR);
        passwordError.setFont(MainWindow.FONT_NOTIFICATION);
        passwordError.setVisible(false);
        add(passwordError);

        securityQuestionChangedNotification = new JLabel("Account details changed successfully", SwingConstants.CENTER);
        securityQuestionChangedNotification.setBounds(componentPositionX, 272, componentFullWidth, 11);
        securityQuestionChangedNotification.setForeground(MainWindow.COLOR_SUCCESS);
        securityQuestionChangedNotification.setFont(MainWindow.FONT_NOTIFICATION);
        securityQuestionChangedNotification.setVisible(false);
        add(securityQuestionChangedNotification);

        JButton changeSecurityQuestionButton = new JButton("Change security question");
        changeSecurityQuestionButton.setBounds(componentPositionX, 295, componentFullWidth, 30);
        changeSecurityQuestionButton.addActionListener(e -> changeSecurityQuestion());
        add(changeSecurityQuestionButton);
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

    private boolean isSecurityQuestionAnswerCorrect() {
        String answer = securityQuestionAnswerTextField.getText();

        if (answer == null || answer.isEmpty() || answer.length() > 50) {
            securityQuestionError.setText("The answer for security question must have between 1 and 50 characters");

            return false;
        }

        for (int i = 0; i < answer.length(); i++) {
            if (!Character.isLetterOrDigit(answer.charAt(i)) && !Character.isWhitespace(answer.charAt(i))) {
                securityQuestionError.setText("The answer for security question contains illegal characters");

                return false;
            }
        }

        return true;
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

    private void checkForErrors() {
        securityQuestionError.setVisible(!isSecurityQuestionAnswerCorrect());
        passwordError.setVisible(!isPasswordCorrect());
    }

    private void changeSecurityQuestion() {
        checkForErrors();

        if (isSecurityQuestionAnswerCorrect() && isPasswordCorrect()) {
            Account account = accountDao.getAccount(loggedInAccountId);
            account.setSecurityQuestion(String.valueOf(securityQuestionComboBox.getSelectedItem()));
            account.setSecurityQuestionAnswer(securityQuestionAnswerTextField.getText());
            accountDao.updateAccount(account);

            passwordField.setText("");

            securityQuestionChangedNotification.setVisible(true);
        } else {
            securityQuestionChangedNotification.setVisible(false);
        }
    }

    private void logout() {
        EmailApplication.loggedIn = false;
        EmailApplication.loggedInAccountId = null;
        MainWindow.getInstance().setLoginPanelActive(this);
    }
}