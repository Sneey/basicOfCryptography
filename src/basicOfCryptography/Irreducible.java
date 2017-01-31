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
		int p = io.getNumberFromConsole("p");
		int m = io.getNumberFromConsole("m");
		
		irreducible.generateAllPossiblePolynomials(p, m);
	}
	
	public void generateAllPossiblePolynomials(int p, int m){
		String[] database = new String[p];
		for(int i=0; i<p;i++){
			database[i] = Integer.toString(i);
		}
	    
	    String[] resultWithoutReduction = getAllLists(database, m+1);
	    for(int j=0; j<resultWithoutReduction.length; j++){	
	    	System.out.println("b " + resultWithoutReduction[j]);
	    }
	    ArrayList<String> resultWithFirstReduction = getAllPolynomialsWithAbsoluteTermAndMDegree(resultWithoutReduction);
	        
	    int counterOfIrreducible = 0;
	    int counterInside = 0;
	    for(int j=0; j<resultWithFirstReduction.size(); j++){	
	    	for(int k = 0;k < resultWithoutReduction.length; k++){
		    	if(Integer.parseInt(resultWithoutReduction[k])<Integer.parseInt(resultWithFirstReduction.get(j))){
		    		if(Integer.parseInt(resultWithoutReduction[k])!=0 && Integer.parseInt(resultWithoutReduction[k])!=1){
		    			counterInside++;
		    			if(isDivisible(resultWithFirstReduction.get(j), resultWithoutReduction[k], p)){
		    				counterInside = 0;
		    				continue;
		    			}
		    			else {
		    				counterOfIrreducible++;
		    			}
		    		}
		    			
		    	}
	    	}
	    	if(counterOfIrreducible==counterInside){
				result.add(resultWithFirstReduction.get(j));
				counterOfIrreducible = 0;
				counterInside = 0;
			} else
				counterOfIrreducible = 0;
	    		counterInside = 0;
	    }
	    System.out.println("Irreducible: " + result);
	}
	public void division(String polynomial1, String polynomial2){
		
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
	public boolean isDivisible(String polynomial1, String polynomial2, int p){
		int multiplier = 0;
		int degreeAfterSubstraction = 0;
		String afterMultiplication = "";
		String afterSubtraction = "";
		
		polynomial2 = removeZerosFromBegin(polynomial2);
		int degreeOfPolynomial2 = polynomial2.length();
		
		multiplier = calculateMultiplier(polynomial1, polynomial2, p);
		
		afterMultiplication = multiplicateByScalar(polynomial2, multiplier, p);
		afterMultiplication = supplementByZerosIfNeed(afterMultiplication, polynomial1.length());
		
		afterSubtraction = substractPolynomials(polynomial1, afterMultiplication, p);
		if(Integer.parseInt(afterSubtraction)!=0)
			afterSubtraction = removeZerosFromBegin(afterSubtraction);
		
		degreeAfterSubstraction = afterSubtraction.length();
		
		if(Integer.parseInt(afterSubtraction) == 0)
			return true;
		else
			if(degreeAfterSubstraction < degreeOfPolynomial2)
				return false;
			else
				return isDivisible(afterSubtraction, polynomial2, p);
	}
	
	public String supplementByZerosIfNeed(String polynomial, int length){
		while(polynomial.length() < length){
			polynomial += 0;
		}
		return polynomial;
	}
	
	public String multiplicateByScalar(String polynomial, int multiplier, int p){
		String afterMultiplication = "";
		for(int i=0;i<polynomial.length(); i++){
			int result = (Character.getNumericValue(polynomial.charAt(i))*multiplier)%p;
			if(result==0) continue;
			afterMultiplication += result;
		}
		return afterMultiplication;
	}
	
	public String substractPolynomials(String polynomial1, String afterMultiplication, int p){
		String afterSubtraction = "";
		for(int i=0; i<polynomial1.length();i++){
			int result = (Character.getNumericValue(polynomial1.charAt(i))-Character.getNumericValue(afterMultiplication.charAt(i)))%p;
			if(result<0) result+=p;
			afterSubtraction+=result;
		}
		return afterSubtraction;
	}
	public String removeZerosFromBegin(String polynomial){
		String result = "";
		if(polynomial.charAt(0) == '0'){
			for(int i=1;i<polynomial.length();i++){
				result += polynomial.charAt(i);
			}
			return removeZerosFromBegin(result);
		} else {
			return polynomial;
		}
	}
	
	public int calculateMultiplier(String polynomial1, String polynomial2, int p){
		CalculationOfBigInteger calcFunc = new CalculationOfBigInteger();
		if(Character.getNumericValue(polynomial1.charAt(0))%Character.getNumericValue(polynomial2.charAt(0))!=0){
			int inverse = Integer.parseInt(calcFunc.inverseBig(Character.toString(polynomial2.charAt(0)), Integer.toString(p)));
			return (Character.getNumericValue(polynomial1.charAt(0))*inverse)%p;
		} else {
			return Character.getNumericValue(polynomial1.charAt(0))/Character.getNumericValue(polynomial2.charAt(0));
		}
	}
	
	public int getDegreeOfPolynomial(String polynomial){
		int countZero = 0;
		for(int i = 0; i<polynomial.length(); i++){
			if(polynomial.charAt(i)=='0')
				countZero++;
			else
				break;
		}
		return polynomial.length()-1-countZero;
	}
	
	public int getStartingPosition(String polynomial){
		int startingPostion = 0;
		for(int i=0; i<polynomial.length();i++){
			if(Character.getNumericValue(polynomial.charAt(i))!=0)
				break;
			else
				startingPostion++;
		}
		return startingPostion; 
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
}