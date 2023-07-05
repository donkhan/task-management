package task;

import runner.Main;

import java.text.SimpleDateFormat;
import java.util.*;
public class Output {

    private String application;
    private String env;
    private Date startDate;
    private GregorianCalendar endDate;
    private String tasks;

    private String sep = ",";

    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");

    public Output(String application, String env, Date startDate, GregorianCalendar endDate, String tasks){
        this.application = application;
        this.env = env;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tasks = tasks;
    }


    public String toString(){
        long diff = findDiff(endDate,startDate);
        String s =  application + sep + env + sep + sdf.format(startDate) + sep + sdf.format(endDate.getTime()) + sep + diff + sep
                + application + "[" + env.toUpperCase() + "] is overlapping for " + diff + " days btw " + tasks;
        String x = "";
        StringTokenizer tokenizer = new StringTokenizer(tasks, "|");

        while(tokenizer.hasMoreTokens()){
            String name = tokenizer.nextToken();
            x = x + name  + Main.descMap.get(name.trim()) + " &";
        }
        s = s + sep + "No issues/conflicts " + x.substring(0,x.length()-1) + " can co exist";
        return s;
    }

    private long findDiff(GregorianCalendar end, Date start){
        long diff = end.getTimeInMillis() - start.getTime();
        long days = diff/(24*60*60*1000);
        return days + 1;
    }


}
