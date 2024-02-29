import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ServersListener implements Runnable
{
    private ObjectInputStream is;
    private ObjectOutputStream os;

    // Stores the which player this listener is for
    private char player;

    // static data that is shared between both listeners
    private static char turn = 'B';
    private static GameData gameData = new GameData();
    private static ArrayList<ObjectOutputStream> outs = new ArrayList<>();


    public ServersListener(ObjectInputStream is, ObjectOutputStream os, char player) {
        this.is = is;
        this.os = os;
        this.player = player;
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
                if(cfc.getCommand()==CommandFromClient.CLOSING){
                    sendCommand(new CommandFromServer(CommandFromServer.CLOSING, null));
                }
                if(cfc.getCommand()==CommandFromClient.CONFIRM){
                    sendCommand(new CommandFromServer(CommandFromServer.CONFIRM, null));
                }
                if(cfc.getCommand() == CommandFromClient.RESTART){
                    gameData.reset();
                    resetTurn();
                }
                if(cfc.getCommand()==CommandFromClient.MOVE &&
                    turn==player && !gameData.isWinner('B')
                        && !gameData.isWinner('R')
                        && !gameData.isCat())
                {
                    // pulls data for the move from the data field
                    String data=cfc.getData();
                    int c = data.charAt(0) - '0';
                    int r = data.charAt(1) - '0';

                    // if the move is invalid it, do not process it
                    if(gameData.getGrid()[r][c]!=' ')
                        continue;

                    // changes the server side game board
                    gameData.getGrid()[r][c] = player;

                    // sends the move out to both players
                    sendCommand(new CommandFromServer(CommandFromServer.MOVE,data));

                    // changes the turn and checks to see if the game is over
                    changeTurn();
                    checkGameOver();
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void changeTurn()
    {
        // changes the turn
        if(turn=='B')
            turn = 'R';
        else
            turn ='B';

        // informs both client of the new player turn
        if (turn == 'B')
            sendCommand(new CommandFromServer(CommandFromServer.BLACK_TURN, null));
        else
            sendCommand(new CommandFromServer(CommandFromServer.RED_TURN, null));
    }

    public void resetTurn(){
        turn = 'B';
        sendCommand(new CommandFromServer(CommandFromServer.RESET, null));
    }

    public void checkGameOver()
    {
        int command = -1;
        if(gameData.isCat())
            command = CommandFromServer.TIE;
        else if(gameData.isWinner('B'))
            command = CommandFromServer.BLACK_WINS;
        else if(gameData.isWinner('R'))
            command = CommandFromServer.RED_WINS;

        // if the game ended, informs both clients of the game's end state
        if(command!=-1)
            sendCommand(new CommandFromServer(command, null));
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