import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class Cut {
    private final int start;
    private final int end;
    private StringBuilder stb = new StringBuilder();


    public Cut(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public String cutChar(String str, long countLines, long currentLine) {
        StringBuilder stb = new StringBuilder();
        int i = str.length() - 1;
        int min = Math.min(end, i);
        if (end == i) {
            if (start == 0) {
                for (int y = start; y < min; y++) {
                    stb.append(str.charAt(y));
                }
            } else for (int y = start - 1; y < min; y++) {
                stb.append(str.charAt(y));
            }
        } else if (start == 0) {
            for (int y = start; y <= min - 1; y++) {
                stb.append(str.charAt(y));
            }
        } else if (end == Integer.MAX_VALUE) {
            for (int y = start - 1; y <= min; y++) {
                stb.append(str.charAt(y));
            }
        } else for (int y = start - 1; y < min; y++) {
            stb.append(str.charAt(y));
        }

        if (currentLine != countLines) {
            stb.append("\n");
        }
        return stb.toString();
    }

    public String cutWord(String str, long countLines, long currentLine) {
        StringBuilder stb = new StringBuilder();
        String[] words = str.split(" ");
        int i = words.length - 1;
        int min = Math.min(end, i);
        if (end == i) {
            if (start == 0) {
                for (int y = start; y < min; y++) {
                    stb.append(words[y]);
                    stb.append(" ");
                }
            } else for (int y = start - 1; y < min; y++) {
                stb.append(words[y]);
                stb.append(" ");
            }
        } else if (start == 0) {
            for (int y = start; y <= min - 1; y++) {
                stb.append(words[y]);
                stb.append(" ");
            }
        } else for (int y = start - 1; y <= min; y++) {
            stb.append(words[y]);
            stb.append(" ");
        }

        stb.deleteCharAt(stb.length() - 1);
        if (currentLine != countLines) {
            stb.append("\n");
        }
        return stb.toString();
    }

    public void completion(BufferedWriter writer, BufferedReader reader, boolean lever) throws IOException {
        List<String> lines = reader.lines().collect(Collectors.toList());
        for (int i = 0; i < lines.size(); i++) {
            if (lever) {
                stb.append(cutWord(lines.get(i), lines.size() - 1, i));
            } else stb.append(cutChar(lines.get(i), lines.size() - 1, i));
        }
        writer.write(stb.toString());
    }


}
