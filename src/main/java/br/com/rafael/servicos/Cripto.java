package br.com.rafael.servicos;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Cripto {
	
	public static String decriptar(String cifrado, int numero_casas) {
		
		String pattern = "([A-Za-z])";
		StringBuilder sb = new StringBuilder();
		int tamanho = cifrado.length();

		for(int i = 0; i < tamanho; i++) {
			Character letra = cifrado.charAt(i);
			
			if (letra.toString().matches(pattern)) {
				
				int letraASCII = (int) letra;
				int novaLetraASCII = 0;
				int diferenca = 0;
				
				if (letraASCII >= 65 && letraASCII <= 90) {
					novaLetraASCII = letraASCII - numero_casas;
					if (novaLetraASCII < 65) {
						diferenca = 65 - novaLetraASCII;
						novaLetraASCII = 90 - diferenca;
					}
				} else if (letraASCII >= 97 && letraASCII <= 122) {
					novaLetraASCII = letraASCII - numero_casas;
					if (novaLetraASCII < 97) {
						diferenca = 97 - novaLetraASCII;
						novaLetraASCII = 122 - diferenca;
					}
				}
				
				char novaLetra = (char) novaLetraASCII;
				sb.append(novaLetra);
				
			} else {
				sb.append(letra);
			}
		}
		
		return sb.toString();
	}
	
	public static String geraSHA1(String decifrado) {
		String sha1 = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.reset();
			digest.update(decifrado.getBytes("utf8"));
			sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sha1;
	}

}
