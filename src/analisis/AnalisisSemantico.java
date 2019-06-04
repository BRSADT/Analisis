package analisis;

import analisis.arrayListLexico;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Samantha
 */
public class AnalisisSemantico {

    boolean brenda = true, aprobado = false, Condelse = false, error = false;
    public HashMap<String, String> tokensitos = new HashMap<String, String>();
    int contador, longitudFrase, linea, contadortokens = 0, espacio, anidado = 0, cicloIf = 0;
    String frase, token = "";
    arrayListLexico lista;
    ArrayList<Integer> listaEspacios, listaEspaciosInden, listaIF;
    ArrayList<String> listaTokenEspacio = new ArrayList<String>();
    ArrayList<Integer>[] arrIF = new ArrayList[2];

    public AnalisisSemantico(arrayListLexico l) {
        this.listaEspacios = new ArrayList<Integer>();
        this.listaEspaciosInden = new ArrayList<Integer>();
        contador = 0;
        lista = l;
        longitudFrase = lista.listadetokens.size();
        System.out.println("semantico");
    }

    public void q0() {
        for (int i = 0; i < 2; i++) {
            arrIF[i] = new ArrayList<Integer>();
        }
        if (contador >= longitudFrase) {
            System.out.println("No hay informacion");
        } else {
            if (lista.listadetokens.get(contador).token.equals("import")) {
                contador++;
                qLibrerias();
            } else {
                qGeneral();
            }
        }
    }

    public void qLibrerias() {
        if (contador >= longitudFrase) {
            System.out.println("Error falta libreria");
        } else {
            if (lista.listadetokens.get(contador).tipo.equals("Libreria")) {
                contador++;
                q0();
            } else {
                System.out.println("libreria no definida");
            }
        }
    }

