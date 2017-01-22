package basicOfCryptography;

import utils.IOThings;

public class Irreducible {

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
	    for(int i=1; i<=m; i++){
	        String[] result = getAllLists(database, i);
	        for(int j=0; j<result.length; j++){
	            System.out.println(result[j]);
	        }
	    }
	}
	public static String[] getAllLists(String[] elements, int lengthOfList)
	{
	    //initialize our returned list with the number of elements calculated above
	    String[] allLists = new String[(int)Math.pow(elements.length, lengthOfList)];

	    //lists of length 1 are just the original elements
	    if(lengthOfList == 1) return elements; 
	    else {
	        //the recursion--get all lists of length 3, length 2, all the way up to 1
	        String[] allSublists = getAllLists(elements, lengthOfList - 1);

	        //append the sublists to each element
	        int arrayIndex = 0;

	        for(int i = 0; i < elements.length; i++){
	            for(int j = 0; j < allSublists.length; j++){
	                //add the newly appended combination to the list
	                allLists[arrayIndex] = elements[i] + allSublists[j];
	                arrayIndex++;
	            }
	        }
	        return allLists;
	    }
	}
	public void sayWhichPolynomialIsIrreducible(){
		
	}
}
