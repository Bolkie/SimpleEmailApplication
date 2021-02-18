package com.company.gui.panel;

import com.company.EmailApplication;
import com.company.dao.AccountDao;
import com.company.gui.window.MainWindow;
import com.company.model.Account;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Date;

public class RegisterPanel extends JPanel {
    private final AccountDao accountDao;

    private final JLabel usernameError;
    private final JLabel firstNameError;
    private final JLabel lastNameError;
    private final JLabel birthdayError;
    private final JLabel securityQuestionAnswerError;
    private final JTextField usernameTextField;
    private final JTextField firstNameTextField;
    private final JTextField lastNameTextField;
    private final JTextField securityQuestionAnswerTextField;
    private final JComboBox<String> domainComboBox;
    private final JComboBox<String> sexComboBox;
    private final JComboBox<String> securityQuestionComboBox;
    private final JCheckBox termsAndConditionsCheckBox;
    private final JSpinner birthdaySpinner;
    private final JButton registerButton;

    public RegisterPanel() {
        setLayout(null);
        setSize(MainWindow.WIDTH, 630);

        accountDao = EmailApplication.accountDao;

        int componentFullWidth = MainWindow.WIDTH - 2 * MainWindow.GAP_BORDER;
        int componentHalfWidth = (componentFullWidth - MainWindow.GAP_ELEMENTS) / 2;
        int componentPositionX_1 = MainWindow.GAP_BORDER;
        int componentPositionX_2 = componentPositionX_1 + componentHalfWidth;
        int componentPositionX_3 = componentPositionX_2 + MainWindow.GAP_ELEMENTS;

        JLabel panelTitleLabel = new JLabel("Create new account", SwingConstants.CENTER);
        panelTitleLabel.setBounds(0, 0, MainWindow.WIDTH, 100);
        panelTitleLabel.setFont(MainWindow.FONT_TITLE);
        add(panelTitleLabel);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(componentPositionX_1, 100, componentHalfWidth, 15);
        add(usernameLabel);

        JLabel domainLabel = new JLabel("Domain");
        domainLabel.setBounds(componentPositionX_3, 100, componentHalfWidth, 15);
        add(domainLabel);

        usernameTextField = new JTextField();
        usernameTextField.setBounds(componentPositionX_1, 115, componentHalfWidth, 30);
        add(usernameTextField);

        JLabel atLabel = new JLabel("@", SwingConstants.CENTER);
        atLabel.setBounds(componentPositionX_2, 115, MainWindow.GAP_ELEMENTS, 30);
        add(atLabel);

        domainComboBox = new JComboBox<>(MainWindow.DOMAIN_OPTIONS);
        domainComboBox.setBounds(componentPositionX_3, 115, componentHalfWidth, 30);
        domainComboBox.setFont(MainWindow.FONT_TEXT_PLAIN);
        domainComboBox.setBackground(Color.WHITE);
        add(domainComboBox);

        usernameError = new JLabel("Username error");
        usernameError.setBounds(componentPositionX_1, 145, componentHalfWidth, 11);
        usernameError.setFont(MainWindow.FONT_NOTIFICATION);
        usernameError.setForeground(MainWindow.COLOR_ERROR);
        usernameError.setVisible(false);
        add(usernameError);

        JLabel firstNameLabel = new JLabel("First name");
        firstNameLabel.setBounds(componentPositionX_1, 160, componentFullWidth, 15);
        add(firstNameLabel);

        firstNameTextField = new JTextField();
        firstNameTextField.setBounds(componentPositionX_1, 175, componentFullWidth, 30);
        add(firstNameTextField);

        firstNameError = new JLabel("First name error");
        firstNameError.setBounds(componentPositionX_1, 205, componentFullWidth, 11);
        firstNameError.setFont(MainWindow.FONT_NOTIFICATION);
        firstNameError.setForeground(MainWindow.COLOR_ERROR);
        firstNameError.setVisible(false);
        add(firstNameError);

        JLabel lastNameLabel = new JLabel("Last name");
        lastNameLabel.setBounds(componentPositionX_1, 220, componentFullWidth, 15);
        add(lastNameLabel);

        lastNameTextField = new JTextField();
        lastNameTextField.setBounds(componentPositionX_1, 235, componentFullWidth, 30);
        add(lastNameTextField);

        lastNameError = new JLabel("Last name error");
        lastNameError.setBounds(componentPositionX_1, 265, componentFullWidth, 11);
        lastNameError.setFont(MainWindow.FONT_NOTIFICATION);
        lastNameError.setForeground(MainWindow.COLOR_ERROR);
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
        add(sexComboBox);

        birthdaySpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor birthdayDateEditor = new JSpinner.DateEditor(birthdaySpinner, "dd/MM/yyyy");
        birthdaySpinner.setBounds(componentPositionX_3, 295, componentHalfWidth, 30);
        birthdaySpinner.setEditor(birthdayDateEditor);
        birthdaySpinner.setFont(MainWindow.FONT_TEXT_PLAIN);
        add(birthdaySpinner);

        birthdayError = new JLabel("Birthday error");
        birthdayError.setBounds(componentPositionX_3, 325, componentHalfWidth, 11);
        birthdayError.setFont(MainWindow.FONT_NOTIFICATION);
        birthdayError.setForeground(MainWindow.COLOR_ERROR);
        birthdayError.setVisible(false);
        add(birthdayError);

        JLabel securityQuestionLabel = new JLabel("Security question");
        securityQuestionLabel.setBounds(componentPositionX_1, 340, componentFullWidth, 15);
        add(securityQuestionLabel);

        securityQuestionComboBox = new JComboBox<>(MainWindow.SECURITY_QUESTIONS);
        securityQuestionComboBox.setBounds(componentPositionX_1, 355, componentFullWidth, 30);
        securityQuestionComboBox.setFont(MainWindow.FONT_TEXT_PLAIN);
        securityQuestionComboBox.setBackground(Color.WHITE);
        add(securityQuestionComboBox);

        JLabel securityQuestionAnswerLabel = new JLabel("Enter your answer here");
        securityQuestionAnswerLabel.setBounds(componentPositionX_1, 400, componentFullWidth, 15);
        add(securityQuestionAnswerLabel);

        securityQuestionAnswerTextField = new JTextField();
        securityQuestionAnswerTextField.setBounds(componentPositionX_1, 415, componentFullWidth, 30);
        add(securityQuestionAnswerTextField);

        securityQuestionAnswerError = new JLabel("Security question answer error");
        securityQuestionAnswerError.setBounds(componentPositionX_1, 445, componentFullWidth, 11);
        securityQuestionAnswerError.setFont(MainWindow.FONT_NOTIFICATION);
        securityQuestionAnswerError.setForeground(MainWindow.COLOR_ERROR);
        securityQuestionAnswerError.setVisible(false);
        add(securityQuestionAnswerError);

        registerButton = new JButton("Register");
        registerButton.setBounds(componentPositionX_1, 490, componentFullWidth, 30);
        registerButton.addActionListener(e -> register());
        registerButton.setEnabled(false);
        add(registerButton);

        termsAndConditionsCheckBox = new JCheckBox("I have read and agree to the Terms and Conditions");
        termsAndConditionsCheckBox.setBounds(componentPositionX_1, 460 , componentFullWidth, 15);
        termsAndConditionsCheckBox.addActionListener(e -> registerButton.setEnabled(termsAndConditionsCheckBox.isSelected()));
        add(termsAndConditionsCheckBox);

        JButton switchToLoginPanelButton = new JButton("Account already created? Click here");
        switchToLoginPanelButton.setBounds(componentPositionX_1, 545, componentFullWidth, 15);
        switchToLoginPanelButton.setBackground(new Color(getBackground().getRGB()));
        switchToLoginPanelButton.setBorder(null);
        switchToLoginPanelButton.addActionListener(e -> MainWindow.getInstance().setLoginPanelActive(this));
        add(switchToLoginPanelButton);
    }

