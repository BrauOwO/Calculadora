/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectocalculadora;

/**
 *
 * @author fernando.puron
 */
public class Posfijo {
    private String  cadena;
    public Posfijo(){
    }    
    
    public Posfijo(String  cadena){
        this.cadena = cadena;
        
    }
    public String[] obtieneTokens(){
      return cadena.split(" ");
    }
    public String[] conviertePostfija() {
        String [] elementos;
        elementos = obtieneTokens();
        String postfija[] = new String[elementos.length];
        PilaADT <String> pila = new PilaA();
        int e, p, n;
        
        e = 0;
        p = 0;
        n = elementos.length;
        while (e < n){
            if (elementos[e].equals("(")) // Es paréntesis izquierdo
                pila.push(elementos[e]);
            else
                if (elementos[e].equals(")")){ // Es paréntesis derecho
                    while (!pila.peek().equals("(")){
                        postfija[p] = pila.pop();
                        p++;
                    }
                    pila.pop();
                }
                else
                    if (noEsOperador(elementos[e])){ // Es un operando
                        postfija[p] = elementos[e];
                        p++;
                    }
                    else {  // Es un operador
                        while (!pila.isEmpty() && prioridad(pila.peek()) > prioridad(elementos[e])){
                            postfija[p] = pila.pop();
                            p++;
                        }
                        pila.push(elementos[e]);
                    }
            e++;          
       }
        while (!pila.isEmpty()){
            postfija[p] = pila.pop();
            p++;
        }
        return postfija;
        
    }
    private boolean noEsOperador(String dato){
        return !dato.equals("+") && !dato.equals("-") && !dato.equals("*") && !dato.equals("/");
    }
    
    /* Método auxiliar para el manejo de las prioridades de los operadores. Regresa 0,
     * el valor más pequeño, cuando el dato dado es un "(". De esta manera
     * el "(" sólo se saca de la pila cuando se encuentre un ")".
     */
    private int prioridad(String dato){
        int resultado = 0; // En caso de que el dato sea un paréntesis izquierdo
        
        switch (dato.charAt(0)){
            case '+':
            case '-': resultado = 1;
                break;
            case '*':
            case '/': resultado = 2;
        }
        return resultado;
    }
    
}
