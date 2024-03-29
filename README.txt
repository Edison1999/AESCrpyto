************ AES Program ************
Created by:
Liam Jialiang Guan 200280
Edison Lozano  180811

This program implements the Advanced Encryption Standard (AES) algorithm for both encryption and decryption in Java.

Usage:

To run the program, execute the following command:

java AES <mode> <keyFilePath> <textFilePath>

- <mode>:   Specify the mode of operation. It can be either "encrypt" or "decrypt", 
            or their respective shorthand notations "e" or "d".
- <keyFilePath>: Provide the path to the file containing the encryption/decryption key.
- <textFilePath>: Provide the path to the file containing the text to be encrypted/decrypted.

Example:

To encrypt a text file plain_text.txt using a key from key.txt and write the encrypted text to
cipher_text.txt, you would execute:

java AES encrypt key.txt plain_text.txt

Running Time:

The running time of the program may vary depending on factors such as the size of the input files
and the computational resources available. Generally, the AES algorithm has a polynomial time 
complexity, and for typical key sizes, it is considered computationally efficient. More precisely,
the AES algorithm has a linear time complexity in most cases. 

We measure the time required to encrypt a specific number of lines with 10 trials for each measurement.
The result is shown below:

10 lines: 0.045s
100 lines: 0.233s
1000 lines: 1.957s
10000 lines: 18.395s
100000 line: 196.324s

The result confirms that the algorithm has a linear time complexity. 

Notes:

- The program expects the key and text files to be in hexadecimal format.
- Output is written to a file with an extension .enc for encryption and .dec for decryption.
- Only one key is allowed in the key file.
- The program performs basic validation on the input files and mode.
- The AES algorithm implementation is based on a 128-bit key.
  
Dependencies:

The program relies on the following utilities:

- AESImplementation: Implements the AES algorithm including key expansion, encryption, and decryption.
- InvalidHexException: Exception class for handling invalid hexadecimal strings.

Contact:

For any questions or issues, please contact the authors lozanoea11@brandonu.ca or guanj80@brandonu.ca
