/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package condicionescompetencias;

import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RCompartido {
    private String datoCompartido;
    private interrupcion[] inter;
    
    RCompartido(){
        datoCompartido="";
    }
    public String getDatoCompartido() {
        return datoCompartido;
    }
    public void setDatoCompartido(String datoCompartido) {
        this.datoCompartido = datoCompartido;
    }
    
    /////////////////////////////////////////////////
    /*Metodos para interrupciones*/
    
    /*
    Metodo solo sirve para inicializar el arreglo
    de interrupciones
    */
    
    public void iniciarInterrupcion(){
        inter = new interrupcion[2]; //tiene minimo dos interrupciones
        for(int i =0; i<inter.length; i++)
            inter[i] = new interrupcion();
    }
    
     /*Función: obtener el largo de nuestro arreglo de interrupciones
        Recibe: Nada
        retorna: Largo del arreglo
    */
    public int largo(){
        return inter.length;
    }
    
    /*Función: Tiene la finalidad de comprobar si esta usando el recurso compartido
                otro hilo
        Recibe: La posición de nuestro arreglo
        retorna: Si se esta o no ocupando el RC
    */
    
    public boolean comprobarInter(int i){
        return inter[i].getInter();
    }
    
     /*Función: Tiene la finalidad de actualizar que estamos usando el RC
        Recibe: La posición de nuestro arreglo y el estado a que se desea actualizar
        retorna: Nada
    */
    
    public void estadoInter(int i, boolean estado){
       inter[i].setDatoInter(estado);
    }
    
    
    /*Fin de los metodos de interrupciones*/
    /////////////////////////////////////////////////
    
   
    
    
}
