/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 * 
 * @author schoenjj
 */
public class Process {
    //private variables
    private int creationTime;
    private String name, state, tape, newTape;
    //constructor
    public Process(int creationTime, String name, String state, String tape){
        this.creationTime = creationTime;
        this.name = name;
        this.state = state;
        this.tape = tape;
        newTape = tape;
    }
    
    public void setCPUTimer(int cpuTimer){
        String[] temp;
        temp = newTape.split(" ");
        temp[1] = ""+cpuTimer;
        newTape = "";
        for(int i = 0; i<temp.length; i++){
            newTape += temp[i]+ " ";
        }
    }
    /**
     * set the iOTimer used in the waitingList
     * @param iOTimer 
     */
    public void setIOTimer(int iOTimer){
        String[] temp;
        temp = newTape.split(" ");
        temp[1] = ""+iOTimer;
        newTape = "";
        for(int i =0; i < temp.length; i++){
            newTape += temp[i] + " ";
        }
    }
    
    /**
     * return true if there is more IO work to do
     * @return 
     */
    public boolean checkForIO(){
        String[] temp;
        temp = newTape.split(" ");
        if(temp.length > 2){
            return true;
        }
        else 
            return false;
    }
    /**
     * remove first two from new tape
     */
    public void removeFirstTwoFromNewTape(){
        String[] temp;
        temp = newTape.split(" ");
        newTape = "";
        for(int i =2;i<temp.length;i++){
            newTape += temp[i]+" ";
        }
    }
    //setter methods
    public void setCreationTime(int creationTime){
        this.creationTime = creationTime;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setState(String state){
        this.state = state;
    }
    public void setTape(String tape){
        this.tape = tape;
    }
    public void setNewTape(String newTape){
        this.newTape = newTape;
    }
    //getter methods
    public int getCreationTime(){return creationTime;}
    public String getName(){return name;}
    public String getState(){return state;}
    public String getTape(){return tape;}
    public String getNewTape() { return newTape; }
    //toString method
    public String toString(){
        return creationTime + " :: " + state + " :: "+ tape;
    }
    public String changingString(){
        return creationTime + " :: " + state + " :: "+ newTape;
    }
}
