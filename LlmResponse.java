package org.example;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Scanner;


public class LlmResponse {


    public static void getResponse() throws Exception {

        Scanner sc = new Scanner(System.in);
        OkHttpClient client = new OkHttpClient();
        String url = "http://192.168.1.6:1234/v1/chat/completions";
        String toMenu = "exit";

        JSONArray messages = new JSONArray();
        messages.put(new JSONObject().put("role", "system").put("content", "Ты полезный ассистент."));

        System.out.println("Введите сообщение для ИИ (exit для выхода).");

        while (true) {
            System.out.print("Вы: ");
            String userInput = sc.nextLine();

            if (userInput.equalsIgnoreCase(toMenu)) {
                System.out.println("Диалог завершён.");
                App.appMenu();
                break;
            }

            messages.put(new JSONObject().put("role", "user").put("content", userInput));

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("model", "gemma-4-e4b-it");
            jsonBody.put("messages", messages);
            jsonBody.put("temperature", 0.7);
            jsonBody.put("max_tokens", 2000);


            Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(jsonBody.toString(), MediaType.parse("application/json")))
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String body = response.body().string();
                JSONObject json = new JSONObject(body);
                String answer = json.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
                System.out.println("ИИ: " + answer);

                messages.put(new JSONObject().put("role", "assistant").put("content", answer));
            }
        }
    }
}