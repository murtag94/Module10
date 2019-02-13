import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Loader
{
    public static void main(String[] args) throws IOException
    {
       String text = new String(Files.readAllBytes(Paths.get("res/Probabilities.txt")));

       fileConverter(text);
    }

    public static void fileConverter(String text)
    {
        String[] lines = text.split("\\n");
        ArrayList<List<String>> data = new ArrayList<>();
        for (int i = 0; i < lines.length; i++)
        {
            String[] buf = lines[i].split("\\s+");
            List<String> list = new ArrayList<>();
            for (int j = 0; j < buf.length; j++)
            {
                list.add(String.format(format(text), buf[j]));
            }
            data.add(list);
        }

        filing(data);
    }

    private static String format(String text)
    {
        int maxWidth = 0;
        String[] fragments = text.split("\\s+");

        for(int i = 0; i < fragments.length; i++)
        {
            if (maxWidth < fragments[i].length())
                maxWidth = fragments[i].length();
        }

        String format = "%-" + maxWidth + "s";

        return format;
    }

    private static void filing(ArrayList<List<String>> data)
    {
        try(PrintWriter writer = new PrintWriter("res/Probabilities - formatted.txt", StandardCharsets.UTF_8.name()))
        {
            for (int i = 0; i < data.size(); i++)
            {
                String line = data.get(i).stream().collect(Collectors.joining("|", "", "\n"));
                writer.print(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
