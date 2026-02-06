package com.example;


import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Accumulators;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Aggregates.sort;
import com.mongodb.client.model.Projections;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.include;
import com.mongodb.client.model.Sorts;
import static com.mongodb.client.model.Sorts.orderBy;


public class CRUD {
    public static void añadir(){

        MongoProvider db = new MongoProvider();

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nintroduce nombre");
        String nombre = scanner.nextLine();
        System.out.println("introduce juego");
        String juego = scanner.nextLine();
        System.out.println("introduce duracion");
        int duracion = scanner.nextInt();
        System.out.println("introduce puntuacion");
        int puntuacion = scanner.nextInt();
        System.out.println("introduce nivel");
        int nivel = scanner.nextInt();

        db.jugadores().insertOne(
            new Document()
            .append("jugador", nombre)
            .append("juego", juego)
            .append("duracion", duracion)
            .append("puntuacion", puntuacion)
            .append("nivel", nivel)
        );
    }

    public static void jugadoresYpuntuacionTotal(){

        MongoProvider db = new MongoProvider();

        List<Bson> pipeline = Arrays.asList(
                group(
                        "$jugador",
                        Accumulators.sum("puntuacionTotal", "$puntuacion")));

        AggregateIterable<Document> iterDoc = db.jugadores().aggregate(pipeline);
        MongoCursor<Document> cursor = iterDoc.iterator();

        while (cursor.hasNext()) {
            Document doc = cursor.next();
            System.out.println();
            System.out.println("jugador: " + doc.get("_id") + ", puntuacion total: " + doc.getInteger("puntuacionTotal"));
        }
    }

    public static void puntuacionMaximaCadaUno(){

        MongoProvider db = new MongoProvider();

        List<Bson> max = Arrays.asList(
                group(
                        "$jugador",
                        Accumulators.max("puntuacionMaxima", "$puntuacion")));

        AggregateIterable<Document> iterDoc = db.jugadores().aggregate(max);
        Iterator<Document> it = iterDoc.iterator();

        while (it.hasNext()) {
            Document doc = it.next();
            System.out.println();
            System.out.println("jugador: " + doc.get("_id") + ", puntuacion maxima: " + doc.getInteger("puntuacionMaxima"));
        }
    }

    public static void partidaMasCorta(){

        MongoProvider db = new MongoProvider();
        
        List<Bson> min = Arrays.asList(
                group(
                        "$juego",
                        Accumulators.min("partidaMasCorta", "$duracion")));

        AggregateIterable<Document> iterDoc = db.jugadores().aggregate(min);
        Iterator<Document> it = iterDoc.iterator();

        while (it.hasNext()) {
            Document doc = it.next();
            System.out.println();
            System.out.println("juego: " + doc.get("_id") + ", partida mas corta: " + doc.getInteger("partidaMasCorta"));
        }
    }

    public static void ranking(){

        MongoProvider db = new MongoProvider();

        List<Bson> max;
        max = Arrays.asList(
                group(
                        "$jugador",
                        Accumulators.max("puntuacionMaxima", "$puntuacion")),
                sort(orderBy(Sorts.descending("puntuacionMaxima"))));

        AggregateIterable<Document> iterDoc = db.jugadores().aggregate(max);
        Iterator<Document> it = iterDoc.iterator();

        while (it.hasNext()) {
            Document doc = it.next();
            System.out.println();
            System.out.println("jugador: " + doc.get("_id") + ", puntuacion maxima: " + doc.getInteger("puntuacionMaxima"));
        }
    }

    public static void listaSimple(){

        MongoProvider db = new MongoProvider();

        List<Bson> avg;
        avg = Arrays.asList(
                project(               
                Projections.fields(include("jugador","juego", "puntuacion"), excludeId())));


        AggregateIterable<Document> iterDoc = db.jugadores().aggregate(avg);
        Iterator<Document> it = iterDoc.iterator();

        while (it.hasNext()) {
            Document doc = it.next();
            System.out.println();
            System.out.println("jugador: " + doc.get("jugador") + ", juego: " + doc.getString("juego") + ", puntuacion: " + doc.getInteger("puntuacion"));
        }
    }


    public static void mediaPuntuable(){

        MongoProvider db = new MongoProvider();

        List<Bson> avg;
        avg = Arrays.asList(
                group(
                "$juego",
                    Accumulators.avg("puntuacionMedia", "$puntuacion")),
                sort(orderBy(Sorts.descending("puntuacionMedia"))));

        AggregateIterable<Document> iterDoc = db.jugadores().aggregate(avg);
        Iterator<Document> it = iterDoc.iterator();

        while (it.hasNext()) {
            Document doc = it.next();
            System.out.println();
            System.out.println("juego: " + doc.get("_id") + ", puntuacion media: " + doc.getDouble("puntuacionMedia"));
        }
    }

    
}