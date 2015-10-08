import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by Eugene Katrechko on 10/4/15.
 */
public class ParserTest {
    private Parser parser;
    private File testFile;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        parser = new Parser();
        testFile = folder.newFile("testfile.txt");
        parser.setFile(testFile);
    }

    @Test
    public void testSaveContent() throws IOException {
        String str = "Hello world!\nThis is test.";
        parser.saveContent(str);
        assertTrue("expected 'true' if file exists", testFile.exists());
        assertTrue("expected 'true' if file is file", testFile.isFile());
        assertTrue("expected 'true' if file can read", testFile.canRead());
        assertTrue("expected 'true' if file can write", testFile.canWrite());

    }

    @Test
    public void testSaveContentUnicode() throws IOException {
        String unicodeString = "\u00C6\u00D7\u00E8";
        parser.saveContent(unicodeString);
        assertTrue("expected 'true' if file exists", testFile.exists());
        assertTrue("expected 'true' if file is file", testFile.isFile());
        assertTrue("expected 'true' if file can read", testFile.canRead());
        assertTrue("expected 'true' if file can write", testFile.canWrite());
    }

    @Test
    public void testSaveReadContent() throws IOException {
        String str = "Hello world! \nThis is test.";
        parser.saveContent(str);
        String strFromFile = parser.getContentWithoutUnicode();
        assertTrue("expected 'true' that equal plain strings written and read", str.equals(strFromFile));
    }

    @Test
    public void testSaveReadContentUnicode() throws Exception {
        String unicodeString = "\u00C6\u00D7\u00E8\n\u00C6\u00D7\u00E8\nHello world!\n";
        parser.saveContent(unicodeString);
        String unicodeStringFromFile = parser.getContent();
        assertTrue("expected 'true' that equal unicode strings written and read", unicodeString.equals(unicodeStringFromFile));
    }

    @Test
    public void testSetFile() {
        parser.setFile(null);
        assertNull("expected 'null'", parser.getFile());
        parser.setFile(testFile);
        assertEquals("expected 'equals'", testFile, parser.getFile());
    }

    @Test
    public void testGetFile() {
        parser.setFile(testFile);
        assertEquals("expected 'equals'", testFile, parser.getFile());
    }

    @Test
    public void testSetFileNull() {
        parser.setFile(null);
        assertNull("expected 'null'", parser.getFile());
    }

    @Test
    public void testGetFileNull() {
        parser.setFile(null);
        assertNull("expected 'null'", parser.getFile());
    }

    @Test(expected = Exception.class)
    public void testIOExceptionWhenGetContent() throws Exception {
        parser.setFile(null);
        parser.getContentWithoutUnicode();
    }
}
