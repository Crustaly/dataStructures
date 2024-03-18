import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ClientsListener implements Runnable
{
    private ObjectInputStream is = null;
    private ObjectOutputStream os = null;
    private TTTFrame frame = null;

    public ClientsListener(ObjectInputStream is,
                           ObjectOutputStream os,
                           TTTFrame frame) {
        this.is = is;
        this.os = os;
        this.frame = frame;

    }

    @Override
    public void run() {
        try
        {
            while(true)
            {
                CommandFromServer cfs = (CommandFromServer)is.readObject();
                if(cfs.getCommand()==CommandFromServer.CLOSING) {
                    try {
                        frame.closing();
                    } catch (InterruptedException e) {
                        System.out.println("Exception exit");
                    }
                }
                if(cfs.getCommand()== CommandFromServer.CONFIRM) {

                }

                if(cfs.getCommand() == CommandFromServer.RESET){

                }
                // processes the received command

                else if(cfs.getCommand() == cfs.MOVE)
                {
                    String data = cfs.getData();
                    // pulls data for the move from the data field
                    int c = data.charAt(0) - '0';
                    int r = data.charAt(1) - '0';

                }

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


}
