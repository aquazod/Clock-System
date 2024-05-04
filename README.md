This is my own implementation of a clock system that helps me track the time I spend working on my other Bookstore project.

The clock system works by relying on IO files in Java, I'm using 6 files to upload and retrieve data from, these files are:
  1- clockStatus.txt: This file saves the clock status (ON/OFF)
  2- sessions.txt: Saves the date, start time, and end time for any working session on the project.
  3- sessionStartDate: Saves the start date of the current session to retrieve it later at the end of the session.
  4- sessionStartTime: Saves the start time of the current session to retreive it later at the end of the session, in order to create the session time.
  5- logs.data: Saves the data of a LinkedHashMap which holds the history of my sessions, key is the date, value is the time spent on the project on that date.
  6- clockLogs.txt: Shows all the records for all the past days, e.g: 03/05/2024: 2.3 (hours)
