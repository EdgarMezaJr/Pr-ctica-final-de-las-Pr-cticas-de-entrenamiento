/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package condicionescompetencias;

/**
 *
 * @author edgar
 */
public class Cerradura {
     boolean cerradura;
    
    Cerradura(){
        this.cerradura = false;
    }
    
    public void reiniciar(){
        this.cerradura = false;
    }
    
    public void setCerradura(boolean ban){
        this.cerradura = ban;
    }
    
    public boolean getCerradura(){
        return cerradura;
    }
}
