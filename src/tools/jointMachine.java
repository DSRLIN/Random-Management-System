package tools;
import entities.*;

import java.util.ArrayList;

public class jointMachine {
    public jointMachine() {}

    public Classroom joint(Classroom c, ArrayList<RentAction> r){
        for (RentAction tempR:r) {
            long startTime = Long.parseLong(tempR.start_time);
            long endTime = Long.parseLong(tempR.last_time) + startTime;
            if (tempR.isFixed){
                c.addNewFixedUsingTime(startTime,endTime);
            }else{
                c.addNewFreeUsingTime(startTime,endTime);
            }
        }
        return c;
    }
    public MeetingRoom joint(MeetingRoom m,ArrayList<RentAction> r){
        for (RentAction tempR:r) {
            long startTime = Long.parseLong(tempR.start_time);
            long endTime = Long.parseLong(tempR.last_time) + startTime;
            if (tempR.isFixed){
            }else{
                m.addNewFreeUsingTime(startTime,endTime);
            }
        }
        return m;
    }
}
