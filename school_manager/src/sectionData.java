import java.sql.*;

public class sectionData implements Comparable<sectionData>{
    int id, courseId, teacherId;
    Statement sn;

    public sectionData(int id, int courseId, int teacherId, Statement sn) throws SQLException {
        this.id = id;
        this.courseId = courseId;
        this.teacherId = teacherId;
        this.sn = sn;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getId() {
        return id;
    }

    public int getTeacherId() {
        return teacherId;
    }

    @Override
    public int compareTo(sectionData o) {
        return Integer.compare(getId(),o.getId());
    }

    @Override
    public String toString() {
        //name of course
        ResultSet rs = null;
        int course_id=-1;
        String course_name="";
        try {
            rs = sn.executeQuery("SELECT course_id from section where section_id="+id+";");
            while(rs!=null&&rs.next()) {
                course_id=rs.getInt("course_id");
            }
            System.out.println("\tcourse_id: " + course_id);
            rs = sn.executeQuery("SELECT title from course where course_id ="+course_id+";");
            while(rs!=null&&rs.next()) {
                course_name=rs.getString("title");
            }
            System.out.println("\ttitle: " + course_name);
        } catch (SQLException e) {
            System.out.println("Exception retrieiving course name");
        }

        return "ID: " + id + " Course: " + course_name;
        //return ""+id+" " + courseId+" " + teacherId;
    }
}