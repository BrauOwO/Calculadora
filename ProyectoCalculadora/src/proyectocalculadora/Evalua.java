/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectocalculadora;

/**
 *
 * @author fernando.puron
 */
public class Evalua {
    private String [] entrada;
    public Evalua(){
    }
    public Evalua(String [] entrada){
        this.entrada = entrada;
    }
    public double calcula(){
        PilaADT<Double> pila = new PilaA();
        double resul, op1, op2;
        int i;
        
        resul = 0;
        i = 0;
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
          return pila.pop();  
    }
    private boolean noEsOperador(String dato){
        return !dato.equals("+") && !dato.equals("-") && !dato.equals("*") && !dato.equals("/");
    }
    
    /* Método auxiliar para el manejo de las prioridades de los operadores. Regresa 0,
     * el valor más pequeño, cuando el dato dado es un "(". De esta manera
     * el "(" sólo se saca de la pila cuando se encuentre un ")".
     */

    
}
