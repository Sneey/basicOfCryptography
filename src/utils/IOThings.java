package utils;

import java.util.Scanner;

public class IOThings {
	public int getNumberFromConsole(String nameOfNumber){
		Scanner reader = new Scanner(System.in);
		System.out.println("Podaj " + nameOfNumber +": ");
		int p = reader.nextInt();
		return p;
	}
}
