package com.example.exwebservicecep;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

//Herda a classe async que ira esperar a resposta do webservice
public class HttpService extends AsyncTask<Void, Void, CEP> {
    private final String cep;
    //Construtor que recebe o cep digitado
    public HttpService(String cep) {
        this.cep = cep;
    }

    @Override
    protected CEP doInBackground(Void... voids) {
        StringBuilder resposta = new StringBuilder();
        //Mostrara a resposta do json numa string
        if (this.cep != null && this.cep.length() == 8) {
            try {//
                URL url = new URL("http://viacep.com.br/ws/" + this.cep + "/json/");
                //Monta a url
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //Estabelece a conexao com o servidor
                connection.setRequestMethod("GET");
                //Requisita o metodo get
                connection.setRequestProperty("Content-type", "application/json");
                //O tipo de conteudo gerado sera do tipo json
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                connection.setConnectTimeout(5000);
                connection.connect();

                Scanner scanner = new Scanner(url.openStream());
                //Sera uma stream ainda sem tipo (um array de bytes)
                while (scanner.hasNext()) {
                    resposta.append(scanner.next());
                    //a resposta do json
                }
                //Percorre todas as linhas do json e monta a resposta
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Converte a resposta do jSon para a classe
        //Ao usar gson, obrigatoriamente a classe deve ter os mesmos atributos
        //do retorno json.
        return new Gson().fromJson(resposta.toString(), CEP.class);
    }

}
