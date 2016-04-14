package elgamal;

import java.math.BigInteger;
import java.lang.Math;
import java.util.Random;
import java.io.*;

public class ElGamal {

    public static BigInteger getPrime(int bitLenth) {
        BigInteger p = BigInteger.probablePrime(bitLenth, new Random());
        while(!p.isProbablePrime(6)) {
            p.nextProbablePrime();
        }
        return p;
    }

    //b ≡ y^k M ( mod p )
    public static BigInteger encrypt (BigInteger m, BigInteger y, BigInteger k, BigInteger p) {
        BigInteger b = null;
        //BigInteger mi = new BigInteger(m.getBytes());
        b = m.multiply(y.modPow(k,p)).mod(p);
        return b;
    }

    //M ≡ b / a^x ( mod p )
    public static BigInteger decrypt (BigInteger b, BigInteger a, BigInteger x, BigInteger p) {
        BigInteger m = b.multiply(a.modPow(x.negate(),p)).mod(p);
        return m;
    }

    public static String serializeMsg (String str) {
        byte[] b = null;
        StringBuffer sb  = new StringBuffer();
        b = str.getBytes();
        for(byte bb : b){
            sb.append(((int)bb + 256));
        }
        return sb.toString();
    }

    public static String deSerializeMsg (String num) {
        String str = null;
        byte [] b = new byte[num.length()];
        for (int i = 0, j = 0; i <= num.length() - 3; i+=3,j++) {
            b[j] = (byte)(Integer.parseInt(num.substring(i,i+3)) - 256);
        }
        str = new String(b);
        return str;
    }

    public static void main (String [] args) {
        BigInteger p = null; //素数P
        //两个随机数 g,x
        BigInteger g = null;
        BigInteger x = null;

        BigInteger y = null;

        String m0 = "";
        System.out.println("请输入消息M:");
        InputStream clav= System.in;
        BufferedReader rd = new BufferedReader(new InputStreamReader(clav));
        try {m0 = rd.readLine();}
        catch(IOException e) {System.out.println(e.getMessage());}

        String m = ElGamal.serializeMsg(m0);
        System.out.println("m = " + m);
        int len = m.length() * 8;

        p = ElGamal.getPrime(len);
        System.out.println("p = "+p.toString());
        g = new BigInteger(len,new Random());
        System.out.println("g = "+g.toString());
        x = new BigInteger(len,new Random());
        System.out.println("x = "+x.toString());

        y = g.modPow(x,p);
        System.out.println("y = "+y.toString());
        // public key: y,g,p
        // private key: x,g,p

        BigInteger k = ElGamal.getPrime(len+1);
        System.out.println("k = "+k.toString());
        //a ≡ g^k ( mod p )
        BigInteger a = g.modPow(k,p);
        System.out.println("a = "+a.toString());

        //b ≡ y^k M ( mod p )
        BigInteger mb = new BigInteger(m);
        System.out.println("mb = "+mb.toString());
        BigInteger b = ElGamal.encrypt(mb,y,k,p);
        System.out.println("b = "+b.toString());

        BigInteger md = ElGamal.decrypt(b,a,x,p);
        System.out.println("md = "+md.toString());

        String ms = ElGamal.deSerializeMsg(md.toString());
        System.out.println("ms = " + ms);
    }
}
