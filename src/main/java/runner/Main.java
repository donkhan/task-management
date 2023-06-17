package runner;
import core.ConflictResolver;
import task.*;
import utils.CalendarUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String args[]) {
        //specific();
        all();
    }

    private static void specific(){
        String[] applications = {"CADH"};
        solve(applications);
    }

    private static void all(){
        String[] applications = {"ATLAS", "CADH", "Call Log", "CASSIE", "CMS", "CPAR", "FLOSSY", "HH", "iHUB",
                "LASSY", "Numero", "Oracle AR & Financials", "Over 75 Refunds", "QASSIE", "SASSIE", "SOA",
                "TVLEO", "Website"};
        solve(applications);
    }

    private static void solve(String[] applications){
        String envs[] = { "dev","sit","uat","prod","train","prp","dr","perf"};
        boolean debug = false;
        try {
            String fName = System.getProperty("java.io.tmpdir") + "output.csv";
            System.out.println(fName);
            PrintStream fos = new PrintStream(new FileOutputStream(fName));
            for (String applicationName : applications) {
                for (int i = 0; i < envs.length; i++) {
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
                            skip(tokenizer, i * 2);
                            String startDate = tokenizer.nextToken().trim();
                            String endDate = tokenizer.nextToken().trim();
                            if (startDate.equals("N/A") || startDate.equals("TBD")) continue;
                            if (endDate.equals("N/A") || endDate.equals("TBD") || endDate.equals("TBC")) continue;

                            if (taskName.equals("TBD")) continue;
                            Task task = new Task(taskName, CalendarUtils.getDateFromString(startDate), CalendarUtils.getDateFromString(endDate));
                            taskList.add(task);
                        }
                        if (debug) {
                            System.out.println(taskList);
                        }
                        if (!taskList.isEmpty()) {
                            List<Output> l = new ConflictResolver(env,applicationName).process(taskList);
                            if(l.size() != 0) {
                                l.stream().forEach(System.out::println);
                                l.stream().forEach(s -> fos.println(s));
                                System.out.println("------------------------------------------------------------------------------");
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            fos.close();
        }catch(Exception e){

        }
    }

    private static void skip(StringTokenizer tokenizer, int index) {
        for(int i = 0;i<index;i++)
            tokenizer.nextToken();
    }
}
