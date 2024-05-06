This is my own implementation of a clock system that helps me track the time I spend working on my other Bookstore project.

The clock system works by relying on IO files in Java, I'm using 6 files to upload and retrieve data from, these files are:
  1- clockStatus.txt: This file saves the clock status (ON/OFF)
  
  2- sessions.txt: Saves the date, start time, and end time for any working session on the project.
  
  3- sessionStartDate: Saves the start date of the current session to retrieve it later at the end of the session.
  
  4- sessionStartTime: Saves the start time of the current session to retreive it later at the end of the session, in order to create the session time.
  
  5- logs.data: Saves the data of a LinkedHashMap which holds the history of my sessions, key is the date, value is the time spent on the project on that date.
  
  6- clockLogs.txt: Shows all the records for all the past days, e.g: 03/05/2024: 2.3 (hours)


If you are willing to use this program you need to make sure of the following:

  - Create the 6 filles with the same names and add their paths to the Clock.java file
  - You will need to run the program when you want to set the clock ON, by typing "1", and at the end of your session, you will need to run the program again and type "0"

Please note that the program doesn't handle the case when the session time is longer than 24 hours, so the session will be ignored.

After all, the program was created in a few hours just to help me to track my time so I configured it according to my usage and preference and it might not be too user friendly.
