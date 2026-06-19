package org.example;
import java.util.Scanner;

public class App {





    public static void appMenu() throws Exception {

        int choice;
        Scanner sc = new Scanner(System.in);

        System.out.println("####           Меню приложения                  ####");
        System.out.println("####        Сделайте выбор цифрой               ####");
        System.out.println("####            1. Погода                       ####");
        System.out.println("####            2. Чат с ИИ                     ####");
        System.out.println("####            3. Промпт-мастер                ####\n");
        System.out.print(">");

        while (!sc.hasNextInt()){
            sc.nextLine();
            System.out.println("Неа, мне нужна цифра :)");
            System.out.print(">");}

        choice = sc.nextInt();
        System.out.println("Выбран пункт: " + choice +".");

        switch (choice){
            case (1):
                Weather weather = new Weather();
                weather.getWeather();
                break;

            case (2):
                LlmResponse.getResponse();
                break;

            case (3):
                PromptMaster.getResponse();
                break;

            default:
                System.out.println("Неужели так сложно выбрать из списка ?)");
                System.out.println();
                appMenu();

        }






    }
}