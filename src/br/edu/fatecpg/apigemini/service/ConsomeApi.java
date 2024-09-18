package br.edu.fatecpg.apigemini.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ConsomeApi {
    private static final String API_KEY="AIzaSyAtQYLCmbyLLr0U6ORWUKBiHJSSmvV61K8";
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key="+API_KEY;
    private static final Pattern RESP_PATTERN = Pattern.compile("\"text\"\\s*:\\s*\"([^\"]+)\"");

    public static String fazerPergunta(String pergunta)throws IOException, InterruptedException {
        String jsonRequest = gerarJsonRequest(pergunta);
        String respostaJson = enviarRequisicao(jsonRequest);


        return extrairResposta(respostaJson);
    }

    private static String gerarJsonRequest(String pergunta) {
        String promptFormatado = "Question:" + pergunta;
        return "{\"contents\":[{\"parts\":[{\"text\":\""+promptFormatado+"\"}]}]}";
    }

    private static String enviarRequisicao(String jsonRequest) throws
            IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //tratamento de erro da API
        if (response.statusCode() != 200) {
            throw new IOException("Erro ao acessar a API: Código de status HTTP " + response.statusCode());
        }

        return response.body();
    }






    private static String extrairResposta(String respostaJson) {
        StringBuilder resposta = new StringBuilder();
        for (String linha : respostaJson.lines().toList()) {
            Matcher matcher = RESP_PATTERN.matcher(linha);
            if (matcher.find()) {
                resposta.append(matcher.group(1)).append(" ");
            }
        }
        return "answer: " + resposta.toString().trim();
    }



}
