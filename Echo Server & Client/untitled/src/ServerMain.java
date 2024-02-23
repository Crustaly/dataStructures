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
            // Creates a sever socket that will allow clients to connect
            ServerSocket serverSocket = new ServerSocket(8000);

            while(true)
            {
                // creates a connection to the client
                Socket socket = serverSocket.accept();

                // creates a stream for writing objects to the client
                ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                // creates a stream for reading objects from the client
                ObjectInputStream is = new ObjectInputStream(socket.getInputStream());

                // creates a Thread for echoing to this client
                Thread t = new Thread(new ServersListener(is,os));
                // starts the thread (calls run)
                t.start();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
