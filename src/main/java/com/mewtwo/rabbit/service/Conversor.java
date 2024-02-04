package com.mewtwo.rabbit.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.FileWriter;

@Service
public class Conversor {
    public void leitor() {
        String csvFile = "input-data.csv";
        String line;
        String cvsSplitBy = ";";

        JSONArray jsonArray = new JSONArray(); // Cria um array JSON vazio

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);

                JSONObject json = new JSONObject();
                json.put("idTransaction", data[0]);
                json.put("dateTransaction", data[1]);
                json.put("document", data[2]);
                json.put("name", data[3]);
                json.put("age", data[4]);
                json.put("value", data[5]);
                json.put("numTransaction", data[6]);

                jsonArray.put(json); // Adiciona o objeto JSON ao array JSON
            }

            // Agora que o loop terminou, o array JSON está completo
            String jsonFile = jsonArray.toString();
            

            // Escrever a string JSON em um arquivo
            try (FileWriter file = new FileWriter("output.json")) {
                file.write(jsonFile);
                System.out.println("Arquivo JSON criado com sucesso.");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
