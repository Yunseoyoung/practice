package baek;

import java.util.Scanner;

public class Main6 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		boolean[] arr = new boolean[31]; 
		
		for(int i = 1; i < 28; i++) {
			arr[i] = true;
			
		}
		for(int j = 1; j < 30; j++) {
			if(arr[j] != true) {
				System.out.println(arr[j]);
			}
		}
			
		
	}
}
