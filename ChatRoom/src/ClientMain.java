import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;


public class ClientMain
{
    static ArrayList<String> names = new ArrayList<>();
    public static void main(String[] args)
    {
        try {
            // create an object for the TTT game
            Data gameData = new Data();

            // create a connection to server
            Socket socket = new Socket("127.0.0.1",8001);
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());

            String name = "";
            String ip = "";
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Enter your name: ");
            name = keyboard.next();

            System.out.println("Enter the ip address of the server: ");
            ip = keyboard.next();

            TTTFrame frame = null;
            CommandFromServer cfs = (CommandFromServer) is.readObject();

            // Create the Frame based on which player the server says this client is
            if(cfs.getCommand() == CommandFromServer.CONNECTED) {
                frame = new TTTFrame(gameData, os, name);
                System.out.println("Connected as " + name);
            }


            // Starts a thread that listens for commands from the server
            ClientsListener cl = new ClientsListener(is,os,frame);
            Thread t = new Thread(cl);
            t.start();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
