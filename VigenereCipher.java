import java.util.*;

public class VigenereCipher {

    static Scanner sc = new Scanner(System.in);

    public static void main(String args[]) {
        String plaintext, ciphertext = "", key;
        System.out.print("\n\tVIGENERE CIPHER\n");

        System.out.print("\tENCRYPTION\n\nEnter the plaintext: ");
        plaintext = sc.nextLine().toUpperCase();
        System.out.print("Enter the keyword: ");
        key = sc.next().toUpperCase();

        System.out.println("\nCiphertext: " + encrypt(plaintext, modifiedKey(key, plaintext)));

        System.out.print("\n\n\n\tDECRYPTION\n\nEnter the ciphertext: ");
        ciphertext = sc.next().toUpperCase();
        System.out.print("Enter the keyword: ");
        key = sc.next().toUpperCase();

        System.out.println("\nDecrypted plaintext: " + decrypt(ciphertext, modifiedKey(key, ciphertext)));
    }

    private static String encrypt(String plaintext, String key) {
        String ciphertext = "";
        for (int i = 0; i < plaintext.length(); i++) {
            int a = Math.floorMod(plaintext.charAt(i) - 65, 26);
            int b = Math.floorMod(key.charAt(i) - 65, 26);
            ciphertext += (char) (Math.floorMod(a + b, 26) + 65);
        }
        return ciphertext;
    }

    private static String decrypt(String ciphertext, String key) {
        String plaintext = "";
        for (int i = 0; i < ciphertext.length(); i++) {
            int a = Math.floorMod(ciphertext.charAt(i) - 65, 26);
            int b = Math.floorMod(key.charAt(i) - 65, 26);
            plaintext += (char) (Math.floorMod(a - b, 26) + 65);
        }
        return plaintext;
    }

    private static String modifiedKey(String key, String msg) {
        int k = 0;
        while (key.length() < msg.length())
            key += key.charAt(k++);
        System.out.println("\nModified Keyword: " + key);
        return key;
    }
}
