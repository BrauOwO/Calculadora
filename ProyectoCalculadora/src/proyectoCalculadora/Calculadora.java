/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoCalculadora;

import pilas.PilaADT;
import pilas.PilaA;
import java.util.ArrayList;

/**
 *
 * 
 * <pre>
 * Clase Calculadora
 * 
 * Contiene las funciones para ser una calculadora sencilla 
 * Usa la tecnica de pasar de infijo a posfijo
 * </pre>
 * 
 * @author Braulio César Partida Meneses
 * 
 */
public class Calculadora {

    /**
     * Constructor
     */
    public Calculadora() {
    }

    /**
     * obtieneTokens transforma una cadena a un array
     * 
     * @param cadena de string
     * @return String[] con la cadena separada cada que hay un espacio
     */
    public String[] obtieneTokens(String cadena) {
        String[] strings = cadena.split(" ");
        return strings;
    }

    /**
     * conviertePosfija recibe un String el cual convierte de infijo a posfijo.
     * 
     * @param cadena de string
     * @see espaciarString
     * @return String[] con la cadena en modo posfijo
     */
    public String[] conviertePosfija(String cadena) {
        String[] elementos; // arreglo de elementos de la expresión infija
        String[] resultado; // arreglo de elementos de la expresión postfija
        elementos = obtieneTokens(cadena); // método que convierte la cadena en un arreglo de elementos
        ArrayList<String> postfija = new ArrayList(); // lista que contendrá los elementos de la expresión postfija
        PilaADT<String> pila = new PilaA(); // pila que ayudará a convertir la expresión infija en postfija
        int e, n;

        e = 0; // índice para recorrer el arreglo de elementos
        n = elementos.length; // tamaño del arreglo de elementos

        boolean negInicial = "-".equals(elementos[0]); // bandera para detectar si el primer elemento es un signo
                                                       // negativo
        if (negInicial) {
            postfija.add("-1"); // agregar un -1 a la lista de elementos postfija
            e++; // mover el índice para evitar volver a procesar el signo negativo
        }

        while (e < n) { // recorrer el arreglo de elementos
            String ele = elementos[e]; // obtener el elemento actual
            if (prioridad(ele) != 0) {// es un operador
                while (!pila.isEmpty() && !"(".equals(pila.peek()) && prioridad(ele) <= prioridad(pila.peek()))
                    // el nuevo operador bota de la pila al resultado a los de mayor o igual
                    // prioridad
                    postfija.add(pila.pop()); // agregar a la lista de elementos postfija los operadores de mayor o
                                              // igual prioridad que estén en la pila
                pila.push(ele); // agregar el operador actual a la pila
            } else {
                switch (ele) {
                    case "(" -> pila.push(ele); // agregar un paréntesis abierto a la pila
                    case ")" -> {
                        // vacío los operadores hasta encontrar el paréntesis que abría
                        while (!"(".equals(pila.peek())) // mientras no se encuentre el paréntesis abierto
                            postfija.add(pila.pop()); // agregar a la lista de elementos postfija los operadores que
                                                      // estén en la pila
                        pila.pop(); // quitar el paréntesis abierto de la pila
                        if (negInicial) {
                            postfija.add("*"); // agregar un asterisco a la lista de elementos postfija
                            negInicial = false; // desactivar la bandera que detecta el signo negativo inicial
                        }
                    }
                    default -> postfija.add(ele); // agregar un operando a la lista de elementos postfija
                }
            }
            e++; // mover el índice al siguiente elemento
        }
        while (!pila.isEmpty()) {// vaciar operadores restantes
            postfija.add(pila.pop()); // agregar a la lista de elementos postfija los operadores que quedaron en la
                                      // pila
        }

        resultado = new String[postfija.size()]; // crear un arreglo del tamaño de la lista de elementos postfija
        for (int i = 0; i < resultado.length; i++) {
            resultado[i] = postfija.get(i); // copiar los elementos de la lista de elementos postfija al arreglo
                                            // resultado
        }

        return resultado;
    }

    /**
     * prioridad es un auxiliar para el manejo de las prioridades de los operadores,
     * regresa 0 si
     * cuando el dato dado es un "(".
     * 
     * @param dato en String
     * @return el int del resultado del switch
     */
    private int prioridad(String dato) {
        int resultado = 0; // En caso de que el dato sea un paréntesis izquierdo

        switch (dato) {
            case "+", "-" -> resultado = 1;
            case "*", "/" -> resultado = 2;
        }
        return resultado;
    }

    /**
     * calcula se encarga de dar el resultado de la operacion ingresada.
     * 
     * @param entrada un String[] recibido de conviertePosfija
     * @see conviertePosfija
     * @return String con el resultado
     */
    public String calcula(String[] entrada) {
        PilaADT<Double> pila = new PilaA();
        double op1, op2, resul;
        String resultado = "";
        int i;

        resul = 0;
        i = 0;
        try {
            while (i < entrada.length && entrada[i] != null) {
                if (noEsOperador(entrada[i])) // Es operando
                    pila.push(Double.valueOf(entrada[i]));
                else { // Es operador
                    op2 = pila.pop();
                    op1 = pila.pop();
                    switch (entrada[i].charAt(0)) {
                        case '+' -> resul = op1 + op2;
                        case '-' -> resul = op1 - op2;
                        case '*' -> resul = op1 * op2;
                        case '/' -> {
                            if (op2 == 0) { // Si el denominador es 0 se lanza una excepción
                                resultado = "No se puede dividir entre 0";
                                throw new RuntimeException();
                            }
                            resul = op1 / op2;
                        }
                        default -> resul = op1 + op2;
                    }
                    pila.push(resul);
                }
                i++;
            }
            resultado = "" + pila.pop();
            return resultado;
        } catch (RuntimeException e) {
            if ("".equals(resultado)) {
                resultado = "Expresión no valida";
            }
            return resultado;

        }
    }

