package task;
import java.text.SimpleDateFormat;
import java.util.*;

public class Task implements Comparable<Task>{

    public String name;

    public String desc;
    public GregorianCalendar startDate;
    public GregorianCalendar endDate;

    public Task(String name, GregorianCalendar startDate, GregorianCalendar endDate) {
        this(name,startDate,endDate,"");
    }

    public Task(String name, GregorianCalendar startDate, GregorianCalendar endDate, String desc){
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.desc = desc;
    }

    public String toString(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        return name +  "  " + sdf.format(startDate.getTime()) + "  " + sdf.format(endDate.getTime());
    }

    @Override
    public int compareTo(Task o) {
        if(o.startDate.after(startDate)) return -1;
        return 1;
    }
}
