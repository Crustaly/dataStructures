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
}