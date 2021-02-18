package com.company.gui.panel;

import com.company.EmailApplication;
import com.company.dao.AccountDao;
import com.company.gui.window.MainWindow;
import com.company.model.Message;
import com.company.model.MessageType;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class ViewedMessagePanel extends JPanel {
    private final AccountDao accountDao;
    private final int loggedInAccountId;

    public ViewedMessagePanel(Message viewedMessage, int page) {
        setLayout(null);
        setSize(MainWindow.WIDTH, 580);

        accountDao = EmailApplication.accountDao;
        loggedInAccountId = EmailApplication.loggedInAccountId;

        int menuButtonWidth = 180;
        int leftSubPanelWidth = 2 * MainWindow.GAP_BORDER + menuButtonWidth;
        int rightSubPanelWidth = MainWindow.WIDTH - leftSubPanelWidth;
        int componentFullWidth = rightSubPanelWidth - MainWindow.GAP_BORDER;
        int componentHalfWidth = (componentFullWidth - MainWindow.GAP_ELEMENTS) / 2;
        int componentPositionX_1 = leftSubPanelWidth;
        int componentPositionX_2 = componentPositionX_1 + componentHalfWidth;
        int componentPositionX_3 = componentPositionX_2 + MainWindow.GAP_ELEMENTS;

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

        JLabel titleLabel_2 = new JLabel("View message", SwingConstants.CENTER);
        titleLabel_2.setBounds(leftSubPanelWidth, 0, componentFullWidth, 100);
        titleLabel_2.setFont(MainWindow.FONT_TITLE);
        add(titleLabel_2);

        JLabel receiverSenderLabel = new JLabel(viewedMessage.getType().equals(MessageType.RECEIVED) ? "Sender" : "Receiver");
        receiverSenderLabel.setBounds(componentPositionX_1, 100, componentHalfWidth, 15);
        add(receiverSenderLabel);

        JLabel dateLabel = new JLabel("Date");
        dateLabel.setBounds(componentPositionX_3, 100, componentHalfWidth, 15);
        add(dateLabel);

        JTextField receiverSenderTextField = new JTextField(viewedMessage.getType().equals(MessageType.RECEIVED) ? viewedMessage.getSender() : viewedMessage.getReceiver());
        receiverSenderTextField.setBounds(componentPositionX_1, 115, componentHalfWidth, 30);
        receiverSenderTextField.setDisabledTextColor(Color.BLACK);
        receiverSenderTextField.setEnabled(false);
        add(receiverSenderTextField);

        JTextField dateTextField = new JTextField(viewedMessage.getSentDateTime().format(DateTimeFormatter.ofPattern("dd LLLL yyyy, HH:mm")));
        dateTextField.setBounds(componentPositionX_3, 115, componentHalfWidth, 30);
        dateTextField.setDisabledTextColor(Color.BLACK);
        dateTextField.setEnabled(false);
        add(dateTextField);

        JLabel topicLabel = new JLabel("Topic");
        topicLabel.setBounds(componentPositionX_1, 160, componentFullWidth, 15);
        add(topicLabel);

        JTextField messageTopicTextField = new JTextField(viewedMessage.getTopic());
        messageTopicTextField.setBounds(componentPositionX_1, 175, componentFullWidth, 30);
        messageTopicTextField.setDisabledTextColor(Color.BLACK);
        messageTopicTextField.setEnabled(false);
        add(messageTopicTextField);

        JLabel messageBodyLabel = new JLabel("Message");
        messageBodyLabel.setBounds(componentPositionX_1, 220, componentFullWidth, 15);
        add(messageBodyLabel);

        JTextArea messageBodyTextArea = new JTextArea(viewedMessage.getMessage());
        messageBodyTextArea.setBounds(componentPositionX_1, 235, componentFullWidth, 210);
        messageBodyTextArea.setBorder(messageTopicTextField.getBorder());
        messageBodyTextArea.setDisabledTextColor(Color.BLACK);
        messageBodyTextArea.setWrapStyleWord(true);
        messageBodyTextArea.setLineWrap(true);
        messageBodyTextArea.setEnabled(false);
        messageBodyTextArea.setBorder(null);

        JScrollPane messageBodyScrollPane = new JScrollPane();
        messageBodyScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        messageBodyScrollPane.setBounds(componentPositionX_1, 235, componentFullWidth, 210);
        messageBodyScrollPane.getViewport().setBackground(Color.WHITE);
        messageBodyScrollPane.getViewport().add(messageBodyTextArea);
        add(messageBodyScrollPane);

        JButton backButton = new JButton("Back");
        backButton.setBounds(componentPositionX_1, 475, componentFullWidth, 30);
        backButton.addActionListener(e -> MainWindow.getInstance().setMessagesPanelActive(this, viewedMessage.getType(), page));
        add(backButton);
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

    private void logout() {
        EmailApplication.loggedIn = false;
        EmailApplication.loggedInAccountId = null;
        MainWindow.getInstance().setLoginPanelActive(this);
    }
}