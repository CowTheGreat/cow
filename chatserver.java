import java.io.*;
import java.util.*;
import java.net.*;

public class chatserver{
    public static void main(String args[]){
        try{
            String msg;
            byte[] buf = new byte[1024];

            DatagramSocket s = new DatagramSocket(1200);
            DatagramPacket dp = new DatagramPacket(buf,buf.length);

            Scanner sc = new Scanner(System.in);
            InetAddress ia = InetAddress.getByName("localhost");

            while(true){
                s.receive(dp);
                msg = new String(dp.getData());
                System.out.println(msg);
                
                System.out.println("Enter msg to client");
                msg = sc.nextLine();
                buf = msg.getBytes();
    
                dp = new DatagramPacket(buf,buf.length,ia,1800);
                s.send(dp);
            }

        } catch(Exception e){

        }
    }
}
