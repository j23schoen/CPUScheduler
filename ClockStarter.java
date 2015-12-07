/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author schoenjj
 */
public class ClockStarter implements Runnable{

   Structure test;
   private Thread thread;
   private int currentTime;
   private Boolean running = false;
   private JTextField clockField;
   
   public ClockStarter(JTextField clockField, Structure test){
       //System.out.println("ClockStarter Instructor");
       this.test = test;
       currentTime = 0;
       thread = new Thread(this);
       thread.start();
       this.clockField = clockField;
   }
   /**
    * everytime the program goes through this method, it calls processStuff()
    */
   @Override
    public void run() {
        while (true) {
            if (running == true) {
                incrementTime();
                test.processStuff();
                clockField.setText("" + getCurrentTime());
            }
            try {
                Thread.sleep(1000);
            } 
            catch (Exception e) {
            }
        }
    }
   public void incrementTime(){
       currentTime++;
       clockField.setText(""+getCurrentTime());
   }
   public int getCurrentTime(){
       return currentTime;
   }
   public Boolean getRunning(){
       return running;
   }
   public void setRunning(Boolean running){
       this.running = running;
   }
   public void setTime(int currentTime){
       this.currentTime = currentTime;
   }
}
