import java.util.*;

public class HillCipher {

    static Scanner sc = new Scanner(System.in);
    static char filler;

    public static void main(String args[]) {
        String plaintext;
        int[][] keymatrix = new int[3][3];
        System.out.print("\n\tHILL CIPHER\n");

        System.out.print("\n\tENCRYPTION\n\nEnter the plaintext: ");
        plaintext = sc.nextLine().toUpperCase();
        filler = filler_char(plaintext);
        keymatrix = readKeymatrix();

        encrypt(plaintext, keymatrix);

        System.out.print("\n\n\n\tDECRYPTION\n\nEnter the ciphertext: ");
        String ciphertext = sc.next().toUpperCase();
        keymatrix = readKeymatrix();

        decrypt(ciphertext, keymatrix);

    }

    public static void encrypt(String plaintext, int keymatrix[][]) {
        int k = 0, rows = (int) Math.ceil(plaintext.length() / 3.0);
        int plaintextmatrix[][] = new int[rows][3], ciphermatrix[][] = new int[rows][3];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < 3; j++) {
                if (k <= rows * 3 - 1 && k < plaintext.length())
                    plaintextmatrix[i][j] = plaintext.charAt(k++) - 65;
                else
                    plaintextmatrix[i][j] = filler - 65;
            }
        }
        System.out.print("\nCiphertext: ");
        ciphermatrix = multiplyMatrix(plaintextmatrix, keymatrix, rows);
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < 3; j++)
                System.out.print((char) (Math.floorMod(ciphermatrix[i][j], 26) + 65));
    }

    public static void decrypt(String ciphertext, int keymatrix[][]) {
        int k = 0, rows = (int) Math.ceil(ciphertext.length() / 3.0);
        int plaintextmatrix[][] = new int[rows][3], ciphermatrix[][] = new int[rows][3];
        System.out.print("\nInverse of key matrix: \n\t");
        int[][] inversematrix = new int[3][3];
        inverse(keymatrix, inversematrix);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < 3; j++)
                ciphermatrix[i][j] = ciphertext.charAt(k++) - 65;
        }
        System.out.print("\nDecrypted plaintext: ");
        plaintextmatrix = multiplyMatrix(ciphermatrix, inversematrix, rows);
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < 3; j++) {
                int c = Math.floorMod(plaintextmatrix[i][j], 26) + 65;
                if ((char) c != filler)
                    System.out.print((char) c);
            }
    }

    public static int[][] readKeymatrix() {
        int[][] keymatrix = new int[3][3];
        System.out.println("Enter the key matrix: ");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                keymatrix[i][j] = sc.nextInt();
        }
        System.out.print("\nKey matrix: \n\t");
        displayMatrix(keymatrix, 3, 3);
        return keymatrix;
    }

    public static void displayMatrix(int mat[][], int n, int m) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++)
                System.out.print(mat[i][j] + " ");
            System.out.print("\n\t");
        }
    }

    public static char filler_char(String p) {
        int a = 0;
        while (p.indexOf(88 - a) != -1)
            a++;
        return (char) (88 - a);
    }

    public static int[][] multiplyMatrix(int a[][], int b[][], int n) {
        int c[][] = new int[n][3];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++)
                    c[i][j] += a[i][k] * b[k][j];
            }
        }
        return c;
    }

    static boolean inverse(int mat[][], int inverse[][]) {
        int det = determinant(mat, 3);
        if (det == 0)
            return false;
        int[][] adj = adjoint(mat);
        int invDet = detInverse(det);
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                inverse[i][j] = Math.floorMod(adj[i][j] * invDet, 26);
        displayMatrix(inverse, 3, 3);
        return true;
    }

    public static int determinant(int mat[][], int n) {
        int det = 0, cofact[][] = new int[3][3], sign = 1;
        if (n == 1)
            return mat[0][0];
        for (int i = 0; i < n; i++) {
            getCofactor(mat, cofact, 0, i, n);
            det += sign * mat[0][i] * determinant(cofact, n - 1);
            sign = -sign;
        }
        return det;
    }

    public static int[][] adjoint(int mat[][]) {
        int sign = 1, cofact[][] = new int[3][3], adj[][] = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                getCofactor(mat, cofact, i, j, 3);
                sign = ((i + j) % 2 == 0) ? 1 : -1;
                adj[j][i] = (sign) * (determinant(cofact, 3 - 1));
            }
        }
        return adj;
    }

    public static int detInverse(int det) {
        det = Math.floorMod(det, 26);
        for (int i = 0; i < 26; i++)
            if (Math.floorMod(det * i, 26) == 1)
                return i;
        System.out.print("Inverse does not exist for the given keymatrix");
        return 0;
    }

    public static void getCofactor(int mat[][], int temp[][], int p, int q, int n) {
        int i = 0, j = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (row != p && col != q) {
                    temp[i][j++] = mat[row][col];
                    if (j == n - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }

}
