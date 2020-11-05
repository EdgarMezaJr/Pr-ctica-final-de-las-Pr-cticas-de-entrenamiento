/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package condicionescompetencias;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Hilo extends Thread{
    private JTextArea area;
    private RCompartido rc;
    private int entrenamiento;
    private boolean salir;
    private boolean muerto;
    private int tiempo; //Variable para interrupciones
    private boolean bandera = false; //variable para interrupciones
    private Cerradura cer; //Variable unica para cerradura
    private Lock MUTEX;
    private mutex m;
    private alternanciaEstricta alt; //variable exclusiva para alternancia
    private int nombre;
    private dijkstra dij;
    
    /*metodo exlusivo para dijkstra    */
    public void dijkstra(dijkstra dij){
        this.dij = dij;
        this.dij.agregarProceso();
        this.nombre = this.dij.getTamano();
        System.out.print(nombre);
    }
    
   /*Metodo exclusivo para alternancia*/
    
    public void alternancia(alternanciaEstricta alt){
        this.alt = alt;
        this.nombre = this.alt.nombre();
    }
    
    /*Metodo exclusivo para cerradura
    */
    public void setMutex(mutex art){
        this.m = art;
    }
    
    /*Metodo exclusivo para cerradura
    */
    public void setCerradura(Cerradura cer){
        this.cer = cer;
    }
    /*Nos permitira acabar el trabajo de los hilos*/
    public void setSalir(){
        this.salir = true;
    }
    /*Metodo que nos ayudara a saber algoritmo elegir*/
    public void setEntrenamiento(int ent){
        this.entrenamiento = ent; 
    }
    
    /*Matado imprevistamente a nuestros hilos*/
    public void matar(){
        this.muerto = true;
    }
    /*Metodo exclusivo para desactivacion de interrupciones*/
    public void setTiempo(int tiempo){
        this.tiempo = tiempo;
    }
    
    Hilo(JTextArea area, RCompartido rc){
        this.area = area; 
        this.rc = rc;
        this.salir = false;
        this.muerto = false;
        MUTEX = new ReentrantLock();
    }
    

    
    public void run(){
        salir = false;
        while(salir == false){
            try{
                switch(entrenamiento){
                    case 0: //Sin algoritmo
                        sinAlgoritmo();
                        break;
                    case 1: //Algoritmo de interrupciones
                        interrupciones();
                        break;
                    case 2: //Algoritmo de la variable de cerradura
                        variableCerradura();
                        break;
                    case 3: //Algoritmo Mutex
                        sleep(tiempo);
                        Mutex();
                        break;
                    case 4:
                        Alternancia();//Algoritmo de Dekker 
                        break;
                    case 5:
                        Dijkstra();//Algortimo de Dijkstra
                        break;
                    case 6:
                        MutexArt();//Algoritmo de mutex artesanal. 
                        break; 
                }
                
            }catch(Exception e){e.printStackTrace();}
        }
        area.setText("");
    }
    /*En este metodo no se usan algoritmos*/
    private void sinAlgoritmo() {
        rc.setDatoCompartido(this.getName());
        area.append(rc.getDatoCompartido()+ "\n");
        try {
            sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*Se aplica el algoritmo de interrupciones*/
    private void interrupciones() {
        try{
                //Nuestro hilo recorre nuestro arreglo de interrupciones.
                int largo = rc.largo();
                
                bandera = false;
                sleep(tiempo);
                //Este for nos ayuda a determinar si nuestro arreglo 
                //de interrupciones nos indica si alguno
                //de nuestros hilos esta usando la sección critica
                for(int i = 0; i<largo; i++)
                    if(rc.comprobarInter(i) == true)
                        bandera = true;
                        
                    
                     
                
                   
                //Si la bandera es verdadera, quiere decir que 
                //otro hilo esta ocupando la sección critica.
                if (bandera == false){
                    //Aca entra a su sección critica
                    rc.setDatoCompartido(this.getName());
                    //Dado que esta en su seccion critica debemos marcar
                    //interrupcion en true
                    for(int i = 0; i<largo; i++)
                        rc.estadoInter(i, true);
                    
                    if(muerto == true){
                        area.append("Me van a matar...\n");
                        this.stop();
                    }

                    area.append(rc.getDatoCompartido()+ "\n");

                    sleep(1000);

                    for(int i = 0; i<largo; i++)
                        rc.estadoInter(i, false);
                }else{
                    area.append("Esperando...\n");
                }
            }catch(Exception e){e.printStackTrace();}
    }
    /*Se aplica el algoritmo de variable de cerradura*/
    private void variableCerradura() {
        if(cer.getCerradura() == false){
                cer.setCerradura(true);
                try{
                    rc.setDatoCompartido(this.getName());
                    area.append(rc.getDatoCompartido()+ "\n");
                    sleep(1000);
                    if(muerto == true){
                        area.append("me van a matar...");
                        this.stop();
                    }
                
                    cer.setCerradura(false);
                    sleep(500);
                }catch(Exception e){e.printStackTrace();}
            }else{
                try{
                    area.append("Esperando\n");
                    sleep(2000);
                }catch(InterruptedException e){e.printStackTrace();}
            }
    }
    /*Metodo que emplea el algoritmo mutex con APIS*/
    private void Mutex() {
        
        try {
            
            if(MUTEX.tryLock()){
                
                
                MUTEX.lock();
                rc.setDatoCompartido(this.getName());
                area.append(rc.getDatoCompartido()+ "\n");
                sleep(500);
                if(muerto == true){
                        area.append("me van a matar...");
                        this.stop();
                }
                MUTEX.unlock();
                
            }else{
                sleep(1000);
            }
            
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
  

    private void Alternancia() {
        try {
            
            
               switch(nombre){
                   
                   case 1:
                        if(alt.getTurno() ==1 && alt.getTurno2()==1){
                            rc.setDatoCompartido(this.getName());
                            area.append(rc.getDatoCompartido()+ "\n");
                            sleep(500);
                            if(muerto == true){
                                area.append("me van a matar...");
                                this.stop();
                            }
                            alt.setTurno(2);
                            sleep(500);
                        }else{
                            sleep(100);
                        }
                        
                   break;
                   case 2:
                        if(alt.getTurno() ==2 && alt.getTurno2()==1){
                            rc.setDatoCompartido(this.getName());
                            area.append(rc.getDatoCompartido()+ "\n");
                            sleep(500);
                            if(muerto == true){
                                area.append("me van a matar...");
                                this.stop();
                            }
                            alt.setTurno(1);
                            alt.setTurno2(2);
                            sleep(500);
                        }else{
                            sleep(100);
                        }
                        
                    break;
                   
                   case 3:
                       if(alt.getTurno1() ==1 && alt.getTurno2()==2){
                            rc.setDatoCompartido(this.getName());
                            area.append(rc.getDatoCompartido()+ "\n");
                            sleep(500);
                            if(muerto == true){
                                area.append("me van a matar...");
                                this.stop();
                            }
                            alt.setTurno1(2);
                            sleep(500);
                        }else{
                           sleep(100);
                       }
                        
                    case 4:
                        if(alt.getTurno1() ==2 && alt.getTurno2()==2){
                            rc.setDatoCompartido(this.getName());
                            area.append(rc.getDatoCompartido()+ "\n");
                            sleep(500);
                            if(muerto == true){
                                area.append("me van a matar...");
                                this.stop();
                            }
                            alt.setTurno1(1);
                            alt.setTurno2(1);
                            sleep(500);
                        }else{
                            sleep(100);
                        }
                        
                    break;
     
               }   
           
        } catch (InterruptedException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void Dijkstra() {
        boolean salir1=true;
        try {
        int i = nombre-1;   
        int cont = 0; 
        
        do{
            dij.setValorB(false, i);
            salir1=true;
            if(dij.getValorK() != i){
                if(cont == 10){
                        dij.setValorB(true,dij.getValorK());
                        dij.setValorC(true,dij.getValorK());
                        cont= 0;
                }
            dij.setValorC(true, i);
            if(dij.getValorB(dij.getValorK())){
                dij.setValork(i);
            }else{
               cont++;
            }
            salir1 = false;
            }else{
                dij.setValorC(false, i);
                for(int j=0;j<dij.getTamano();j++){
                    if(j != i && dij.getValorC(j) == false){
                        salir1 = false;
                    }
                }
            }
            if(salir ==true)
                salir1 = true;
           sleep(100);
           
        }while(salir1 == false);
        dij.setValorC(false, i);
        rc.setDatoCompartido(this.getName());
        area.append(rc.getDatoCompartido()+ "\n");
        sleep(500);
        if(muerto == true){
            area.append("me van a matar...");
            this.stop();
        }
        
        dij.setValorC(true, i);
        dij.setValorB(true, i);
        sleep(30);
        } catch (InterruptedException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void MutexArt() {
        try {
            sleep(tiempo);
            if(m.getPase()){
                
                
                m.setPase(false);
                m.lock();
                rc.setDatoCompartido(this.getName());
                area.append(rc.getDatoCompartido()+ "\n");
                sleep(500);
                if(muerto == true){
                        area.append("me van a matar...");
                        this.stop();
                }
                m.unLock();
                m.setPase(true);
                sleep(700);
            }else{
                sleep(500);
            }
            
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
