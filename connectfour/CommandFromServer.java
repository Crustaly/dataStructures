import java.io.Serializable;

public class CommandFromServer implements Serializable
{
    // The command being sent to the client
    private int command;
    // Text data for the command
    private String data ="";

    // Command list
    public static final int CONNECTED_AS_BLACK=0;
    public static final int CONNECTED_AS_RED=1;
    public static final int BLACK_TURN=2;
    public static final int RED_TURN=3;
    public static final int MOVE=4;
    public static final int BLACK_WINS=5;
    public static final int RED_WINS=6;
    public static final int TIE=7;

    public CommandFromServer(int command, String data) {
        this.command = command;
        this.data = data;
    }

    public int getCommand() {
        return command;
    }

    public String getData() {
        return data;
    }
}
