/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectocalculadora;

import java.util.ArrayList;

/**
 *
 * @author fernando.puron
 */
public class Calculadora {
    public Calculadora(){
    }    
    
    
    public String[] obtieneTokens(String  cadena){
        String[] ojo = cadena.split(" ");
      return ojo;
    }
    
    
    public String[] conviertePostfija(String  cadena) {
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
    
    /* Método auxiliar para el manejo de las prioridades de los operadores. Regresa 0,
     * el valor más pequeño, cuando el dato dado es un "(". De esta manera
     * el "(" sólo se saca de la pila cuando se encuentre un ")".
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
     * 
     * @param entrada
     * toda la clase evalua la muevo aqui por comodidad
     * @return 
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
    private boolean noEsOperador(String dato){
        return !dato.equals("+") && !dato.equals("-") && !dato.equals("*") && !dato.equals("/");
    }
    
    /* Método auxiliar para el manejo de las prioridades de los operadores. Regresa 0,
     * el valor más pequeño, cuando el dato dado es un "(". De esta manera
     * el "(" sólo se saca de la pila cuando se encuentre un ")".
     */
    
    
    /**
     * 
     * 
     * @param cadena
     * Este metodo divide el String divido en espacio, diferenciando entre numeros 
     * @return String aditado
     */    
    public String espaciarString(String cadena){
        String text ="";
        
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            
            if (!Character.isDigit(c) && c!='-' && c!='.') {
                text+=" "+c+" ";
                
            } else {
                if (c == '-' && boolenaExcepcionNum(cadena, (i-1))) {
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

    public boolean boolenaExcepcionNum(String cadena, int charABuscar){
        boolean prueba ;
        try {
            char charAt = cadena.charAt(charABuscar);            
            prueba = Character.isDigit(charAt);
            return prueba;
            
        } catch (Exception e) {
            return prueba = false;
           
        } 
    }
    
    public String calcEnUnPaso(String cadena){
        cadena = calcula(conviertePostfija(espaciarString(cadena)));
        return  cadena;
        
    }
    
    public static void main(String[] args) {
        Calculadora calc = new Calculadora();
        
        
        System.out.println(calc.calcEnUnPaso("-1+212*(-8/-94.56+-9)"));
        
        
    }
}