    public void qGeneral() {
        //   if (!lista.listadetokens.get(contador).token.equals("elif")) {
        boolean hayerror = false;
        if (contador >= longitudFrase) {
            System.out.println("termino");
            hayerror = true;
        } else {
            System.out.println("token~" + lista.listadetokens.get(contador).token);
            if (!listaEspacios.isEmpty()) {
                //si el ultimo token tiene un espaciado mayor al del ultimo agregado de la lista de espacios entonces se aprueba
                if (lista.listadetokens.get(contador).espacios > listaEspacios.get(listaEspacios.size() - 1)) {

                    if (anidado == 0) {

                        listaEspaciosInden.add(lista.listadetokens.get(contador).espacios);

                        anidado = listaEspaciosInden.get(listaEspaciosInden.size() - 1);
                        aprobado = true;
                        hayerror = false;
                        if (hayerror == false) {

                            general2parte();
                        }
                    } else {

                        if (lista.listadetokens.get(contador).espacios == anidado) {
                            general2parte();
                        } else {
                            hayerror = true;
                            System.out.println("ERROR, NO ES CORRECTA LA INDENTACION  " + lista.listadetokens.get(contador).token + " linea " + lista.listadetokens.get(contador).linea);
                        }
                    }

                } else {
                    if (lista.listadetokens.get(contador).token.equals("else") && aprobado == true) { //falta verificar que  en el anidado este un if
                        if (listaEspacios.indexOf(lista.listadetokens.get(contador).espacios) != -1) {
                            int iIndentado = listaEspacios.indexOf(lista.listadetokens.get(contador).espacios);
                            if (listaTokenEspacio.get(iIndentado).equals("if") || listaTokenEspacio.get(iIndentado).equals("elif")) {
                                qElse();
                            } else {
                                System.out.println("No se encuentra un if o elif correspondiente" + lista.listadetokens.get(contador).token + " linea " + lista.listadetokens.get(contador).linea);
                            }
                        } else {
                            System.out.println("No se encuentra un if o elif correspondiente" + lista.listadetokens.get(contador).token + " linea " + lista.listadetokens.get(contador).linea);
                        }

                    } else {
                        if (lista.listadetokens.get(contador).token.equals("elif") && aprobado == true) { //falta verificar que  en el anidado este un if
                            if (listaEspacios.indexOf(lista.listadetokens.get(contador).espacios) != -1) {
                                int iIndentado = listaEspacios.indexOf(lista.listadetokens.get(contador).espacios);
                                if (listaTokenEspacio.get(iIndentado).equals("if") || listaTokenEspacio.get(iIndentado).equals("elif")) {
                                    qElif();
                                } else {
                                    System.out.println("No se encuentra un if o elif correspondiente" + lista.listadetokens.get(contador).token + " linea " + lista.listadetokens.get(contador).linea);
                                }
                            } else {
                                System.out.println("No se encuentra un if o elif correspondiente" + lista.listadetokens.get(contador).token + " linea " + lista.listadetokens.get(contador).linea);
                            }

                        } else {
                            System.out.println("es menor la inden");
                            //si el ultimo token tiene un espaciado menor al del ultimo agregado de la lista de espacios. entonces se aprueba
                            int IdentacionesNoeliminadas = listaEspacios.size();

                            if (aprobado == true) {//  ya tiene minimo una instruccion

                                Iterator it = listaEspacios.iterator();
                                while (it.hasNext()) {

                                    int x = (Integer) it.next();
                                    if (x > lista.listadetokens.get(contador).espacios) {
                                        it.remove();
                                        IdentacionesNoeliminadas--;
                                    }
                                }
                                IdentacionesNoeliminadas--;
                                int aux = 0;
                                Iterator i = listaEspaciosInden.iterator();
                                while (i.hasNext()) {
                                    int x = (Integer) i.next();

                                    if ((aux >= IdentacionesNoeliminadas)) {

                                        i.remove();
                                    }
                                    aux++;
                                }

                                if (IdentacionesNoeliminadas > 0) {
                                    anidado = listaEspaciosInden.get(listaEspaciosInden.size() - 1);
                                    //aqui hay error    cambie el != por >=  luego >=  por >  AL FINAL DE NUEVO !=
                                    if (lista.listadetokens.get(contador).espacios != anidado) {
                                        hayerror = true;
                                        System.out.println("error Indentacion" + lista.listadetokens.get(contador).token + " linea " + lista.listadetokens.get(contador).linea);
                                    }
                                } else {
                                    System.out.println("se eliminaron todas las indentaciones");
                                    if (lista.listadetokens.get(contador).espacios != 0) {
                                        hayerror = true;
                                        System.out.println("error Indentacion (recuerda que si no hay ciclos no debe haber espacios :)  )" + lista.listadetokens.get(contador).token + " linea " + lista.listadetokens.get(contador).linea);
                                    }
                                }
                                if (hayerror != true) {
                                    general2parte();
                                }
                            } else {
                                hayerror = true;
                                System.out.println("error indentacion" + lista.listadetokens.get(contador).token + " linea " + lista.listadetokens.get(contador).linea);
                            }

                        }//fin else

                        /*  if (hayerror == false) {
                        System.out.println("aqui2");
                        general2parte();
                    }*/
                    }
                }

            } else {

                // no hay identacion
                if (lista.listadetokens.get(contador).espacios != 0) {
                    hayerror = true;
                    System.out.println("error Indentacion (recuerda que si no hay ciclos no debe haber espacios :)  )" + lista.listadetokens.get(contador).token + " linea " + lista.listadetokens.get(contador).linea);
                } else {
                    if (hayerror == false) {
                        general2parte();
                    }
                }
            }

        }
        //   }
    }

