// Hunter Cavers (1288108)
// Sivaram Manoharan (1299026)
public class LZunpack {
	public static void main(String[] args) {	
		int counter = 1;
	}


public static int bitsNeeded(int counter) {
	   double bits = Math.log(counter)/Math.log(2.0);
	   if(bits == 0) {
		   return 1;
	   }
	   return (int)(Math.ceil(bits));
}

}