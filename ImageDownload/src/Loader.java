import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Loader
{
    public static void main(String[] args) throws IOException
    {
        String url = "https://skillbox.ru/java/";
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select("img[src~=(?i)\\.(png|jpe?g)]");
        for(Element img : elements)
        {
            String imgUrl = img.absUrl("src");
            System.out.println(imgUrl);
            DownloadImages(imgUrl);
        }
    }

    public static void DownloadImages(String url) throws MalformedURLException
    {
        URL imgUrl = new URL(url);
        String imgFilePath = getImgFilePath(url);

        try(ReadableByteChannel rbc = Channels.newChannel(imgUrl.openStream());
            FileOutputStream fos = new FileOutputStream(imgFilePath)
        ) {
            fos.getChannel().transferFrom(rbc, 0, 1 << 24);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getImgFilePath(String url)
    {
        String dest = "res/img";
        File absDest = new File(dest).getAbsoluteFile();
        String[] fragments = url.split("/");
        String fileName = fragments[fragments.length - 1];
        String filePath = absDest + "/" + fileName;

        return filePath;
    }
}
