import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class ServersListener implements Runnable
{
    private ObjectInputStream is;
    private ObjectOutputStream os;

    // Stores the which player this listener is for
    private String name;

    // static data that is shared between both listeners
    private static ArrayList<String> names = new ArrayList<>();
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
                    names.remove(cfc.getData()); //once a person exits their name is no longer on the list
                    Collections.sort(names);
                    sendCommand(new CommandFromServer(CommandFromServer.NEWNAMES, names.toString()));
                }
                if(cfc.getCommand()==CommandFromClient.JOIN){
                   // System.out.println(syst===cfc.getData());
                    if(names.contains(cfc.getData())){

                        os.writeObject(new CommandFromServer(CommandFromServer.INVALID, null));
                        System.out.println("invalid");
                    }
                    else {
                        os.writeObject(new CommandFromServer(CommandFromServer.VALID, null));
                        names.add(cfc.getData());
                        Collections.sort(names);
                        sendCommand(new CommandFromServer(CommandFromServer.NEWNAMES, names.toString()));
                    }
                }
                if(cfc.getCommand() == CommandFromClient.SEND){
                    System.out.println("ServersListener");
                    sendCommand(new CommandFromServer(CommandFromServer.SEND, cfc.getData()));
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
