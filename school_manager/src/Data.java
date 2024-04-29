public class Data implements Comparable<Data>{
    String firstName;
    String lastName;
    String id;

    public Data(String first, String last, String ID){
        firstName = first;
        lastName = last;
        id = ID;
    }
    public void setID(String ID)
    {
        id = ID;
    }
    public String getFirst(){
        return firstName;
    }
    public String getLast(){
        return lastName;
    }
    public String getID(){
        return id;
    }
    public String toString()
    {
        return getLast() + ", " + getFirst()+ " ID: " + getID();
    }
    @Override
    public int compareTo(Data o) {
        if(getLast().equals(o.getLast())){
            if(getFirst().equals(o.getFirst())) return getID().compareTo(o.getID());
            return getFirst().compareTo(o.getFirst());
        }
        return getLast().compareToIgnoreCase(o.getLast());
    }
}
