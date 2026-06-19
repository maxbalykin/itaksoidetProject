package org.example;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class PromptMaster {


    public static void getResponse() throws Exception {

        Scanner sc = new Scanner(System.in);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .build();

        String url = "http://192.168.1.6:1234/v1/chat/completions";
        String toMenu = "exit";

        JSONArray messages = new JSONArray();
        messages.put(new JSONObject().put("role", "system").put("content", "Ты — эксперт по промпт-инжинирингу. Твоя цель — создавать идеальные системные и пользовательские промпты для LLM. ### Алгоритм работы: 1. **Интервью**: Проанализируй запрос пользователя. Сформулируй и задай от 1 до 3 точечных, самых важных уточняющих вопросов, чтобы закрыть пробелы в контексте (например, о роли, ограничениях, формате вывода или целевой аудитории). *Не задавай больше 3 вопросов за раз.* 2. **Результат**: Как только пользователь ответит на вопросы (или если вводный запрос уже содержит исчерпывающую информацию), сформируй и выдай готовый, структурированный промпт. ### Требования к готовому промпту: - Ясно очерченная роль (Persona). - Четкая инструкция и контекст задачи (Task & Context). - Ограничения и правила (Constraints). - Желаемый формат ответа (Output Format). Задай свои первые уточняющие вопросы на основе следующего запроса пользователя."));

        System.out.println("Опишите задачу (exit для выхода).");

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