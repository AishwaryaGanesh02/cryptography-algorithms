import java.math.*;
import java.util.*;

public class RSAAlgorithm {

    static Scanner sc = new Scanner(System.in);
    static BigInteger p, q, n, t, one, e;

    public static void main(String args[]) {
        System.out.print("\n\tRSA Algorithm\n");
        setGlobalParameters();
        BigInteger d = keyGeneration();
        System.out.print("\n\tENCRYPTION\n\nEnter the message: ");
        BigInteger msg = sc.nextBigInteger();
        System.out.println("Ciphertext: " + encrypt(msg, e));

        System.out.print("\n\n\tDECRYPTION\n\nEnter the ciphertext: ");
        BigInteger ciphertext = sc.nextBigInteger();
        System.out.println("Decrypted plaintext: " + decrypt(ciphertext, d));
    }

    private static BigInteger encrypt(BigInteger msg, BigInteger key) {
        while (msg.compareTo(n) != -1) {
            System.out.print("\tInvalid input. Message should be < n\nEnter the message: ");
            msg = sc.nextBigInteger();
        }
        return msg.modPow(key, n);
    }

    private static BigInteger decrypt(BigInteger cipher, BigInteger d) {
        return cipher.modPow(d, n);
    }

    private static void setGlobalParameters() {
        one = BigInteger.valueOf(1);
        System.out.print("Enter two large prime number: ");
        p = sc.nextBigInteger();
        q = sc.nextBigInteger();
        while (!p.isProbablePrime(1) || !q.isProbablePrime(1)) {
            System.out.print("\tInvalid input.\nEnter two large prime number: ");
            p = sc.nextBigInteger();
            q = sc.nextBigInteger();
        }
        n = p.multiply(q);
        t = (p.subtract(one)).multiply(q.subtract(one));
        System.out.println("n : " + n + "\nEuler's totient function: " + t);
    }

    public static BigInteger keyGeneration() {
        System.out.print("Enter encryption key, e (<" + t + "): ");
        e = sc.nextBigInteger();
        while (!((e.gcd(t)).equals(one) && t.compareTo(e) == 1)) {
            System.out.print("\tInvalid input.\nEnter encryption key, e (<" + t + "): ");
            e = sc.nextBigInteger();
        }
        BigInteger d = e.modInverse(t);
        System.out.println(" Public key:  (PU) = {" + e + ", " + n + "}");
        System.out.println("Private key:  (PR) = {" + d + ", " + n + "}");
        return d;
    }
}
