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
                    frame.repaint();
                }

                if(cfs.getCommand() == CommandFromServer.RESET){
                    if(!frame.getResetRequest()) {
                        frame.confirm("Press OK to accept a rematch, else exit out the pop up", "Rematch Request");
                    }
                    //display for only one of the frames
                    frame.resetGrid();
                    frame.setTurn('R');
                    frame.repaint();

                }
                // processes the received command
                if(cfs.getCommand() == CommandFromServer.BLACK_TURN)
                    frame.setTurn('B');
                else if(cfs.getCommand() == CommandFromServer.RED_TURN)
                    frame.setTurn('R');
                else if(cfs.getCommand() == cfs.MOVE)
                {
                    String data = cfs.getData();
                    // pulls data for the move from the data field
                    int c = data.charAt(0) - '0';
                    int r = data.charAt(1) - '0';

                    // changes the board and redraw the screen
                    frame.makeMove(c,r,data.charAt(2));
                }
                // handles the various end game states
                else if(cfs.getCommand() == CommandFromServer.TIE)
                {
                    frame.setText("Tie game.");
                    frame.setTurn('t');
                }
                else if(cfs.getCommand() == CommandFromServer.BLACK_WINS)
                {
                    frame.setText("Black wins!");
                    frame.setTurn('t');
                }
                else if(cfs.getCommand() == CommandFromServer.RED_WINS)
                {
                    frame.setText("Red wins!");
                    frame.setTurn('t');
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


}
