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

            // determine if playing as X or O
            CommandFromServer cfs = (CommandFromServer) is.readObject();
            TTTFrame frame;

            // Create the Frame based on which player the server says this client is
            if(cfs.getCommand() == CommandFromServer.CONNECTED_AS_RED)
                frame = new TTTFrame(gameData,os,"R");
            else
                frame = new TTTFrame(gameData,os, "B");

            // Starts a thread that listens for commands from the server
            ClientsListener cl = new ClientsListener(is,os,frame);
            Thread t = new Thread(cl);
            t.start();

            String text = "";
            Scanner keyboard = new Scanner(System.in);
            do {
                System.out.print("Enter text to send to the server (\"Exit\" to Quit): ");
                text = keyboard.next();


                if(!text.equals("exit"))
                {
                    // write the given text to the server
                    os.writeObject(text);
                    os.reset();
                    // reads the text back from the server
                    String echo = (String) is.readObject();
                    System.out.println("\t echo: " + echo);
                }
            }while(!text.equals("exit"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
