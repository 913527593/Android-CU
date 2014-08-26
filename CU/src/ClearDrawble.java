import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClearDrawble {

    public static final String separator = File.separator;

    public static void clearDrable(String referDir, String projectPath, String[] dirArray) {

        FileWriter fw = null;
        int fileCount = 0;
        long fileSize = 0;

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            Date curDate = new Date(System.currentTimeMillis());
            String dateStr = formatter.format(curDate);
            fw = new FileWriter("XH" + dateStr + ".txt", true);

            File referPath = new File(projectPath + separator + "res" + separator + referDir);
            File[] files = referPath.listFiles();
            for (File file : files) {
                boolean hasSame = false;
                for (int i = 0; i < dirArray.length; i++) {
                    File file1 = new File(projectPath + separator + "res" + separator + dirArray[i]
                            + separator + file.getName());
                    if (file1.exists()) {
                        System.out.print(dirArray[i] + ": ");
                        hasSame = true;
                        ++fileCount;
                        fileSize += file.length();
                    }
                }
                if (hasSame) {
                    System.out.println(file.getName());
                }
            }
            String result = "delete file " + fileCount + ",save space " + fileSize / 1024 + "KB.";
            System.out.println(result);
            fw.write(result);
            fw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void findBig(String referDir, String projectPath, long line) {
        File referPath = new File(projectPath + separator + "res" + separator + referDir);
        File[] files = referPath.listFiles();
        for (File file : files) {
            if (file.length() > line) {
                System.out.println(file.getName() + " " + file.length() / 1024 + "KB");
            }
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please config program argument. Correct argument contains absolute path of android project.");
            return;
        }
        String projectPath = args[0];
        String referDir = "drawable-xhdpi";
        String[] dirArray = { "drawable", "drawable-hdpi", "drawable-ldpi", "drawable-mdpi",
                "drawable-nodpi" };
        ClearDrawble.clearDrable(referDir, projectPath, dirArray);
        ClearDrawble.findBig(referDir, projectPath, 30000);
    }
}
