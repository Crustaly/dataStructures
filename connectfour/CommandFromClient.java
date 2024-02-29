import java.io.Serializable;

public class CommandFromClient implements Serializable
{
    public static final int MOVE    =0;
    public static final int RESTART =1;
    public static final int CLOSING = 2;
    public static final int CONFIRM = 3;
    // The command being sent to the server
    private int command;
    // Text data for the command
    private String data ="";



    public CommandFromClient(int command, String data) {
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
