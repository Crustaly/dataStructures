import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain
{
    public static void main(String[] args)
    {
        try
        {
            // creates a socket that allows connections on port 8001
            ServerSocket serverSocket = new ServerSocket(8001);

            // allow X to connect and build streams to / from X
            Socket xCon = serverSocket.accept();
            ObjectOutputStream xos = new ObjectOutputStream(xCon.getOutputStream());
            ObjectInputStream xis = new ObjectInputStream(xCon.getInputStream());

            // Lets the client know they are the X player
            xos.writeObject(new CommandFromServer(CommandFromServer.CONNECTED_AS_RED,null));
            System.out.println("RED has Connected.");

            // Creates a Thread to listen to the X client
            ServersListener sl = new ServersListener(xis,xos,'R');
            Thread t = new Thread(sl);
            t.start();

            // allow O to connect and build streams to / from O
            Socket oCon = serverSocket.accept();
            ObjectOutputStream oos = new ObjectOutputStream(oCon.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(oCon.getInputStream());

            // Lets the client know they are the X player
            oos.writeObject(new CommandFromServer(CommandFromServer.CONNECTED_AS_BLACK,null));
            System.out.println("BLACK has Connected.");

            // Creates a Thread to listen to the X client
            sl = new ServersListener(ois,oos,'B');
            t = new Thread(sl);
            t.start();


            xos.writeObject(new CommandFromServer(CommandFromServer.RED_TURN,null));
            oos.writeObject(new CommandFromServer(CommandFromServer.RED_TURN,null));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
