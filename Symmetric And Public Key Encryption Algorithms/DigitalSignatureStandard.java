import java.util.*;

class DigitalSignatureStandard {
    static Scanner sc = new Scanner(System.in);

    static double p = 0, q, g, y;

    public static void main(String args[]) {
        double rv, sv, h_m;
        System.out.println("\tSIGNATURE GENERATION");
        System.out.print("Enter the hash of the message, H(M): ");
        h_m = sc.nextDouble();
        generateSignature(h_m);

        System.out.print("\n\tSIGNATURE VERIFICATION\n");
        System.out.print("Enter the signature\n\tr: ");
        rv = sc.nextDouble();
        System.out.print("\ts: ");
        sv = sc.nextDouble();
        verifySignature(rv, sv, h_m);

    }

    private static void generateSignature(double h_m) {
        double h = 0, x = 0, k = 0, r, s;
        while (!isPrime(p)) {
            System.out.print("Enter a prime number, p: ");
            p = sc.nextDouble();
        }
        q = largestPrimeDivisor(p - 1);
        while (h >= p - 1 || h < 2) {
            System.out.print("Enter the value of, h: ");
            h = sc.nextDouble();
        }
        g = Math.pow(h, (p - 1) / q) % p;
        while (x >= q || x <= 0) {
            System.out.print("Enter the private key, x: ");
            x = sc.nextDouble();
        }
        y = Math.pow(g, x) % p;

        while (k >= q || k <= 0) {
            System.out.print("Enter the secret key, k: ");
            k = sc.nextDouble();
        }
        r = (Math.pow(g, k) % p) % q;
        s = (modInverse(k, q) * (h_m + x * r)) % q;
        System.out.println("\nPrivate key, y: " + (int) y);
        System.out.println("\nSignature = (" + (int) r + ", " + (int) s + ")");
    }

    private static void verifySignature(double r, double s, double h_m) {
        double w, u1, u2, v;
        w = modInverse(s, q);
        u1 = (h_m * w) % q;
        u2 = (r * w) % q;
        v = ((Math.pow(g, u1) * Math.pow(y, u2)) % p) % q;
        if (v == r)
            System.out.print("Signature is verified");
        else
            System.out.print("Signature is invalid");
    }

    private static double largestPrimeDivisor(double n) {
        double largestPrime = -1;
        while (n % 2 == 0) {
            largestPrime = 2;
            n /= 2;
        }
        for (int i = 3; i <= Math.sqrt(n); i += 2) {
            while (n % i == 0) {
                largestPrime = i;
                n /= i;
            }
        }
        if (n > 2)
            largestPrime = n;
        return largestPrime;
    }

    private static int modInverse(double a, double m) {
        for (int i = 1; i < m; i++)
            if ((a * i) % m == 1)
                return i;
        return -1;
    }

    private static boolean isPrime(double num) {
        if (num == 0 || num == 1)
            return false;
        for (int x = 2; x < num / 2; x++)
            if (num % x == 0)
                return false;
        return true;
    }

}
