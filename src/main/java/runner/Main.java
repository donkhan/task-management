package runner;
import core.ConflictResolver;
import task.*;
import utils.CalendarUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.io.*;

public class Main {
    public static void main(String args[]){
        boolean debug = true;
        String[] applications = {"ATLAS","CADH","Call Log","CASSIE","CMS","CPAR","FLOSSY","HH","iHUB",
                "LASSY","Numero","Oracle AR & Financials", "Over 75 Refunds","QASSIE","SASSIE","SOA","TVLEO","Website"};
        String envs[] = { "dev","sit","uat","prod","train","prp","dr","perf"};
        for(String applicationName : applications) {
            for (int i = 0; i< envs.length;i++) {
                String env = envs[i];
                try {
                    List<Task> taskList = new ArrayList<>();
                    BufferedReader reader = new BufferedReader(new FileReader(new File("data/TVL.csv")));
                    int ignoreHeaderLines = 0;
                    for (int l = 0; l < ignoreHeaderLines; l++) {
                        reader.readLine();
                    }
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        StringTokenizer tokenizer = new StringTokenizer(line, ",");
                        String taskName = tokenizer.nextToken();
                        tokenizer.nextToken();// Description
                        String appName = tokenizer.nextToken().trim();
                        if (!appName.equals(applicationName)) continue;
                        tokenizer.nextToken(); // PM
                        skip(tokenizer,i*2);
                        String startDate = tokenizer.nextToken().trim();
                        String  endDate = tokenizer.nextToken().trim();
                        if (startDate.equals("N/A") || startDate.equals("TBD")) continue;
                        if (endDate.equals("N/A") || endDate.equals("TBD") || endDate.equals("TBC")) continue;

                        if (taskName.equals("TBD")) continue;
                        Task task = new Task(taskName, CalendarUtils.getDateFromString(startDate), CalendarUtils.getDateFromString(endDate));
                        taskList.add(task);
                    }
                    System.out.println("Application " + applicationName + "  env " + env);
                    if (debug) {
                        System.out.println(taskList);
                    }
                    if (!taskList.isEmpty()) {
                        List<String> l = new ConflictResolver().process(taskList);
                        l.stream().forEach(System.out::println);
                    }
                    System.out.println("------------------------------------------------------------------------------");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void skip(StringTokenizer tokenizer, int index) {
        for(int i = 0;i<index;i++)
            tokenizer.nextToken();
    }
}
