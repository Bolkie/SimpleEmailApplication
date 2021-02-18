package com.company.gui.window;

import com.company.EmailApplication;
import com.company.gui.panel.*;
import com.company.model.Message;
import com.company.model.MessageType;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

public class MainWindow extends JFrame {
    private static MainWindow instance;

    public static final String PASSWORD_CHAR_POOL = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static final String[] DOMAIN_OPTIONS = {"pog.com", "poggers.com"};
    public static final String[] SEX_OPTIONS = {"Male", "Female"};
    public static final String[] SECURITY_QUESTIONS = {"What’s the street you were born on?", "Who was your first teacher?", "What what the house number your first lived?", "What’s the name of your first pet?"};

    public static final Pattern LETTER = Pattern.compile("[a-zA-z]");
    public static final Pattern DIGIT = Pattern.compile("[0-9]");
    public static final Pattern SPECIAL = Pattern.compile("[!@#$%^&*()\\-_=+\\[{\\]};:',<.>/?|]");

    public static final Font FONT_NOTIFICATION = new Font(null, Font.PLAIN, 10);
    public static final Font FONT_TEXT_PLAIN = new Font(null, Font.PLAIN, 12);
    public static final Font FONT_TEXT_BOLD = new Font(null, Font.BOLD, 12);
    public static final Font FONT_TITLE = new Font(null, Font.BOLD, 20);

    public static final Color COLOR_ERROR = new Color(200, 0, 0);
    public static final Color COLOR_SUCCESS = new Color(0, 150, 0);

    public static final int WIDTH = 800;
    public static final int GAP_BORDER = 50;
    public static final int GAP_ELEMENTS = 20;

    private MainWindow() {
        setLoginPanelActive(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new CardLayout());
        setLocationRelativeTo(null);
        setTitle("PogMail");
        setResizable(false);
        setVisible(true);

        UIManager.put("ComboBox.disabledBackground", Color.WHITE);
        UIManager.put("ComboBox.disabledForeground", Color.BLACK );
    }

    public static MainWindow getInstance() {
        if (instance == null) {
            instance = new MainWindow();
        }

        return instance;
    }

    public void setLoginPanelActive(JPanel fromPanel) {
        if (fromPanel != null) {
            remove(fromPanel);
        }

        LoginPanel loginPanel = new LoginPanel();
        add(loginPanel);
        setSize(loginPanel.getSize());
        setVisible(true);
    }

    public void setRegisterPanelActive(JPanel fromPanel) {
        if (fromPanel != null) {
            remove(fromPanel);
        }

        RegisterPanel registerPanel = new RegisterPanel();
        add(registerPanel);
        setSize(registerPanel.getSize());
        setVisible(true);
    }

    public void setPasswordRecoveryPanelActive(JPanel fromPanel) {
        if (fromPanel != null) {
            remove(fromPanel);
        }

        PasswordRecoveryPanel passwordRecoveryPanel = new PasswordRecoveryPanel();
        add(passwordRecoveryPanel);
        setSize(passwordRecoveryPanel.getSize());
        setVisible(true);
    }

    public void setGeneratedPasswordPanelActive(JPanel fromPanel, char[] password) {
        if (fromPanel != null) {
            remove(fromPanel);
            GeneratedPasswordPanel generatedPasswordPanel = new GeneratedPasswordPanel(password, fromPanel);
            add(generatedPasswordPanel);
            setSize(generatedPasswordPanel.getSize());
            setVisible(true);
        }
    }

    public void setNewMessagePanelActive(JPanel fromPanel) {
        if (EmailApplication.loggedIn) {
            if (fromPanel != null) {
                remove(fromPanel);
            }

            NewMessagePanel newMessagePanel = new NewMessagePanel();
            add(newMessagePanel);
            setSize(newMessagePanel.getSize());
            setVisible(true);
        }
    }

    public void setMessagesPanelActive(JPanel fromPanel, MessageType messageType, int page) {
        if (EmailApplication.loggedIn) {
            if (fromPanel != null) {
                remove(fromPanel);
            }

            MessagesPanel messagesPanel = new MessagesPanel(messageType, page);
            add(messagesPanel);
            setSize(messagesPanel.getSize());
            setVisible(true);
        }
    }

    public void setViewedMessagePanelActive(JPanel fromPanel, Message message, int page) {
        if (EmailApplication.loggedIn) {
            if (fromPanel != null) {
                remove(fromPanel);
            }

            ViewedMessagePanel viewedMessagePanel = new ViewedMessagePanel(message, page);
            add(viewedMessagePanel);
            setSize(viewedMessagePanel.getSize());
            setVisible(true);
        }
    }

    public void setAccountDetailsPanelActive(JPanel fromPanel) {
        if (EmailApplication.loggedIn) {
            if (fromPanel != null) {
                remove(fromPanel);
            }

            AccountDetailsPanel accountDetailsPanel = new AccountDetailsPanel();
            add(accountDetailsPanel);
            setSize(accountDetailsPanel.getSize());
            setVisible(true);
        }
    }

    public void setSecurityPanelActive(JPanel fromPanel) {
        if (EmailApplication.loggedIn) {
            if (fromPanel != null) {
                remove(fromPanel);
            }

            SecurityPanel securityPanel = new SecurityPanel();
            add(securityPanel);
            setSize(securityPanel.getSize());
            setVisible(true);
        }
    }

    public void setDeleteAccountPanelActive(JPanel fromPanel) {
        if (EmailApplication.loggedIn) {
            if (fromPanel != null) {
                remove(fromPanel);
            }

            DeleteAccountPanel deleteAccountPanel = new DeleteAccountPanel();
            add(deleteAccountPanel);
            setSize(deleteAccountPanel.getSize());
            setVisible(true);
        }
    }

    public void setChangePasswordPanelActive(JPanel fromPanel) {
        if (EmailApplication.loggedIn) {
            if (fromPanel != null) {
                remove(fromPanel);
            }

            ChangePasswordPanel changePasswordPanel = new ChangePasswordPanel();
            add(changePasswordPanel);
            setSize(changePasswordPanel.getSize());
            setVisible(true);
        }
    }

    public void setChangeSecurityQuestionPanelActive(JPanel fromPanel) {
        if (EmailApplication.loggedIn) {
            if (fromPanel != null) {
                remove(fromPanel);
            }

            ChangeSecurityQuestionPanel changeSecurityQuestionPanel = new ChangeSecurityQuestionPanel();
            add(changeSecurityQuestionPanel);
            setSize(changeSecurityQuestionPanel.getSize());
            setVisible(true);
        }
    }

    public static char[] generateRandomPassword(int length) {
        Random random = new Random();
        char[] password = new char[length];

        for (int i = 0; i < length; i++) {
            password[i] = MainWindow.PASSWORD_CHAR_POOL.charAt(random.nextInt(MainWindow.PASSWORD_CHAR_POOL.length()));
        }

        return password;
    }

    public static LocalDate convertDateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date convertLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}