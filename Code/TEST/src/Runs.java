import java.util.Arrays;


public class Runs {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String x = "a,x,b,w,m,v,c,f,e";
		String[] a = x.split(",");
		
		String one = "";
		for(String y : a)
			one += y;
		
		System.out.println(one);
		
		Arrays.sort(a);
		
		String two = "";
		for(String y : a)
			two += y;
		
		System.out.println(two);
	}

}
