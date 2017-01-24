package utils;

import java.math.BigInteger;
public class CalculationOfBigInteger {
	public String inverseBig(String a, String p){
		BigInteger aBig = new BigInteger(a);
		BigInteger pBig = new BigInteger(p);
		return (aBig.modInverse(pBig)).toString();
	}
}
