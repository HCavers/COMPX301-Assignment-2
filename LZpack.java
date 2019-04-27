// Hunter Cavers (1288108)
// Sivaram Manoharan (1299026)

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class LZpack {
public static int currUsedBits = 0; // X length
public static void main(String[] args) {	

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
	
	int maxBits = 32; // B length
	int value = 0; // B
	int counter = 1;
	//File input = new File("input.txt");
	try {
		//BufferedReader br = new BufferedReader(new FileReader(input));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String Line = br.readLine();
		while(Line != null) {
			value = encodeLine(Line,counter,value);
			Line = br.readLine();
			counter++;
		}
		br.close();
		System.out.write(value);
		System.out.flush();
		
		
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	
	
}

public static int bitsNeeded(int counter) {
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
	int character = Integer.parseInt(lineValues[1]);
	int iBits = bitsNeeded(counter);
	int cBits = 8;
	int availableBits = 32 - currUsedBits;
	int mask = generateMask(currUsedBits,32);
	
	
	if(iBits < availableBits) { // if the bits needed to encode the Phrase number is less than the available bits encode the phrase number
		//System.out.println("Index: " + index + " Which is in binary: " + (Integer.toBinaryString(index)) );
		//System.out.println("Int bits required: " + iBits);
		availableBits = availableBits - iBits;
		value = pack(value,mask,index,availableBits);
		currUsedBits = currUsedBits + iBits;
		//System.out.println(currUsedBits);
		if(cBits < availableBits) {
			//System.out.println("Character: " + character + " Which is in binary: " + (Integer.toBinaryString(character)) );
			availableBits = availableBits - cBits;
			value = pack(value,mask,character,availableBits);
			currUsedBits = currUsedBits + cBits;
			//System.out.println(currUsedBits);
			return value;
		}
		value = outputBytes(value);
		availableBits = availableBits + 16;
		//System.out.println("Character: " + character + " Which is in binary: " + (Integer.toBinaryString(character)) );
		availableBits = availableBits - cBits;
		value = pack(value,mask,character,availableBits);
		currUsedBits = currUsedBits + cBits;
		//System.out.println(currUsedBits);
		return value;
	}
	value = outputBytes(value);
	availableBits = availableBits + 16;
	//System.out.println("Index: " + index + " Which is in binary: " + (Integer.toBinaryString(index)) );
	//System.out.println("Int bits required: " + iBits);
	availableBits = availableBits - iBits;
	value = pack(value,mask,index,availableBits);
	currUsedBits = currUsedBits + iBits;
	//System.out.println(currUsedBits);
	if(cBits < availableBits) {
		//System.out.println("Character: " + character + " Which is in binary: " + (Integer.toBinaryString(character)) );
		availableBits = availableBits - cBits;
		value = pack(value,mask,character,availableBits);
		currUsedBits = currUsedBits + cBits;
		//System.out.println(currUsedBits);
		return value;
	}
	value = outputBytes(value);
	availableBits = availableBits + 16;
	//System.out.println("Character: " + character + " Which is in binary: " + (Integer.toBinaryString(character)) );
	availableBits = availableBits - cBits;
	value = pack(value,mask,character,availableBits);
	currUsedBits = currUsedBits + cBits;
	//System.out.println(currUsedBits);
	return value;
	
	
	
}
	
public static int pack(int value, int masdsk, int inputBits, int bitOffset) {
	int mask = generateMask(currUsedBits,32);
	//System.out.println("Value at start  : 	" + (Integer.toBinaryString(value)) );
	value = value & mask;
	//System.out.println("Value after mask: 	" + (Integer.toBinaryString(value)) );
	//System.out.println("inputBits before Shift: " + (Integer.toBinaryString(inputBits)));
	inputBits = inputBits << bitOffset;
	//System.out.println("inputBits after Shift : " + (Integer.toBinaryString(inputBits)));
	value = value | inputBits;
	//System.out.println("Value after Copy:	" + (Integer.toBinaryString(value)) );
	//System.out.println("");
	//System.out.println("");
	return value;
}
	
public static int outputBytes(int value) {
	//System.out.println("THE VALUE IS: " + Integer.toBinaryString(value));
	 int firstOutInt =  ((value & generateMask(8,32))>>> 24) ;
	 int secondOutInt =  ((value &  0x00FF0000)>>> 16);
	 System.out.println("FIRST INTEGER OUTPUT" + Integer.toBinaryString(firstOutInt));
	 System.out.println("SECOND INTEGER OUTPUT" + Integer.toBinaryString(secondOutInt));
	 byte firstOut = (byte) ((value & generateMask(8,32))>>> 24) ;
	 byte secondOut = (byte) ((value &  0x00FF0000)>>> 16);
	 //System.out.write(firstOut);
	 
	// System.out.write(secondOut);
	
	 System.out.flush();
	 currUsedBits = currUsedBits - 16;
	 value = value << 16;
	 //System.out.println("THE VALUE IS: " + Integer.toBinaryString(value));
	 return value;
}
	
	
	
	
	
	
	
}
