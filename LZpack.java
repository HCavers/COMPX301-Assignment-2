import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class LZpack {
public static int currUsedBits = 0; // X length
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
	//int usedBits = 10; // X length
	
	int availableBits = 32;
	int maxBits = 32; // B length
	int value = 0; // B
	int counter = 1;
	File input = new File("input.txt");
	try {
		BufferedReader br = new BufferedReader(new FileReader(input));
		String Line = br.readLine();
	
		while(Line != null) {
			value = encodeLine(Line,counter,value);
			Line = br.readLine();
			counter++;
		}
		br.close();
		
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	
	
}


public static void shiftLeft(int value, int shift) {
	value = value << shift;
}

public void shiftRight(int value, int shift) {
	value = value >> shift;
}

public void uShiftRight(int value, int shift) {
	value = value >>> shift;
}
	
public static void mask(int value, int mask) {
	value = value & mask;
}

public static void copy(int value, int copy) {
	value = value | copy;
}

public static int bitsNeeded(int counter) {
	   //int offset = Integer.SIZE-Integer.numberOfLeadingZeros(value);
	   double bits = Math.log(counter)/Math.log(2.0);
	   if(bits == 0) {
		   return 1;
	   }
	   return (int)(Math.ceil(bits));
}

public static int generateMask(int length, int maxBits) {
	int offset = maxBits - length;
	int mask = 1 << length;
	mask--;
	mask <<= offset;
	return mask;
}

public static int encodeLine(String Line, int counter, int value) {
	String[] lineValues = Line.split(",");
	int index = Integer.parseInt(lineValues[0]);
	char character = lineValues[1].charAt(0);
	int iBits = bitsNeeded(counter);
	int cBits = 8;
	int availableBits = 32 - currUsedBits;
	int mask = generateMask(currUsedBits,32);
	System.out.println("Index: " + index + " Which is in binary: " + (Integer.toBinaryString(index)) );
	System.out.println("Int bits required: " + iBits);
	System.out.println("Character: " + character + " Which is in binary: " + (Integer.toBinaryString(character)) );
	//System.out.println("Mask is: " + (Integer.toBinaryString(mask)));
	if(iBits < availableBits) { // if the bits needed to encode the Phrase number is less than the available bits encode the phrase number
		availableBits = availableBits - iBits;
		value = pack(value,mask,index,availableBits);
		currUsedBits = currUsedBits + iBits;
		System.out.println(currUsedBits);
		if(cBits < availableBits) {
			availableBits = availableBits - cBits;
			value = pack(value,mask,character,availableBits);
			currUsedBits = currUsedBits + cBits;
			System.out.println(currUsedBits);
		}
	}
	
	return value;
}
	
//public void pack(int value, int mask, int phraseNum, int charMismatched, int pOffset, int cOffset) {
//	mask(value,mask);
//	shiftLeft(phraseNum,pOffset);
//	copy(value,phraseNum);
//	shiftLeft(charMismatched,cOffset); // Todo: Case where not enough space
//	copy(value,charMismatched);
//}
	
public static int pack(int value, int mask, int inputBits, int bitOffset) {
	//System.out.println("BitOffset: " + bitOffset);
	System.out.println("Value at start  : 	" + (Integer.toBinaryString(value)) );
	value = value & mask;
	System.out.println("Value after mask: 	" + (Integer.toBinaryString(value)) );
	//mask(value,mask);
	System.out.println("inputBits before Shift: " + (Integer.toBinaryString(inputBits)));
	//shiftLeft(inputBits,bitOffset);
	inputBits = inputBits << bitOffset;
	System.out.println("inputBits after Shift : " + (Integer.toBinaryString(inputBits)));
	//copy(value,inputBits);
	value = value | inputBits;
	System.out.println("Value after Copy:	" + (Integer.toBinaryString(value)) );
	System.out.println("");
	System.out.println("");
	return value;
}
	
	
	
	
	
	
	
	
	
}
