import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by AmierNaji on 1/19/15.
 */
public class TerminalOutput {

    public static void main(String[] args) {

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < args.length; i++) {

            sb.append(args[i] + " ");
        }

        System.out.println(sb.toString());
        System.out.println(execute(args));
    }

    public static String executeString(String s) {

        return execute(s.split(" "));
    }

    public static String execute(String[] cmd) {

        StringBuffer out = new StringBuffer();

        try {

            Process toRun = new ProcessBuilder(cmd).start();
            BufferedReader reader = getBufferReader(toRun);

            String line = reader.readLine();

            while(line != null) {

                out.append(line + "\n");
                line = reader.readLine();
            }

        }

        catch (Exception e) {

            e.printStackTrace();
            return "Error";
        }

        return out.toString();
    }

    public static BufferedReader getBufferReader(Process p) {

        return new BufferedReader (new InputStreamReader(p.getInputStream()));
    }
}
