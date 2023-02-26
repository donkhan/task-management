package runner;
import core.ConflictResolver;
import task.*;
import java.util.*;
import static utils.FileUtils.readFromFile;

public class Main {
    public static void main(String args[]){
        List<String> l = new ConflictResolver().process(readFromFile("test_cases/lassy.txt"));
        l.stream().forEach(System.out::println);
    }
}
