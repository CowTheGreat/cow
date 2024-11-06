import java.io.*;
import java.util.*;
import java.net.*;

public class chatclient{
    public static void main(String args[]){
        try{
            String msg;
            byte[] buf = new byte[1024];

            DatagramSocket s = new DatagramSocket(1800);
            DatagramPacket dp = new DatagramPacket(buf,buf.length);

            Scanner sc = new Scanner(System.in);
            InetAddress ia = InetAddress.getByName("localhost");

            while(true){
            System.out.println("Enter msg to server");
            msg = sc.nextLine();
            buf = msg.getBytes();
            
            dp = new DatagramPacket(buf,buf.length,ia,1200);
            s.send(dp);

            s.receive(dp);
            msg = new String(dp.getData());
            System.out.println(msg);
            }
            
        } catch(Exception e){

        }
    }
}
