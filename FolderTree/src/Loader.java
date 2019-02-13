import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Loader
{
    public static final String HYPHEN = "-";
    public static int count = 1;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Пожалуйста, введите путь к папке: ");
        String path = reader.readLine().trim();
        File name = new File(path);

        if(name.exists() && name.isDirectory())
        {
            System.out.println(name.getName());
            PrintDirTree(name);
        }
        else {
            System.out.println("Такой папки не существует!");
        }
    }

    public static void PrintDirTree(File name)
    {
        File[] files = name.listFiles();
        if(files != null)
        {
            for(File file : files)
            {
                if(file.isDirectory())
                {
                    System.out.println(HyphenCounter(count) + file.getName());
                    count ++;
                    PrintDirTree(file);
                }
                else {
                    System.out.println(HyphenCounter(count) + file.getName() + "  " + file.length() + " байт");
                }
            }
        }
        else {
            System.out.println("Доступ закрыт!");
        }
        count --;
    }

    public static String HyphenCounter(int count)
    {
        String str = "";
        for(int i = 0; i < count; i++)
        {
            str += HYPHEN;
        }
        return str;
    }
}
