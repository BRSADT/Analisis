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
import java.util.ArrayList;

public class arrayListLexico {

    public int contador;
    private String token; //Establecemos un atributo nombre de la lista

   public ArrayList<lex> listadetokens; //Declaramos un ArrayList que contiene objetos String

    public arrayListLexico() {    //Constructor: crea una lista de nombres vacía
        listadetokens = new ArrayList<lex>(); //Creamos el objeto de tipo ArrayList
        contador = 0;
       

    } //Cierre del constructor

    public void addToken(lex a) {
        listadetokens.add(a);
        contador++;

    } //Cierre del método

    public lex getNombre(int posicion) { //Método

        if (posicion >= 0 && posicion < listadetokens.size()) {

            return listadetokens.get(posicion);
        } else {
            return listadetokens.get(posicion);
        }

    } //Cierre del método  

    public int getTamaño() {
        return listadetokens.size();
    } //Cierre del método

    public void removeNombre(int posicion) {  //Método
        contador--;
        if (posicion >= 0 && posicion < listadetokens.size()) {

            listadetokens.remove(posicion);
        } else {
        } //else vacío. No existe nombre para la posición solicitada, no se ejecuta ninguna instrucción

    } //Cierre del método removeNombre

}
