import java.io.Serializable;

public class CommandFromClient implements Serializable
{
    // The command being sent to the server
    private int command;
    // Text data for the command
    private String data ="";

    // Command list
    public static final int SEND = 0;
    public static final int EXIT = 1;

    public static final int JOIN = 2;

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
