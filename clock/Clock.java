package clock;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;


public class Clock{

    // working with serialised file
    private static final String FILENAME = "E:\\Projects\\Clock System\\logs.data";
    private static final String CLOCKSTATUSFILE = "E:\\Projects\\Clock System\\clockStatus.txt";
    private static final String SESSIONSTARTDATE = "E:\\Projects\\Clock System\\sessionStartDate.txt";
    private static final String SESSIONSTARTTIME = "E:\\Projects\\Clock System\\sessionStartTime.txt";
    private static final String CLOCKLOGS = "E:\\Projects\\Clock System\\clockLogs.txt";

    public static void saveMap(LinkedHashMap<String, Float> mp){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))){
            oos.writeObject(mp);
            System.out.println("Map saved successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LinkedHashMap<String, Float> loadMap(){
        LinkedHashMap<String, Float> mp = new LinkedHashMap<>();

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILENAME))){
            mp = (LinkedHashMap<String, Float>) ois.readObject();
            System.out.println("Map loaded successfully");
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

        return mp;
    }

    public static void saveFlag(int value){
        try(PrintWriter writer = new PrintWriter(new FileWriter(CLOCKSTATUSFILE))){
            writer.println(value);
            System.out.println("Flag value was saved successfully");
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static int loadFlag(){
        int value = 0;

        try(BufferedReader reader = new BufferedReader(new FileReader(CLOCKSTATUSFILE))){
            value = Integer.parseInt(reader.readLine());
            System.out.println("Flag value was loaded successfully");
        } catch(IOException | NumberFormatException e){
            e.printStackTrace();
        }

        return value;
    }

    public static void saveStartTime(String startTime){
        try(PrintWriter writer = new PrintWriter(new FileWriter(SESSIONSTARTTIME))){
            writer.println(startTime);
            System.out.println("Session start time was saved successfully");
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static String loadStartTime(){
        String startTime = "";

        try(BufferedReader reader = new BufferedReader(new FileReader(SESSIONSTARTTIME))){
            startTime = reader.readLine();
            System.out.println("Session start time was loaded successfully");
        } catch(IOException e){
            e.printStackTrace();
        }

        return startTime;
    }

    public static void saveStartDate(String startDate){
        try(PrintWriter writer = new PrintWriter(new FileWriter(SESSIONSTARTDATE))){
            writer.println(startDate);
            System.out.println("Session start date was saved successfully");
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static String loadStartDate(){
        String startDate = "";

        try(BufferedReader reader = new BufferedReader(new FileReader(SESSIONSTARTDATE))){
            startDate = reader.readLine();
            System.out.println("Session start date was loaded successfully");
        } catch(IOException e){
            e.printStackTrace();
        }

        return startDate;
    }


    public static String currentDate(LocalDateTime obj){
        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return obj.format(date);
    }

    public static String currentTime(LocalDateTime obj){
        DateTimeFormatter time = DateTimeFormatter.ofPattern("H:m");

        return obj.format(time);
    }
    
    public static void clockON(){
        LocalDateTime obj = LocalDateTime.now();
        String clockStartDate = currentDate(obj);
        String clockStartTime = currentTime(obj);

        // Write to the sessions file
        try{
            FileWriter toWrite = new FileWriter("E:\\Projects\\Clock System\\sessions.txt", true);
            toWrite.write(clockStartDate + ": " + clockStartTime + " to "); // 03-05-2024: 01:58 to 
            toWrite.close();
            System.out.println("Session's start was written to the file successfully!");
            saveFlag(1);
        }
        catch (IOException e){
            System.out.println("Error occurred");
            e.printStackTrace();
        }

        // Save start time and start date for the session
        saveStartTime(clockStartTime);
        saveStartDate(clockStartDate);
    }

    // stoi(takes two strings which represents the start and end time of the session and calculates the session time)
    public static float stoi(String s, String s1){ 
        //H:m, HH:m
        int n = s.length(), i = 0, tmp = 0, startHour = 0, startMin = 0, endHour = 0, endMin = 0;
        while(s.charAt(i) != ':'){
            char c = s.charAt(i);
            tmp = (tmp * 10) + (c - '0');
            i++;
        }
        startHour = tmp;
        tmp = 0;
        while(i < n){
            char c = s.charAt(i);
            tmp = (tmp * 10) + (c - '0');
            i++;
        }
        startMin = tmp;
        n = s1.length();
        tmp = 0;
        i = 0;
        while(s1.charAt(i) != ':'){
            char c = s1.charAt(i);
            tmp = (tmp * 10) + (c - '0');
            i++;
        }
        endHour = tmp;
        tmp = 0;
        while(i < n){
            char c = s1.charAt(i);
            tmp = (tmp * 10) + (c - '0');
            i++;
        }
        endMin = tmp;

        int startTimeInMinutes = 60 * startHour + startMin;
        int endTimeInMinuets = 60 * endHour + endMin;
        int sessionTimeInMinutes = 0;

        if(endTimeInMinuets < startTimeInMinutes){
            int restOfThedayAfterStart = (24 * 60) - startTimeInMinutes;
            sessionTimeInMinutes = restOfThedayAfterStart + endTimeInMinuets;
        } else {
            sessionTimeInMinutes = endTimeInMinuets - startTimeInMinutes;
        }

        return (float) (sessionTimeInMinutes / 60.0);
    }

    public static void clockOUT(){
        LocalDateTime obj = LocalDateTime.now();
        String latestDate = currentDate(obj);
        String latestTime = currentTime(obj);

        // Load the start time and date of the session
        String clockStartTime = loadStartTime();
        String clockStartDate = loadStartDate();

        // Write to the sessions file
        try{
            FileWriter toWrite = new FileWriter("E:\\Projects\\Clock System\\sessions.txt", true);
            toWrite.write(latestDate + ": " + latestTime + "\n");
            toWrite.close();
            System.out.println("Session's end was written to the file successfully!");
            saveFlag(0);
        }
        catch (IOException e){
            System.out.println("Error occured");
            e.printStackTrace();
        }

        // Calculate sessionTime
        float sessionTime = stoi(clockStartTime, latestTime);

        // Load the map from logs file
        LinkedHashMap<String, Float> mp = loadMap();
        float currentValue = mp.getOrDefault(clockStartDate, (float) 0);
        currentValue += sessionTime;
        mp.put(clockStartDate, currentValue);

        // Save the map to the logs file
        saveMap(mp);
    }

    public static void showHistory(){
        LinkedHashMap<String, Float> mp = loadMap();
        try(FileWriter writer = new FileWriter(CLOCKLOGS)){
            for(String s : mp.keySet()){
                writer.write(s + ": " + mp.get(s) + "\n");
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}

class RunClock{
    public static void main(String[] args){
        Scanner obj = new Scanner(System.in);
        System.out.println("Clock ON or OFF? 1 for ON, 0 for OFF");
        int clockFlag = Clock.loadFlag();
        
        while(true){
            int flag = obj.nextInt();
            if(flag == 1 && clockFlag == 1){
                System.out.println("Clock is already ON");
            }
            else if(flag == 0 && clockFlag == 0){
                System.out.println("Clock is already off");
            }
            else{
                if(flag == 1){
                    Clock.clockON();
                }
                else{
                    Clock.clockOUT();
                }
                break;
            }
        }
        obj.close();
        Clock.showHistory();
    }
}

/*
    ClockON(){
        takes current time and write on the file the first time period
        
    }
 
    ClockOUT(){
        takes current time and write on the file the second time period, and calculates the session time by the first and second period
        takes what has been done during the session

        updates the days file by calculating the studying time in X day
    }

    two files:
        1- Sessions file: includes the studying sessions for all the days
        2- Studying time: includes the studying time for each day
 */