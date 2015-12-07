/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.util.ArrayList;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author schoenjj
 */
public class Structure {

    //variables
    ClockStarter clock;
    private ArrayList<Process> list;
    private ArrayList<Process> newList;
    private ArrayList<Process> readyList;
    private ArrayList<Process> terminatedList;
    private ArrayList<Process> waitingList;
    private Process runningProcess;
    private JTextArea inputData;
    private JTextArea outputData;
    private JTextField clockField;
    String[] str;
    String[] temp;
    int cpuTimer;
    int iOTimer;
    private ArrayList<String> traceTape;
    int timeQuantum;
    private ArrayList<Process> runningList;
    boolean runningToReady;
    boolean waitingToReady;
    boolean runningToWaiting;

    /**
     * The Structure constructor uses the clockField from the main frame to use
     * throughout this class.
     *
     * @param clockField
     */
    public Structure(JTextField clockField) {
        list = new ArrayList<>();
        this.clockField = clockField;
        clock = new ClockStarter(clockField, this);
        newList = new ArrayList<>();
        readyList = new ArrayList<>();
        terminatedList = new ArrayList<>();
        waitingList = new ArrayList<>();
        traceTape = new ArrayList<>();
        runningList = new ArrayList<>(1);
        runningToReady = false;
    }

    /**
     * The readData method splits the input from the text area by each new line,
     * then fills the list with processes.
     *
     * @param inputData
     * @param outputData
     * @param clockField
     */
    public void readData(JTextArea inputData, JTextArea outputData, JTextField clockField) {
        cpuTimer = 0;
        traceTape.clear();
        list.clear();
        newList.clear();
        readyList.clear();
        runningProcess = null;
        waitingList.clear();
        runningList.clear();
        terminatedList.clear();
        clock.setTime(0);
        clockField.setText("0");
        try {
            if (inputData.getText().isEmpty()) {
                outputData.append("enter some data\n");
            } else {
                str = inputData.getText().split("\n");
                timeQuantum = Integer.parseInt(str[0]);
                for (int i = 1; i < str.length; i = i + 3) {
                    if (isInteger(str[i]) == false) {
                        outputData.append("creation time must be an integer\n");
                    } else if (str[i + 1].length() > 3) {
                        outputData.append("state cannot be more than 3 letters\n");
                    } else {
                        list.add(new Process(Integer.parseInt(str[i]), "unknown", str[i + 1], str[i + 2]));
                    }
                }
                outputData.append("data read\n");
            }
        } catch (Exception e) {
        }
        
        
    }

    /**
     * showStatus() displays all the process and state information of the program
     *
     * @param outputData
     * @param clockField
     * @param inputData
     */
    public void showStatus(JTextArea outputData, JTextField clockField, JTextArea inputData) {
            outputData.append("clock -- " + clockField.getText() + "\n");
            outputData.append("time quantum -- " + str[0] + "\n");
            
            outputData.append("processList.size() -- " + list.size() + "\n");

            for (int m = 0; m < list.size(); m++) {
                outputData.append("     " + list.get(m) + "\n");
            }
            //display the new list
            outputData.append("newList.size() -- " + newList.size() + "\n");
            for (int q = 0; q < newList.size(); q++) {
                outputData.append("     " + newList.get(q) + "\n");
            }
            //display the ready list
            outputData.append("readyList.size() -- " + readyList.size() + "\n");
            for (int y = 0; y < readyList.size(); y++) {
                outputData.append("     " + readyList.get(y).changingString() + "\n");
            }
            //display the running list
            if(!runningList.isEmpty()){
                outputData.append("runningProcess -- " + runningList.get(0).changingString() + "\n");
                outputData.append("       (currentTimeQuantumRemaining) -- " + timeQuantum+"\n");
            }
            else
                 outputData.append("runningProcess -- " + runningProcess + "\n");
            //display the waiting list
            outputData.append("waitingList.size() -- " + waitingList.size() + "\n");
            for (int y = 0; y < waitingList.size(); y++) {
                outputData.append("     " + waitingList.get(y).changingString() + "\n");
            }
            //display the terminated list
            outputData.append("terminatedList.size() -- " + terminatedList.size() + "\n");
            for (int y = 0; y < terminatedList.size(); y++) {
                outputData.append("     " + terminatedList.get(y).changingString() + "\n");
            }
            outputData.append("\n");
    }
    /**
     * processStuff() calls all the methods which moves processes around states
     */
    public void processStuff(){
        //check the waitingToReady boolean to see if a process has been sent from the waitingList to readyList
        if(waitingToReady == true){
        }
        else if(waitingToReady == false && runningToWaiting == false){
            processWaitingList();
        }
        
        //add new processes from the process list to the newList
        addNewProcesses();
        
        //if the runningList is not empty, process the running list
        if(!runningList.isEmpty()){
            processRunningList();
        }
        //check the runningToReady boolean to see if a process has been sent from running back to ready. if it's true, wait one more tick, if not, add it to running
        if(runningToReady == true){
        }
        else if(runningToReady == false && waitingToReady == false){
            addToRunning();
        }
        //if the newList is empty, call newToReady() which will add processes to the ready list from the newList
        if (!newList.isEmpty()) {
            newToReady();
        }
        //newToReady();
        //set all the booleans to false so they will run unless set otherwise set
        runningToReady = false;
        waitingToReady = false;
        runningToWaiting = false;
    }
    
