package br.edu.satc.ec.erp.utils.security;

public final class Criptografia {
	
	private Criptografia() {}

	private static char[] hexCodes(byte[] text) {
		char[] hexOutput = new char[text.length * 2];
		String hexString;

		for (int i = 0; i < text.length; i++) {
			hexString = "00" + Integer.toHexString(text[i]);
			hexString.toUpperCase().getChars(hexString.length() - 2, hexString.length(), hexOutput, i * 2);
		}
		return hexOutput;
	}

	public static String criptografar(String valor) {
		return new String(hexCodes(AlgoritmoCriptografia.getInstance().digest(valor.getBytes())));
	}

}
