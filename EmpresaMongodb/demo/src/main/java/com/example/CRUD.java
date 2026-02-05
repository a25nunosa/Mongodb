package com.example;


import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import static com.mongodb.client.model.Accumulators.avg;
import static com.mongodb.client.model.Aggregates.group;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;


public class CRUD {
    public static void añadir(){

        MongoProvider db = new MongoProvider();

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nintroduce nombre");
        String nombre = scanner.nextLine();
        System.out.println("introduce numero de departmento");
        int departamento = scanner.nextInt();
        System.out.println("introduce salario");
        int salario = scanner.nextInt();
        scanner.nextLine();
        System.out.println("introduce fecha de alta");
        String fechaAlta = scanner.nextLine();
        System.out.println("introduce oficio");
        String oficio = scanner.nextLine();
        System.out.println("introduce comision");
        int comision = scanner.nextInt();

        db.empleados().insertOne(
            new Document()
            .append("nombre", nombre)
            .append("departamento", departamento)
            .append("salario", salario)
            .append("fechaAlta", fechaAlta)
            .append("oficio", oficio)
            .append("comision", comision)
        );
    }

    public static void borrar(){

        MongoProvider db = new MongoProvider();

        Scanner scanner = new Scanner(System.in);
        System.out.println("introduce el nombre del empleado que quieres borrar");
        String nombre = scanner.nextLine();
        
        db.empleados().deleteOne(Filters.eq("nombre", nombre))
            .getDeletedCount();
    }

    public static void buscarPorDepartamento(){

        MongoProvider db = new MongoProvider();

        Scanner scanner = new Scanner(System.in);
        System.out.println("introduce el nombre del empleado que quieres buscar");
        int departamento = scanner.nextInt();
        
        FindIterable<Document> iterDoc = db.empleados().find(Filters.eq("departamento", departamento));
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public static void buscarEnDepartamentos10y20(){

        MongoProvider db = new MongoProvider();
        
        FindIterable<Document> iterDoc = db.empleados().find(Filters.or(Filters.eq("departamento", 10), Filters.eq("departamento", 20)));
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public static void buscarProfesores1300(){

        MongoProvider db = new MongoProvider();
        
        FindIterable<Document> iterDoc = db.empleados().find(Filters.and(Filters.eq("oficio", "profesor"), Filters.gt("salario", 1300)));
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public static void actualizar(){

        MongoProvider db = new MongoProvider();

        Scanner scanner = new Scanner(System.in);
        System.out.println("A que empleado quieres cambiarle el nombre");
        String nombre = scanner.nextLine();
        System.out.println("Escribe el nuevo nombre");
        String nombre2 = scanner.nextLine();
        
        db.empleados().updateOne(Filters.eq("nombre", nombre), Updates.set("nombre", nombre2));
    }

    public static void subirSalarioAnalistas(){

        MongoProvider db = new MongoProvider();
        
        db.empleados().updateMany(Filters.eq("oficio", "analistas"), Updates.inc("salario", 100));
    }

    public static void decrementarComision(){

        MongoProvider db = new MongoProvider();
        
        db.empleados().updateMany(Filters.gte("comision", 20), Updates.inc("comision", -20));
    }

    public static void mediaSalarios(){

        MongoProvider db = new MongoProvider();

        List<Bson> media = Arrays.asList(
                group(
                        null,
                        avg("mediaSalario", "$salario")));

        AggregateIterable<Document> iterDoc = db.empleados().aggregate(media);
        Iterator<Document> it = iterDoc.iterator();

        while (it.hasNext()) {
            Document doc = it.next();
            System.out.println("La media de los salarios es: " + doc.getDouble("mediaSalario"));
        }
    }

    public static void empleadoMayorSalario(){

        MongoProvider db = new MongoProvider();

        Document doc = db.empleados().find().sort(Sorts.descending("salario")).first();
        if (doc != null) {
            System.out.println("Empleado con mayor salario: " + doc.toJson());
        } else {
            System.out.println("No se encontraron empleados.");
        }
    }
}