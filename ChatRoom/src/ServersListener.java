import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ServersListener implements Runnable
{
    private ObjectInputStream is;
    private ObjectOutputStream os;

    // Stores the which player this listener is for
    private String name;

    // static data that is shared between both listeners
    private static ArrayList<String> names = new ArrayList<>();
    private static Data gameData = new Data();
    private static ArrayList<ObjectOutputStream> outs = new ArrayList<>();


    public ServersListener(ObjectInputStream is, ObjectOutputStream os) {
        this.is = is;
        this.os = os;
        //this.name = name;
        outs.add(os);
    }

    @Override
    public void run() {
        try
        {
            while(true)
            {
                CommandFromClient cfc = (CommandFromClient) is.readObject();

                // handle the received command
                if(cfc.getCommand()==CommandFromClient.EXIT){
                    //sendCommand(new CommandFromServer(CommandFromServer.CLOSING, null));
                }

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void sendCommand(CommandFromServer cfs)
    {
        // Sends command to both players
        for (ObjectOutputStream o : outs) {
            try {
                o.writeObject(cfs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
