package task;
import java.util.*;

public class Task {

    public String name;
    public GregorianCalendar startDate;
    public GregorianCalendar endDate;

    public Task(String name, GregorianCalendar startDate, GregorianCalendar endDate){
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String toString(){
        return name +  "  " + startDate.getTime() + "  " + endDate.getTime();
    }
}
