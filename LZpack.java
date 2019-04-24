import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class LZpack {

public static void main(String[] args) {	
//	double pattNum = Double.valueOf(args[0]);
//	InputStream bitStream = new BufferedInputStream(System.in);
//	byte [] byteArray = new byte[1024];
//	try {
//		bitStream.read(byteArray);
//		//String s = new String(byteArray);
//		//System.out.println("Contents of the byte stream are :: "+ s);
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
	int usedBits = 10; // X
	int maxBits = 32; // B
	int charBits = 0; // C
	int intBits = 0; // P
	int mask = 0; // MASK for X
	
	File input = new File("input.txt");
	try {
		BufferedReader br = new BufferedReader(new FileReader(input));
		String Line = br.readLine();
		
		while(Line != null) {
			encodeLine(Line,5);
			Line = br.readLine();
		}
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	
	
}

public static String encodeLine(String Line, int minBits) {
	String[] lineValues = Line.split(",");
	int index = Integer.parseInt(lineValues[0]);
	char character = lineValues[1].charAt(0);
	System.out.println("Index: " + index);
	System.out.println("Character: " + character);
	return null;
}
public void shiftLeft(int value, int shift) {
	value = value << shift;
}

public void shiftRight(int value, int shift) {
	value = value >> shift;
}

public void uShiftRight(int value, int shift) {
	value = value >>> shift;
}
	
public void mask(int value, int mask) {
	value = value & mask;
}

public void copy(int value, int copy) {
	value = value | copy;
}

public int calculateOffset(int value) {
	   int offset = Integer.SIZE-Integer.numberOfLeadingZeros(value);
	   if(offset == 0) {
		   return 1;
	   }
	   return offset;
}

public static int generateMask(int length, int maxBits) {
	int offset = maxBits - length;
	int mask = 1 << length;
	mask--;
	mask <<= offset;
	System.out.println(Integer.toBinaryString(mask));
	return mask;
}
	
public void pack(int value, int mask, int phraseNum, int charMismatched, int pOffset, int cOffset) {
	mask(value,mask);
	shiftLeft(phraseNum,pOffset);
	copy(value,phraseNum);
	shiftLeft(charMismatched,cOffset); // Todo: Case where not enough space
	copy(value,charMismatched);
}
	
	
	
	
	
	
	
	
	
	
}
