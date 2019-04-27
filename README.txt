Repository for COMPX301 Assignment 2
Authors: Hunter Cavers (1288108), Sivaram Manoharan (1299026)

Usage:
		cat <source file> | java LZencode | java LZpack | java LZunpack | java LZdecode > <output file>
		
		Note: LZdecode writes byte stream into file and will add a NUL byte to end of file if source
			  file does not contain one, so if original so if original file does not contain one hashes 
			  will be different but file contents will be the same.