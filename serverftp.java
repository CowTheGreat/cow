import java.io.*;
import java.net.*;

public class serverftp{
    public static void main(String args[]){
        try{
            ServerSocket ss = new ServerSocket(1234);
            Socket s = ss.accept();

            File file = new File("a.txt");
            FileInputStream fin = new FileInputStream(file);
            OutputStream out = s.getOutputStream();

            byte[] buffer = new byte[1024];
            int bytesRead;
            while((bytesRead=fin.read(buffer))!=-1){
                out.write(buffer,0,bytesRead);
            }
        } catch(Exception e) {

        }
    }
}