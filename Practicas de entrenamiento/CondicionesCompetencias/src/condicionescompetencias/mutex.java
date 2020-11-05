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
public class mutex {
    private boolean pase;
    mutex(boolean pase){
        this.pase = pase;
    }
    
    public synchronized void lock(){
        while(pase){
            try {
                wait();
            } catch (Exception ex) {

            }
        }
        pase = true;        
    }
    
    public synchronized void unLock(){
        pase = false;
        notify();
    }
    
    public synchronized boolean getPase(){
        return pase;
    }
    synchronized void setPase(boolean pase){
        this.pase=pase;
    } 
    
    public synchronized boolean tryLock(){
        try {
           lock();
        } 
        finally{
           unLock();
           return false;
        } 
        
    }
    
}
