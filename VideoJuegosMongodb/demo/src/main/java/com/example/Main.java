package com.example;

import java.util.Scanner;

import static com.example.CRUD.añadir;
import static com.example.CRUD.jugadoresYpuntuacionTotal;
import static com.example.CRUD.listaSimple;
import static com.example.CRUD.mediaPuntuable;
import static com.example.CRUD.partidaMasCorta;
import static com.example.CRUD.puntuacionMaximaCadaUno;
import static com.example.CRUD.ranking;

public class Main {

    public static void main(String[] args) {
         try (MongoProvider provider = new MongoProvider()) {
            System.out.println("Mongo provider creado");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("=== menu principal ===");
            System.out.println("1. añadir");
            System.out.println("2. jugadores y puntuacion total");
            System.out.println("3. puntuaciones maximas");
            System.out.println("4. partidas mas cortas");
            System.out.println("5. Ranking");
            System.out.println("6. lista simple");
            System.out.println("7. media puntuable");
            System.out.println("=== ============== ===");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    añadir();
                    break;
                case 2:
                    jugadoresYpuntuacionTotal();
                    break;
                case 3:
                    puntuacionMaximaCadaUno();
                    break;
                case 4:
                    partidaMasCorta();
                    break;
                case 5:
                    ranking();
                    break;
                case 6:
                    listaSimple();
                    break;
                case 7:
                    mediaPuntuable();
                    break;
                case 0:
                    System.out.println("programa cerrado");
                    break;
                default:
                    System.out.println("no es una opcio");
            }
            System.out.println(); 
        } while (opcion != 0);

        scanner.close();
    }

}