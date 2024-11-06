import java.util.*;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class parser{
    public static void main(String args[]){
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            File file = new File("user.xml");
            Document document = builder.parse(file);

            Scanner sc = new Scanner(System.in);
            System.out.println("Enter id");
            String id = sc.nextLine();

            NodeList users = document.getElementsByTagName("user");
            for(int i=0;i<users.getLength();i++){
                Element user = (Element)users.item(i);
                if(user.getAttribute("id").equals(id)){
                    System.out.println(user.getAttribute("id"));
                    System.out.println(user.getElementsByTagName("name").item(0).getTextContent());
                    System.out.println(user.getElementsByTagName("phno").item(0).getTextContent());
                }
            }
        } catch (Exception e){

        }
    }
}