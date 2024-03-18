import java.util.*;
public class Data
{
    private ArrayList<String> msgs;
    private ArrayList<String> names;
    public Data(){
        msgs =  new ArrayList<>();
        names = new ArrayList<>();
    }


    public ArrayList<String> getMsgs()
    {
        return msgs;
    }

    public ArrayList<String> getNames(){ return names;}
}
