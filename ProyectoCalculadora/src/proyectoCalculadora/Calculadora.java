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
     *Constructor
     */
    public Calculadora(){
    }    
    
    /**
     * obtieneTokens transforma una cadena a un array
     * @param cadena de string
     * @return String[] con la cadena separada cada que hay un espacio
     */
    public String[] obtieneTokens(String  cadena){
        String[] strings = cadena.split(" ");
      return strings;
    }
    
    /**
     * conviertePosfija recibe un String el cual convierte de infijo a posfijo.
     * @param cadena de string
     * @see espaciarString
     * @return String[] con la cadena en modo posfijo
     */
    public String[] conviertePosfija(String  cadena) {
        String [] elementos;
        String [] resultado;
        elementos = obtieneTokens(cadena);
        ArrayList<String> postfija = new ArrayList();
        PilaADT <String> pila = new PilaA();
        int e, n;
        
        e = 0;
        n =  elementos.length;
        
        boolean negInicial = "-".equals(elementos[0]);
        if (negInicial){ 
            postfija.add("-1");
            e++;
        }
        
        while(e<n){
            String ele = elementos[e];
            if(prioridad(ele) != 0){//es un operador
                while (!pila.isEmpty() && !"(".equals(pila.peek()) && prioridad(ele) <= prioridad(pila.peek()))
                    //el nuevo operador bota de la pila al resultado a los de mayor o igual prioridad
                    postfija.add(pila.pop());
                pila.push(ele);
            } 
            else{
            switch (ele) {
                    case "(" -> pila.push(ele);
                    case ")" -> {
                        //vacío los operadores hasta encontrar el paréntesis que abría
                        while(!"(".equals(pila.peek()))
                            postfija.add(pila.pop());
                        pila.pop();
                        if (negInicial){
                            postfija.add("*");
                            negInicial = false;
                        }
                    }
                    default -> postfija.add(ele); //es un operando
                }
            }
            e++;
        }
        while(!pila.isEmpty()){//vaciar operadores restantes
            postfija.add(pila.pop());
        }
        
        resultado = new String[postfija.size()];
        for (int i = 0; i < resultado.length; i++) {
            resultado[i] = postfija.get(i);
        }
        
        return resultado;
    }
    
    /**
     * prioridad es un auxiliar para el manejo de las prioridades de los operadores, regresa 0 si 
     * cuando el dato dado es un "(". 
     * @param dato en String
     * @return el int del resultado del switch 
     */
    private int prioridad(String dato){
        int resultado = 0; // En caso de que el dato sea un paréntesis izquierdo
        
        switch (dato){
            case "+", "-" -> resultado = 1;
            case "*", "/" -> resultado = 2;
        }
        return resultado;
    }
    
    /**
     * calcula se encarga de dar el resultado de la operacion ingresada.
     * @param entrada un String[] recibido de conviertePosfija
     * @see conviertePosfija
     * @return String con el resultado
     */
    public String calcula(String [] entrada){
        PilaADT<Double> pila = new PilaA();
        double  op1, op2, resul;
        String resultado="";
        int i;
        
        resul = 0;
        i = 0;
        try {
            while (i < entrada.length && entrada[i] != null){
                if (noEsOperador(entrada[i])) // Es operando
                    pila.push(Double.valueOf(entrada[i]));
                else { // Es operador
                    op2 = pila.pop();
                    op1 = pila.pop();
                    switch (entrada[i].charAt(0)){
                        case '+' -> resul = op1 + op2;
                        case '-' -> resul = op1 - op2;
                        case '*' -> resul = op1 * op2;
                        case '/' -> {
                            if (op2 == 0){ // Si el denominador es 0 se lanza una excepción
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
            resultado = ""+pila.pop();
            return resultado;
        } catch (RuntimeException e) {
            if ("".equals(resultado)) {
                resultado = "Expresión no valida";
            }
            return resultado;
            
        }      
    }
    
    /**
     * no es operador es un auxiliar para el manejo de las prioridades de los operadores.
     * @param dato de un String
     * @return <ul>
     *      <li>true: Si no es operado</li>
     *      <li>false: Si es un operador</li>
     *      </ul>
     */
    private boolean noEsOperador(String dato){
        return !dato.equals("+") && !dato.equals("-") && !dato.equals("*") && !dato.equals("/");
    }
    
    
    
    
    /**
     * espaciarString divide el String divido en espacio, diferenciando entre numeros
     * @param cadena de String
     * @return String con espacios
     */    
    public String espaciarString(String cadena){
        String text ="";
        
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            
            if (!Character.isDigit(c) && c!='-' && c!='.') {
                text+=" "+c+" ";
                
            } else {
                if (c == '-' && booleanExcepcion(cadena, (i-1))) {
                    text+=" "+c+" ";
                    
                } else {
                    text+=c;
                }
            }
            
        }
        
        String replaceAll = text.replaceAll("\\s+", " ");
        String trim = replaceAll.trim();
        return  trim;
    }
    
    /**
     * booleanExcepcion checa si un caracter es un digito y evita el error de no econtrar characteres
     * @param cadena de String
     * @param charABuscar int que indica el lugar donde se busca 
     * @see espaciarString
     * @return <ul> 
     *      <li>true: si es un digito</li>
     *      <li>false: si no es un digito o es un error</li>
     *      </ul>
     */

    public boolean booleanExcepcion(String cadena, int charABuscar){
        boolean prueba ;
        try {
            char charAt = cadena.charAt(charABuscar);            
            prueba = Character.isDigit(charAt);
            if (prueba || charAt ==')') {
                prueba = true;
                
            } else {
                prueba = false;
                
            }
            return prueba;
            
        } catch (Exception e) {
            return prueba = false;
           
        } 
    }
    
    /**
     * calcular usa todas las funciones y las engloba en una 
     * @param cadena de String
     * @see espaciarString
     * @see conviertePosfija
     * @see calcula
     * @return String con el resultado
     */
    public String calcular(String cadena){
        cadena = calcula(conviertePosfija(espaciarString(arreglaParentesisMultiplicacion(cadena))));
        return  cadena;
        
    }
    
    /**
     * Método que agrega un signo de multiplicación entre paréntesis sin operador entre ellos.
     * @author Samuel Orduña
     * @param a cadena de String
     * @return Cadena arreglada 
     */
    public String arreglaParentesisMultiplicacion(String a){
        // Método que agrega un signo de multiplicación entre paréntesis sin operador entre ellos.
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
        
       
    }
}
