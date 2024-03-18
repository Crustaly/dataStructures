import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain
{
    public static void main(String[] args)
    {
        try {
            // create an object for the TTT game
            Data gameData = new Data();

            // create a connection to server
            Socket socket = new Socket("127.0.0.1",8001);
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());

            CommandFromServer cfs = (CommandFromServer) is.readObject();
            TTTFrame frame = null;

            String name = "";
            String ip = "";
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter your name: ");
            name = sc.next();
            System.out.println("Enter the server ip address: ");
            ip = sc.next();

            if(cfs.getCommand() == CommandFromServer.CONNECTED){
                gameData.sendMsg(name + " has connected.");
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
