package utils;

import task.Task;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static utils.CalendarUtils.getDateFromString;

public class FileUtils {
    public static List<Task> readFromFile(String fileName) {
        List<Task> tasks = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new java.io.File(fileName)));
            String s = "";
            while((s = reader.readLine()) != null){
                StringTokenizer tokenizer = new StringTokenizer(s,",");
                if(!tokenizer.hasMoreTokens()) continue;
                String taskName = "";
                taskName = tokenizer.nextToken();
                String startDate = tokenizer.nextToken();
                String endDate = tokenizer.nextToken();
                tasks.add(new Task(taskName,getDateFromString(startDate), getDateFromString(endDate)));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return tasks;
    }
}
