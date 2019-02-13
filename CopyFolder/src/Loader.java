import java.io.*;

public class Loader
{
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Пожалуйста, введите путь к папке, которую нужно скопировать");
        String sourcePath = reader.readLine().trim();
        System.out.println("Пожалуйста, укажите путь по которому нужно сохранить скопированную папку");
        String destinationPath = reader.readLine().trim();
        copyDirectory(new File(sourcePath), new File(destinationPath));
    }

    public static void copyDirectory(File source, File dest) throws IOException {
        if(!source.isDirectory() || !dest.isDirectory())
            throw new IllegalArgumentException("Один из введеных путей не является папкой");

        File[] files = source.listFiles();
        if(files != null)
        {
            File destDir = new File(dest + File.separator + source.getName());
            destDir.mkdirs();
            for (File sourceFile : files)
            {
                if(sourceFile.isDirectory())
                {
                    copyDirectory(sourceFile, destDir);
                }
                else{
                    File destFile = new File(destDir + File.separator + sourceFile.getName());
                    copyFile(sourceFile, destFile);
                }
            }
        }
        else {
            System.out.println("Нет доступа к папке по пути: " + source.getAbsolutePath() + ", копирование невозможно!");
        }
    }

    private static void copyFile(File source, File dest) throws IOException
    {
        try(InputStream in = new FileInputStream(source);
            OutputStream out = new FileOutputStream(dest)
        ) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) >= 0) {
                out.write(buffer, 0, len);
            }
        }
    }
}
