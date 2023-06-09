package baek;

import java.util.Scanner;

public class star {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		for(int test_case = 1; test_case <= N; test_case++) {
			
			for(int j = 1; j <= test_case; j++) {
				System.out.print("*");
			}
			System.out.println();
			
		}
		
	}

}
