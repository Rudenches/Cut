import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;

public class CutLauncher {
    private int start;
    private int end;
    @Option(name = "-c", metaVar = "IndentChars", usage = "Work with chars", forbids = {"-w"})
    private boolean chars;
    @Option(name = "-w", metaVar = "IndentWord", usage = "Work with words", forbids = {"-c"})
    private boolean words;
    @Option(name = "-o", metaVar = "OutputName", usage = "Output file")
    private File outputFile;
    @Option(name = "-r", metaVar = "range", usage = "range", required = true)
    private String range;
    @Argument(metaVar = "InputName", usage = "InputFile name")
    private File inputFile;

    public static void main(String[] args) throws IOException {
        new CutLauncher().cmd(args);
    }

    private void cmd(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("cut [-c| -w] [-o file] [inputFile] range");
            parser.printUsage(System.err);
            return;
        }

        if (!words && !chars) throw new IllegalArgumentException();

        String[] parts = range.split("-");
        if (range.matches("[0-9]+-[0-9]+")) {
            start = Integer.parseInt(parts[0]);
            end = Integer.parseInt(parts[1]);
        } else if (range.matches("[0-9]+-")) {
            start = Integer.parseInt(parts[0]);
            end = Integer.MAX_VALUE;
        } else if (range.matches("-[0-9]+")) {
            start = 0;
            end = Integer.parseInt(parts[1]);
        } else throw new IllegalArgumentException();
        if (start > end) throw new IllegalArgumentException();


        Cut cutter = new Cut(start, end);

        BufferedWriter writer;
        BufferedReader reader;
        try {
            if (inputFile != null) {
                reader = new BufferedReader(new FileReader(inputFile));
            } else reader = new BufferedReader(new InputStreamReader(System.in));
            if (outputFile != null) {
                writer = new BufferedWriter(new FileWriter(outputFile));
            } else
                writer = new BufferedWriter(new OutputStreamWriter(System.out));

            cutter.completion(writer, reader, words);
            writer.close();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}







