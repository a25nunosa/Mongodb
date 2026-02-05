package com.example;

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