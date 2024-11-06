import java.util.*;
import java.io.*;
import java.net.*;

public class clientftp{
    public static void main(String args[]){
        try{
            Socket s = new Socket("localhost",1234);

            InputStream in = s.getInputStream();
            FileOutputStream fout = new FileOutputStream("b.txt");

            byte[] buffer = new byte[1024];
            int bytesRead;

            while((bytesRead=in.read(buffer))!=-1){
                fout.write(buffer,0,bytesRead);
            }

        } catch(Exception e){

        }
    }
}