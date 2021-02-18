package com.company.gui.panel;

import com.company.EmailApplication;
import com.company.dao.AccountDao;
import com.company.gui.window.MainWindow;
import com.company.model.Account;
import com.company.model.Message;
import com.company.model.MessageType;

import javax.swing.*;
import java.awt.*;

public class NewMessagePanel extends JPanel {
    private final AccountDao accountDao;
    private final int loggedInAccountId;

    private final JLabel receiverError;
    private final JLabel messageTopicError;
    private final JLabel messageBodyError;
    private final JLabel messageSentNotification;
    private final JTextField receiverTextField;
    private final JTextField messageTopicTextField;
    private final JTextArea messageBodyTextArea;

    public NewMessagePanel() {
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

        JLabel titleLabel_2 = new JLabel("Send new message", SwingConstants.CENTER);
        titleLabel_2.setBounds(leftSubPanelWidth, 0, componentFullWidth, 100);
        titleLabel_2.setFont(MainWindow.FONT_TITLE);
        add(titleLabel_2);

        JLabel receiverLabel = new JLabel("Receiver");
        receiverLabel.setBounds(componentPositionX, 100, componentFullWidth, 15);
        add(receiverLabel);

        receiverTextField = new JTextField();
        receiverTextField.setBounds(componentPositionX, 115, componentFullWidth, 30);
        add(receiverTextField);

        receiverError = new JLabel("Receiver is incorrect");
        receiverError.setBounds(componentPositionX, 145, componentFullWidth, 11);
        receiverError.setForeground(MainWindow.COLOR_ERROR);
        receiverError.setFont(MainWindow.FONT_NOTIFICATION);
        receiverError.setVisible(false);
        add(receiverError);

        JLabel topicLabel = new JLabel("Topic");
        topicLabel.setBounds(componentPositionX, 160, componentFullWidth, 15);
        add(topicLabel);

        messageTopicTextField = new JTextField();
        messageTopicTextField.setBounds(componentPositionX, 175, componentFullWidth, 30);
        add(messageTopicTextField);

        messageTopicError = new JLabel("Topic error");
        messageTopicError.setBounds(componentPositionX, 205, componentFullWidth, 11);
        messageTopicError.setForeground(MainWindow.COLOR_ERROR);
        messageTopicError.setFont(MainWindow.FONT_NOTIFICATION);
        messageTopicError.setVisible(false);
        add(messageTopicError);

        JLabel messageBodyLabel = new JLabel("Message");
        messageBodyLabel.setBounds(componentPositionX, 220, componentFullWidth, 15);
        add(messageBodyLabel);

        messageBodyTextArea = new JTextArea();
        messageBodyTextArea.setBounds(componentPositionX, 235, componentFullWidth, 210);
        messageBodyTextArea.setBorder(messageTopicTextField.getBorder());
        messageBodyTextArea.setWrapStyleWord(true);
        messageBodyTextArea.setLineWrap(true);
        messageBodyTextArea.setEditable(true);
        messageBodyTextArea.setBorder(null);

        JScrollPane messageBodyScrollPane = new JScrollPane();
        messageBodyScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        messageBodyScrollPane.setBounds(componentPositionX, 235, componentFullWidth, 210);
        messageBodyScrollPane.getViewport().setBackground(Color.WHITE);
        messageBodyScrollPane.getViewport().add(messageBodyTextArea);
        add(messageBodyScrollPane);

        messageBodyError = new JLabel("Message error");
        messageBodyError.setBounds(componentPositionX, 445, componentFullWidth, 11);
        messageBodyError.setForeground(MainWindow.COLOR_ERROR);
        messageBodyError.setFont(MainWindow.FONT_NOTIFICATION);
        messageBodyError.setVisible(false);
        add(messageBodyError);

        messageSentNotification = new JLabel("Message sent", SwingConstants.CENTER);
        messageSentNotification.setBounds(componentPositionX, 452, componentFullWidth, 11);
        messageSentNotification.setForeground(MainWindow.COLOR_SUCCESS);
        messageSentNotification.setFont(MainWindow.FONT_NOTIFICATION);
        messageSentNotification.setVisible(false);
        add(messageSentNotification);

        JButton sendMessageButton = new JButton("Send");
        sendMessageButton.setBounds(componentPositionX, 475, componentFullWidth, 30);
        sendMessageButton.addActionListener(e -> sendMessage());
        add(sendMessageButton);
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

    private boolean isReceiverCorrect() {
        String receiver = receiverTextField.getText();
        int atPosition = 0;

        if (receiver == null || receiver.isEmpty()) {
            receiverError.setText("Receiver can't be empty");

            return false;
        }

        for (int i = 0; i < receiver.length(); i++) {
            if (receiver.charAt(i) == '@') {
                atPosition = i;
            }
        }

        if (atPosition == 0) {
            receiverError.setText("Receiver is incorrect");

            return false;
        }

        String receiverUsername = receiver.substring(0, atPosition);
        String receiverDomain = receiver.substring(atPosition + 1);

        for (int i = 0; i < receiverUsername.length(); i++) {
            if (!Character.isLetterOrDigit(receiverUsername.charAt(i)) && receiverUsername.charAt(i) != '_' && receiverUsername.charAt(i) != '-') {
                receiverError.setText("Receiver is incorrect");

                return false;
            }
        }

        if (!receiverDomain.equals(MainWindow.DOMAIN_OPTIONS[0]) && !receiverDomain.equals(MainWindow.DOMAIN_OPTIONS[1])) {
            receiverError.setText("Receiver is incorrect");

            return false;
        }

        return true;
    }

    private boolean isMessageTopicCorrect() {
        String topic = messageTopicTextField.getText();

        if (topic == null || topic.isEmpty() || topic.length() > 100) {
            messageTopicError.setText("Topic must have between 1 and 100 characters");

            return false;
        }

        return true;
    }

    private boolean isMessageBodyCorrect() {
        String message = messageBodyTextArea.getText();

        if (message == null || message.isEmpty() || message.length() > 1000) {
            messageBodyError.setText("Message must have between 1 and 1000 characters");

            return false;
        }

        return true;
    }

    private void checkForErrors() {
        receiverError.setVisible(!isReceiverCorrect());
        messageTopicError.setVisible(!isMessageTopicCorrect());
        messageBodyError.setVisible(!isMessageBodyCorrect());
    }

    private void sendMessage() {
        checkForErrors();

        if (isReceiverCorrect() && isMessageTopicCorrect() && isMessageBodyCorrect()) {
            String sender = accountDao.getAccount(loggedInAccountId).getFullUsername();
            String receiver = receiverTextField.getText();
            String topic = messageTopicTextField.getText();
            String message = messageBodyTextArea.getText();

            Message sentMessage = new Message(MessageType.SENT ,sender, receiver, topic, message);
            Message receivedMessage = new Message(MessageType.RECEIVED ,sender, receiver, topic, message);

            accountDao.getAccount(loggedInAccountId).getInbox().addSentMessage(sentMessage);

            for (Account account : accountDao.getAllAccounts()) {
                if (receiver.equalsIgnoreCase(account.getUsername() + "@" + account.getDomain())) {
                    account.getInbox().addReceivedMessage(receivedMessage);
                }
            }

            receiverTextField.setText("");
            messageTopicTextField.setText("");
            messageBodyTextArea.setText("");

            messageSentNotification.setVisible(true);
        } else {
            messageSentNotification.setVisible(false);
        }
    }

    private void logout() {
        EmailApplication.loggedIn = false;
        EmailApplication.loggedInAccountId = null;
        MainWindow.getInstance().setLoginPanelActive(this);
    }
}