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

	int value = 0; // B
	int counter = 1;
	
	try {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String Line = br.readLine();
		while(Line != null) {
			value = encodeLine(Line,counter,value);
			Line = br.readLine();
			counter++;
		}
		br.close();
		double numOut = Math.ceil(currUsedBits / 8);
		for(int i =0;i< numOut; i ++) {
			int mask = generateMask(8,32);
			int output = value & mask;
			output = output >>> 24;
			System.out.write(output);
			value = value << 8;
			System.out.flush();
		}
		
	
	
	
		
		
		
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	
	
}

public static int bitsNeeded(int counter) {
	   double bits = Math.log(counter)/Math.log(2.0);
	   return ((int)(bits)) + 1;
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
	
	
	
	if(iBits < availableBits) { // if the bits needed to encode the Phrase number is less than the available bits encode the phrase number
		availableBits = availableBits - iBits;
		value = pack(value,index,availableBits);
		currUsedBits = currUsedBits + iBits;
		if(cBits < availableBits) {
			availableBits = availableBits - cBits;
			value = pack(value,character,availableBits);
			currUsedBits = currUsedBits + cBits;
			return value;
		}
		value = outputBytes(value);
		availableBits = availableBits + 16;
		availableBits = availableBits - cBits;
		value = pack(value,character,availableBits);
		currUsedBits = currUsedBits + cBits;
		return value;
	}
	value = outputBytes(value);
	availableBits = availableBits + 16;
	value = pack(value,index,availableBits);
	currUsedBits = currUsedBits + iBits;
	if(cBits < availableBits) {
		availableBits = availableBits - cBits;
		value = pack(value,character,availableBits);
		currUsedBits = currUsedBits + cBits;
		return value;
	}
	value = outputBytes(value);
	availableBits = availableBits + 16;
	value = pack(value,character,availableBits);
	currUsedBits = currUsedBits + cBits;
	return value;
	
	
	
}
	
public static int pack(int value, int inputBits, int bitOffset) {
	int mask = generateMask(currUsedBits,32);
	value = value & mask;
	inputBits = inputBits << bitOffset;
	value = value | inputBits;
	return value;
}
	
public static int outputBytes(int value) {
	 byte firstOut = (byte) ((value & generateMask(8,32))>>> 24) ;
	 byte secondOut = (byte) ((value &  0x00FF0000)>>> 16);
	 System.out.write(firstOut);
	 
	 System.out.write(secondOut);
	
	 System.out.flush();
	 currUsedBits = currUsedBits - 16;
	 value = value << 16;
	
	 return value;
}
	
	
	
	
	
	
	
}
