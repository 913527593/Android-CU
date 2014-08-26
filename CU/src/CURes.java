import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CURes {

    public static final String separator = File.separator;

    public static void clearRes(String lintPath, String projectPath, String[] dirArray) {
        Process process = null;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileWriter fw = null;
        String cmd = lintPath + " --check UnusedResources " + projectPath;
        int fileCount = 0, singleCount;
        long fileSize = 0;

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("CURes-yyyy-MM-dd-HH-mm-ss");
            Date curDate = new Date(System.currentTimeMillis());
            String dateStr = formatter.format(curDate);
            fw = new FileWriter(dateStr + ".txt", true);

            Runtime runtime = Runtime.getRuntime();
            String line = null;

            do {
                singleCount = 0;
                process = runtime.exec(cmd);
                is = process.getInputStream();
                isr = new InputStreamReader(is);
                br = new BufferedReader(isr);

                while ((line = br.readLine()) != null) {
                    boolean needDel = false;
                    for (String dir : dirArray) {
                        if (line.startsWith("res" + separator + dir)) {
                            needDel = true;
                            break;
                        }
                    }
                    if (needDel) {
                        int index = line.indexOf(":");
                        if (index > 0) {
                            String filePath = projectPath + separator + line.substring(0, index);
                            ++fileCount;
                            ++singleCount;
                            File file = new File(filePath);
                            fileSize += file.length();
                            boolean success = file.delete();
                            System.out.println(filePath + " " + success);
                            fw.write(filePath + " " + success + "\n");
                            fw.flush();
                        }
                    }
                }

            } while (singleCount != 0);

            String result = "delete file " + fileCount + ",save space " + fileSize / 1024 + "KB.";
            System.out.println(result);
            fw.write(result);
            fw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (process != null) {
                process.destroy();
            }
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Please config program arguments. Correct arguments contain both absolute path of lint.bat and that of android project.");
            return;
        }
        String lintPath = args[0];
        String projectPath = args[1];
        String[] dirArray = { "drawable", "layout", "anim", "color" };
        CURes.clearRes(lintPath, projectPath, dirArray);
    }
}
