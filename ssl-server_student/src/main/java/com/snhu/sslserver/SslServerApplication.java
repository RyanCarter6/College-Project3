package com.snhu.sslserver;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SslServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SslServerApplication.class, args);
	}

}

@RestController
class ServerController{
	
	// Creates a hash
	public String createHash(String data) {
		// Tries to convert String to SHA-256 hash
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			byte[] bytes = messageDigest.digest(data.getBytes(StandardCharsets.UTF_8));
			return convertToString(bytes);
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		// Returns default String if error
		return data;
	}
	
	// Turns bytes to a string
	public String convertToString(byte[] bytes) {
		BigInteger num = new BigInteger(1, bytes);
		StringBuilder hex = new StringBuilder(num.toString(16));
		
		// Make string required size if too small
		while(hex.length() < 64) {
			hex.insert(0, "0");
		}
		
		return hex.toString();
	}
	
	@RequestMapping("/hash")
	public String newHash() {
		// Set variables
		String data = "My name is Ryan Carter.";
		String hex = createHash(data);

		// Output info
		return "<p>data:" + data + "</p><p>SHA-256 : CheckSum Value:" + hex + "</p>";
	}
}