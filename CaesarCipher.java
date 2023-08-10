import java.util.*;

public class CaesarCipher {

    static Scanner sc = new Scanner(System.in);

    private static void encrypt(String plaintext, int key) {
        String ciphertext = "";
        for (int i = 0; i < plaintext.length(); i++) {
            int ch = plaintext.charAt(i);
            if (ch > 96)
                ciphertext += (char) (((ch - 97 + key) % 26) + 97);
            else
                ciphertext += (char) (((ch - 65 + key) % 26) + 65);
        }
        System.out.println("\nCiphertext: " + ciphertext);
    }

    private static void decrypt(String ciphertext, int key) {
        String plaintext = "";
        for (int i = 0; i < ciphertext.length(); i++) {
            int ch = ciphertext.charAt(i);
            if (ch > 96)
                plaintext += (char) (Math.floorMod(ch - 97 - key, 26) + 97);
            else
                plaintext += (char) (Math.floorMod(ch - 65 - key, 26) + 65);
        }
        System.out.println("\nPlaintext: " + plaintext);
    }

    public static void main(String args[]) {
        String plaintext, ciphertext;
        int key;
        System.out.print("\n\tCAESAR CIPHER\n");

        System.out.print("ENCRYPTION\nEnter the plaintext: ");
        plaintext = sc.nextLine();
        System.out.print("Enter the key: ");
        key = sc.nextInt();
        encrypt(plaintext, key);

        System.out.print("\n\nDECRYPTION\nEnter the ciphertext: ");
        ciphertext = sc.next();
        System.out.print("Enter the key: ");
        key = sc.nextInt();
        decrypt(ciphertext, key);
    }
}
