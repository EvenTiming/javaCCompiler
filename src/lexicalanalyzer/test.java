package lexicalanalyzer;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;

public class test {
    //功能测试
    public static void main(String[] args) throws IOException {
        ArrayList<Character> ReArray = new ArrayList<Character>();
        preprocessor pre=new preprocessor();
       // pre.readRe("Rewords.txt",ReArray);
       String str= pre.readProgram("testHard.c");
        System.out.println(str);
//        for(int i=0;i<ReArray.size();i++){
//            System.out.println(ReArray.get(i));
//        }
    }
}
