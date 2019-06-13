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

    boolean brenda = true, aprobado = false, Condelse = false, error = false, dentroDefFuncion = false;
    public HashMap<String, String> tokensitos = new HashMap<String, String>();
    int contador, longitudFrase, linea, contadortokens = 0, espacio, anidado = 0, cicloIf = 0, nArr, paramaux = 0;
    String frase, token = "", NombreFun = "", nombreidT;
    arrayListLexico lista;
    ArrayList<Integer> listaEspacios, listaEspaciosInden, listaIF, Parentesis, insertado, fin;
    ArrayList<String> listaTokenEspacio = new ArrayList<String>();
    ArrayList<String> operaciones;
    ArrayList<Identificadores> identi;
    ArrayList<ArrayList<String>> operacionesarr;
    ArrayList<Integer>[] arrIF = new ArrayList[2];

    public AnalisisSemantico(arrayListLexico l) {
        this.operacionesarr = new ArrayList<ArrayList<String>>();
        this.identi = new ArrayList<Identificadores>();
        nArr = 0;
        this.listaEspacios = new ArrayList<Integer>();
        this.listaEspaciosInden = new ArrayList<Integer>();
        this.Parentesis = new ArrayList<Integer>();
        insertado = new ArrayList<Integer>();
        fin = new ArrayList<Integer>();
        ArrayList<String> listaParametrosAux = new ArrayList<String>();
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
                if (lista.listadetokens.get(contador).token.equals("def")) {

                    linea = Integer.parseInt(lista.listadetokens.get(contador).linea);
                    int espacios = lista.listadetokens.get(contador).espacios;
                    if (espacios == 0) {
                        dentroDefFuncion = true;
                        anidado = 0;
                        aprobado = false;

                        listaEspacios.add(lista.listadetokens.get(contador).espacios);
                        listaTokenEspacio.add("def");
                        contador++;
                        qDefFuncion();

                        if (error == false) {
                            System.out.println(NombreFun + "¿ya termino?");
                            qGeneral();
                            //   System.out.println("...fin de  funcion en " + lista.listadetokens.get(contador).linea);
                            //aqui se llamaria a q0 ¿n0?

                            q0();
                        } else {
                            System.out.println("error fun");
                        }
                    } else {
                        error = true;
                        System.out.println("una definicion de funciones no debe esta indentada >:v, revisa" + lista.listadetokens.get(contador).linea);

                    }

                } else {
                    if (error == false) {
                        qGeneral();
                    }
                }
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
            String aaaa = lista.listadetokens.get(contador).token;
            if (!dentroDefFuncion == true || !lista.listadetokens.get(contador).token.equals("def")) {
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
                                String xe = lista.listadetokens.get(contador).token;
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
                                    //agregado

                                    aux = 0;
                                    Iterator i4 = listaTokenEspacio.iterator();
                                    while (i4.hasNext()) {
                                        String x = (String) i4.next();

                                        if ((aux >= IdentacionesNoeliminadas)) {

                                            i4.remove();
                                        }
                                        aux++;
                                    }
                                    //fin agregado 12/06/2019
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
                                        if (dentroDefFuncion) {
                                            System.out.println(NombreFun);
                                            System.out.println("repetira");
                                            System.out.println("aqui sale1");//
                                            //   q0();
                                            System.out.println("aqui sale2");
                                            dentroDefFuncion = false;

                                        } else {
                                            general2parte();
                                        }
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

            } else {
                dentroDefFuncion = false;

                listaEspacios.clear();

                listaTokenEspacio.clear();

                listaEspaciosInden.clear();
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
                    case "def": {
                        System.out.println("Error en" + lista.listadetokens.get(contador).token + " linea " + lista.listadetokens.get(contador).linea);

                    }
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
                        break;
                    case "import":
                        System.out.println("error libreria se define al principio");

                        break;
                }

                break;
            case "Identificador":
                paramaux = 0;
                if (lista.listadetokens.get(contador).tipo.equals("Identificador")) {
                    nombreidT = lista.listadetokens.get(contador).token;

                    linea = Integer.parseInt(lista.listadetokens.get(contador).linea);
                    contador++;
                    if (contador >= longitudFrase) {
                        System.out.println("Error");
                    } else {
                        if (lista.listadetokens.get(contador).token.equals("=")) {
                            contador++;
                            if (contador >= longitudFrase) {
                                System.out.println("Error");
                            } else {
                                if (!lista.listadetokens.get(contador).token.equals("\"")) {
                                    System.out.println("no sera un string");
                                    // contador++;
                                    error = false;
                                    qAsignacion(); //aqiu regresa el contador como 1 más
                                    if (error == false) {
                                        operacionesarr.add(operaciones);
                                        float valor = resultadoOperaciones(operacionesarr.get(0));
                                        int indice = -1, co = 0;
                                        for (Identificadores x : identi) {
                                            if (x.nombre.equals(nombreidT)) {
                                                System.out.println("ya existe variable");
                                                if (x.tipo.equals("funcion")) {
                                                    error = true;
                                                    System.out.println("esta variable se uso como funcion en linea" + lista.listadetokens.get(contador - 1).linea);
                                                }
                                                indice = co;
                                                break;
                                            }
                                            co++;
                                        }
                                        if (error == false) {
                                            if (indice == -1) {
                                                identi.add(new Identificadores(nombreidT, String.valueOf(valor), "variable float"));
                                            } else {
                                                identi.get(co).setTipo("variable float");
                                                identi.get(co).setValor(String.valueOf(valor));
                                                identi.get(co).setTipo("variable float");
                                            }

                                            operacionesarr.remove(0);

                                            qGeneral();
                                        }
                                    } else {
                                        System.out.println("es un string");
                                        error = false;
                                        contador++;
                                        String valor = qString();
                                        if (error == false) {
                                            System.out.println("se declaro bien el string");
                                            int indice = -1, co = 0;
                                            for (Identificadores x : identi) {
                                                if (x.nombre.equals(nombreidT)) {
                                                    System.out.println("ya existe variable");
                                                    if (x.tipo.equals("funcion")) {
                                                        error = true;
                                                        System.out.println("esta variable se uso como funcion en linea" + lista.listadetokens.get(contador - 1).linea);
                                                    }
                                                    indice = co;
                                                    break;
                                                }
                                                co++;
                                            }
                                            if (error == false) {
                                                if (indice == -1) {
                                                    identi.add(new Identificadores(nombreidT, String.valueOf(valor), "variable String"));
                                                } else {
                                                    identi.get(co).setTipo("variable String");
                                                    identi.get(co).setValor(String.valueOf(valor));

                                                }

                                                qGeneral();

                                                for (Identificadores x : identi) {
                                                    System.out.println(x.getNombre() + "pronsdf");
                                                    System.out.println(x.getValor());
                                                }
                                            }
                                        }
                                    }

                                }
                            }

                        } else {
                            if (lista.listadetokens.get(contador).token.equals("--") || lista.listadetokens.get(contador).token.equals("++") && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                                //revisar que existe declarada y que sea float
                                System.out.println("aumento");
                                int indice = -1, co = 0;
                                for (Identificadores x : identi) {
                                    if (x.nombre.equals(nombreidT)) {
                                        System.out.println("ya existe variable");
                                        if (!x.tipo.equals("variable float")) {
                                            error = true;
                                            System.out.println("esta variable no es numero" + lista.listadetokens.get(contador - 1).linea);
                                        }
                                        indice = co;
                                        break;
                                    }
                                    co++;
                                }
                                if (error == false && indice != -1) {

                                    contador++;
                                    qGeneral();

                                }
                            }
                            if (lista.listadetokens.get(contador).token.equals("(") && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                                System.out.println("llamada");
                                int indice = -1, co = 0;
                                for (Identificadores x : identi) {
                                    if (x.nombre.equals(nombreidT)) {
                                        System.out.println("ya existe variable");
                                        if (!x.tipo.equals("funcion")) {
                                            error = true;
                                            System.out.println("esta variable no es funcion" + lista.listadetokens.get(contador - 1).linea);
                                        }
                                        indice = co;
                                        break;
                                    }
                                    co++;
                                }

                                if (error == false && indice != -1) {
                                    System.out.println("tiene " + identi.get(co).listaParametros.size());
                                    if ((lista.listadetokens.get(contador).token.equals("(")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                                        contador++;
                                        if (contador >= longitudFrase) {
                                            System.out.println("Error en:" + lista.listadetokens.get(contador - 1).token + " linea " + lista.listadetokens.get(contador - 1).linea);
                                        } else {
                                            if ((lista.listadetokens.get(contador).token.equals(")") || lista.listadetokens.get(contador).tipo.equals("Identificador")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {

                                                if ((lista.listadetokens.get(contador).token.equals(")")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                                                    error = false;

                                                } else {
                                                    if (lista.listadetokens.get(contador).tipo.equals("Identificador") && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                                                        paramaux++;
                                                        contador++;
                                                        llamadaParametros();

                                                        if (contador >= longitudFrase) {
                                                            System.out.println("Error en:" + lista.listadetokens.get(contador - 1).token + " linea " + lista.listadetokens.get(contador - 1).linea);
                                                        } else {
                                                            if ((lista.listadetokens.get(contador).token.equals(")")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                                                                error = false;

                                                            }
                                                        }
                                                    }

                                                }
                                                if (error == false) {
                                                    contador++;
                                                    if (contador >= longitudFrase) {
                                                        System.out.println("Error en:" + lista.listadetokens.get(contador - 1).token + " linea " + lista.listadetokens.get(contador - 1).linea);
                                                    } else {
                                                        if ((lista.listadetokens.get(contador).token.equals(":")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                                                            contador++;
                                                            System.out.println("fin declaracion funcion");
                                                            dentroDefFuncion = true;
                                                            //                           qGeneral();
                                                        }
                                                    }

                                                } else {
                                                    System.out.println("Error en la definicion funcion" + NombreFun);
                                                }

                                            } else {
                                                System.out.println("Error en:" + lista.listadetokens.get(contador).token + " linea " + lista.listadetokens.get(contador).linea);

                                            }

                                        }
                                    } else {
                                        System.out.println("Error en:" + lista.listadetokens.get(contador).token + " linea " + lista.listadetokens.get(contador).linea);

                                    }
                                    if (error==false) {
                                        System.out.println("todo bien en tu declaracion");
                                    if(paramaux==identi.get(co).listaParametros.size()){
                                        System.out.println("parametros correcots");
                                    }else{
                                        System.out.println("revisa parametros funcion");
                                    error=true;
                                    }
                                    }
                                } else {
                                    System.out.println("esta variable ha sido declarada como funcion" + lista.listadetokens.get(contador - 1).linea);

                                }
                            }
                        }
                    }

                }

                break;

            default:
                System.out.println("error no se reconoce token");

        }
    }

    public void llamadaParametros() {
        error = false;
        if (contador >= longitudFrase) {
            System.out.println("Error en:" + lista.listadetokens.get(contador - 1).token + " linea " + lista.listadetokens.get(contador - 1).linea);
            error = true;
        } else {
            while (!lista.listadetokens.get(contador).token.equals(")") && error == false) {
                if (contador >= longitudFrase) {
                    error = true;
                    System.out.println("Error en:" + lista.listadetokens.get(contador - 1).token + " linea " + lista.listadetokens.get(contador - 1).linea);
                    break;
                } else {
                    if (lista.listadetokens.get(contador).token.equals(",") && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                        contador++; //falta revisar
                        if (contador >= longitudFrase) {
                            error = true;
                            System.out.println("Error en:" + lista.listadetokens.get(contador - 1).token + " linea " + lista.listadetokens.get(contador - 1).linea);
                            break;
                        } else {
                            if (lista.listadetokens.get(contador).tipo.equals("Identificador") && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                                paramaux++;
                            } else {
                                System.out.println("Error en:" + lista.listadetokens.get(contador).token + " linea " + lista.listadetokens.get(contador).linea);
                                error = true;
                                break;
                            }
                        }
                    } else {
                        System.out.println("Error en:" + lista.listadetokens.get(contador).token + " linea " + lista.listadetokens.get(contador).linea);
                        error = true;
                    }
                }
                contador++;
                if (contador >= longitudFrase) {
                    error = true;
                    break;
                }
            }

            if (error == true) {
                System.out.println("hay error");
            } else {
                System.out.println("termino parametros");
            }
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
                if ((lista.listadetokens.get(contador).tipo.equals("Identificador") || (lista.listadetokens.get(contador).tipo.equals("numero"))) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {

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
            System.out.println("Error en: " + lista.listadetokens.get(contador - 1).token + " linea " + lista.listadetokens.get(contador - 1).linea);

        } else {

            //revisa que despues del if haya un identificador o numero 
            if ((lista.listadetokens.get(contador).token.equals("(")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                contador++;
                if (contador >= longitudFrase) {
                    System.out.println("Error en: " + lista.listadetokens.get(contador - 1).token + " linea " + lista.listadetokens.get(contador - 1).linea);

                } else {
                    if ((lista.listadetokens.get(contador).token.equals("\"") || (lista.listadetokens.get(contador).tipo.equals("Identificador"))) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {

                        if ((lista.listadetokens.get(contador).token.equals("\"")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                            contador++;
                            //funcion verificar palabra string
                            qString();
                        } else {
                            if ((lista.listadetokens.get(contador).tipo.equals("Identificador")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                                contador++;
                                error = false;
                            }
                        }
                        if (error == false) {
                            //siguiente paso
                            if (contador >= longitudFrase) {
                                System.out.println("Error en: " + lista.listadetokens.get(contador - 1).token + " linea " + lista.listadetokens.get(contador - 1).linea);

                            } else {
                                if ((lista.listadetokens.get(contador).token.equals(")")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                                    contador++;

                                    qGeneral();

                                } else {
                                    System.out.println("Error en: " + lista.listadetokens.get(contador).token + " linea " + lista.listadetokens.get(contador).linea);

                                }

                            }

                        }
                        //siguietne
                    } else {
                        System.out.println("Error en: " + lista.listadetokens.get(contador).token + " linea " + lista.listadetokens.get(contador).linea);

                    }
                }
            } else {
                System.out.println("Error en: " + lista.listadetokens.get(contador).token + " linea " + lista.listadetokens.get(contador).linea);

            }

        }

    }

    public String qString() {
        String res = "";
        error = false;
        while (!lista.listadetokens.get(contador).token.equals("\"")) {
            res += lista.listadetokens.get(contador).token;
            if (lista.listadetokens.get(contador).token.equals("\\")) {
                contador++;
            }

            contador++;
            if (contador >= longitudFrase) {
                error = true;
                break;
            }
        }

        if (error == true) {
            System.out.println("hay error");
        } else {
            contador++;
        }
//!lista.listadetokens.get(contador).token.equals("\"")
        return res;
    }

    public void qDefFuncion() {
        error = true;
        if (contador >= longitudFrase) {
            System.out.println("Error");
        } else {
            if ((lista.listadetokens.get(contador).tipo.equals("Identificador")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                NombreFun = lista.listadetokens.get(contador).token;
                int indice = -1, co = 0;
                for (Identificadores x : identi) {
                    if (x.nombre.equals(NombreFun)) {
                        System.out.println("ya existe funcion");
                        indice = co;
                        break;
                    }
                    co++;
                }
                if (indice == -1) {
                    System.out.println("nueva funcion");
                    identi.add(new Identificadores(NombreFun, "funcion"));

                    contador++;
                    if (contador >= longitudFrase) {
                        System.out.println("Error en:" + lista.listadetokens.get(contador - 1).token + " linea " + lista.listadetokens.get(contador - 1).linea);
                    } else {
                        if ((lista.listadetokens.get(contador).token.equals("(")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                            contador++;
                            if (contador >= longitudFrase) {
                                System.out.println("Error en:" + lista.listadetokens.get(contador - 1).token + " linea " + lista.listadetokens.get(contador - 1).linea);
                            } else {
                                if ((lista.listadetokens.get(contador).token.equals(")") || lista.listadetokens.get(contador).tipo.equals("Identificador")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {

                                    if ((lista.listadetokens.get(contador).token.equals(")")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                                        error = false;

                                    } else {
                                        if (lista.listadetokens.get(contador).tipo.equals("Identificador") && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                                            identi.get(identi.size() - 1).listaParametros.add(lista.listadetokens.get(contador).token);

                                            contador++;
                                            qParametros();

                                            if (contador >= longitudFrase) {
                                                System.out.println("Error en:" + lista.listadetokens.get(contador - 1).token + " linea " + lista.listadetokens.get(contador - 1).linea);
                                            } else {
                                                if ((lista.listadetokens.get(contador).token.equals(")")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                                                    error = false;

                                                }
                                            }
                                        }

                                    }
                                    if (error == false) {
                                        contador++;
                                        if (contador >= longitudFrase) {
                                            System.out.println("Error en:" + lista.listadetokens.get(contador - 1).token + " linea " + lista.listadetokens.get(contador - 1).linea);
                                        } else {
                                            if ((lista.listadetokens.get(contador).token.equals(":")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                                                contador++;
                                                System.out.println("fin declaracion funcion");
                                                dentroDefFuncion = true;
                                                //                           qGeneral();
                                            }
                                        }

                                    } else {
                                        System.out.println("Error en la definicion funcion" + NombreFun);
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
                    error = true;
                    System.out.println("error de ambiguedad en funcion " + NombreFun + " en linea " + lista.listadetokens.get(contador).linea);
                }
            }
        }
    }

    public void qParametros() {
        error = false;
        if (contador >= longitudFrase) {
            System.out.println("Error en:" + lista.listadetokens.get(contador - 1).token + " linea " + lista.listadetokens.get(contador - 1).linea);
            error = true;
        } else {
            while (!lista.listadetokens.get(contador).token.equals(")") && error == false) {
                if (contador >= longitudFrase) {
                    error = true;
                    System.out.println("Error en:" + lista.listadetokens.get(contador - 1).token + " linea " + lista.listadetokens.get(contador - 1).linea);
                    break;
                } else {
                    if (lista.listadetokens.get(contador).token.equals(",") && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                        contador++; //falta revisar
                        if (contador >= longitudFrase) {
                            error = true;
                            System.out.println("Error en:" + lista.listadetokens.get(contador - 1).token + " linea " + lista.listadetokens.get(contador - 1).linea);
                            break;
                        } else {
                            if (lista.listadetokens.get(contador).tipo.equals("Identificador") && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                                identi.get(identi.size() - 1).listaParametros.add(lista.listadetokens.get(contador).token);

                            } else {
                                System.out.println("Error en:" + lista.listadetokens.get(contador).token + " linea " + lista.listadetokens.get(contador).linea);
                                error = true;
                                break;
                            }
                        }
                    } else {
                        System.out.println("Error en:" + lista.listadetokens.get(contador).token + " linea " + lista.listadetokens.get(contador).linea);
                        error = true;
                    }
                }
                contador++;
                if (contador >= longitudFrase) {
                    error = true;
                    break;
                }
            }

            if (error == true) {
                System.out.println("hay error");
            } else {
                System.out.println("termino parametros");
            }
        }
    }

    public void qAsignacion() { //al inicio solo puede haber un delimitador o un numero
        if (contador >= longitudFrase) {
            System.out.println("Error");
        } else {
            if (error == false) {
                if (Integer.parseInt(lista.listadetokens.get(contador).linea) == linea) {
                    operaciones = new ArrayList<String>();
                    if (lista.listadetokens.get(contador).token.equals("(") || lista.listadetokens.get(contador).token.equals("[")) {
                        contador++;
                        qDel();
                    } else {
                        if (lista.listadetokens.get(contador).tipo.equals("numeroFloat") || lista.listadetokens.get(contador).tipo.equals("numero")) {
                            operaciones.add((lista.listadetokens.get(contador).token));
                            contador++;
                            qNum();
                        }
                    }
                } else {
                    error = true;
                    System.out.println("error en linea: " + lista.listadetokens.get(contador).linea + " La asignacicon debe completarse");
                }
            }
        }
    }

    public void qOperaciones() {
        if (contador >= longitudFrase) {
            // qGeneral();
        } else {
            if (error == false) {
                if (Integer.parseInt(lista.listadetokens.get(contador).linea) == linea) {
                    if (lista.listadetokens.get(contador).token.equals("(") || lista.listadetokens.get(contador).token.equals("[")) {
                        contador++;
                        qDel();
                    } else {
                        if (lista.listadetokens.get(contador).tipo.equals("numeroFloat") || lista.listadetokens.get(contador).tipo.equals("numero")) {
                            operaciones.add((lista.listadetokens.get(contador).token));
                            contador++;
                            qNum();
                        } else {
                            if (lista.listadetokens.get(contador).tipo.equals("Operador Aritmetico")) {
                                operaciones.add((lista.listadetokens.get(contador).token));
                                contador++;
                                qOperadorAritmetico();
                            } else {
                                //aqui puede haber un ) queriendo regresar
                                if (!Parentesis.isEmpty()) {
                                    if (!lista.listadetokens.get(contador).token.equals(")")) {
                                        System.out.println("error en linea " + lista.listadetokens.get(contador).linea + "Error en asignacion token" + lista.listadetokens.get(contador).token);
                                        error = true;
                                    }

                                } else {
                                    System.out.println("error en linea " + lista.listadetokens.get(contador).linea + "Error en asignacion token" + lista.listadetokens.get(contador).token);
                                    error = true;
                                }
                            }
                        }
                    }
                } else {
                    contador++;
                    //       qGeneral();
                }
            }
        }
    }

    public void qDel() {
        if (contador >= longitudFrase) {
            System.out.println("Error");
            error = true;
        } else {
            if (error == false) {
                if (Integer.parseInt(lista.listadetokens.get(contador).linea) == linea) {

                    if (lista.listadetokens.get(contador - 1).token.equals("(")) {
                        Parentesis.add(1);
                        operaciones.add((lista.listadetokens.get(contador - 1).token));
                        qOperaciones();
                        if (contador >= longitudFrase) {
                            System.out.println("error en linea " + lista.listadetokens.get(contador - 1).linea + "falta cerrar (");
                            error = true;
                        } else {
                            if (lista.listadetokens.get(contador).token.equals(")") && Integer.parseInt(lista.listadetokens.get(contador).linea) == linea) {
                                operaciones.add((lista.listadetokens.get(contador).token));
                                Parentesis.remove(Parentesis.size() - 1);
                                contador++;
                                qOperaciones();

                            } else {
                                System.out.println("error en linea " + lista.listadetokens.get(contador).linea + "falta cerrar (");
                                error = true;
                            }
                        }
                    } else {
                        if (lista.listadetokens.get(contador - 1).token.equals("[")) {
                            operaciones.add((lista.listadetokens.get(contador - 1).token));
                            qOperaciones();
                            if (contador >= longitudFrase) {
                                System.out.println("error en linea " + lista.listadetokens.get(contador - 1).linea + "falta cerrar [");
                                error = true;
                            } else {
                                if (lista.listadetokens.get(contador).token.equals("]") && Integer.parseInt(lista.listadetokens.get(contador).linea) == linea) {
                                    operaciones.add((lista.listadetokens.get(contador).token));
                                    contador++;
                                    qOperaciones();

                                } else {
                                    System.out.println("error en linea " + lista.listadetokens.get(contador).linea + "falta cerrar ]");
                                    error = true;
                                }
                            }

                        } else {
                            System.out.println("error en linea " + lista.listadetokens.get(contador).linea + "Delimitador no correcto");
                            error = true;
                        }
                    }

                }
            }
        }
    }

    public void qNum() {
        if (contador >= longitudFrase) {
            // qGeneral();
        } else {
            if (error == false) {
                if (Integer.parseInt(lista.listadetokens.get(contador).linea) == linea) {

                    if (lista.listadetokens.get(contador).tipo.equals("Operador Aritmetico")) {
                        operaciones.add((lista.listadetokens.get(contador).token));
                        contador++;
                        qOperadorAritmetico();

                    } else {

                        error = true;
                        System.out.println("hay un error");

                    }

                } else {
                    //        qGeneral();
                }
            }
        }
    }

    public void qOperadorAritmetico() {
        if (contador >= longitudFrase) {
            error = true;
            System.out.println("error en linea " + lista.listadetokens.get(contador - 1).linea + "No puede terminar la asignacion con un operador");
            //  qGeneral();

        } else {
            if (error == false) {
                if (Integer.parseInt(lista.listadetokens.get(contador).linea) == linea) {
                    if (lista.listadetokens.get(contador).token.equals("(") || lista.listadetokens.get(contador).token.equals("[")) {
                        contador++;
                        qDel();
                    } else {
                        if (lista.listadetokens.get(contador).tipo.equals("numeroFloat") || lista.listadetokens.get(contador).tipo.equals("numero")) {
                            operaciones.add((lista.listadetokens.get(contador).token));
                            contador++;
                            qNum();
                        }

                    }
                } else {
                    error = true;
                    System.out.println("error en linea " + lista.listadetokens.get(contador - 1).linea + "No puede terminar la asignacion con un operador");

                }

            }
        }
    }

    public Float resultadoOperaciones(ArrayList<String> ope) {
        ArrayList<Float> numeros = new ArrayList();
        int auxC = 0;
        ArrayList<String> operadores = new ArrayList();
        HashMap<String, Integer> JerarquiaOpe = new HashMap<String, Integer>();
        JerarquiaOpe.put("+", 1);
        JerarquiaOpe.put("-", 1);
        JerarquiaOpe.put("*", 2);
        JerarquiaOpe.put("/", 2);
        JerarquiaOpe.put("^", 3);
        JerarquiaOpe.put("(", 0);
        JerarquiaOpe.put(")", 7);

        for (int i = 0; i < ope.size(); i++) {

            if (ope.get(i).equals("(")) {
                insertado.add(i);
                System.out.println("parentesis");
                nArr++;
                operacionesarr.add(new ArrayList());
                auxC++;
                i++;
                while (auxC > 0) {
                    if (ope.get(i).equals("(")) {
                        auxC++;
                    }

                    operacionesarr.get(nArr).add(ope.get(i));
                    System.out.println(ope.get(i));
                    if (ope.get(i).equals(")")) {
                        auxC--;
                    }
                    i++;
                }
                fin.add(i);
                operacionesarr.get(nArr).remove(operacionesarr.get(nArr).size() - 1);
                for (String x : operacionesarr.get(nArr)) {
                    System.out.println(x);
                }
                float medio = resultadoOperaciones(operacionesarr.get(nArr));
                numeros.add(medio);
                System.out.println("regreso");
                System.out.println("res parentesis" + medio);
                Iterator<String> iterado = ope.iterator();
                int auIterador = 0;
                while (iterado.hasNext()) {
                    String s = iterado.next();
                    if (auIterador >= insertado.get(insertado.size() - 1) && auIterador < fin.get(fin.size() - 1)) {
                        iterado.remove();

                    }
                    auIterador++;
                }
                ope.add(insertado.get(insertado.size() - 1), String.valueOf(medio));
                if (!insertado.isEmpty()) {
                    i = insertado.get(insertado.size() - 1);
                }
                insertado.remove(insertado.size() - 1);
                fin.remove(fin.size() - 1);

            } else {

                if (JerarquiaOpe.containsKey(ope.get(i))) {
                    if (!operadores.isEmpty()) {
                        //no esta vacia so, a verificar
                        int ultimoLista = JerarquiaOpe.get(operadores.get(operadores.size() - 1));
                        int act = JerarquiaOpe.get(ope.get(i));
                        if (ultimoLista < act) {
                            operadores.add((ope.get(i)));
                        } else {

                            do {
                                String op = operadores.get(operadores.size() - 1);
                                float num1 = numeros.get(numeros.size() - 2), num2 = numeros.get(numeros.size() - 1);
                                float res = operacionBasica(num1, num2, op);
                                numeros.remove(numeros.size() - 1);
                                numeros.remove(numeros.size() - 1);
                                numeros.add(res);
                                operadores.remove(operadores.size() - 1);
                                if (operadores.isEmpty()) {
                                    ultimoLista = 0;
                                } else {
                                    ultimoLista = JerarquiaOpe.get(operadores.get(operadores.size() - 1));
                                }
                            } while ((ultimoLista > act) && (!operadores.isEmpty()));
                            operadores.add((ope.get(i)));
                            System.out.println("otra parte");
                        }
                    } else {
                        operadores.add((ope.get(i)));
                    }
                } else {
                    numeros.add(Float.parseFloat(ope.get(i)));
                }

            }
        }

        while (!operadores.isEmpty()) {
            String op = operadores.get(operadores.size() - 1);
            float num1 = numeros.get(numeros.size() - 2), num2 = numeros.get(numeros.size() - 1);
            float res = operacionBasica(num1, num2, op);
            numeros.remove(numeros.size() - 1);
            numeros.remove(numeros.size() - 1);
            numeros.add(res);
            operadores.remove(operadores.size() - 1);

        }
        System.out.println(numeros.get(0));
        return numeros.get(0);

    }

    public Float operacionBasica(float numero1, float num2, String Ope) {
        float res = 0;
        switch (Ope) {
            case "+":
                res = numero1 + num2;
                break;
            case "-":
                res = numero1 - num2;
                break;
            case "*":
                res = numero1 * num2;
                break;
            case "/":
                res = numero1 / num2;
                break;
            case "^":
                res = (float) Math.pow(numero1, num2);
        }
        return res;
    }

}
//verificar si el if puede tener numero no enteros

