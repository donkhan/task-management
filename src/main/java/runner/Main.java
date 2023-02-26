package runner;
import core.ConflictResolver;
import task.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import static utils.CalendarUtils.*;
import static utils.FileUtils.readFromFile;

public class Main {
    public static void main(String args[]){
        List<Task> tasks = readFromFile("input.txt");
        new ConflictResolver().process(tasks).stream().sorted().forEach(System.out::println);
    }


}
