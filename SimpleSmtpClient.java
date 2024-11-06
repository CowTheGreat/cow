import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SimpleSmtpClient {
    public static void main(String[] args) {
        String smtpServer = "smtp.gmail.com";
        int port = 587;
        String username = "thememelord987@gmail.com";
        String password = "Cow@r@98";
        String senderEmail = "thememelord987@gmail.com";
        String recipientEmail = "cowara987@gmail.com";
        String subject = "Test Email";
        String message = "Hello, this is a test email sent using Java!";

        try (Socket socket = new Socket(smtpServer, port);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             OutputStream writer = socket.getOutputStream()) {

            // Initial server response
            System.out.println(reader.readLine());

            // EHLO command
            sendCommand(writer, "EHLO " + smtpServer);
            readMultiLine(reader);

            // STARTTLS command to switch to a secure connection
            sendCommand(writer, "STARTTLS");
            System.out.println(reader.readLine());

            // Upgrade to SSL/TLS socket
            SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket(socket, smtpServer, port, true);
            sslSocket.startHandshake();

            BufferedReader sslReader = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
            OutputStream sslWriter = sslSocket.getOutputStream();

            // Authenticate
            sendCommand(sslWriter, "AUTH LOGIN");
            sslReader.readLine();
            sendCommand(sslWriter, java.util.Base64.getEncoder().encodeToString(username.getBytes()));
            sslReader.readLine();
            sendCommand(sslWriter, java.util.Base64.getEncoder().encodeToString(password.getBytes()));
            sslReader.readLine();

            // Send email details
            sendCommand(sslWriter, "MAIL FROM:<" + senderEmail + ">");
            sslReader.readLine();
            sendCommand(sslWriter, "RCPT TO:<" + recipientEmail + ">");
            sslReader.readLine();
            sendCommand(sslWriter, "DATA");
            sslReader.readLine();

            // Send subject, headers, and body
            sslWriter.write(("Subject: " + subject + "\r\n").getBytes());
            sslWriter.write(("From: " + senderEmail + "\r\n").getBytes());
            sslWriter.write(("To: " + recipientEmail + "\r\n").getBytes());
            sslWriter.write(("\r\n" + message + "\r\n.\r\n").getBytes());
            sslWriter.flush();
            sslReader.readLine();

            // End session
            sendCommand(sslWriter, "QUIT");
            sslReader.readLine();

            sslSocket.close();

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
            if (line.startsWith("250 ") || line.startsWith("220 ")) break;
        }
    }
}