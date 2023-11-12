import java.util.*;

public class RailFenceCipher {

    static Scanner sc = new Scanner(System.in);

    public static void main(String args[]) {
        String plaintext, ciphertext = "";
        int key;
        System.out.print("\n\tRAIL FENCE CIPHER\n");

        System.out.print("\tENCRYPTION\n\nEnter the plaintext: ");
        plaintext = sc.nextLine().toUpperCase();
        System.out.print("Enter the rail depth(key>1): ");
        key = sc.nextInt();

        System.out.println("\nCiphertext: " + encrypt(plaintext, key));

        System.out.print("\n\n\n\tDECRYPTION\n\nEnter the ciphertext: ");
        ciphertext = sc.next().toUpperCase();
        System.out.print("Enter the rail depth(key>1): ");
        key = sc.nextInt();

        System.out.println("\nDecrypted plaintext: " + decrypt(ciphertext, key));
    }

    private static String encrypt(String plaintext, int key) {
        String ciphertext = "";
        int row = 0, column = 0, k = 0;
        int totalCoulmns = (int) Math.ceil(plaintext.length() / (float) (key - 1));
        char plaintextmatrix[][] = new char[key][totalCoulmns];
        boolean dir = false;
        for (int i = 0; i < plaintext.length(); i++) {
            plaintextmatrix[row][column] = plaintext.charAt(i);
            if (!dir)
                row++;
            else
                row--;
            if (row == 0 || row == key - 1) {
                dir = !dir;
                column++;
            }
        }
        for (int i = 0; i < key; i++)
            for (int j = 0; j < totalCoulmns; j++)
                if (Character.isAlphabetic(plaintextmatrix[i][j]))
                    ciphertext += plaintextmatrix[i][j];
        return ciphertext;
    }

    private static String decrypt(String ciphertext, int key) {
        int totalCoulmns = (int) Math.ceil(ciphertext.length() / (float) (key - 1)), k = 0;
        String plaintext = "";
        int rem = ciphertext.length() - totalCoulmns, filled = rem / (totalCoulmns), small = key - filled - 2;
        char ciphermatrix[][] = new char[key][10];
        int n = 0;
        for (int i = 0; i < key; i++) {
            int m = 1;
            for (int j = 0; j < totalCoulmns; j++) {
                if (i == 0 && (j % 2 == 0))
                    ciphermatrix[i][j] = ciphertext.charAt(k++);
                else if (i == 0 && (j % 2 == 1))
                    ciphermatrix[i][j] = '*';
                else if (i == key - 1 && (j % 2 == 0))
                    ciphermatrix[i][j] = '*';
                else if (i == key - 1 && (j % 2 == 1))
                    ciphermatrix[i][j] = ciphertext.charAt(k++);
                else {
                    if (totalCoulmns % 2 == 0) {
                        if (n < small) {
                            if (m == totalCoulmns) {
                                n++;
                                ciphermatrix[i][j] = '*';
                                continue;
                            }
                            ciphermatrix[i][j] = ciphertext.charAt(k++);
                            m++;
                        } else
                            ciphermatrix[i][j] = ciphertext.charAt(k++);
                    } else {
                        if (n < filled) {
                            if (m == totalCoulmns)
                                n++;
                            ciphermatrix[i][j] = ciphertext.charAt(k++);
                            m++;
                        } else {
                            if (m == totalCoulmns) {
                                ciphermatrix[i][j] = '*';
                                continue;
                            }
                            ciphermatrix[i][j] = ciphertext.charAt(k++);
                            m++;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < totalCoulmns; i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < key; j++)
                    if (Character.isAlphabetic(ciphermatrix[j][i]))
                        plaintext += ciphermatrix[j][i];
            } else {
                for (int j = key - 1; j >= 0; j--)
                    if (Character.isAlphabetic(ciphermatrix[j][i]))
                        plaintext += ciphermatrix[j][i];
            }
        }
        return plaintext;

    }
}

// ----------ALTERNATE WAY----------
// import java.io.*;
// import java.util.*;
// public class RailFenceCipher{
// public static void main(String args[]){
// Scanner sc = new Scanner(System.in);
// String ct, pt;
// int r, f=0, j=0, k=0;
// System.out.print("\tENCRYPTION\nEnter the message: ");
// pt = sc.next();
// System.out.print("Enter the rail depth: ");
// r = sc.nextInt();
// char a[][] = new char[r][pt.length()];
// for(int i=0; i<pt.length(); i++){
// if(f==1)
// a[j++][k++] = pt.charAt(i);
// else
// a[j--][k++] = pt.charAt(i);
// if(j==r){
// j=r-2;
// f=0;
// }
// if(j==-1){
// j=1;
// f=1;
// }
// }
// System.out.print("Ciphertext: ");
// for(int i=0; i<r;i++){
// for(j=0;j<pt.length();j++){
// if(Character.isAlphabetic(a[i][j]))
// System.out.print(a[i][j]);
// }
// }

// System.out.print("\n\tDECRYPTION\nEnter the ciphertext: ");
// ct = sc.next();
// System.out.print("Enter the rail depth: ");
// r = sc.nextInt();
// char mat[][] = new char[r][ct.length()];
// int i=0;
// for(j=0; j<r-1; j++){
// for(k=j; k<ct.length(); k+=(r-j-2)*2+2){
// mat[j][k] = ct.charAt(i++);
// }
// }
// for(k=r-1; k<ct.length(); k+=(r-2)*2+2){
// mat[j][k] = ct.charAt(i++);
// }
// System.out.print("Message: ");
// for(i=0; i<pt.length();i++){
// for(j=0;j<r;j++){
// if(Character.isAlphabetic(mat[j][i]))
// System.out.print(mat[j][i]);
// }
// }
// }
// }
