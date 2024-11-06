import java.util.*;
import java.io.*;
import java.net.*;
public class http{
    public static void main(String args[]){
        try{
            URL url = new URL("https://www.wikepedia.org");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String str;
            while((str = br.readLine())!= null){
                System.out.println(str);
            }
        } catch(Exception e){

        }
    }
}
