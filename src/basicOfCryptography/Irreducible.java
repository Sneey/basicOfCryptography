package basicOfCryptography;

import java.util.ArrayList;
import java.util.HashMap;

import utils.CalculationOfBigInteger;
import utils.IOThings;

public class Irreducible {
	ArrayList<String> result = new ArrayList<String>();
	
	public static void main(String[] args) {
		IOThings io = new IOThings();
		Irreducible irreducible = new Irreducible();
		// TODO Auto-generated method stub
		int p = io.getNumberFromConsole("p");
		int m = io.getNumberFromConsole("m");
		
		//irreducible.generateAllPossiblePolynomials(p, m);
		System.out.println(irreducible.extendedEuclideanAlgorithm(4864, 3458));
		irreducible.division("721948327", "84461");
		
	}
	public void division(String x, String y){
		int n = x.length()-1;
		int t = y.length()-1;
		String q = "";
		
		for(int j=0;j<(n-t);j++){
			q+="0";
		}
		
	}
	
	public ArrayList<Integer> extendedEuclideanAlgorithm(int a, int b){
		int d = 0, x = 0, y = 0;
		int x2 = 1, x1 = 0, y2 = 0, y1 = 1;
		int q = 0, r = 0;
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		if(b==0) {
			d = a;
			x = 1;
			y = 0;
			result.add(d);
			result.add(x);
			result.add(y);
			return result;
		} else {
			while(b>0){
				q = (int) a/b;
				r = a - q*b;
				x = x2 - q*x1;
				y = y2 - q*y1;
				
				a = b;
				b = r;
				x2 = x1;
				x1 = x;
				y2 = y1;
				y1 = y;
			}

			d = a;
			x = x2;
			y = y2;
			result.add(d);
			result.add(x);
			result.add(y);
			
			return result;
		}
	}
	
	public void generateAllPossiblePolynomials(int p, int m){
		//System.out.println(p + " " + m);
		String[] database = new String[p];
		for(int i=0; i<p;i++){
			database[i] = Integer.toString(i);
		}
	    
	    String[] resultWithoutReduction = getAllLists(database, m);
	    for(int j=0; j<resultWithoutReduction.length; j++){	
	    	System.out.println("b " + resultWithoutReduction[j]);
	    }
	    ArrayList<String> resultWithFirstReduction = getAllPolynomialsWithAbsoluteTermAndMDegree(resultWithoutReduction);
	    for(int j=0; j<resultWithFirstReduction.size(); j++){	
	    	System.out.println("b " + resultWithFirstReduction.get(j));
	    }
	    
	    int last = resultWithFirstReduction.size()-1;
	}
	
	public static String[] getAllLists(String[] elements, int lengthOfList)
	{
	    String[] allLists = new String[(int)Math.pow(elements.length, lengthOfList)];
	    
	    if(lengthOfList == 1) return elements; 
	    else {
	        String[] allSublists = getAllLists(elements, lengthOfList - 1);
	        int arrayIndex = 0;

	        for(int i = 0; i < elements.length; i++){
	            for(int j = 0; j < allSublists.length; j++){
	                allLists[arrayIndex] = elements[i] + allSublists[j];
	                arrayIndex++;
	            }
	        }
	        return allLists;
	    }
	}
	public ArrayList<String> getAllPolynomialsWithAbsoluteTermAndMDegree(String polynomial[]){
		ArrayList<String> resultOfReduction = new ArrayList<String>();
		for(int i=0;i<polynomial.length;i++){
			if(polynomial[i].charAt(polynomial[i].length()-1)!='0' && polynomial[i].charAt(0)!='0')
				resultOfReduction.add(polynomial[i]);
		}
		return resultOfReduction;
	}
	public ArrayList<Integer> findPrimeNumbersUsingSieveOfErasthenes(int maxNumber){
		ArrayList<Integer> primeNumbers = new ArrayList<Integer>();
		for(int i=1; i<=maxNumber; i++) 
			primeNumbers.add(i);
		int howMany = (int) Math.floor(Math.sqrt(maxNumber));
		int j;
		for(int i=2; i<=howMany; i++) {
			if(primeNumbers.get(i-1)!=0){
				j = i+i;
				while(j<=maxNumber) {
					primeNumbers.set(j-1,0);
					j += i;
				}
			}
		}
		return primeNumbers;
	}
	//public boolean isDivisible(String polynomial1, String polynomial2, int p){
	//}
}
