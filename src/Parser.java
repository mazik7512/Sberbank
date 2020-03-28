import java.io.*;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Parser {

    private static int count;
    private static int parentCount;
    private static boolean m_bCheck;

    public Parser() {
        count = 0;
        parentCount = 0;
        m_bCheck = false;
    }

    public static int getCount(){
       return ++count;
    }

    public static void increaseParentCount(){
        parentCount++;
    }

    public static void decreaseParentCount(){ parentCount--; }

    public static int getParentCount(){
        return parentCount;
    }

    public String parseTitle(StringBuilder line){
        String t = "";
        for (int j = 0; j < line.length(); j++){
            if (line.charAt(j) == '{')
                increaseParentCount();
            else if (line.charAt(j) == '}')
                decreaseParentCount();
            if (line.charAt(j) == '='){
                if (line.charAt(j + 2) == '{'){
                    m_bCheck = false;
                }
                else {
                    m_bCheck = true;
                }
                t = line.substring(0, j);
                line.delete(0, j + 1);
                t = t.replaceAll("\\W", "");
                t = t.trim();
                return t;
            }

        }
        //parseTitle(line);
        return t;
    }

    public String parseContent(StringBuilder line){
        String t = "";
        for (int j = 0; j < line.length(); j++){
            if (line.charAt(j) == '"'){
                int t1 = line.indexOf("\"", j + 1);
                t = line.substring(j + 1, t1);
                line.delete(j, t1 + 1);
                t = t.trim();
                return t;
            }
        }
        return t;
    }


    public void parseText(File file) throws IOException {
        int st = 0;
        FileWriter fileWriter = new FileWriter("output.txt");
        FileReader fileReader = new FileReader(file);
        if(fileReader.ready())
        {
            char[] chars = new char[(int)file.length()];
            String t;
            String tt;
            fileReader.read(chars);
            String s = new String(chars);
            StringBuilder sb = null;
            for (int i = 0; i < s.length(); i++){
                if (s.charAt(i) == '=')
                    st++;
            }
            s = s.replaceAll("\\p{Cntrl}", "");
            sb = new StringBuilder(s);
            for (int i = 0; i < st; i++){
                t = parseTitle(sb);
                if (m_bCheck)
                    tt = parseContent(sb);
                else
                    tt = "";
                String out = String.format("(%d, %d, %s, %s)",getCount(), getParentCount(), t, tt);
                System.out.println(out);
                fileWriter.write(out + '\n');
            }
            fileReader.close();
            fileWriter.close();
        }
    }
}
