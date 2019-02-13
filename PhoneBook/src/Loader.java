import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Loader
{
    public static final String FILE_NAME = "phoneBook.json";

    public static boolean isPhone(String string) {
        return Pattern.compile("\\+\\d{4,}").matcher(string).matches();
    }

    public static boolean isName(String string) {
        return Pattern.compile("[А-ЯЁ&&[^ЪЬЫ]]{1}[а-яё]+(\\s[А-ЯЁ&&[^ЪЬЫ]]{1}[а-яё]+)?").matcher(string).matches();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        HashMap<String, String> phoneBook = new HashMap<>();

        for(;;)
        {
            System.out.println("Пожалуйста, введите команду:");
            String command = reader.readLine().trim();

            if(command.equals("LIST"))
            {
                ArrayList<String> list = new ArrayList<>();
                if(phoneBook.size() == 0){
                    System.out.println("Список контактов пуст!");
                }
                for(String number : phoneBook.keySet())
                {
                    list.add(String.format("%s - %s", phoneBook.get(number), number));
                }
                Collections.sort(list);
                for (String nameNumber : list)
                {
                    System.out.println(nameNumber);
                }
            }
            else if(command.equals("EXPORT"))
            {
                if(phoneBook.size() == 0){
                    System.out.println("Список контактов пуст!");
                }

                System.out.println("Введите путь к папке для сохранения телефонной книги");
                String path = reader.readLine().trim();
                File file = new File(path);

                if(!file.isDirectory() && (file.exists() || !file.mkdirs()))
                {
                    System.out.println("Неверный путь к папке!");
                }

                File filePath = new File(file, FILE_NAME);
                JSONArray phoneBookArray = new JSONArray();

                for(String number : phoneBook.keySet())
                {
                    JSONObject object = new JSONObject();
                    object.put("name", phoneBook.get(number));
                    object.put("phone", number);
                    phoneBookArray.add(object);
                }

                try (FileWriter writer = new FileWriter(filePath))
                {
                    writer.write(phoneBookArray.toJSONString());
                    System.out.println("Файл успешно сохранен");
                } catch (IOException e) {
                    System.out.println("Не удалось записать файл!!");
                }

            }
            else if(isPhone(command))
            {
                String name = phoneBook.get(command);
                if(name == null)
                {
                    System.out.println("Этого номера телефона нет в списке контактов, чтобы сохранить его, введите, пожалуйста, имя контакта");
                    name = reader.readLine().trim();
                    if(isName(name))
                    {
                        phoneBook.put(command, name);
                        System.out.println("Запись сохранена, спасибо!");
                    }
                    else{
                        System.out.println("Имя введено не корректно!");
                    }
                }
                else{
                    System.out.println(String.format("Номер %s принадлежит контакту с именем: %s", command, name));
                }
            }
            else if(isName(command))
            {
                String number = null;
                for(String key : phoneBook.keySet())
                {
                    if(phoneBook.get(key).equals(command))
                    {
                        number = key;
                        System.out.println(command + " " + number);
                    }
                }

                if(number == null)
                {
                    System.out.println("Этого имени нет в списке контактов, чтобы его сохранить, пожалуйста, введите его номер телефона");
                    number = reader.readLine().trim();
                    if(isPhone(number))
                    {
                        String name = phoneBook.get(number);
                        if(name != null)
                        {
                            System.out.println("Этот номер уже есть в списке контактов!");
                        }
                        else{
                            phoneBook.put(number, command);
                            System.out.println("Запись сохранена, спасибо!");
                        }
                    }
                    else {
                        System.out.println("Номер введен не корректно!!!");
                    }
                }
            }
            else{
                System.out.println("Введена неверная команда!!!");
            }
        }
    }
}
