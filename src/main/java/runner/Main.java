package runner;
import core.ConflictResolver;
import task.*;
import java.util.*;
import static utils.FileUtils.readFromFile;

public class Main {
    public static void main(String args[]){
        List<Task> l = new ConflictResolver().process(readFromFile("test_cases/input.txt"));
        l.stream().sorted().forEach(System.out::println);
    }
}
