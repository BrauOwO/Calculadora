/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyectoCalculadora;

import proyectocalculadora.Calculadora;


/**
 *
 * @author Braulio César Partida Meneses
 */
public class PruebaEvalua {

    public static void main(String [] args){
        Calculadora calculadora = new Calculadora();
        String micadena;
        String resultado;
        micadena = "((9)-((10)))";
        micadena = calculadora.espaciarString(micadena);
        
        resultado = calculadora.calcula(calculadora.conviertePostfija(micadena));
        
        
        
        
        System.out.println("El resultado de evaluar la expresion es: " + resultado);
    }
    
    
}
