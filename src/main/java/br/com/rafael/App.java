package br.com.rafael;

import java.util.Scanner;

import com.google.gson.Gson;

import br.com.rafael.modelos.Answer;
import br.com.rafael.servicos.Chamadas;
import br.com.rafael.servicos.Cripto;

public class App {

	public static void main(String[] args) {
		Answer answer = new Answer();
		Gson gson = new Gson();
		Scanner sc = new Scanner(System.in);
		System.out.println("Digite o seu token:");
		String token = sc.nextLine();

		String requisicao = Chamadas.requisicaoDesafio(token);

		answer = gson.fromJson(requisicao, Answer.class);
		
		String decifrado = Cripto.decriptar(answer.getCifrado(), answer.getNumero_casas());
		answer.setDecifrado(decifrado);
		
		String sha1 = Cripto.geraSHA1(decifrado);
		answer.setResumo_criptografico(sha1);
		
		Chamadas.enviarDesafio(answer);
		sc.close();
	}

}
