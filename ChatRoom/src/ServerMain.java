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
            xos.writeObject(new CommandFromServer(CommandFromServer.CONNECTED,null));

            ServersListener sl = new ServersListener(xis,xos);
            Thread t = new Thread(sl);
            t.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
