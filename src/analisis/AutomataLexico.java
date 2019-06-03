/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisis;

import java.util.HashMap;

/**
 *
 * @author Samantha
 */
public class AutomataLexico {

    public HashMap<String, String> tokensitos = new HashMap<String, String>();
    int contador, longitudFrase, linea;
    String frase;
    String token = "";
    int contadortokens = 0;
    arrayListLexico lista;
int espacio;
    public AutomataLexico(arrayListLexico l) {

        lista = l;
    }

    public AutomataLexico(int contador, int longitudFrase, String frase, int linea, arrayListLexico l, HashMap t,int conti) {
        tokensitos = t;
        this.linea = linea;
        this.contador = contador;
        this.longitudFrase = longitudFrase;
        this.frase = frase;
        lista = l;
        this.espacio=conti;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public int getLongitudFrase() {
        return longitudFrase;
    }

    public void setLongitudFrase(int longitudFrase) {
        this.longitudFrase = longitudFrase;
    }

    public String getFrase() {
        return frase;
    }

    public void setFrase(String frase) {
        this.frase = frase;
    }

    //inicio 
    public void q0() {

        if (contador >= longitudFrase) {

        } else {

            //Si el token tiene un _ o una letra al inicio es probable que sea una identificacion
            if (frase.charAt(contador) == '_' || Character.isLetter(frase.charAt(contador))) {

                //contador++;
                Identificador();

            } else {
                ////Si el token tiene un digito al inicio es probable que sea una expresion matematica

                // token+=frase.charAt(contador);
                // contador++;
                qExpresion();

            }

        }
    }

    public void Identificador() {
        if (contador >= longitudFrase) {

            lista.addToken(new lex(token, "", String.valueOf(linea), "Identificador",espacio));

        } else { //no Ha terminado

            if (frase.charAt(contador) == '_') {
                token += frase.charAt(contador);
                contador++;
                Identificador();
            } else {
                if (Character.isLetter(frase.charAt(contador))) {
                    token += frase.charAt(contador);
                    contador++;
                    Identificador();
                } else {
                    if (Character.isDigit(frase.charAt(contador))) {
                        token += frase.charAt(contador);
                        contador++;
                        Identificador();
                    } else {

                        if (tokensitos.containsKey(String.valueOf(frase.charAt(contador)))) {
                            if (tokensitos.get(frase.charAt(contador)) != "Reservada") { //checa si hay algo que pueda delimitarlo
                     
                                if(tokensitos.containsKey(token)){
                                 lista.addToken(new lex(token, "", String.valueOf(linea),tokensitos.get(token) ,espacio));
                             
                                }else{
                                lista.addToken(new lex(token, "", String.valueOf(linea), "Identificador",espacio));
                                }
                                token = "";
                                q0();
                            } else {
                                qError();
                            }
                        } else {

                            qError();
                        }
                    }
                }

            }

        }
    }

    public void qExpresion() {

        if (contador >= longitudFrase) {

            if (tokensitos.containsKey(token)) {
                lista.addToken(new lex(token, "", String.valueOf(linea), tokensitos.get(token),espacio));
            }
            lista.addToken(new lex(token, "", String.valueOf(linea), "numero",espacio));

        } else { //no Ha terminado
            if (Character.isDigit(frase.charAt(contador))) {

                token += frase.charAt(contador);
                contador++;
                qNumero1();
            } else {

                if (tokensitos.containsKey(String.valueOf(frase.charAt(contador)))) {

                    token += frase.charAt(contador);
                    contador++;
                    qtoken2partes();
                }

            }

        }

    }

    public void qtoken2partes() {
        if (contador >= longitudFrase) {

            lista.addToken(new lex(token, "", String.valueOf(linea), tokensitos.get(token),espacio));
        } else { //no Ha terminado
            token += frase.charAt(contador);

            if (tokensitos.containsKey(token)) {
                contador++;
                qtoken3partes();
            } else {

                token = token.substring(0, token.length() - 1);

                lista.addToken(new lex(token, "", String.valueOf(linea), tokensitos.get(token),espacio));
                token = "";

                q0();

            }

        }
    }

    public void qtoken3partes() {
        if (contador >= longitudFrase) {

            lista.addToken(new lex(token, "", String.valueOf(linea), tokensitos.get(token),espacio));
        } else { //no Ha terminado
            token += frase.charAt(contador);
            if (tokensitos.containsKey(token)) {

                lista.addToken(new lex(token, "", String.valueOf(linea), tokensitos.get(token),espacio));
                token = "";
                contador++;
                q0();
            } else {
                token = token.substring(0, token.length() - 1);

                lista.addToken(new lex(token, "", String.valueOf(linea), tokensitos.get(token),espacio));
                token = "";

                q0();

            }

        }
    }

    public void qError() {
        System.out.println("Error en linea" + linea + frase);

    }

    public void qNumero() {

    }

    public void qNumero1() {

        if (contador >= longitudFrase) {

            lista.addToken(new lex(token, "", String.valueOf(linea), "numero",espacio));
        } else { //no Ha terminado

            if (Character.isDigit(frase.charAt(contador))) {
                token += frase.charAt(contador);
                contador++;
                qNumero1();
            } else {
                if (frase.charAt(contador) == '.') {//hay un punto
                    token += frase.charAt(contador);
                    contador++;
                    punto();
                } else { //si ya hay un token que no sea parte de un numero
                    lista.addToken(new lex(token, "", String.valueOf(linea), "numero",espacio));

                    token = "";

                    qExpresion();

                }

            }

        }

    }

    public void punto() {
        if (contador >= longitudFrase) {
            token += '0';
            lista.addToken(new lex(token, "", String.valueOf(linea), "numero",espacio));

        } else { //no Ha terminado

            if (Character.isDigit(frase.charAt(contador))) {
                token += frase.charAt(contador);

                contador++;
                qNumero2();
            } else {
                token += '0';
                lista.addToken(new lex(token, "", String.valueOf(linea), "numero",espacio));

                qExpresion();
            }

        }
    }

    public void qNumero2() {
        if (contador >= longitudFrase) {
            lista.addToken(new lex(token, "", String.valueOf(linea), "numeroFloat",espacio));

        } else { //no Ha terminado

            if (Character.isDigit(frase.charAt(contador))) {
                token += frase.charAt(contador);
                contador++;
                qNumero2();
            } else {

                lista.addToken(new lex(token, "", String.valueOf(linea), "numero",espacio));
                token = "";

                qExpresion();

            }

        }

    }

}
