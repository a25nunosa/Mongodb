package com.example;

public class Jugador{
        String jugador;
        String juego;
        int puntuacion;
        int duracion;
        int nivel;
        public Jugador(String jugador, String juego, int puntuacion, int duracion,  int nivel) {
            this.jugador = jugador;
            this.juego = juego;
            this.puntuacion = puntuacion;
            this.duracion = duracion;
            this.nivel = nivel;
        }
    }