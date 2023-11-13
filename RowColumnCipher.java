import java.util.*;

public class RowColumnCipher {

    static Scanner sc = new Scanner(System.in);
    static char filler;

    public static void main(String args[]) {
        String plaintext, ciphertext = "", key;
        System.out.print("\n\tROW COLUMN CIPHER\n");

        System.out.print("\tENCRYPTION\n\nEnter the plaintext: ");
        plaintext = sc.nextLine().toUpperCase();
        System.out.print("Enter the key sequence: ");
        key = sc.next();

        System.out.println("\nCiphertext: " + encrypt(plaintext, key));

        System.out.print("\n\n\n\tDECRYPTION\n\nEnter the ciphertext: ");
        ciphertext = sc.next().toUpperCase();
        System.out.print("Enter the key sequence: ");
        key = sc.next();

        System.out.println("\nDecrypted plaintext: " + decrypt(ciphertext, key));
    }

    private static String encrypt(String plaintext, String key) {
        String ciphertext = "";
        int k = 0;
        filler = filler_char(plaintext);
        int rows = (int) Math.ceil(plaintext.length() / (float) key.length());
        char plaintextmatrix[][] = new char[rows][key.length()];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < key.length(); j++) {
                if (k < plaintext.length())
                    plaintextmatrix[i][j] = plaintext.charAt(k++);
                else
                    plaintextmatrix[i][j] = filler;
            }
        }
        System.out.print("\nPlaintext in matrix format: \n\t");
        displayMatrix(plaintextmatrix, rows, key.length());
        for (int i = 0; i < key.length(); i++) {
            int c = key.indexOf(Integer.toString(i + 1));

            for (int j = 0; j < rows; j++)
                ciphertext += plaintextmatrix[j][c];
        }
        return ciphertext;
    }

    private static String decrypt(String ciphertext, String key) {
        String plaintext = "";
        int rows = (int) Math.ceil(ciphertext.length() / (float) key.length()), k = 0;

        char ciphermatrix[][] = new char[rows][key.length()];
        for (int i = 0; i < rows; i++) {
            k = 0;
            for (int j = i; j < ciphertext.length(); j += rows) {
                ciphermatrix[i][k++] = ciphertext.charAt(j);
            }
        }
        System.out.print("\nCiphertext in matrix format: \n\t");

        displayMatrix(ciphermatrix, rows, key.length());
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < key.length(); j++) {
                int c = key.charAt(j) - '0' - 1;
                plaintext += ciphermatrix[i][c];
            }
        }
        System.out.println("\nPlaintext with filler: " + plaintext);
        String modifiedText = "";
        for (int i = 0; i < plaintext.length(); i++) {
            if (plaintext.charAt(i) == filler)
                continue;
            modifiedText += plaintext.charAt(i);
        }
        return modifiedText;
    }

    public static char filler_char(String p) {
        int a = 0;
        while (p.indexOf(88 - a) != -1)
            a++;
        return (char) (88 - a);
    }

    public static void displayMatrix(char mat[][], int n, int m) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++)
                System.out.print(mat[i][j] + " ");
            System.out.print("\n\t");
        }
    }
}
