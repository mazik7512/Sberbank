import kotlin.text.Regex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Main {


    public static void main(String[] args) throws IOException {
        File file = new File("parse.TXT");
        Parser prs = new Parser();
        prs.parseText(file);
    }
}
