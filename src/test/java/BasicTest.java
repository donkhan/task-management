import static org.junit.Assert.*;
import static utils.FileUtils.readFromFile;

import core.ConflictResolver;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

public class BasicTest {

    @Test
    public void testBasicInput() {
        List<String> l = new ConflictResolver().process(readFromFile("test_cases/input.txt"));
        assertEquals(5, l.size());
        Collections.sort(l);
    }

    @Test
    public void testAtlasApplication() {
        List<String> l = new ConflictResolver().process(readFromFile("test_cases/atlas.txt"));
        assertEquals(7, l.size());
    }

    @Test
    public void testCassieApplication() {
        List<String> l = new ConflictResolver().process(readFromFile("test_cases/cassie.txt"));
        assertEquals(11, l.size());
    }

    @Test
    public void testLassyApplication() {
        List<String> l = new ConflictResolver().process(readFromFile("test_cases/lassy.txt"));
        assertEquals(17, l.size());
    }
}
