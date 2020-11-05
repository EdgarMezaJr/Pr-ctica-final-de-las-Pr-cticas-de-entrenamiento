/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package condicionescompetencias;

import java.util.ArrayList;


/**
 *
 * @author edgar
 */
public class dijkstra {
    private ArrayList<Boolean> b;
    private ArrayList<Boolean> c;
    private int k=3;
    
    dijkstra(){
       this.b = new ArrayList<>();
       this.c = new ArrayList<>();    
    }
    
    public void reiniciar(){
        b.clear();
        c.clear();
        k=3;
    }
    
    public void agregarProceso(){
        b.add(true);
        c.add(true);
    }
    
    public void setValork(int k){
        this.k = k;
    }
    
    public int getValorK(){
        return k;
    }
    
    public void setValorB(boolean valor,int pos){
        
        b.set(pos, valor);
    }
    
    public boolean getValorB(int pos){
        return b.get(pos);
    }
    
    public void setValorC(boolean valor,int pos){
        c.set(pos, valor);
    }
    
    public boolean getValorC(int pos){
        return c.get(pos);
    }
    
    public int getTamano(){
        return b.size();
    }
    
}
