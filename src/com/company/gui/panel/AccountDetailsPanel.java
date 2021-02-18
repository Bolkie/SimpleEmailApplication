package com.company.gui.panel;

import com.company.EmailApplication;
import com.company.dao.AccountDao;
import com.company.gui.window.MainWindow;
import com.company.model.Account;
import com.company.model.Message;
import com.company.model.MessageType;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Date;

public class AccountDetailsPanel extends JPanel {
    private final AccountDao accountDao;
    private final int loggedInAccountId;

    private final JLabel firstNameError;
    private final JLabel lastNameError;
    private final JLabel birthdayError;
    private final JLabel accountDetailsChangedNotification;
    private final JTextField firstNameTextField;
    private final JTextField lastNameTextField;
    private final JComboBox<String> sexComboBox;
    private final JSpinner birthdaySpinner;

    public AccountDetailsPanel() {
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

        JLabel titleLabel_2 = new JLabel("Personal details", SwingConstants.CENTER);
        titleLabel_2.setBounds(leftSubPanelWidth, 0, componentFullWidth, 100);
        titleLabel_2.setFont(MainWindow.FONT_TITLE);
        add(titleLabel_2);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(componentPositionX_1, 100, componentHalfWidth, 15);
        add(usernameLabel);

        JLabel domainLabel = new JLabel("Domain");
        domainLabel.setBounds(componentPositionX_3, 100, componentHalfWidth, 15);
        add(domainLabel);

        JTextField usernameTextField = new JTextField(accountDao.getAccount(loggedInAccountId).getUsername());
        usernameTextField.setBounds(componentPositionX_1, 115, componentHalfWidth, 30);
        usernameTextField.setDisabledTextColor(Color.BLACK);
        usernameTextField.setEnabled(false);
        add(usernameTextField);

        JLabel atLabel = new JLabel("@", SwingConstants.CENTER);
        atLabel.setBounds(componentPositionX_2, 115, MainWindow.GAP_ELEMENTS, 30);
        add(atLabel);

        JComboBox<String> domainComboBox = new JComboBox<>(MainWindow.DOMAIN_OPTIONS);
        domainComboBox.setBounds(componentPositionX_3, 115, componentHalfWidth, 30);
        domainComboBox.setFont(MainWindow.FONT_TEXT_PLAIN);
        domainComboBox.setBackground(Color.WHITE);
        domainComboBox.setEnabled(false);
        for (int i = 0; i < MainWindow.DOMAIN_OPTIONS.length; i++) {
            if (accountDao.getAccount(loggedInAccountId).getDomain().equals(MainWindow.DOMAIN_OPTIONS[i])) {
                domainComboBox.setSelectedIndex(i);
            }
        }
        add(domainComboBox);

        JLabel firstNameLabel = new JLabel("First name");
        firstNameLabel.setBounds(componentPositionX_1, 160, componentFullWidth, 15);
        add(firstNameLabel);

        firstNameTextField = new JTextField(accountDao.getAccount(loggedInAccountId).getOwnerFirstName());
        firstNameTextField.setBounds(componentPositionX_1, 175, componentFullWidth, 30);
        add(firstNameTextField);

        firstNameError = new JLabel("First name error");
        firstNameError.setBounds(componentPositionX_1, 205, componentFullWidth, 11);
        firstNameError.setForeground(MainWindow.COLOR_ERROR);
        firstNameError.setFont(MainWindow.FONT_NOTIFICATION);
        firstNameError.setVisible(false);
        add(firstNameError);

        JLabel lastNameLabel = new JLabel("Last name");
        lastNameLabel.setBounds(componentPositionX_1, 220, componentFullWidth, 15);
        add(lastNameLabel);

        lastNameTextField = new JTextField(accountDao.getAccount(loggedInAccountId).getOwnerLastName());
        lastNameTextField.setBounds(componentPositionX_1, 235, componentFullWidth, 30);
        add(lastNameTextField);

        lastNameError = new JLabel("Last name error");
        lastNameError.setBounds(componentPositionX_1, 265, componentFullWidth, 11);
        lastNameError.setForeground(MainWindow.COLOR_ERROR);
        lastNameError.setFont(MainWindow.FONT_NOTIFICATION);
        lastNameError.setVisible(false);
        add(lastNameError);

        JLabel sexLabel = new JLabel("Sex");
        sexLabel.setBounds(componentPositionX_1, 280, componentHalfWidth, 15);
        add(sexLabel);

        JLabel birthdayLabel = new JLabel("Birthday");
        birthdayLabel.setBounds(componentPositionX_3, 280, componentHalfWidth, 15);
        add(birthdayLabel);

        sexComboBox = new JComboBox<>(MainWindow.SEX_OPTIONS);
        sexComboBox.setBounds(componentPositionX_1, 295, componentHalfWidth, 30);
        sexComboBox.setFont(MainWindow.FONT_TEXT_PLAIN);
        sexComboBox.setBackground(Color.WHITE);
        for (int i = 0; i < MainWindow.SEX_OPTIONS.length; i++) {
            if (accountDao.getAccount(loggedInAccountId).getOwnerSex().equals(MainWindow.SEX_OPTIONS[i])) {
                sexComboBox.setSelectedIndex(i);
            }
        }
        add(sexComboBox);

        birthdaySpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor birthdayDateEditor = new JSpinner.DateEditor(birthdaySpinner, "dd/MM/yyyy");
        birthdaySpinner.setValue(MainWindow.convertLocalDateToDate(accountDao.getAccount(loggedInAccountId).getOwnerBirthday()));
        birthdaySpinner.setBounds(componentPositionX_3, 295, componentHalfWidth, 30);
        birthdaySpinner.setFont(MainWindow.FONT_TEXT_PLAIN);
        birthdaySpinner.setEditor(birthdayDateEditor);
        add(birthdaySpinner);

        birthdayError = new JLabel("Birthday error");
        birthdayError.setBounds(componentPositionX_3, 325, componentHalfWidth, 11);
        birthdayError.setForeground(MainWindow.COLOR_ERROR);
        birthdayError.setFont(MainWindow.FONT_NOTIFICATION);
        birthdayError.setVisible(false);
        add(birthdayError);

        accountDetailsChangedNotification = new JLabel("Account details changed successfully", SwingConstants.CENTER);
        accountDetailsChangedNotification.setBounds(componentPositionX_1, 332, componentFullWidth, 11);
        accountDetailsChangedNotification.setForeground(MainWindow.COLOR_SUCCESS);
        accountDetailsChangedNotification.setFont(MainWindow.FONT_NOTIFICATION);
        accountDetailsChangedNotification.setVisible(false);
        add(accountDetailsChangedNotification);

        JButton saveChangesButton = new JButton("Save changes");
        saveChangesButton.setBounds(componentPositionX_1, 355, componentFullWidth, 30);
        saveChangesButton.addActionListener(e -> changeAccountDetails());
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

    private boolean isFirstNameCorrect() {
        String firstName = firstNameTextField.getText();

        if (firstName == null || firstName.isEmpty() || firstName.length() < 2 || firstName.length() > 25) {
            firstNameError.setText("First name must have between 2 and 25 characters");

            return false;
        }

        for (int i = 0; i < firstName.length(); i++) {
            if (!Character.isLetter(firstName.charAt(i))) {
                firstNameError.setText("First name can only contain letters");

                return false;
            }
        }

        return true;
    }

    private boolean isLastNameCorrect() {
        String lastName = lastNameTextField.getText();

        if (lastName == null || lastName.isEmpty() || lastName.length() < 2 || lastName.length() > 25) {
            lastNameError.setText("Last name must have between 2 and 25 characters");

            return false;
        }

        for (int i = 0; i < lastName.length(); i++) {
            if (!Character.isLetter(lastName.charAt(i))) {
                lastNameError.setText("Last name can only contain letters");

                return false;
            }
        }

        return true;
    }

    private boolean isBirthdayCorrect() {
        Date birthdayDate = (Date) birthdaySpinner.getValue();
        LocalDate birthdayLocalDate = MainWindow.convertDateToLocalDate(birthdayDate);

        if (birthdayLocalDate.getYear() < 1900 || birthdayLocalDate.isAfter(LocalDate.now())) {
            birthdayError.setText("Birthday must be between year 1900 and today");

            return false;
        }

        return true;
    }

    private void checkForErrors() {
        firstNameError.setVisible(!isFirstNameCorrect());
        lastNameError.setVisible(!isLastNameCorrect());
        birthdayError.setVisible(!isBirthdayCorrect());
    }

    private void changeAccountDetails() {
        checkForErrors();

        if (isFirstNameCorrect() && isLastNameCorrect() && isBirthdayCorrect()) {
            Account account = accountDao.getAccount(loggedInAccountId);
            account.setOwnerFirstName(firstNameTextField.getText());
            account.setOwnerLastName(lastNameTextField.getText());
            account.setOwnerSex(String.valueOf(sexComboBox.getSelectedItem()));
            account.setOwnerBirthday(MainWindow.convertDateToLocalDate((Date)birthdaySpinner.getValue()));
            accountDao.updateAccount(account);

            accountDetailsChangedNotification.setVisible(true);
        } else {
            accountDetailsChangedNotification.setVisible(false);
        }
    }

    private void logout() {
        EmailApplication.loggedIn = false;
        EmailApplication.loggedInAccountId = null;
        MainWindow.getInstance().setLoginPanelActive(this);
    }
}