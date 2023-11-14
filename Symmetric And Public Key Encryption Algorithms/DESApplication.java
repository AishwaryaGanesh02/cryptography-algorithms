import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DESApplication {
    private static final String SECRET_KEY = "$e(retK3?", ALGORITHM = "DES";
    private static final String USER_NAME = "admin", PASSWORD = "pA$$W0RD";
    private static JFrame loginFrame, homeFrame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    public static void addComponent(JPanel p, JComponent comp, int x, int y, int w, int h) {
        GridBagConstraints constr = new GridBagConstraints();
        constr.gridx = x;
        constr.gridy = y;
        constr.gridheight = h;
        constr.gridwidth = w;
        constr.insets = new Insets(2, 2, 2, 2);
        constr.anchor = GridBagConstraints.CENTER;
        constr.fill = GridBagConstraints.BOTH;
        p.add(comp, constr);
    }

    private static void createAndShowGUI() {
        loginFrame = new JFrame("DES Application Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new GridBagLayout());
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton encryptButton = new JButton("Encrypt");
        JButton decryptButton = new JButton("Decrypt");
        JButton loginButton = new JButton("Login");
        JTextArea displayResult = new JTextArea(5, 20);
        displayResult.setEditable(false);
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayResult.setText("Encrypted Password: " + encrypt(passwordField));
            }
        });
        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String decryptedPassword = decryption(encrypt(passwordField));
                displayResult.setText("Decrypted Password: " + decryptedPassword);
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = usernameField.getText();
                String actualPassEncrypted = encryption(PASSWORD);
                String encryptedPassword = encrypt(passwordField);
                if (user.equals(USER_NAME) && actualPassEncrypted.equals(encryptedPassword)) {
                    showhomeFrame();
                    loginFrame.setVisible(false);
                } else
                    JOptionPane.showMessageDialog(loginFrame, "Login failed. Incorrect username or password.");
            }
        });
        addComponent(panel, usernameLabel, 0, 0, 1, 1);
        addComponent(panel, usernameField, 1, 0, 1, 1);
        addComponent(panel, passwordLabel, 0, 1, 1, 1);
        addComponent(panel, passwordField, 1, 1, 1, 1);
        addComponent(panel, loginButton, 0, 2, 2, 1);
        addComponent(panel, encryptButton, 0, 3, 1, 1);
        addComponent(panel, decryptButton, 1, 3, 1, 1);
        Border padding = BorderFactory.createEmptyBorder(25, 25, 25, 25);
        panel.setBorder(padding);
        loginFrame.add(panel, BorderLayout.CENTER);
        loginFrame.add(displayResult, BorderLayout.SOUTH);
        loginFrame.pack();
        loginFrame.setVisible(true);
    }

    private static String encrypt(JPasswordField passwordField) {
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);
        return (encryption(password));
    }

    private static String encryption(String plaintext) {
        try {
            DESKeySpec keySpec = new DESKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            SecretKey key = keyFactory.generateSecret(keySpec);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String decryption(String encryptedText) {
        try {
            DESKeySpec keySpec = new DESKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            SecretKey key = keyFactory.generateSecret(keySpec);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void showhomeFrame() {
        homeFrame = new JFrame("Home");
        homeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JLabel successLabel = new JLabel("Logged in successfully!!");
        JLabel userLabel = new JLabel("Welcome " + USER_NAME);
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homeFrame.setVisible(false);
                loginFrame.setVisible(true);
            }
        });
        homeFrame.add(successLabel);
        homeFrame.add(userLabel);
        homeFrame.add(logoutButton);
        homeFrame.setLayout(new BoxLayout(homeFrame.getContentPane(), BoxLayout.Y_AXIS));
        homeFrame.pack();
        homeFrame.setVisible(true);
    }
}
