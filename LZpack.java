import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.IOException;

public class LZpack {

public static void main(String[] args) {	
	double pattNum = Double.valueOf(args[0]);
	InputStream bitStream = new BufferedInputStream(System.in);
	byte [] byteArray = new byte[1024];
	try {
		bitStream.read(byteArray);
		//String s = new String(byteArray);
		//System.out.println("Contents of the byte stream are :: "+ s);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
}

public String encodeLine(String Line, int minBits) {
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
	
public void pack(int value, int mask, int phraseNum, int charMismatched, int pOffset, int cOffset) {
	mask(value,mask);
	shiftLeft(phraseNum,pOffset);
	copy(value,phraseNum);
	shiftLeft(charMismatched,cOffset); // Todo: Case where not enough space
	copy(value,charMismatched);
}
	
	
	
	
	
	
	
	
	
	
}
