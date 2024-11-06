import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SimplePop {
    public static void main(String args[]) {
        String pop3server = "pop.gmail.com";
        int port = 995;
        String username = "thememelord987@gmail.com";
        String password = "Cow@r@98"; 

        try {

            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket socket = (SSLSocket) factory.createSocket(pop3server, port);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStream writer = socket.getOutputStream();

            System.out.println(reader.readLine());

            sendCommand(writer, "USER " + username);
            System.out.println(reader.readLine());

            sendCommand(writer, "PASS " + password);
            System.out.println(reader.readLine());

            sendCommand(writer, "STAT");
            System.out.println(reader.readLine());

            sendCommand(writer, "LIST");
            readMultiLine(reader);
             

            sendCommand(writer, "QUIT");
            System.out.println(reader.readLine());

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendCommand(OutputStream writer, String command) throws Exception {
        writer.write((command + "\r\n").getBytes());
        writer.flush();
    }

    private static void readMultiLine(BufferedReader reader) throws Exception {
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            if (line.startsWith(".")) break; 
        }
    }
}