package baek;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		for(int test_case = 1 ; test_case <= T; test_case++) {
			int a = sc.nextInt();
			int b = sc.nextInt();
			int sum = a+b;
			System.out.println("Case #"+test_case+": " +a+ " + " +b+ " = " +sum);
		}
	}

}