    public void general2parte() {

        switch (lista.listadetokens.get(contador).tipo) {
            case "Reservada":
                switch (lista.listadetokens.get(contador).token) {
                    case "if":
                        if (lista.listadetokens.get(contador).token.equals("if")) {
                            anidado = 0;
                            aprobado = false;
                            linea = Integer.parseInt(lista.listadetokens.get(contador).linea);
                            listaEspacios.add(lista.listadetokens.get(contador).espacios);
                            listaTokenEspacio.add("if");
                            contador++;
                            qIf();

                        }
                        break;
                    case "for": {
                        if (lista.listadetokens.get(contador).token.equals("for")) {
                            anidado = 0;
                            aprobado = false;
                            linea = Integer.parseInt(lista.listadetokens.get(contador).linea);
                            listaEspacios.add(lista.listadetokens.get(contador).espacios);
                            listaTokenEspacio.add("for");
                            contador++;
                            qFor();
                            break;
                        }

                    }
                    break;
                    case "while":
                        if (lista.listadetokens.get(contador).token.equals("while")) {
                            anidado = 0;
                            aprobado = false;
                            linea = Integer.parseInt(lista.listadetokens.get(contador).linea);
                            listaEspacios.add(lista.listadetokens.get(contador).espacios);
                            listaTokenEspacio.add("while");
                            contador++;
                            qWhile();
                            break;
                        }

                        break;
                    case "else":

                        break;
                    case "elif":
                        break;
                    case "print":
                        contador++;
                        //temporal
                        linea = Integer.parseInt(lista.listadetokens.get(contador).linea);
                        qPrint();
                        qGeneral();

                        break;
                }

                break;
            case "Identificador":
                if (lista.listadetokens.get(contador).tipo.equals("Identificador")) {

                    linea = Integer.parseInt(lista.listadetokens.get(contador).linea);
                    contador++;
                    qGeneral();
                }

                break;
            default:
                System.out.println("error no se reconoce token");

        }
    }

    public void qIf() {
        QExpresion();
        if (error == false) {
            contador++;
            //revisar indentacion
            System.out.println("fin declaracion if");

            qGeneral();
            System.out.println("fin if");
        }
    }

