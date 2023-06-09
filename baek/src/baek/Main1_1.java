package baek;

import java.util.Scanner;

public class Main1_1 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while(true) {
			int a = sc.nextInt();
			int b = sc.nextInt();
			int sum = a + b;
			if(a==0 && b==0) {
				break;
			}
			System.out.println(sum);
		}
		
	}

}