    /**
     * no es operador es un auxiliar para el manejo de las prioridades de los
     * operadores.
     * 
     * @param dato de un String
     * @return
     *         <ul>
     *         <li>true: Si no es operado</li>
     *         <li>false: Si es un operador</li>
     *         </ul>
     */
    private boolean noEsOperador(String dato) {
        return !dato.equals("+") && !dato.equals("-") && !dato.equals("*") && !dato.equals("/");
    }

    /**
     * espaciarString divide el String divido en espacio, diferenciando entre
     * numeros
     * 
     * @param cadena de String
     * @return String con espacios
     */
    public String espaciarString(String cadena) {
        String text = ""; // Se inicializa la variable que contendrá la cadena con espacios

        for (int i = 0; i < cadena.length(); i++) { // Se recorre la cadena caracter por caracter
            char c = cadena.charAt(i); // Se obtiene el caracter en la posición i

            if (!Character.isDigit(c) && c != '-' && c != '.') { // Si el caracter no es un dígito, un signo negativo ni
                                                                 // un punto
                text += " " + c + " "; // Se agrega el caracter con espacios alrededor
            } else {
                if (c == '-' && booleanExcepcion(cadena, (i - 1))) { // Si el caracter es un signo negativo y el
                                                                     // caracter anterior cumple cierta condición
                    text += " " + c + " "; // Se agrega el caracter con espacios alrededor
                } else { // Si no se cumple ninguna de las condiciones anteriores
                    text += c; // Se agrega el caracter sin espacios
                }
            }

        }

        String replaceAll = text.replaceAll("\\s+", " "); // Se eliminan los espacios redundantes
        String trim = replaceAll.trim(); // Se eliminan los espacios al inicio y al final
        return trim; // Se retorna la cadena con espacios
    }

    /**
     * booleanExcepcion checa si un caracter es un digito y evita el error de no
     * econtrar characteres
     * 
     * @param cadena      de String
     * @param charABuscar int que indica el lugar donde se busca
     * @see espaciarString
     * @return
     *         <ul>
     *         <li>true: si es un digito</li>
     *         <li>false: si no es un digito o es un error</li>
     *         </ul>
     */

    public boolean booleanExcepcion(String cadena, int charABuscar) {
        boolean prueba; // Declaración de la variable booleana prueba
        try {
            char charAt = cadena.charAt(charABuscar); // Se obtiene el caracter de la cadena en la posición indicada por
                                                      // charABuscar
            prueba = Character.isDigit(charAt); // Se verifica si el caracter es un dígito
            if (prueba || charAt == ')') { // Si es un dígito o un paréntesis de cierre, la prueba es verdadera
                prueba = true;
            } else { // Si no, la prueba es falsa
                prueba = false;

            }
            return prueba; // Se retorna el valor de la prueba
        } catch (Exception e) { // Si se produce alguna excepción
            return prueba = false; // La prueba es falsa y se retorna este valor
        }
    }

    /**
     * calcular usa todas las funciones y las engloba en una
     * 
     * @param cadena de String
     * @see espaciarString
     * @see conviertePosfija
     * @see calcula
     * @return String con el resultado
     */
    public String calcular(String cadena) {
        cadena = calcula(conviertePosfija(espaciarString(arreglaParentesisMultiplicacion(cadena))));
        return cadena;

    }

    /**
     * Método que agrega un signo de multiplicación entre paréntesis sin operador
     * entre ellos.
     * 
     * @author Samuel Orduña
     * @param a cadena de String
     * @return Cadena arreglada
     */
    public String arreglaParentesisMultiplicacion(String a) {
        // Método que agrega un signo de multiplicación entre paréntesis sin operador
        // entre ellos.
        StringBuilder sb = new StringBuilder();
        int n = a.length();
        for (int i = 0; i < n; i++) {
            char c = a.charAt(i);
            sb.append(c);
            if (c == ')' && i < n - 1 && a.charAt(i + 1) == '(') {
                sb.append('*');
            }
        }

        return sb.toString();

    }

    public static void main(String[] args) {
        Calculadora calc = new Calculadora();

        System.out.println(calc.calcular("((1+2)-(3+5)-9)*4"));
        System.out.println(calc.calcular(""));
        System.out.println(calc.calcular("2+3*4"));
        System.out.println(calc.calcular("2+3+4+5+6"));
        System.out.println(calc.calcular("2+3+4*5+6"));
        System.out.println(calc.calcular("2*3/4*5/6"));
        System.out.println(calc.calcular("((9)+((-9)))"));
        System.out.println(calc.calcular("(-.9*9)"));
        System.out.println(calc.calcular("2+3/(5-2.5*2)"));
        System.out.println(calc.calcular("-1+212*(-8/-94.56+-9)"));
        System.out.println(calc.calcular("-(1+212)*(-8/-94.56+-9)"));
        System.out.println(calc.calcular("(2)(2)"));

    }
}