    public void QExpresion() {
        error = true;
        if (contador >= longitudFrase) {
            System.out.println("Error");
        } else {

            //revisa que despues del if haya un identificador o numero 
            if ((lista.listadetokens.get(contador).tipo.equals("numero") || lista.listadetokens.get(contador).tipo.equals("Identificador")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                //verifica que al inicio haya un identificador aqui no va contadaor
                if (lista.listadetokens.get(contador).tipo.equals("Identificador")) {

                    contador++; //siguiente token operador comparacion

                    if (contador >= longitudFrase) {
                        System.out.println("Error en:" + lista.listadetokens.get(contador - 1).token + " linea " + lista.listadetokens.get(contador - 1).linea);
                    } else {

                        //el operador de comparacion
                        if ((lista.listadetokens.get(contador).tipo.equals("Operador Comparacion")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {

                            contador++;//siguiente token numero o identificador
                            if (contador >= longitudFrase) {
                                System.out.println("Error en:" + lista.listadetokens.get(contador - 1).token + " linea " + lista.listadetokens.get(contador - 1).linea);
                                //el segundo termino, identificador o numero  
                            } else {
                                if ((lista.listadetokens.get(contador).tipo.equals("numero") || lista.listadetokens.get(contador).tipo.equals("Identificador")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {

                                    contador++; //token :

                                    if (contador >= longitudFrase) {
                                        System.out.println("Error en:" + lista.listadetokens.get(contador - 1).token + " linea " + lista.listadetokens.get(contador - 1).linea);
                                    } else {

                                        if ((lista.listadetokens.get(contador).token.equals(":")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                                            error = false;
                                        } else {
                                            System.out.println("Error en:" + lista.listadetokens.get(contador - 1).token + " linea " + lista.listadetokens.get(contador - 1).linea);
                                        }
                                    }

                                } else {
                                    System.out.println("Error en:" + lista.listadetokens.get(contador).token + " linea " + lista.listadetokens.get(contador).linea);

                                }
                            }
                        } else {
                            System.out.println("Error en:" + lista.listadetokens.get(contador).token + " linea " + lista.listadetokens.get(contador).linea);
                        }
                    }

                } else {
                    System.out.println("Error en:" + lista.listadetokens.get(contador).token + " linea " + lista.listadetokens.get(contador).linea);
                }

            } else {
                System.out.println("Error en:" + lista.listadetokens.get(contador).token + " linea " + lista.listadetokens.get(contador).linea);
            }

        }
    }

    public void qElif() {
        int IdentacionesNoeliminadas = listaEspacios.size();

        Iterator it = listaEspacios.iterator();
        while (it.hasNext()) {

            int x = (Integer) it.next();
            if (x > lista.listadetokens.get(contador).espacios) {
                it.remove();
                IdentacionesNoeliminadas--;

            }

        }

        IdentacionesNoeliminadas--;

        int aux = 0;
        Iterator i = listaEspaciosInden.iterator();
        while (i.hasNext()) {
            int x = (Integer) i.next();

            if ((aux >= IdentacionesNoeliminadas)) {

                i.remove();
            }
            aux++;
        }

        aux = 0;
        Iterator i4 = listaTokenEspacio.iterator();
        while (i4.hasNext()) {
            String x = (String) i4.next();

            if ((aux >= IdentacionesNoeliminadas)) {

                i4.remove();
            }
            aux++;
        }

        if (IdentacionesNoeliminadas > 0) {
            anidado = listaEspaciosInden.get(listaEspaciosInden.size() - 1);

            //aqui hay error    cambie el != por >=  luego >=  por >  AL FINAL DE NUEVO !=
        }

        listaEspacios.add(lista.listadetokens.get(contador).espacios);
        listaTokenEspacio.add("elif");
        linea = Integer.parseInt(lista.listadetokens.get(contador).linea);
        contador++;

        QExpresion();
        if (error == false) {
            anidado = 0;
            aprobado = false;
            contador++; //dos puntos4

            qGeneral();

        }

    }

    public void qElse() {
        int IdentacionesNoeliminadas = listaEspacios.size();

        Iterator it = listaEspacios.iterator();
        while (it.hasNext()) {

            int x = (Integer) it.next();
            if (x > lista.listadetokens.get(contador).espacios) {
                it.remove();
                IdentacionesNoeliminadas--;

            }

        }

        IdentacionesNoeliminadas--;

        int aux = 0;
        Iterator i = listaEspaciosInden.iterator();
        while (i.hasNext()) {
            int x = (Integer) i.next();

            if ((aux >= IdentacionesNoeliminadas)) {

                i.remove();
            }
            aux++;
        }
        aux = 0;
        Iterator i4 = listaTokenEspacio.iterator();
        while (i4.hasNext()) {
            String x = (String) i4.next();

            if ((aux >= IdentacionesNoeliminadas)) {

                i4.remove();
            }
            aux++;
        }

        if (IdentacionesNoeliminadas > 0) {
            anidado = listaEspaciosInden.get(listaEspaciosInden.size() - 1);

            //aqui hay error    cambie el != por >=  luego >=  por >  AL FINAL DE NUEVO !=
        }

        listaEspacios.add(lista.listadetokens.get(contador).espacios);
        listaTokenEspacio.add("else");
        linea = Integer.parseInt(lista.listadetokens.get(contador).linea);
        contador++;
        if (contador >= longitudFrase) {
            System.out.println("Error en:  " + lista.listadetokens.get(contador - 1).token);

        } else {

            if ((lista.listadetokens.get(contador).token.equals(":")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                anidado = 0;
                aprobado = false;
                contador++; //dos puntos4
                System.out.println("el ultimo indentado" + listaEspacios.get(listaEspacios.size() - 1));

                qGeneral();
            } else {
                System.out.println("Error en:  -" + lista.listadetokens.get(contador).token);
            }
        }
    }

    public void qFor() {
        if (contador >= longitudFrase) {
            System.out.println("error en:" + lista.listadetokens.get(contador).token);

        } else {
            System.out.println(lista.listadetokens.get(contador).tipo);
            //revisa que despues del if haya un identificador  
            if ((lista.listadetokens.get(contador).tipo.equals("Identificador")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                contador++;
                if (contador >= longitudFrase) {
                    System.out.println("error en:" + lista.listadetokens.get(contador - 1).token);

                } else {
                    System.out.println(lista.listadetokens.get(contador).token);
                    if ((lista.listadetokens.get(contador).token.equals("in")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                        contador++;
                        if (contador >= longitudFrase) {
                            System.out.println("error en:" + lista.listadetokens.get(contador - 1).token);

                        } else {

                            System.out.println(lista.listadetokens.get(contador).token);
                            //AQUI SE PUEDE DIVIDIR EN DOS FOR WORDS IN WORD  O  FOR I IN RANGE (5):
                            if ((lista.listadetokens.get(contador).token.equals("range")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                                contador++;
                                if (contador >= longitudFrase) {
                                    System.out.println("error en:" + lista.listadetokens.get(contador - 1).token);

                                } else {
                                    System.out.println(lista.listadetokens.get(contador).token);
                                    if ((lista.listadetokens.get(contador).token.equals("(")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                                        contador++;
                                        if (contador >= longitudFrase) {
                                            System.out.println("error en:" + lista.listadetokens.get(contador - 1).token);

                                        } else {
                                            System.out.println(lista.listadetokens.get(contador).token);
                                            if ((lista.listadetokens.get(contador).tipo.equals("numero")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                                                contador++;
                                                if (contador >= longitudFrase) {
                                                    System.out.println("error en:" + lista.listadetokens.get(contador - 1).token);

                                                } else {
                                                    System.out.println(lista.listadetokens.get(contador).token);
                                                    if ((lista.listadetokens.get(contador).token.equals(")")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                                                        contador++;
                                                        if (contador >= longitudFrase) {
                                                            System.out.println("error en:" + lista.listadetokens.get(contador - 1).token);

                                                        } else {
                                                            if ((lista.listadetokens.get(contador).token.equals(":")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                                                                contador++;
                                                                if (contador >= longitudFrase) {

                                                                } else {
                                                                    qGeneral();
                                                                }
                                                            }

                                                        }
                                                    } else {
                                                        System.out.println("error en:" + lista.listadetokens.get(contador - 1).token);
                                                    }

                                                }

                                            } else {
                                                System.out.println("error en:" + lista.listadetokens.get(contador - 1).token);
                                            }

                                        }

                                    } else {
                                        System.out.println("error en:" + lista.listadetokens.get(contador - 1).token);
                                    }
                                }

                            }//aqui es el otro tipo de for 
                            else {
                                if ((lista.listadetokens.get(contador).tipo.equals("Identificador")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                                    contador++;
                                    if (contador >= longitudFrase) {
                                        System.out.println("error en:" + lista.listadetokens.get(contador - 1).token);

                                    } else {
                                        if ((lista.listadetokens.get(contador).token.equals(":")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                                            contador++;

                                            System.out.println("fin for");
                                            qGeneral();
                                        } else {
                                            System.out.println("error en:" + lista.listadetokens.get(contador - 1).token);

                                        }

                                    }
                                } else {
                                    System.out.println("error en:" + lista.listadetokens.get(contador - 1).token);

                                }

                            }
                        }
                    } else {
                        System.out.println("error en:" + lista.listadetokens.get(contador - 1).token);
                    }
                }
            }
        }
    }

    public void qWhile() {
        QExpresion();
        if (error == false) {
            contador++;
            //revisar indentacion
            qGeneral();
        }
    }

    public void qPrint() {

        error = true;
        if (contador >= longitudFrase) {
            System.out.println("Error en:" + lista.listadetokens.get(contador - 1).token + " linea " + lista.listadetokens.get(contador - 1).linea);

        } else {

            //revisa que despues del if haya un identificador o numero 
            if ((lista.listadetokens.get(contador).token.equals("(")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                contador++;
                if (contador >= longitudFrase) {
                    System.out.println("Error en:" + lista.listadetokens.get(contador - 1).token + " linea " + lista.listadetokens.get(contador - 1).linea);

                } else {
                    if ((lista.listadetokens.get(contador).token.equals("\"") || (lista.listadetokens.get(contador).tipo.equals("Identificador"))) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {

                        if ((lista.listadetokens.get(contador).token.equals("\"")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                            contador++;
                            //funcion verificar palabra string
                            qString();
                        } else {
                            if ((lista.listadetokens.get(contador).tipo.equals("Identificador")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                                contador++;
                                error=false;
                            } 
                         }
                        if(error==false){
                            //siguiente paso
                            
                        }else{
                        System.out.println("Error en:" + lista.listadetokens.get(contador).token + " linea " + lista.listadetokens.get(contador).linea);
                     
                        }
                    //siguietne
                    }else{
                        System.out.println("Error en:" + lista.listadetokens.get(contador).token + " linea " + lista.listadetokens.get(contador).linea);
                       
                    }
                }
            } else {
                System.out.println("Error en:" + lista.listadetokens.get(contador).token + " linea " + lista.listadetokens.get(contador).linea);

            }

        }

    }

    
    public void qString(){
while(!lista.listadetokens.get(contador).token.equals("\"")){
    
    
    
    }


}

}
//verificar si el if puede tener numero no enteros