    private boolean isUsernameCorrect() {
        String username = usernameTextField.getText();

        if (username == null || username.isEmpty() || username.length() < 5 || username.length() > 25) {
            usernameError.setText("Username must have between 5 and 25 characters");

            return false;
        }

        for (int i = 0; i < username.length(); i++) {
            if (!Character.isLetterOrDigit(username.charAt(i)) && username.charAt(i) != '_' && username.charAt(i) != '-') {
                usernameError.setText("Username contains illegal characters");

                return false;
            }
        }

        for (Account account : accountDao.getAllAccounts()) {
            if (account.getUsername().equalsIgnoreCase(username)) {
                usernameError.setText("Username already taken");

                return false;
            }
        }

        return true;
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

    private boolean isSecurityQuestionAnswerCorrect() {
        String answer = securityQuestionAnswerTextField.getText();

        if (answer == null || answer.isEmpty() || answer.length() > 50) {
            securityQuestionAnswerError.setText("The answer for security question must have between 1 and 50 characters");

            return false;
        }

        for (int i = 0; i < answer.length(); i++) {
            if (!Character.isLetterOrDigit(answer.charAt(i)) && !Character.isWhitespace(answer.charAt(i))) {
                securityQuestionAnswerError.setText("The answer for security question contains illegal characters");

                return false;
            }
        }

        return true;
    }

    private void checkForErrors() {
        usernameError.setVisible(!isUsernameCorrect());
        firstNameError.setVisible(!isFirstNameCorrect());
        lastNameError.setVisible(!isLastNameCorrect());
        birthdayError.setVisible(!isBirthdayCorrect());
        securityQuestionAnswerError.setVisible(!isSecurityQuestionAnswerCorrect());
    }

    private void register() {
        checkForErrors();

        if (isUsernameCorrect() && isFirstNameCorrect() && isLastNameCorrect() && isBirthdayCorrect() && isSecurityQuestionAnswerCorrect()) {
            String username = usernameTextField.getText().toLowerCase();
            String domain = String.valueOf(domainComboBox.getSelectedItem());
            char[] password = MainWindow.generateRandomPassword(8);
            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();
            String sex = String.valueOf(sexComboBox.getSelectedItem());
            LocalDate birthday = MainWindow.convertDateToLocalDate((Date) birthdaySpinner.getValue());
            String securityQuestion = String.valueOf(securityQuestionComboBox.getSelectedItem());
            String securityQuestionAnswer = securityQuestionAnswerTextField.getText();

            Account account = new Account(username, domain, password, firstName, lastName, sex, birthday, securityQuestion, securityQuestionAnswer);
            accountDao.saveAccount(account);

            MainWindow.getInstance().setGeneratedPasswordPanelActive(this, password);
        }
    }
}