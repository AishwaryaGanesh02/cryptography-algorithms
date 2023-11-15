import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AESApplication {
    private static final String ALGORITHM = "AES";
    private static SecretKey secretKey;
    private static int MINIMUM_BALANCE = 5000;

    public static void main(String[] args) {
        generateSecretKey();
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void generateSecretKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            keyGenerator.init(128); // 128-bit key
            secretKey = keyGenerator.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Bank Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        JLabel nameLabel = new JLabel("Customer Name:");
        JTextField nameField = new JTextField();
        JLabel accountLabel = new JLabel("Account Number:");
        JLabel balanceLabel = new JLabel("Balance:");
        JTextField accountField = new JTextField();
        JTextField balanceField = new JTextField();
        JButton encryptButton = new JButton("Encrypt");
        JButton decryptButton = new JButton("Decrypt");
        JTextArea resultArea = new JTextArea(6, 10);
        resultArea.setEditable(false);
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String balance = encrypt(balanceField.getText());
                resultArea.setText("Encryption: \n    Balance: " + balance);
            }
        });
        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String encryptedText = resultArea.getText().replace("Encryption: \n    Balance: ", "");
                String decryptedText = decrypt(encryptedText);
                if (decryptedText != null) {
                    resultArea.setText("Decryption\n    Balance: " + decryptedText);
                    if (Integer.parseInt(decryptedText) < MINIMUM_BALANCE)
                        resultArea.append("\n    Balance less than minimum balance.");
                }
            }
        });
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(accountLabel);
        panel.add(accountField);
        panel.add(balanceLabel);
        panel.add(balanceField);
        panel.add(encryptButton);
        panel.add(decryptButton);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(resultArea, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }

    private static String encrypt(String plaintext) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String decrypt(String encryptedText) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
