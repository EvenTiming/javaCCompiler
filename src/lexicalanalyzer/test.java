package lexicalanalyzer;

import java.io.IOException;
import java.util.HashMap;

public class test {
    //功能测试
    public static void main(String[] args) throws IOException {
        HashMap<String, Integer> hm=new HashMap<String,Integer>();
        preprocessor pre=new preprocessor();

        pre.readRe("Rewords.txt",hm);
        pre.readRe("Delimiter.txt",hm);
     //   System.out.println(hm.containsKey("include"));

      processor pro=new processor();
      pro.process("testHard.c",hm,"result.txt");

    }
}
