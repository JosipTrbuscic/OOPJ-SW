package hr.fer.zemris.java.hw07.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static hr.fer.zemris.java.hw07.crypto.Util.hexToByte;
import static hr.fer.zemris.java.hw07.crypto.Util.byteToHex;

/**
 * Simple program that allows user to encrypt/decrypt given file using the AES crypto-
 * algorithm and the 128-bit encryption key or calculate and check the SHA-256 file digest.
 * @author Josip Trbuscic
 *
 */
public class Crypto {

	/**
	 * Main method where program starts
	 * @param args - command line arguments 
	 * 				provided by the user
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Invalid number of arguments. Terminating..");
			System.exit(0);
		}

		Scanner sc = new Scanner(System.in);

		switch (args[0]) {
		case "checksha":
			if (args.length != 2) {
				System.out.println("Invalid checksha arguments number. Terminating..");
				System.exit(0);
			}

			System.out.print("Please provide expected sha-256 digest for " + args[1] + ": \n> ");
			String digest = sc.nextLine().trim();
			
			try {
				checksha(args[1], digest);
			}catch (IOException e) {
				System.out.println("File could not be read or doesn't exist");
			}
			break;
		case "encrypt":
			if (args.length != 3) {
				System.out.println("Invalid encrypt argument number. Terminating..");
				System.exit(0);
			}

			encrypt(args[1], args[2], getPassword(), getInitVector());
			break;
		case "decrypt":
			if (args.length != 3) {
				System.out.println("Invalid decrypt argument number. Terminating..");
				System.exit(0);
			}

			decrypt(args[1], args[2], getPassword(), getInitVector());
			break;
		default:
			System.out.println("Invalid operation. Terminating");
			System.exit(0);
		}
		sc.close();
	}

	/**
	 * Calculates digest of file represented by given 
	 * path and compares it to the given digest.
	 * @param file - File path
	 * @param expectedDigest - expected digest
	 * @throws IOException if file doesn't exist or can't be read
	 */
	private static void checksha(String file, String expectedDigest) throws IOException {
		try (InputStream is = new BufferedInputStream(
				Files.newInputStream(Paths.get("./" + file), StandardOpenOption.READ))) {

			MessageDigest mes = MessageDigest.getInstance("SHA-256");

			byte[] buffer = new byte[4096];
			while (true) {
				int r = is.read(buffer);
				if (r < 1)
					break;

				mes.update(buffer, 0, r);
			}

			String fileDigest = byteToHex(mes.digest());

			System.out.print("Digesting completed. ");
			if (fileDigest.equals(expectedDigest)) {
				System.out.print("Digest of " + file + " matches expected digest.");
			} else {
				System.out.print(
						"Digest of " + file + "does not match the  expected digest." + "Digest was: " + fileDigest);
			}

		}catch (NoSuchAlgorithmException e) {
			//ignorable
		}
	}

	/**
	 * Encrypts file which is represented by the given string, and stores it 
	 * to to new file. Encryption is executed using given password and key.
	 * @param inputFile - path of file which will be encrypted
	 * @param outputFile - path of file which where encrypted data will be stored
	 * @param pass - password
	 * @param key - key
	 */
	private static void encrypt(String inputFile, String outputFile, String pass, String key) {
		Cipher cipher = getCipher(pass, key, true);
		writeToFile(inputFile, outputFile, cipher);
		System.out.println("Encryption completed. Generated file "+outputFile+
							" based on file "+inputFile);
	}

	/**
	 * Decrypts file which is represented by the given string, and stores it 
	 * to to new file. Encryption is executed using given password and key.
	 * @param inputFile - path of file which will be decrypted
	 * @param outputFile - path of file which where decrypted data will be stored
	 * @param pass - password
	 * @param key - key
	 */
	private static void decrypt(String inputFile, String outputFile, String pass, String key) {
		Cipher cipher = getCipher(pass, key, false);
		writeToFile(inputFile, outputFile, cipher);
		System.out.println("Decryption completed. Generated file "+outputFile+
				" based on file "+inputFile);
	}

	/**
	 * Asks user to enter password in a form of 
	 * 32 hex digits.
	 * @return String representation of password
	 */
	private static String getPassword() {
		Scanner sc = new Scanner(System.in);

		System.out.print("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):\n> ");
		String pass = sc.nextLine().trim();
		if (pass.length() != 32)
			throw new IllegalArgumentException("Invalid password");
		return pass;
	}

	/**
	 * Asks user to enter initialization vector in a form of 
	 * 32 hex digits.
	 * @return String representation of password
	 */
	private static String getInitVector() {
		Scanner sc = new Scanner(System.in);

		System.out.print("Please provide initialization vector as hex-encoded text (32 hex-digits):\n> ");
		String vec = sc.nextLine().trim();
		if (vec.length() != 32)
			throw new IllegalArgumentException("Invalid initialization vector");
		return vec;
	}

	/**
	 * Returns new Cypher object which is used for 
	 * encryption/decryption of a file
	 * @param password - password
	 * @param initVector - key
	 * @param encrypt - encrypt/decrypt flag
	 * @return new Cypher object
	 */
	private static Cipher getCipher(String password, String initVector, boolean encrypt) {
		String keyText = password;
		String ivText = initVector;
		SecretKeySpec keySpec = new SecretKeySpec(hexToByte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(hexToByte(ivText));
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

		} catch (NoSuchAlgorithmException | NoSuchPaddingException e1) {
			//ignorable
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			//ignorable
		}
		return cipher;
	}
	
	/**
	 * Writes encrypted/decrypted data to new file. 
	 * Data to be encrypted is provided by input file.
	 * @param inputFile -input file
	 * @param outputFile - output file
	 * @param cipher - cypher object
	 */
	private static void writeToFile(String inputFile, String outputFile, Cipher cipher ) {
		try(InputStream is = new BufferedInputStream(
									Files.newInputStream(Paths.get("./" + inputFile)));
				OutputStream os = new BufferedOutputStream(
									Files.newOutputStream(Paths.get("./" + outputFile)))){
			
			
			byte[] buffer = new byte[4096];
			while (true) {
				int r = is.read(buffer);
				if (r < 1) break;

				os.write(cipher.update(buffer, 0, r));
			}
			
			os.write(cipher.doFinal());
			
			
		} catch (IllegalBlockSizeException | BadPaddingException | IOException e) {
			//ignorable
		} 
	}

}
