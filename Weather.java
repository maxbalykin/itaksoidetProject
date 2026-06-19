package org.example;
import java.util.Scanner;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Weather {

    private OkHttpClient client = new OkHttpClient();
    private String city = "Minsk";
    Scanner sc = new Scanner(System.in);




    public void getWeather() throws Exception {

        System.out.println("Введите API для получения погоды в городе Минск: ");
        System.out.print("API можно получить здесь: https://openweathermap.org/api\nAPI key:");
        String apiKey = sc.nextLine();

        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;

        Request request = new Request.Builder() // создаем обьект, который содержит url - куда отправляем запрос, можно добавить header body
                .url(url)
                .build(); //  GET по умолчанию

        try (Response response = client.newCall(request).execute()) { // newCall() -подготовка к отправке .execute() - отправка + ожидание ответа
            System.out.println(response.body().string());
            App.appMenu();
        }
    }
}
