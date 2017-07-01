package br.edu.satc.ec.erp.utils.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public final class AlgoritmoCriptografia {
	
	private AlgoritmoCriptografia() {}

	public static MessageDigest messageDigest;

	public static MessageDigest getInstance() {

		if (Objects.isNull(messageDigest)) {
			try {
				messageDigest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException ex) {
				ex.printStackTrace();
			}
		}

		return messageDigest;
	}

}
