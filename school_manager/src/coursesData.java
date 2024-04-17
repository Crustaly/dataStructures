public class coursesData implements Comparable<coursesData>
{
    String ID;
    String courseName;
    int type;
    public coursesData(String ID, String courseName, int type)
    {
        this.ID = ID;
        this.courseName = courseName;
        this.type = type;
    }

    public void setCourseName(String a)
    {
        courseName = a;
    }
    public void setType(int a)
    {
        type = a;
    }
    public String getCourseName()
    {
        return courseName;
    }
    public int getType()
    {
        return type;
    }
    public String getID()
    {
        return ID;
    }
    public void setID(String ID) {this.ID = ID;}
    @Override
    public String toString() {
        //return courseName;
        return courseName + " ID: " + ID;
    }

@Override
    public int compareTo(coursesData o) {
        return getCourseName().compareTo(o.getCourseName());
    }

}
