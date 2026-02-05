package com.example;

import java.util.Scanner;

import static com.example.CRUD.actualizar;
import static com.example.CRUD.añadir;
import static com.example.CRUD.borrar;
import static com.example.CRUD.buscarEnDepartamentos10y20;
import static com.example.CRUD.buscarPorDepartamento;
import static com.example.CRUD.buscarProfesores1300;
import static com.example.CRUD.decrementarComision;
import static com.example.CRUD.mediaSalarios;
import static com.example.CRUD.subirSalarioAnalistas;

public class Main {

    public class Empleado{
        String nombre;
        int departamento;
        int salario;
        String fechaAlta;
        String oficio;
        int comision;
        public Empleado(String nombre, int departamento, int salario, String fechaAlta, String oficio, int comision) {
            this.nombre = nombre;
            this.departamento = departamento;
            this.salario = salario;
            this.fechaAlta = fechaAlta;
            this.oficio = oficio;
            this.comision = comision;
        }
    }

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
            System.out.println("2. buscar por nombre");
            System.out.println("3. buscar en departamentos 10 y 20");
            System.out.println("4. borrar");
            System.out.println("5. actualizar");
            System.out.println("6. Salir");
            System.out.println("=== ============== ===");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    añadir();
                    break;
                case 2:
                    buscarPorDepartamento();
                    break;
                case 3:
                    buscarEnDepartamentos10y20();
                    break;
                case 4:
                    buscarProfesores1300();
                    break;
                case 5:
                    subirSalarioAnalistas();
                    break;
                case 6:
                    decrementarComision();
                    break;
                case 7:
                    mediaSalarios();
                    break;
                case 8:
                    borrar();
                    break;
                case 9:
                    actualizar();
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