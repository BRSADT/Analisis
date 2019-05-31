/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisis;

/**
 *
 * @author Samantha
 */
public class lex {

    String token;
    String lexema;
    String linea;
    String tipo;
public int espacios;
    public lex(String token, String lexema, String linea, String tipo,int espacio) {
        this.token = token;
        this.lexema = lexema;
        this.linea = linea;
        this.tipo = tipo;
        this.espacios=espacio;
    }

}
