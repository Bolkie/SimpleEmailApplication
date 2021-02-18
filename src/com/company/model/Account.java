package com.company.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

public class Account {
    private int id;
    private static int nextId = 0;
    private String username;
    private String domain;
    private String fullUsername;
    private char[] password;
    private String ownerFirstName;
    private String ownerLastName;
    private String ownerSex;
    private LocalDate ownerBirthday;
    private String securityQuestion;
    private String securityQuestionAnswer;
    private LocalDate creationDate;
    private Inbox inbox;

    public Account(String username, String domain, char[] password, String ownerFirstName, String ownerLastName, String ownerSex, LocalDate ownerBirthday, String securityQuestion, String securityQuestionAnswer) {
        this.id = nextId;
        this.username = username;
        this.domain = domain;
        this.fullUsername = this.username + "@" + this.domain;
        this.password = password;
        this.ownerFirstName = ownerFirstName;
        this.ownerLastName = ownerLastName;
        this.ownerSex = ownerSex;
        this.ownerBirthday = ownerBirthday;
        this.securityQuestion = securityQuestion;
        this.securityQuestionAnswer = securityQuestionAnswer;
        this.creationDate = LocalDate.now();
        this.inbox = new Inbox();
        nextId++;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getDomain() {
        return domain;
    }

    public String getFullUsername() {
        return fullUsername;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public void setOwnerFirstName(String ownerFirstName) {
        this.ownerFirstName = ownerFirstName;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }

    public String getOwnerSex() {
        return ownerSex;
    }

    public void setOwnerSex(String ownerSex) {
        this.ownerSex = ownerSex;
    }

    public LocalDate getOwnerBirthday() {
        return ownerBirthday;
    }

    public void setOwnerBirthday(LocalDate ownerBirthday) {
        this.ownerBirthday = ownerBirthday;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityQuestionAnswer() {
        return securityQuestionAnswer;
    }

    public void setSecurityQuestionAnswer(String securityQuestionAnswer) {
        this.securityQuestionAnswer = securityQuestionAnswer;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Inbox getInbox() {
        return inbox;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && username.equals(account.username) && domain.equals(account.domain) && fullUsername.equals(account.fullUsername) && Arrays.equals(password, account.password) && ownerFirstName.equals(account.ownerFirstName) && ownerLastName.equals(account.ownerLastName) && ownerSex.equals(account.ownerSex) && ownerBirthday.equals(account.ownerBirthday) && securityQuestion.equals(account.securityQuestion) && securityQuestionAnswer.equals(account.securityQuestionAnswer) && creationDate.equals(account.creationDate);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, username, domain, fullUsername, ownerFirstName, ownerLastName, ownerSex, ownerBirthday, securityQuestion, securityQuestionAnswer, creationDate);
        result = 31 * result + Arrays.hashCode(password);
        return result;
    }
}