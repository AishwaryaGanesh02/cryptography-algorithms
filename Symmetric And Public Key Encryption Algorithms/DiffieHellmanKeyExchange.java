import java.math.*;
import java.util.*;

public class DiffieHellmanKeyExchange {

    static Scanner sc = new Scanner(System.in);
    static BigInteger q, alpha, ya, yb;

    public static void main(String args[]) {
        setGlobalParameters();
        UserA userA = new UserA(q, alpha);
        UserB userB = new UserB(q, alpha);
        ya = userA.userA_KeyGen();
        yb = userB.userB_KeyGen();

        System.out.println("\n    USER\tA\tB\nPRIVATE KEY\t" + ya + "\t" + yb);

        System.out.println("Shared key computed by UserA: " + userA.secretKeyGen(yb));
        System.out.println("Shared key computed by UserB: " + userB.secretKeyGen(ya));
    }

    private static void setGlobalParameters() {
        System.out.print("Enter a large prime number: ");
        q = sc.nextBigInteger();

        while (!q.isProbablePrime(1)) {
            System.out.print("\tInvalid input.\nEnter a large prime number: ");
            q = sc.nextBigInteger();
        }

        System.out.print("Enter a primitive root of " + q + ", α: ");
        alpha = sc.nextBigInteger();
        while (!isPrimitiveRoot(alpha, q)) {
            System.out.print("\tInvalid input.\nEnter a primitive root of " + q + ", α: ");
            alpha = sc.nextBigInteger();
        }
    }

    public static boolean isPrimitiveRoot(BigInteger a, BigInteger p) {
        BigInteger one = BigInteger.valueOf(1);
        for (int i = 1; i < p.intValue() - 1; i++) {
            if ((a.pow(i)).mod(p).equals(one))
                return false;
        }
        return true;
    }
}

class UserA {
    BigInteger q, alpha, xa;
    Scanner sc = new Scanner(System.in);

    UserA(BigInteger p, BigInteger a) {
        q = p;
        alpha = a;
    }

    public BigInteger userA_KeyGen() {
        System.out.print("Enter the private key of A (<" + q + "): ");
        xa = sc.nextBigInteger();
        while (xa.compareTo(q) == 1) {
            System.out.print("\tInvalid input.\nEnter the private key of A (<" + q + "): ");
            xa = sc.nextBigInteger();
        }
        return alpha.modPow(xa, q);
    }

    public BigInteger secretKeyGen(BigInteger a) {
        return a.modPow(xa, q);
    }
}

class UserB {
    BigInteger q, alpha, xb;
    Scanner sc = new Scanner(System.in);

    UserB(BigInteger p, BigInteger a) {
        q = p;
        alpha = a;
    }

    public BigInteger userB_KeyGen() {
        System.out.print("Enter the private key of B (<" + q + "): ");
        xb = sc.nextBigInteger();
        while (xb.compareTo(q) == 1) {
            System.out.print("\tInvalid input.\nEnter the private key of B (<" + q + "): ");
            xb = sc.nextBigInteger();
        }
        return alpha.modPow(xb, q);
    }

    public BigInteger secretKeyGen(BigInteger a) {
        return a.modPow(xb, q);
    }
}