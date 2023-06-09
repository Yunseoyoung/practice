package baek;

import java.util.Scanner;

public class Main1 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
	
		for(int test_case = 1; test_case < T; test_case++) {
			int a = sc.nextInt();
			int b = sc.nextInt();
			int sum = a+b;
			if(a!=0 && b!=0) {
				System.out.println(sum);
			}else {
				break;
			}
		}
		
	}

}
