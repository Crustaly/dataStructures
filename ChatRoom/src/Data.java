import java.util.*;
public class Data
{
    private ArrayList<String> msgs;
    public Data(){
        msgs =  new ArrayList<>();
    }


    public ArrayList<String> getMsgs()
    {
        return msgs;
    }

    public void sendMsg(String s){
        msgs.add(s);

    }

}