    /**
     *  isInteger checks if an input variable is an integer and returns true if it is or false if it's not an integer
     * @param input
     * @return 
     */
    public boolean isInteger( String input ) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * add processes to the new list
     */
    public void addNewProcesses(){
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCreationTime() == clock.getCurrentTime()) {
                newList.add(list.get(i));
            }
        }
    }
    
    /**
     * add processes from new to ready and remove the process from the new list
     */
    public void newToReady(){
        for (int i = 0; i < newList.size(); i++) {
            if (newList.get(i).getCreationTime() + 1 <= Integer.parseInt(clockField.getText())) {
                readyList.add(newList.get(i));
                newList.remove(i);
            }
        }
    }
    
    /**
     * add processes to the running process if the running process is null
     */
    public void addToRunning(){
        if (readyList.size() > 0 && runningList.isEmpty()) {
                runningList.add(readyList.get(0));
                readyList.remove(0);
                temp = runningList.get(0).getNewTape().split(" ");
                cpuTimer = Integer.parseInt(temp[1]);
        }
    }
    
    /**
     * checks the trace tape of the process and moves it to the appropriate list
     */
    public void processRunningList(){
        if(cpuTimer == 0 && timeQuantum >0){
            if(runningList.get(0).checkForIO()== true){
                timeQuantum = Integer.parseInt(str[0]);
                runningList.get(0).removeFirstTwoFromNewTape();
                waitingList.add(runningList.get(0));
                runningList.remove(0);
                runningToWaiting = true;
                waitingListTimer();
            }
            else if(runningList.get(0).checkForIO() == false){
                timeQuantum = Integer.parseInt(str[0]);
                terminatedList.add(runningList.get(0));
                runningList.get(0).removeFirstTwoFromNewTape();
                runningList.remove(0);
            }
        }
        else if(timeQuantum == 0 && cpuTimer > 0){
            timeQuantum = Integer.parseInt(str[0]);
            runningList.get(0).setNewTape(runningList.get(0).getNewTape());
            readyList.add(runningList.get(0));
            runningList.remove(0);
            runningToReady = true;
        }
        else if(timeQuantum == 0 && cpuTimer ==0){
            if(runningList.get(0).checkForIO() == true){
                timeQuantum = Integer.parseInt(str[0]);
                runningList.get(0).removeFirstTwoFromNewTape();
                waitingList.add(runningList.get(0));
                runningList.remove(0);
                runningToWaiting = true;
                waitingListTimer();
            }
            else if(runningList.get(0).checkForIO() == false){
                timeQuantum = Integer.parseInt(str[0]);
                terminatedList.add(runningList.get(0));
                runningList.get(0).removeFirstTwoFromNewTape();
                runningList.remove(0);
            }
        }
        else {
            timeQuantum--;
            cpuTimer--;
            runningList.get(0).setCPUTimer(cpuTimer);
            System.out.println("running List:  "+runningList.get(0).changingString());
        }
    }
    
    /**
     * waitingListTimer sets the waiting list timer
     */
    public void waitingListTimer(){
        String[] waitingTemp;
        waitingTemp = waitingList.get(0).getNewTape().split(" ");
        System.out.println("waitingList: "+waitingList.get(0).changingString());
        iOTimer = Integer.parseInt(waitingTemp[1]);
        System.out.println("iotimer: "+iOTimer);
    }
    
    /**
     * processWaitingList() processes the waiting list, decrements the IOTimer, and moves processes where they need to be moved to 
     */
    public void processWaitingList(){
        for (int i = 0; i < waitingList.size(); i++) {
            if (iOTimer == 0) {
                if(waitingList.get(i).checkForIO() == true){
                    waitingList.get(i).removeFirstTwoFromNewTape();
                    readyList.add(waitingList.get(i));
                    waitingList.remove(i);
                    waitingToReady = true;
                }
                else if(waitingList.get(i).checkForIO() == false){
                    waitingList.get(i).removeFirstTwoFromNewTape();
                    terminatedList.add(waitingList.get(i));
                    waitingList.remove(i);
                }
            }
            else{
                iOTimer--;
                waitingList.get(i).setIOTimer(iOTimer);
                System.out.println("iOTimer: "+iOTimer);
            }
        }
        
    }
    
    public ClockStarter getClock(){
        return clock;
    }
}
