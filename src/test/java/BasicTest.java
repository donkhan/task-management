import static org.junit.Assert.*;
import static utils.FileUtils.readFromFile;

import core.ConflictResolver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import task.Task;

import java.util.Collections;
import java.util.List;

public class BasicTest {

    @Test
    public void testBasicInput() {
        List<Task> l = new ConflictResolver().process(readFromFile("test_cases/input.txt"));
        assertEquals(l.size(),5);
        Collections.sort(l);
        assertEquals(l.get(0).name,"A");
        assertEquals(l.get(1).name,"A-B");
    }

    @Test
    public void testAtlasApplication() {
        List<Task> l = new ConflictResolver().process(readFromFile("test_cases/atlas.txt"));
        assertEquals(7, l.size());
    }
}
