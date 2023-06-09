package baek;

import java.util.Scanner;

public class Main3 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt(); //정수 N개로 이루어진 수열 A
		int X = sc.nextInt(); //X보다 작은 수
		int[] arr = new int[N];
		
		for(int i = 0; i < N; i++ ) {
			arr[i] = sc.nextInt();
			if(arr[i] < X) {
				System.out.print(arr[i]+" ");
			}
		}
	}
}
