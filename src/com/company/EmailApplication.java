package com.company;

import com.company.dao.AccountDao;
import com.company.dao.AccountDaoImpl;
import com.company.gui.window.MainWindow;

import java.awt.*;

public class EmailApplication {
    public static AccountDao accountDao = new AccountDaoImpl();
    public static boolean loggedIn;
    public static Integer loggedInAccountId;

    public static void main(String[] args) {
        loggedIn = false;
        loggedInAccountId = null;

        EventQueue.invokeLater(MainWindow::getInstance);
    }
}