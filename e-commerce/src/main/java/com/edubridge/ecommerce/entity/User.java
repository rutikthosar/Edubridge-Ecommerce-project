package com.edubridge.ecommerce.entity;

import javax.persistence.*;
import java.util.Set;
import javax.swing.JOptionPane;

@Entity
public class User {

    @Id
    private String userName;
    private String userFirstName;
    private String userLastName;
    private String userPassword;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLE",
            joinColumns = {
                    @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID")
            }
    )
    private Set<Role> role;

    public String getUserName() {
        if (isValidUserName(userName)) {
            return userName;
        } else {
            throw new IllegalStateException("Invalid username");
        }
    }

    private boolean isValidUserName(String userName) {
        if (userName == null || userName.isEmpty()) {
            return false; // Username should not be null or empty
        }

        // Regular expression pattern for alphanumeric characters
        String pattern = "^[a-zA-Z0-9]+$";

        // Minimum length required for the username
        int minLength = 6;

        return userName.matches(pattern) && userName.length() >= minLength;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserPassword() {
        if (!isValidUserPassword(userPassword)) {
            showAlert("Invalid Password", "The password must include at least one uppercase letter, one lowercase letter, one digit, and one special character.");
        }
        return userPassword;

    }


    private static void showAlert(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

    private boolean isValidUserPassword(String userPassword) {
        if (userPassword == null || userPassword.isEmpty()) {
            return false; // Password should not be null or empty
        }

        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (char c : userPassword.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else {
                // Check for special characters
                if (!Character.isLetterOrDigit(c)) {
                    hasSpecialChar = true;
                }
            }

            if (hasUppercase && hasLowercase && hasDigit && hasSpecialChar) {
                return true; // Password has all required elements
            }
        }

        return false; // Password does not meet the criteria
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }
}
