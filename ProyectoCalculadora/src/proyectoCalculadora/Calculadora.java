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
        int e, p, n;
        
        e = 0;
        p = 0;
        n = elementos.length;
        while(e<n){
            String uwu = elementos[e];
            if(prioridad(uwu) != 0){//es un operador
                while (!pila.isEmpty() && !"(".equals(pila.peek()) && prioridad(uwu) <= prioridad(pila.peek()))
                    //el nuevo operador bota de la pila al resultado a los de mayor o igual prioridad
                    postfija.add(pila.pop());
                pila.push(uwu);
            } 
            else{
                switch (uwu) {
                    case "(" -> pila.push(uwu);
                    case ")" -> {
                        //vacío los operadores hasta encontrar el paréntesis que abría
                        while(!"(".equals(pila.peek()))
                            postfija.add(pila.pop());
                        pila.pop();
                    }
                    default -> postfija.add(uwu); //es un operando
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
        
        switch (dato.charAt(0)){
            case '+', '-' -> resultado = 1;
            case '*', '/' -> resultado = 2;
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
                        case '+': resul = op1 + op2;
                            break;
                        case '-': resul = op1 - op2;
                            break;
                        case '*': resul = op1 * op2;
                            break;
                        case '/': if (op2 == 0) // Si el denominador es 0 se lanza una excepción
                                        throw new RuntimeException();
                                  resul = op1 / op2;
                    }
                    pila.push(resul);                        
                    }
                i++;
            }
            resultado = ""+pila.pop();
            return resultado;
        } catch (RuntimeException e) {
            resultado = "inválida";
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
            if (!Character.isDigit(c)) {
                text+=" "+c+" ";
            } else {
                text+=c;
            }
        }
        
        String replaceAll = text.replaceAll("\\s+", " ");
        String trim = replaceAll.trim();
        
        
        
        
        
        return  trim;
        
    }
    
}
