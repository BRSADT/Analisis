/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisis;

import java.util.ArrayList;

/**
 *
 * @author Samantha
 */
public class Identificadores {
    String nombre;
    String valor;
    String tipo;
    ArrayList<String> listaParametros = new ArrayList<String>();

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Identificadores(String nombre, String valor, String tipo) {
        this.nombre = nombre;
        this.valor = valor;
        this.tipo = tipo;
    }
    
     public Identificadores(String nombre, String tipo) {
        this.nombre = nombre;
      
        this.tipo = tipo;
    }
    
}
