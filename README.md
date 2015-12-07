# CPUScheduler
This project simulates a CPU Scheduler.

* Justin Schoen, September 10, 2014

 This program reads process information from a text area, creates an appropriate data structure, and outputs the contents of the 
 data structure to a second text area. The program adds a process to the newList when the clock reaches the creation time. 
 When the clock goes one tick past the creation time, the process in the newList is added to the ready list.
 For full description, see http://raider.mountunion.edu/csc/CSC370/Fall2014/projects/project2.html
 This project is basically an operating system moving processes from the new to ready to running to waiting until it gets 
 terminated.



* Work Log:
   * 8/28: Created GUI with buttons and text fields-1 hour
   * 9/1: Worked on runnable clock-1.5 hours
   * 9/2: Finished clock with pause and one tick functions-.5 hours
   * 9/2: Created Process class and started data structure for processes-.5 hours
   * 9/3: Added functionality to the read data and show system status buttons-2 hours
   * 9/9: Developed showStatus method to add new and remove new after a second tick then add the process to the readyList-2 hours
   * 9/10: Documentation and validity checks-1.5 hours
   * 9/17: Data is reset when "read data" button is clicked. No processing done in showStatus method-1 hour
   * 9/24: Moved process from ready state to running. Split trace tape. Set time quantum-2 hours
   * 9/29: Got trace tape working accordingly and created methods in the process class to handle the tape- 3 hours
   * 9/30: Sent processes back and forth from waitinglist to ready, running to ready, running to terminated- 4 hours
   * 10/1: Arranged method calls in the right order to places processes in the right states- 2 hours
   * 10/2: Finished setting the new tape so the processes would remember the tape- 1 hour
