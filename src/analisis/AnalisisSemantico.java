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

    boolean brenda = true, aprobado = false;
    public HashMap<String, String> tokensitos = new HashMap<String, String>();
    int contador, longitudFrase, linea;
    String frase;
    String token = "";
    int contadortokens = 0;
    arrayListLexico lista;
    int espacio, anidado = 0;
    ArrayList<Integer> listaEspacios, listaEspaciosInden;

    public AnalisisSemantico(arrayListLexico l) {
        this.listaEspacios = new ArrayList<Integer>();
        this.listaEspaciosInden = new ArrayList<Integer>();
        contador = 0;
        lista = l;
        longitudFrase = lista.listadetokens.size();
        System.out.println("semantico");
    }

    public void q0() {
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

    public void qLibrerias() {  if (contador >= longitudFrase) {
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

        boolean hayerror = false;
        if (contador >= longitudFrase) {

        } else {
            if (!listaEspacios.isEmpty()) {
                //si el ultimo token tiene un espaciado mayor al del ultimo agregado de la lista de espacios entonces se aprueba
                if (lista.listadetokens.get(contador).espacios > listaEspacios.get(listaEspacios.size() - 1)) {
                    if (anidado == 0) {
                        listaEspaciosInden.add(lista.listadetokens.get(contador).espacios);
                        anidado = listaEspaciosInden.get(listaEspaciosInden.size() - 1);
                        aprobado = true;
                    } else {

                        if (lista.listadetokens.get(contador).espacios == anidado) {

                        } else {
                            hayerror = true;
                            System.out.println("ERROR, NO ES CORRECTA LA INDENTACION  " + lista.listadetokens.get(contador).token + " linea " + lista.listadetokens.get(contador).linea);
                        }
                    }

                } else {
                    //si el ultimo token tiene un espaciado menor al del ultimo agregado de la lista de espacios entonces se aprueba
                    int IdentacionesNoeliminadas = listaEspacios.size();

                    if (aprobado == true) {
                        Iterator it = listaEspacios.iterator();

                        while (it.hasNext()) {

                            int x = (Integer) it.next();
                            if (x > lista.listadetokens.get(contador).espacios) {
                                it.remove();
                                IdentacionesNoeliminadas--;

                            }

                        }
                        IdentacionesNoeliminadas--;

                        //System.out.println("los que aun no han salido");
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

                    } else {
                        hayerror = true;
                        System.out.println("error indentacion" + lista.listadetokens.get(contador).token + " linea " + lista.listadetokens.get(contador).linea);
                    }
                }
                if (hayerror == false) {
                    general2parte();
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

    }

    public void general2parte() {
        System.out.println(lista.listadetokens.get(contador).tipo);
        System.out.println(lista.listadetokens.get(contador).token);
        switch (lista.listadetokens.get(contador).tipo) {
            case "Reservada":
                switch (lista.listadetokens.get(contador).token) {
                    case "if":
                        if (lista.listadetokens.get(contador).token.equals("if")) {
                            anidado = 0;
                            aprobado = false;
                            linea = Integer.parseInt(lista.listadetokens.get(contador).linea);
                            listaEspacios.add(lista.listadetokens.get(contador).espacios);
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
                            contador++;
                            qFor();

                        }

                    }
                    break;
                    case "print":
                        contador++;
                        //temporal
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
        if (contador >= longitudFrase) {
            System.out.println("Error");
        } else {

            //revisa que despues del if haya un identificador o numero 
            if ((lista.listadetokens.get(contador).tipo.equals("numero") || lista.listadetokens.get(contador).tipo.equals("Identificador")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                //verifica que al inicio haya un identificador aqui no va contadaor
                if (lista.listadetokens.get(contador).tipo.equals("Identificador")) {

                    contador++; //siguiente token operador comparacion

                    if (contador >= longitudFrase) {
                        System.out.println("Error en:" + lista.listadetokens.get(contador - 1).token);
                    } else {

                        //el operador de comparacion
                        if ((lista.listadetokens.get(contador).tipo.equals("Operador Comparacion")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {

                            contador++;//siguiente token numero o identificador
                            if (contador >= longitudFrase) {
                                System.out.println("Error en  -: " + lista.listadetokens.get(contador - 1).token);
                                //el segundo termino, identificador o numero  
                            } else {
                                if ((lista.listadetokens.get(contador).tipo.equals("numero") || lista.listadetokens.get(contador).tipo.equals("Identificador")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {

                                    contador++; //token :
                                    if (contador >= longitudFrase) {
                                        System.out.println("Error en:  -" + lista.listadetokens.get(contador - 1).token);
                                    } else {

                                        if ((lista.listadetokens.get(contador).token.equals(":"))) {

                                            contador++;
                                            //revisar indentacion

                                            qGeneral();

                                            qGeneral();

                                        } else {
                                            System.out.println("error en  *:" + lista.listadetokens.get(contador).token);
                                        }
                                    }

                                } else {
                                    System.out.println("error en:" + lista.listadetokens.get(contador).token);
                                }
                            }
                        } else {
                            System.out.println("error en:" + lista.listadetokens.get(contador).token);
                        }
                    }

                } else {
                    System.out.println("error en:" + lista.listadetokens.get(contador).token);
                }

            } else {
                System.out.println("error en:" + lista.listadetokens.get(contador).token);
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
                    System.out.println("error en:" + lista.listadetokens.get(contador-1).token);

                } else {
                    System.out.println(lista.listadetokens.get(contador).token);
                    if ((lista.listadetokens.get(contador).token.equals("in")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                        contador++;
                        if (contador >= longitudFrase) {
                            System.out.println("error en:" + lista.listadetokens.get(contador-1).token);

                        } else {

                            System.out.println(lista.listadetokens.get(contador).token);
                            //AQUI SE PUEDE DIVIDIR EN DOS FOR WORDS IN WORD  O  FOR I IN RANGE (5):
                            if ((lista.listadetokens.get(contador).token.equals("range")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                                contador++;
                                if (contador >= longitudFrase) {
                                    System.out.println("error en:" + lista.listadetokens.get(contador-1).token);

                                } else {
                                    System.out.println(lista.listadetokens.get(contador).token);
                                    if ((lista.listadetokens.get(contador).token.equals("(")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                                        contador++;
                                        if (contador >= longitudFrase) {
                                            System.out.println("error en:" + lista.listadetokens.get(contador-1).token);

                                        } else {
                                            System.out.println(lista.listadetokens.get(contador).token);
                                            if ((lista.listadetokens.get(contador).tipo.equals("numero")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                                                contador++;
                                                if (contador >= longitudFrase) {
                                                    System.out.println("error en:" + lista.listadetokens.get(contador-1).token);

                                                } else {
                                                    System.out.println(lista.listadetokens.get(contador).token);
                                                    if ((lista.listadetokens.get(contador).token.equals(")")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                                                        contador++;
                                                        if (contador >= longitudFrase) {
                                                            System.out.println("error en:" + lista.listadetokens.get(contador-1).token);

                                                        } else {
                                                            if ((lista.listadetokens.get(contador).token.equals(":")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                                                                contador++;
                                                                if (contador >= longitudFrase) {
                                                                  
                                                                } else {
                                                                    qGeneral();
                                                                }
                                                            }

                                                        }
                                                    }else{ System.out.println("error en:" + lista.listadetokens.get(contador-1).token);
}

                                                }

                                            }else{ System.out.println("error en:" + lista.listadetokens.get(contador-1).token);
}

                                        }

                                    }else{ System.out.println("error en:" + lista.listadetokens.get(contador-1).token);
}
                                }

                            }//aqui es el otro tipo de for 
                            else {
                                if ((lista.listadetokens.get(contador).tipo.equals("Identificador")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                                    contador++;
                                    if (contador >= longitudFrase) {
                                        System.out.println("error en:" + lista.listadetokens.get(contador-1).token);

                                    } else {
                                        if ((lista.listadetokens.get(contador).token.equals(":")) && linea == Integer.parseInt(lista.listadetokens.get(contador).linea)) {
                                            contador++;

                                            System.out.println("fin for");
                                            qGeneral();
                                        } else {
                                            System.out.println("error en:" + lista.listadetokens.get(contador-1).token);

                                        }

                                    }
                                } else {
                                    System.out.println("error en:" + lista.listadetokens.get(contador-1).token);

                                }

                            }
                        }
                    } else {
                        System.out.println("error en:" + lista.listadetokens.get(contador-1).token);
                    }
                }
            }
        }
    }
}
//verificar si el if puede tener numero no enteros
