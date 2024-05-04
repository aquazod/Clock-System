package clock;

import java.util.Scanner;

public class Runner {
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
