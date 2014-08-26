import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

public class CUSrc {
    private static String clearUnusedJavaFile(String inFileName, String localProjectPath)
            throws IOException {

        File infile = new File(inFileName);
        RandomAccessFile outfile = new RandomAccessFile("UnusedJava.txt", "rw");
        int index = -1;
        String path = localProjectPath + "/src/";
        BufferedReader bf = new BufferedReader(new FileReader(infile));
        String content = "";
        StringBuilder sb = new StringBuilder();

        while ((content = bf.readLine()) != null) {

            index = content.indexOf(".<init>");
            if (index != -1
                    && (content.indexOf("\tClass") == (content.indexOf(")") + 1) || content.indexOf("\tInterface") == (content.indexOf(")") + 1))) {
                String temp = path + content.substring(0, index).replace('.', '/') + ".java";
                sb.append(temp).append("\t" + new File(temp).delete()).append(
                    "\t" + System.currentTimeMillis()).append("\r\n");
            }

        }
        outfile.seek(outfile.length());
        outfile.writeBytes(sb.toString());
        bf.close();
        outfile.close();

        return sb.toString();
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        if (args.length == 2) {
            String inputFile = args[0];
            String localProjectPath = args[1];
            try {
                String str = clearUnusedJavaFile(inputFile, localProjectPath);
                System.out.print(str);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.out.print("something wrong");
            }

        } else {
            System.out.println("arguments wrong！！");
        }

    }

}
