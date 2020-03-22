package br.com.rafael.servicos;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import com.google.gson.Gson;

import br.com.rafael.modelos.Answer;

public class Chamadas {
	
	public static String requisicaoDesafio(String token) {
//		if (token.contentEquals("teste")) {
//			String resultado = "{\r\n" + 
//					"	\"numero_casas\": 3,\r\n" + 
//					"	\"token\":\"teste\",\r\n" + 
//					"	\"cifrado\": \"d oljhlud udsrvd pduurp vdowrx vreuh r fdfkruur fdqvdgr\",\r\n" + 
//					"	\"decifrado\": \"\",\r\n" + 
//					"	\"resumo_criptografico\": \"\"\r\n" + 
//					"}";
//			return resultado;
//		}
		
		String url = "https://api.codenation.dev/v1/challenge/dev-ps/generate-data?token=" + token;
		String resultado = "";
		
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpGet get = new HttpGet(url);
			CloseableHttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			
			if (entity != null) {
				resultado = EntityUtils.toString(entity);
				System.out.println(resultado);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultado;
	}
	
	public static void enviarDesafio(Answer answer) {
		Gson gson = new Gson();
		try (Writer writer = new FileWriter("answer.json")) {
			gson.toJson(answer, writer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String url = "https://api.codenation.dev/v1/challenge/dev-ps/submit-solution?token=" + answer.getToken();
		
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("answer", new File("answer.json"));
			HttpEntity entity = builder.build();
			HttpPost post = new HttpPost(url);
			post.setEntity(entity);
			CloseableHttpResponse response = client.execute(post);
			HttpEntity result = response.getEntity();
			
			if (entity != null) {
				String resultado = EntityUtils.toString(result);
				System.out.println(resultado);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
