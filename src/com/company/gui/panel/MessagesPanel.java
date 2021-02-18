package com.company.gui.panel;

import com.company.EmailApplication;
import com.company.dao.AccountDao;
import com.company.gui.window.MainWindow;
import com.company.model.Message;
import com.company.model.MessageType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MessagesPanel extends JPanel {
    private final AccountDao accountDao;
    private final int loggedInAccountId;
    private final int messagesPerPage = 10;
    private final List<JButton> messagesButtons;
    private int currentPage;

    public MessagesPanel(MessageType messageType, int page) {
        setLayout(null);
        setSize(MainWindow.WIDTH, 580);

        accountDao = EmailApplication.accountDao;
        loggedInAccountId = EmailApplication.loggedInAccountId;
        messagesButtons = new ArrayList<>(messagesPerPage);
        currentPage = page;

        int menuButtonWidth = 180;
        int pageButtonWidth = 50;
        int leftSubPanelWidth = 2 * MainWindow.GAP_BORDER + menuButtonWidth;
        int rightSubPanelWidth = MainWindow.WIDTH - leftSubPanelWidth;
        int componentFullWidth = rightSubPanelWidth - MainWindow.GAP_BORDER;
        int componentPositionX_1 = leftSubPanelWidth;
        int componentPositionX_2 = componentPositionX_1 + (componentFullWidth - pageButtonWidth) / 2;

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

        JLabel titleLabel_2 = new JLabel(messageType.equals(MessageType.RECEIVED) ? "Received messages" : "Sent messages", SwingConstants.CENTER);
        titleLabel_2.setBounds(leftSubPanelWidth, 0, componentFullWidth, 100);
        titleLabel_2.setFont(MainWindow.FONT_TITLE);
        add(titleLabel_2);

        for (int i = 0; i < messagesPerPage; i++) {
            messagesButtons.add(new JButton());
            messagesButtons.get(i).setBounds(componentPositionX_1, 115 + (i * 30), componentFullWidth, 30);
            messagesButtons.get(i).setHorizontalAlignment(SwingConstants.LEFT);
            messagesButtons.get(i).setFont(MainWindow.FONT_TEXT_PLAIN);
            messagesButtons.get(i).setBackground(Color.WHITE);
            messagesButtons.get(i).setEnabled(false);
            add(messagesButtons.get(i));
        }

        JButton firstPageButton = new JButton("≤");
        firstPageButton.setBounds(componentPositionX_2 - 2 * pageButtonWidth, 445, pageButtonWidth, 30);
        firstPageButton.addActionListener(e -> {
            currentPage = 0;

            MainWindow.getInstance().setMessagesPanelActive(this, messageType, currentPage);
        });
        add(firstPageButton);

        JButton pageBackButton = new JButton("<");
        pageBackButton.setBounds(componentPositionX_2 - pageButtonWidth, 445, pageButtonWidth, 30);
        pageBackButton.addActionListener(e -> {
            if (currentPage > 0) {
                currentPage--;
            }

            MainWindow.getInstance().setMessagesPanelActive(this, messageType, currentPage);
        });
        add(pageBackButton);

        JLabel pageNumber = new JLabel(String.valueOf(page + 1), SwingConstants.CENTER);
        pageNumber.setBounds(componentPositionX_2, 445, pageButtonWidth, 30);
        add(pageNumber);

        JButton pageForwardButton = new JButton(">");
        pageForwardButton.setBounds(componentPositionX_2 + pageButtonWidth, 445, pageButtonWidth, 30);
        pageForwardButton.addActionListener(e -> {
            if (messageType.equals(MessageType.RECEIVED) && currentPage < (accountDao.getAccount(loggedInAccountId).getInbox().getReceivedMessages().size() - 1) / messagesPerPage) {
                currentPage++;
            } else if (messageType.equals(MessageType.SENT) && currentPage < (accountDao.getAccount(loggedInAccountId).getInbox().getSentMessages().size() - 1) / messagesPerPage) {
                currentPage++;
            }

            MainWindow.getInstance().setMessagesPanelActive(this, messageType, currentPage);
        });
        add(pageForwardButton);

        JButton lastPageButton = new JButton("≥");
        lastPageButton.setBounds(componentPositionX_2 + 2 * pageButtonWidth, 445, pageButtonWidth, 30);
        lastPageButton.addActionListener(e -> {
            if (messageType.equals(MessageType.RECEIVED)) {
                currentPage = (accountDao.getAccount(loggedInAccountId).getInbox().getReceivedMessages().size() - 1) / messagesPerPage;
            } else {
                currentPage = (accountDao.getAccount(loggedInAccountId).getInbox().getSentMessages().size() - 1) / messagesPerPage;
            }

            MainWindow.getInstance().setMessagesPanelActive(this, messageType, currentPage);
        });
        add(lastPageButton);

        if (messageType.equals(MessageType.RECEIVED)) {
            setReceivedMessages(currentPage);
        } else {
            setSentMessages(currentPage);
        }
        //setMessages(currentPage, messageType);
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

    public void setReceivedMessages(int page) {
        List<Message> messages = accountDao.getAccount(loggedInAccountId).getInbox().getReceivedMessages();

        messages = messages.stream()
                .sorted(Comparator.comparingInt(Message::getId).reversed())
                .collect(Collectors.toList());

        for (JButton button : messagesButtons) {
            for (ActionListener actionListener : button.getActionListeners()) {
                button.removeActionListener(actionListener);
            }
        }

        for (int i = 0; i < messagesPerPage; i++) {
            int messageId = page * messagesPerPage + i;
            int realMessageId = messages.size() - page * messagesPerPage - i - 1;

            if (messageId < messages.size()) {
                Message message = messages.get(messageId);
                messagesButtons.get(i).setFont(message.isViewed() ? MainWindow.FONT_TEXT_PLAIN : MainWindow.FONT_TEXT_BOLD);
                messagesButtons.get(i).setText(message.getSender() + " - " + message.getTopic());
                messagesButtons.get(i).setEnabled(true);
                messagesButtons.get(i).addActionListener(e -> {
                    accountDao.getAccount(loggedInAccountId).getInbox().getReceivedMessages().get(realMessageId).setViewed(true);
                    MainWindow.getInstance().setViewedMessagePanelActive(this, message, page);
                });
            } else {
                messagesButtons.get(i).setEnabled(false);
                messagesButtons.get(i).setText("");
            }
        }
    }

    public void setSentMessages(int page) {
        List<Message> messages = accountDao.getAccount(loggedInAccountId).getInbox().getSentMessages();

        messages = messages.stream()
                .sorted(Comparator.comparingInt(Message::getId).reversed())
                .collect(Collectors.toList());

        for (JButton button : messagesButtons) {
            for (ActionListener actionListener : button.getActionListeners()) {
                button.removeActionListener(actionListener);
            }
        }

        for (int i = 0; i < messagesPerPage; i++) {
            int messageId = page * messagesPerPage + i;

            if (messageId < messages.size()) {
                Message message = messages.get(messageId);
                messagesButtons.get(i).setFont(MainWindow.FONT_TEXT_PLAIN);
                messagesButtons.get(i).setText(message.getReceiver() + " - " + message.getTopic());
                messagesButtons.get(i).setEnabled(true);
                messagesButtons.get(i).addActionListener(e -> MainWindow.getInstance().setViewedMessagePanelActive(this, message, page));
            } else {
                messagesButtons.get(i).setEnabled(false);
                messagesButtons.get(i).setText("");
            }
        }
    }

    private void logout() {
        EmailApplication.loggedIn = false;
        EmailApplication.loggedInAccountId = null;
        MainWindow.getInstance().setLoginPanelActive(this);
    }
}