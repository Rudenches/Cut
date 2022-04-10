import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class CutLauncherTest {
    private final File inputFile = new File("./src/test/java/input");
    private final File outputFile = new File("./src/test/java/output");
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();


    @Test
    void main() {
    }

    @Test
    void wordTest1() throws IOException {
        CutLauncher.main(new String[]{"-w", "-o", outputFile.toString(), "-r", "2-6", inputFile.toString()});
        BufferedReader reader = new BufferedReader(new FileReader(outputFile.toString()));
        String str = reader.readLine();
        assertEquals("же прекрасный сегодня день", str);
        str = reader.readLine();
        assertEquals("самая большая страна в мире", str);
        str = reader.readLine();
        assertEquals("свободное время я хожу в", str);
        str = reader.readLine();
        assertEquals("бывают красные, зеленые, желтые", str);
        reader.close();

    }

    @Test
    void wordTest2() throws IOException {
        CutLauncher.main(new String[]{"-w", "-o", outputFile.toString(), "-r", "-4", inputFile.toString()});
        BufferedReader reader = new BufferedReader(new FileReader(outputFile.toString()));
        String str = reader.readLine();
        assertEquals("какой же прекрасный сегодня", str);
        str = reader.readLine();
        assertEquals("Россия самая большая страна", str);
        str = reader.readLine();
        assertEquals("В свободное время я", str);
        str = reader.readLine();
        assertEquals("Яблоки бывают красные, зеленые,", str);
        reader.close();

    }

    @Test
    void wordTest3() throws IOException {
        CutLauncher.main(new String[]{"-w", "-o", outputFile.toString(), "-r", "2-", inputFile.toString()});
        BufferedReader reader = new BufferedReader(new FileReader(outputFile.toString()));
        String str = reader.readLine();
        assertEquals("же прекрасный сегодня день", str);
        str = reader.readLine();
        assertEquals("самая большая страна в мире", str);
        str = reader.readLine();
        assertEquals("свободное время я хожу в бассейн", str);
        str = reader.readLine();
        assertEquals("бывают красные, зеленые, желтые", str);
        reader.close();

    }

    @Test
    void charTest1() throws IOException {
        CutLauncher.main(new String[]{"-c", "-o", outputFile.toString(), "-r", "-7", inputFile.toString()});
        BufferedReader reader = new BufferedReader(new FileReader(outputFile.toString()));
        String str = reader.readLine();
        assertEquals("какой ж", str);
        str = reader.readLine();
        assertEquals("Россия ", str);
        reader.close();

    }

    @Test
    void charTest2() throws IOException {
        CutLauncher.main(new String[]{"-c", "-o", outputFile.toString(), "-r", "2-7", inputFile.toString()});
        BufferedReader reader = new BufferedReader(new FileReader(outputFile.toString()));
        String str = reader.readLine();
        assertEquals("акой ж", str);
        str = reader.readLine();
        assertEquals("оссия ", str);
        reader.close();

    }

    @Test
    void charTest3() throws IOException {
        CutLauncher.main(new String[]{"-c", "-o", outputFile.toString(), "-r", "5-", inputFile.toString()});
        BufferedReader reader = new BufferedReader(new FileReader(outputFile.toString()));
        String str = reader.readLine();
        assertEquals("й же прекрасный сегодня день", str);
        str = reader.readLine();
        assertEquals("ия самая большая страна в мире", str);
        reader.close();

    }

    @Test
    void rangeTest() {
        assertThrows(IllegalArgumentException.class, () -> CutLauncher.main(new String[]{"-w", "-o",
                outputFile.toString(), "-r", "2---3", inputFile.toString()}));
        assertThrows(IllegalArgumentException.class, () -> CutLauncher.main(new String[]{"-w", "-o",
                outputFile.toString(), "-r", "10-5", inputFile.toString()}));
    }

    @Test
    void consoleInput() throws IOException {
        System.setOut(new PrintStream(outContent));
        ByteArrayInputStream input = new ByteArrayInputStream(("привет, как прошли твои выходные?" +
                "\n" + "Хорошо, я ходил в кино" ).getBytes());

        System.setIn(input);
        CutLauncher.main(new String[]{"-w", "-r", "2-4"});
        assertEquals("как прошли твои" + "\n" + "я ходил в"
                , outContent.toString());

    }
    @Test
    void consoleInput2() throws IOException {
        System.setOut(new PrintStream(outContent));
        ByteArrayInputStream input = new ByteArrayInputStream(("привет, как прошли твои выходные?" +
                "\n" + "Хорошо, я ходил в кино" ).getBytes());

        System.setIn(input);
        CutLauncher.main(new String[]{"-c", "-r", "2-4"});
        assertEquals("рив" + "\n" + "оро"
                , outContent.toString());

    }
}

