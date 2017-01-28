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
	    isDivisible("221", "012", p);
	    /*int last = resultWithFirstReduction.size()-1;
	    ArrayList<Integer> prime = findPrimeNumbersUsingSieveOfErasthenes(Integer.parseInt(resultWithFirstReduction.get(last)));
	    for(int i=0;i<resultWithFirstReduction.size();i++)
	    	if(prime.contains(Integer.parseInt(resultWithFirstReduction.get(i)))){
	    		result.add(resultWithFirstReduction.get(i));
	    		resultWithFirstReduction.remove(i);
	    	}
	    int counterOfIrreducible = 0;
	    for(int j=0; j<resultWithFirstReduction.size(); j++){	
	    	//System.out.println("BASE: " + resultWithFirstReduction.get(j));
	    	for(int k = 0;k<resultWithoutReduction.length;k++){
		    	if(Integer.parseInt(resultWithoutReduction[k])<Integer.parseInt(resultWithFirstReduction.get(j))){
		    		//if(Integer.parseInt(resultWithoutReduction[k])!=0 && Integer.parseInt(resultWithoutReduction[k])!=1){
		    		if(resultWithoutReduction[k].charAt(0)!='0'){
		    			if(isDivisible(resultWithFirstReduction.get(j), resultWithoutReduction[k], p))
		    				continue;
		    			else{
		    				counterOfIrreducible++;
		    				//System.out.println(counterOfIrreducible);
			    			if(counterOfIrreducible==(resultWithFirstReduction.size()-1)){
			    				result.add(resultWithFirstReduction.get(j));
			    				counterOfIrreducible=0;
			    			}
		    			}
		    			//System.out.println("b" + resultWithoutReduction[k]);
		    		}
		    			
		    	}
	    	}
	    }
	    System.out.println("Irreducible: " + result);
	*/}
	
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
		CalculationOfBigInteger calcFunc = new CalculationOfBigInteger();
		//System.out.println("1: " + polynomial1);
		//System.out.println("2: " + polynomial2);
		int polynomial1StartingPosition = getStartingPosition(polynomial1);
		int polynomial2StartingPosition = getStartingPosition(polynomial2);
		
		int multiplier=0;
		//System.out.println("St "+ startingPostion);
		String afterMultiplication = "";
		if(Character.getNumericValue(polynomial1.charAt(polynomial1StartingPosition))%Character.getNumericValue(polynomial2.charAt(polynomial2StartingPosition))!=0){
			int inverse = Integer.parseInt(calcFunc.inverseBig(Character.toString(polynomial2.charAt(polynomial2StartingPosition)), Integer.toString(p)));
			multiplier = (Character.getNumericValue(polynomial1.charAt(polynomial1StartingPosition))*inverse)%p;
		} else {
			multiplier = Character.getNumericValue(polynomial1.charAt(polynomial1StartingPosition))/Character.getNumericValue(polynomial2.charAt(polynomial2StartingPosition));
		}
		//System.out.println("Mu "+ startingPostion);
		for(int i=0;i<polynomial2.length(); i++){
			int result = (Character.getNumericValue(polynomial2.charAt(i))*multiplier)%p;
			if(result==0) continue;
			afterMultiplication += result;
		}
		
		while(true){
			if(getDegreeOfPolynomial(afterMultiplication)!= polynomial2.length()) 
				afterMultiplication += 0;
			else
				break;
		}
		//System.out.println("Po 1" + afterMultiplication);
		int counterOfZeros = 0;
		while(true){
			if(polynomial1.charAt(counterOfZeros)=='0'){
				counterOfZeros++;
			} else {
				String polynomial1Memory = polynomial1;
				polynomial1 = "";
				for(int i=counterOfZeros;i<polynomial1Memory.length();i++){
					polynomial1 += polynomial1Memory.charAt(i);
				}
				for(int i=0;i<afterMultiplication.length()-polynomial1.length();i++){
					polynomial1 += 0;
				}
				break;
			}
		}
		String afterSubtraction = "";
		for(int i=0; i<polynomial2.length();i++){
			int result = (Character.getNumericValue(polynomial1.charAt(i))-Character.getNumericValue(afterMultiplication.charAt(i)))%p;
			if(result<0) result+=p;
			afterSubtraction+=result;
		}
		//System.out.println("Po 2" + afterSubtraction);
		int degreeAfterSubstraction = getDegreeOfPolynomial(afterSubtraction);
		int degreeOfPolynomial2 = getDegreeOfPolynomial(polynomial2);
		
		if(Integer.parseInt(afterSubtraction)==0)
			return true;
		else
			if(degreeAfterSubstraction<degreeOfPolynomial2)
				return false;
			else
				isDivisible(afterSubtraction, polynomial2, p);
		System.out.println("ERROR");
		return true;
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
}