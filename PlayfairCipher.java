import java.io.*;
import java.util.*;

public class PlayfairCipher {

    static Scanner sc = new Scanner(System.in);
    static char filler;

    public static void main(String args[]) {
        String key, plaintext, ciphertext;
        System.out.print("\n\tPLAYFAIR CIPHER\n");

        System.out.print("ENCRYPTION\nEnter the plaintext: ");
        plaintext = sc.nextLine();
        System.out.print("Enter the keyword: ");
        key = sc.nextLine();

        encrypt(plaintext, key);

        System.out.print("\n\nDECRYPTION\nEnter the ciphertext: ");
        ciphertext = sc.nextLine();
        System.out.print("Enter the keyword: ");
        key = sc.nextLine();

        decrypt(ciphertext, key);
    }

    private static void encrypt(String plaintext, String key) {
        String ciphertext = "", modifiedText = "";
        char[][] keymatrix = new char[10][20];
        key = duplicateRemove(key);
        keymatrix = generate_keymatrix(key);
        filler = filler_char(plaintext);
        int l = 0;
        while (l < plaintext.length()) {
            if (plaintext.charAt(l) == 'j')
                modifiedText += 'i';
            else
                modifiedText += plaintext.charAt(l);

            if (l + 1 < plaintext.length() &&
                    plaintext.charAt(l) == plaintext.charAt(l + 1)) {
                modifiedText += filler;
                l--;
            } else {
                if (l + 1 >= plaintext.length())
                    modifiedText += filler;
                else {
                    if (plaintext.charAt(l + 1) == 'j')
                        modifiedText += 'i';
                    else
                        modifiedText += plaintext.charAt(l + 1);
                }
            }
            l += 2;
        }

        System.out.println("\nModified plaintext: " + modifiedText);
        for (int i = 0; i < modifiedText.length(); i += 2) {
            char k1 = modifiedText.charAt(i);
            char k2 = modifiedText.charAt(i + 1);
            int p1[], p2[];
            p1 = cal_position(keymatrix, k1);
            p2 = cal_position(keymatrix, k2);
            if (p1[0] == p2[0]) { // same row
                ciphertext += keymatrix[p1[0]][(p1[1] + 1) == 5 ? 0 : p1[1] + 1];
                ciphertext += keymatrix[p2[0]][(p2[1] + 1) == 5 ? 0 : p2[1] + 1];
            } else if (p1[1] == p2[1]) { // same col
                ciphertext += keymatrix[(p1[0] + 1) == 5 ? 0 : p1[0] + 1][p1[1]];
                ciphertext += keymatrix[(p2[0] + 1) == 5 ? 0 : p2[0] + 1][p2[1]];
            } else {
                ciphertext += keymatrix[p1[0]][p2[1]];
                ciphertext += keymatrix[p2[0]][p1[1]];
            }
        }
        System.out.println("\nCiphertext: " + ciphertext);
    }

    private static void decrypt(String ciphertext, String key) {
        String plaintext = "", modifiedText = "";
        char[][] keymatrix = new char[10][20];
        key = duplicateRemove(key);
        keymatrix = generate_keymatrix(key);
        for (int i = 0; i < ciphertext.length(); i += 2) {
            char k1 = ciphertext.charAt(i);
            char k2 = ciphertext.charAt(i + 1);
            int p1[], p2[];
            p1 = cal_position(keymatrix, k1);
            p2 = cal_position(keymatrix, k2);
            if (p1[0] == p2[0]) { // same row
                plaintext += keymatrix[p1[0]][(p1[1] - 1) == -1 ? 4 : p1[1] - 1];
                plaintext += keymatrix[p2[0]][(p2[1] - 1) == -1 ? 4 : p2[1] - 1];
            } else if (p1[1] == p2[1]) { // same col
                plaintext += keymatrix[(p1[0] - 1) == -1 ? 4 : p1[0] - 1][p1[1]];
                plaintext += keymatrix[(p2[0] - 1) == -1 ? 4 : p2[0] - 1][p2[1]];
            } else {
                plaintext += keymatrix[p1[0]][p2[1]];
                plaintext += keymatrix[p2[0]][p1[1]];
            }
        }
        for (int i = 0; i < plaintext.length(); i++) {
            if (plaintext.charAt(i) == filler)
                continue;
            if (plaintext.charAt(i) == 'i')
                modifiedText += "(i/j)";
            else
                modifiedText += plaintext.charAt(i);
        }
        System.out.println("\nDecrypted text: " + modifiedText);
    }

    public static String duplicateRemove(String str) {
        String nodup = "" + str.charAt(0);
        for (int i = 1; i < str.length(); i++) {
            if (!nodup.contains(String.valueOf(str.charAt(i))))
                nodup += str.charAt(i);
        }
        return nodup;
    }

    public static int[] cal_position(char[][] mat, char k) {
        int[] pos = new int[2];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (mat[i][j] == k) {
                    pos[0] = i;
                    pos[1] = j;
                    return pos;
                }
            }
        }
        return pos;
    }

    public static char[][] generate_keymatrix(String key) {
        char[][] keymatrix = new char[10][20];
        int k = 0, a = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (key.length() > k)
                    keymatrix[i][j] = key.charAt(k++);
                else {
                    while (key.indexOf(97 + a) != -1)
                        a++;
                    if (a == 9)
                        a++;
                    keymatrix[i][j] = (char) (97 + (a++));
                }
            }
        }
        System.out.println("\nKeyword matrix: ");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++)
                System.out.print(keymatrix[i][j] + " ");
            System.out.println();
        }
        return keymatrix;
    }

    public static char filler_char(String p) {
        int a = 0;
        while (p.indexOf(120 - a) != -1)
            a++;
        return (char) (120 - a);
    }
}
