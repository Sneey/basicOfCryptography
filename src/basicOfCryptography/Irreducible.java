package basicOfCryptography;

import java.util.ArrayList;
import java.util.HashMap;


import utils.IOThings;

public class Irreducible {
	ArrayList<String> result = new ArrayList<String>();
	
	public static void main(String[] args) {
		IOThings io = new IOThings();
		Irreducible irreducible = new Irreducible();
		// TODO Auto-generated method stub
		int p = io.getNumberFromConsole("p");
		int m = io.getNumberFromConsole("m");
		
		irreducible.generateAllPossiblePolynomials(p, m);
		irreducible.sayWhichPolynomialIsIrreducible();
	}
	
	public void generateAllPossiblePolynomials(int p, int m){
		System.out.println(p + " " + m);
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
	    	System.out.println(resultWithFirstReduction.get(j));
	    }
	    int last = resultWithFirstReduction.size()-1;
	    
	    ArrayList<Integer> prime = findPrimeNumbersUsingSieveOfErasthenes(Integer.parseInt(resultWithFirstReduction.get(last)));
	    for(int i=0;i<resultWithFirstReduction.size();i++)
	    	if(prime.contains(Integer.parseInt(resultWithFirstReduction.get(i))))
	    		result.add(resultWithFirstReduction.get(i));
	    
	    System.out.println(result);
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
	public void sayWhichPolynomialIsIrreducible(){
		
	}
}
