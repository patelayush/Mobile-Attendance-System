package com.example.jarvis.mas;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {

    public static final String DB_NAME = "ClassroomManager";
    public static final int DB_VERSION = 1;

    public DatabaseManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    String create_table_classroom = "CREATE TABLE classroom (Cid INTEGER PRIMARY KEY AUTOINCREMENT," +
            "Subject char(20) NOT NULL ,Dept char(5) NOT NULL, Division char(2) NOT NULL," +
            "Sem INTEGER NOT NULL);";

    String create_table_student = "CREATE Table student(Sid INTEGER PRIMARY KEY AUTOINCREMENT," +
            "Roll Integer(12) NOT NULL ,Name char(30) NOT NULL,Cid INTEGER ," +
            "FOREIGN KEY(Cid) REFERENCES classroom (Cid));";

    String create_table_classroom_student = "CREATE TABLE classroom_student" +
            "(CSid INTEGER PRIMARY KEY AUTOINCREMENT,C_id INTEGER ," +
            "S_id INTEGER,FOREIGN KEY(C_id) references classroom(Cid)," +
            "FOREIGN KEY (S_id) references student (Sid));";

    String create_table_attendance = "CREATE TABLE attendance(Aid INTEGER PRIMARY KEY AUTOINCREMENT," +
            "Datetime DATETIME NOT NULL, Present INTEGER NOT NULL DEFAULT 0,CS_id INTEGER, " +
            "FOREIGN KEY(CS_id) REFERENCES classroom_student(CSid)); ";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_table_student);
        db.execSQL(create_table_classroom_student);
        db.execSQL(create_table_classroom);
        db.execSQL(create_table_attendance);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS student");
        db.execSQL("DROP TABLE IF EXISTS classroom");
        db.execSQL("DROP TABLE IF EXISTS classroom_student");
        db.execSQL("DROP TABLE IF EXISTS attendance");

        onCreate(db);
    }

    //Store classroom data
    public void storeClassroomdata(Classroom classroom) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Subject", classroom.getsubname());
        contentValues.put("Dept", classroom.getdeptname());
        contentValues.put("Division", classroom.getdivname());
        contentValues.put("Sem", classroom.getSem());

        db.insert("classroom", null, contentValues);
        db.close();
    }

    //show stored Classroom data
    public String showClassroomdata() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from classroom where Cid=1 ;", null);
        if (cursor != null)
            cursor.moveToFirst();
        Classroom classroom = new Classroom(Integer.parseInt(cursor.getString(cursor.getColumnIndex("Cid"))),
                cursor.getString(cursor.getColumnIndex("Subject")),
                cursor.getString(cursor.getColumnIndex("Dept")),
                cursor.getString(cursor.getColumnIndex("Division")),
                Integer.parseInt(cursor.getString(cursor.getColumnIndex("Sem"))));

        cursor.close();
        db.close();
        return classroom.getsubname();

    }

    // to get an array list
    public ArrayList<String> showClassroomlist() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select Subject from classroom;", null);

        if (cursor.moveToFirst()) {
            do {
                Classroom classroom = new Classroom();
                classroom.setsubname(cursor.getString(cursor.getColumnIndex("Subject")));
                list.add(classroom.getsubname());

            } while (cursor.moveToNext());

        }

        cursor.close();
        db.close();
        return list;

    }

    public ArrayList<Classroom> getclassroom(String cname){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<Classroom> classrooms=new ArrayList<>();
        Cursor cursor = db.rawQuery("Select * from classroom ;",null);
        if(cursor.moveToFirst()){
            do{
                Classroom classroom=new Classroom();
                classroom.setcid(cursor.getInt(cursor.getColumnIndex("Cid")));
                classroom.setdeptname(cursor.getString(cursor.getColumnIndex("Dept")));
                classroom.setdivname(cursor.getString(cursor.getColumnIndex("Division")));
                classroom.setSem(cursor.getInt(cursor.getColumnIndex("Sem")));
                classroom.setsubname(cursor.getString(cursor.getColumnIndex("Subject")));
                classrooms.add(classroom);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return classrooms;
    }

    public ArrayList<Student> getstudent(int cid){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<Student> students=new ArrayList<>();
        Cursor cursor = db.rawQuery("Select * from student where Cid="+cid+";",null);
        if(cursor.moveToFirst()){
            do{
                Student student=new Student();
                student.setSroll(cursor.getInt(cursor.getColumnIndex("Roll")));
                student.setSname(cursor.getString(cursor.getColumnIndex("Name")));
                students.add(student);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return students;
    }

    public String getDepartmentname(int cid) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select Dept from classroom where Cid=" + cid + ";", null);
        String deptname = null;
        if (cursor.moveToFirst()) {
            do {
                deptname = cursor.getString(cursor.getColumnIndex("Dept"));
                // list.add(classroom.getsubname());

            } while (cursor.moveToNext());

        }

        cursor.close();
        db.close();
        return deptname;

    }

    public String getDivname(int cid) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select Division from classroom where Cid=" + cid + ";", null);
        String deptname = null;
        if (cursor.moveToFirst()) {
            do {
                deptname = cursor.getString(cursor.getColumnIndex("Division"));
                // list.add(classroom.getsubname());

            } while (cursor.moveToNext());

        }

        cursor.close();
        db.close();
        return deptname;

    }

    public String getSubject(int cid) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select Subject from classroom where Cid=" + cid + ";", null);
        String subject = null;
        if (cursor.moveToFirst()) {
            do {
                subject = cursor.getString(cursor.getColumnIndex("Subject"));
                // list.add(classroom.getsubname());

            } while (cursor.moveToNext());

        }

        cursor.close();
        db.close();
        return subject;

    }

    public int getSemester(int cid) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select Sem from classroom where Cid=" + cid + ";", null);
        int semester = 0;
        if (cursor.moveToFirst()) {
            do {
                semester = cursor.getInt(cursor.getColumnIndex("Sem"));
                // list.add(classroom.getsubname());

            } while (cursor.moveToNext());

        }

        cursor.close();
        db.close();
        return semester;

    }

    //To store Student data

    public void storeStudentData(Student student) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Roll", student.getSroll());
        contentValues.put("Name", student.getSname());
        contentValues.put("Cid", student.getCid());


        db.insert("student", null, contentValues);
        db.close();
        int studentid = selectLastStudentId();
        setcsid(studentid, student.getCid());
    }

    //to give us sid of last student
    private int selectLastStudentId() {
        SQLiteDatabase db = this.getReadableDatabase();

        int lastStudentId = 0;
        String query = "SELECT MAX(Sid) FROM student";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            lastStudentId = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return lastStudentId;
    }


    public ArrayList<String> showStudentslist(int subject) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select Name from student where Cid=" + subject + " ;", null);

        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setSname(cursor.getString(cursor.getColumnIndex("Name")));
                list.add(student.getSname());

            } while (cursor.moveToNext());

        }

        cursor.close();
        db.close();
        return list;
    }

    //get count of students
    public int getstudentscount() {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select Count(Sid) as count from  student;", null);

        if (cursor.moveToFirst()) {
            do {
                count = Integer.parseInt(cursor.getString(cursor.getColumnIndex("count")));
            } while (cursor.moveToNext());

        }

        cursor.close();
        db.close();
        return count;
    }

    public void setcsid(int sid, int cid) {

        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("C_id", cid);
        contentValues.put("S_id", sid);
        db1.insert("classroom_student", null, contentValues);
        db1.close();

    }


    public int showcsid(int sid, int cid) {
        SQLiteDatabase db = this.getReadableDatabase();
        int csid = 0;
        Cursor cursor = db.rawQuery("Select CSid from classroom_student where S_id =" + sid +
                " and " + "C_id = " + cid + " ;", null);

        if (cursor.moveToFirst()) {
            do {
                csid = cursor.getInt(cursor.getColumnIndex("CSid"));

            } while (cursor.moveToNext());

        }

        cursor.close();
        db.close();
        return csid;

    }

    //Save Attendance
    public void saveattendance(Attendance attendance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("DateTime", attendance.getDateTime());
        values.put("Present", attendance.isPresent());
        values.put("CS_id", attendance.getCSid());

        db.insert("attendance", null, values);
        db.close();

    }


    //get Sid for Cid
    public int getSid(String sname, int cid) {
        int sid = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select Sid from student where Name = '" + sname +
                "' and Cid = " + cid + " ;", null);
        if (cursor.moveToFirst()) {
            do {
                sid = cursor.getInt(cursor.getColumnIndex("Sid"));

            } while (cursor.moveToNext());

        }
        db.close();
        return sid;
    }

    //to get all sids associated to a cid
    public ArrayList<Integer> getAllSids(int cid) {
        ArrayList<Integer> all_sid = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select Sid from student where Cid = " + cid + " ;", null);
        if (cursor.moveToFirst()) {
            do {
                all_sid.add(cursor.getInt(cursor.getColumnIndex("Sid")));
            } while (cursor.moveToNext());

        }
        db.close();
        return all_sid;
    }

    //to get csid for cid

    public ArrayList<Integer> getcsid(int cid) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Integer> csidlist = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select CSid from classroom_student where C_id = " + cid + " ;", null);

        if (cursor.moveToFirst()) {
            do {
                csidlist.add(cursor.getInt(cursor.getColumnIndex("CSid")));

            } while (cursor.moveToNext());

        }

        cursor.close();
        db.close();
        return csidlist;

    }

    public ArrayList<String> getdates(Integer integer) {
        ArrayList<String> datetime = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select Datetime from attendance where CS_id= " + integer + " ;", null);
        if (cursor.moveToFirst()) {
            do {
                datetime.add(cursor.getString(cursor.getColumnIndex("Datetime")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return datetime;
    }

    public int getcid(String cname) {
        int cid=0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select Cid from classroom where Subject = '" + cname + "' ;", null);

        if (cursor.moveToFirst()) {
            do {
                cid = cursor.getInt(cursor.getColumnIndex("Cid"));

            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return cid;
    }

    public int getsidbyname(String sname) {
        int sid=0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select Sid from student where Name = '" + sname + "' ;", null);

        if (cursor.moveToFirst()) {
            do {
                sid = cursor.getInt(cursor.getColumnIndex("Sid"));

            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return sid;
    }

/*
    private Attendance showAttendancedata() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from attendance;", null);
        int i=0;
        if (cursor.moveToFirst()) {

            do {
                Attendance attendance[i] = new Attendance(cursor.getInt(cursor.getColumnIndex("Aid")),
                        cursor.getString(cursor.getColumnIndex("Datetime")),
                        cursor.getInt(cursor.getColumnIndex("Present")),
                        cursor.getInt(cursor.getColumnIndex("CS_id")));
                return attendance[i];
                i++;
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return null;
    }*/


    public ArrayList<Integer> getallcsid() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Integer> csidlist = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select CSid from classroom_student ;", null);

        if (cursor.moveToFirst()) {
            do {
                csidlist.add(cursor.getInt(cursor.getColumnIndex("CSid")));

            } while (cursor.moveToNext());

        }

        cursor.close();
        db.close();
        return csidlist;

    }


    public ArrayList<String> getalldates() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> csidlist = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select Datetime from attendance ;", null);

        if (cursor.moveToFirst()) {
            do {
                csidlist.add(cursor.getString(cursor.getColumnIndex("Datetime")));

            } while (cursor.moveToNext());

        }

        cursor.close();
        db.close();
        return csidlist;

    }

    public int getdatesfromatendance(int csid) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;
        Cursor cursor = db.rawQuery("SELECT Count(Datetime) as count from attendance where CS_id= "
                + csid + ";", null);
        if (cursor.moveToFirst()) {
            do {
                count = cursor.getInt(cursor.getColumnIndex("count"));


            } while (cursor.moveToNext());
        }
        return count;
    }

    public ArrayList<Integer> getacsid(String datetime) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Integer> acsidlist=new ArrayList<>();
        Cursor cursor = db.rawQuery("Select CS_id from attendance where Datetime='" +datetime+"' ;", null);

        if (cursor.moveToFirst()) {
            do {
                acsidlist.add(cursor.getInt(cursor.getColumnIndex("CS_id")));

            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return acsidlist;
    }

    //get sroll from cids;
    public ArrayList<String> getsname(int cid) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> sname=new ArrayList<>();
        Cursor cursor = db.rawQuery("Select Name from student where Cid = " + cid + " ;", null);

        if (cursor.moveToFirst()) {
            do {
                sname.add(cursor.getString(cursor.getColumnIndex("Name")));

            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return sname;
    }


    //get presentinfo from attendance
    public int getpresentinfo(int csid, String datetime) {
        SQLiteDatabase db = this.getReadableDatabase();
        int present = 0;
        Cursor cursor = db.rawQuery("Select Present from attendance where CS_id = " + csid +
                " and Datetime='"+datetime+"' ;", null);

        if (cursor.moveToFirst()) {
            do {
                present= (cursor.getInt(cursor.getColumnIndex("Present")));

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return present;
    }

    //to get studentid for present students
    public int getsid(int csid) {
        SQLiteDatabase db = this.getReadableDatabase();
        int sid=0;
        Cursor cursor = db.rawQuery("Select S_id from classroom_student where CSid= "+csid+" ;", null);

        if (cursor.moveToFirst()) {
            do {
                sid=(cursor.getInt(cursor.getColumnIndex("S_id")));

            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return sid;
    }
    //
    public String getstuname(int sid,int cid){
        SQLiteDatabase db = this.getReadableDatabase();
        String sname=null;
        Cursor cursor = db.rawQuery("Select Name from student where Sid= "+sid+" and Cid= "+
                cid+" ;", null);

        if (cursor.moveToFirst()) {
            do {
                sname=(cursor.getString(cursor.getColumnIndex("Name")));

            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return sname;
    }
    //get sid for cid
    public ArrayList<Integer> allsid(int cid) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Integer> sid=new ArrayList<>();
        Cursor cursor = db.rawQuery("Select Sid from student where Cid = " + cid + " ;", null);

        if (cursor.moveToFirst()) {
            do {
                sid.add(cursor.getInt(cursor.getColumnIndex("Sid")));

            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return sid;
    }

    public int getattendancepercentage(int csid) {
        SQLiteDatabase db=this.getReadableDatabase();
        int percent = 0;
        Cursor cursor=db.rawQuery("select sum(present)*100/count(Aid) as percent " +
                "from attendance where CS_id= "+csid+" ;",null);
        if(cursor.moveToFirst()){
            do{
                percent=cursor.getInt(cursor.getColumnIndex("percent"));

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return percent;
    }

    public int gettotalattendance(int csid) {
        SQLiteDatabase db=this.getReadableDatabase();
        int total = 0;
        Cursor cursor=db.rawQuery("select count(Aid) as count from attendance where CS_id= "+csid+" ;",null);
        if(cursor.moveToFirst()){
            do{
                total=cursor.getInt(cursor.getColumnIndex("count"));

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    public int getpresentcount(int csid) {
        SQLiteDatabase db=this.getReadableDatabase();
        int total = 0;
        Cursor cursor=db.rawQuery("select sum(present) as sum from attendance where CS_id= " + csid + " ;", null);
        if(cursor.moveToFirst()){
            do{
                total=cursor.getInt(cursor.getColumnIndex("sum"));

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    public ArrayList<String> getdateslist(int csid){
        ArrayList<String> datelist=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select Datetime from attendance where CS_id= " + csid +
                " and Present=1 ;", null);
        if(cursor.moveToFirst()){
            do{
                datelist.add(cursor.getString(cursor.getColumnIndex("Datetime")));

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return datelist;
    }


    public void deleteclassroom(int cid) {
        String c_id = String.valueOf(cid);

        //delete attendances of the classroom
        deleteAttendanceRowsForClassroom(c_id);
        //delete students of the classroom
        deleteStudentsOfClassroom(c_id);
        //delete related classroom-student rows
        deleteClassroomStudentRowsForClassroom(c_id);

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("classroom", "Cid = ?", new String[]{c_id}) ;

        db.close();

    }


     // Delete attendance table rows that are related with given classroom.

    private void deleteAttendanceRowsForClassroom(String classroom_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("attendance", "attendance.CS_id IN " +
                        "(SELECT classroom_student.CSid from classroom_student " +
                        "WHERE classroom_student.C_id = ?)",
                new String[]{classroom_id});

        db.close();
    }


    //  Delete classroomstudent table rows that are related with given classroom.
    private void deleteClassroomStudentRowsForClassroom(String classroom_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("classroom_student", "C_id = ?",
                new String[]{classroom_id});

        db.close();
    }

    //Delete students related with a given classroom
    private void deleteStudentsOfClassroom(String classroom_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("student", "Cid = ?" ,
                new String[]{classroom_id});

        db.close();
    }

    public void deleteStudent(int studentId, int classroomId) {
        String student_id = String.valueOf(studentId);
        String classroom_id = String.valueOf(classroomId);

        //delete attendances of the student
        deleteAttendanceRowsForStudent(student_id, classroom_id);
        //delete related classroom-student rows
        deleteClassroomStudentRowsForStudent(student_id, classroom_id);

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("student", "Sid = ?", new String[]{student_id});

        db.close();

    }

    //delete attendance rows related with student
    private void deleteAttendanceRowsForStudent(String student_id, String classroom_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("attendance", "attendance.CS_id IN " +
                        "(SELECT classroom_student.CSid FROM classroom_student " +
                        "WHERE classroom_student.C_id = ? " +
                        "AND classroom_student.S_id = ?)",
                new String[]{classroom_id, student_id});

        db.close();
    }


    public void deleteClassroomStudentRowsForStudent(String student_id, String classroom_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("classroom_student", "S_id = ?"
                + " and C_id = ?", new String[]{student_id, classroom_id}) ;

        db.close();

    }

    public void deleteAttendance(String dateTime, int classroomId) {
        SQLiteDatabase db = this.getWritableDatabase();

        String classroom_id = String.valueOf(classroomId);

        db.delete("attendance", "attendance.CS_id IN " +
                "(SELECT CSid FROM classroom_student WHERE classroom_student.C_id = ?) " +
                "AND attendance.Datetime = ?", new String[]{classroom_id, dateTime});

        db.close();

    }


    public boolean deleteAllAttendancesOfClass(int classroomId) {
        SQLiteDatabase db = this.getWritableDatabase();

        String classroom_id = String.valueOf(classroomId);

        boolean isSuccessful = db.delete("attendance", "attendance.CS_id IN " +
                        "(SELECT id FROM classroomStudent WHERE classroomStudent.classroom_id = ?)",
                new String[]{classroom_id}) > 0;

        db.close();

        return isSuccessful;
    }

    public void updatestudentname(int sid, String new_name) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("Name",new_name);
        db.update("student",values,"Sid=?",new String[]{String.valueOf(sid)});
        db.close();
    }

    public ArrayList<Integer> getrolls(int cid) {
        ArrayList<Integer> rolllist=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select Roll from student where Cid= " + cid , null);
        if(cursor.moveToFirst()){
            do{
                rolllist.add(cursor.getInt(cursor.getColumnIndex("Roll")));

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return rolllist;
    }
}