package com.tommy.autocheckin.utils;


import java.io.DataOutputStream;
import java.util.List;

public class ShellUtils {

    private ShellUtils() {
        throw new UnsupportedOperationException("...");
    }

    /**
     * 执行adb 批命令
     *
     * @param list 传入的命令list
     * @return CommandResult
     */
    public static void execCmd(List<String> list) {
        String[] commands = (String[]) list.toArray();
        if (commands.length == 0) {
            return;
        }
        Process process = null;
        DataOutputStream os = null;
        try {
            //必须要 su
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            for (String command : commands) {
                //逐行执行 adb 指令
                if (command == null) continue;
                os.write(command.getBytes());
                os.writeBytes("\n");
                os.flush();
            }
            os.writeBytes("exit\n");
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIO(os);
            if (process != null) {
                process.destroy();
            }
        }
    }

}
