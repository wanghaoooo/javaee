package org.example.spring.mvc.service;
import org.example.spring.mvc.dao.HomeworkDao;
import org.example.spring.mvc.dao.HomeworkDaoImpl;
import org.example.spring.mvc.dao.StudentDaoImpl;
import org.example.spring.mvc.dao.StudentHomeworkDaoImpl;
import org.example.spring.mvc.model.Homework;
import org.example.spring.mvc.model.Student;
import org.example.spring.mvc.model.StudentHomework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
class AllServiceImpl implements AllService{
    private final HomeworkDao homeworkDao;
    private final StudentDaoImpl studentDao;
    private final StudentHomeworkDaoImpl studentHomeworkDao;

    @Autowired
    public AllServiceImpl(HomeworkDaoImpl homeworkDao,
                          StudentDaoImpl studentDao,
                          StudentHomeworkDaoImpl studentHomeworkDao) {
        this.homeworkDao = homeworkDao;
        this.studentDao = studentDao;
        this.studentHomeworkDao = studentHomeworkDao;
    }

    @Override
    public List<StudentHomework> selectAll(String id) {
        return studentHomeworkDao.getAll(id);
    }

    @Override
    public boolean addHomework(HttpServletRequest request){
        Homework homework = new Homework();

        homework.setTitle(request.getParameter("title"));
        return homeworkDao.add(homework);
    }

    @Override
    public boolean addStudent(HttpServletRequest request){
        Student student = new Student();
        student.setId(Long.parseLong(request.getParameter("id")));
        student.setName(request.getParameter("name"));
        return studentDao.add(student);
    }

    @Override
    public List<Homework> showHomework(){
        return homeworkDao.selectAll();
    }

    @Override
    public Homework showHomeworkDetails(String id){
        return homeworkDao.select(id);
    }

    @Override
    public boolean addStudentHomework(HttpServletRequest req){
        StudentHomework studentHomework = new StudentHomework();
        studentHomework.setStudentId(Long.parseLong(req.getParameter("studentId")));
        studentHomework.setHomeworkId(Long.parseLong(req.getParameter("homeworkId")));
        studentHomework.setHomeworkTitle(req.getParameter("title"));
        studentHomework.setHomeworkContent(req.getParameter("content"));
        return studentHomeworkDao.add(studentHomework);
    }

}


//@Service
//public class Service implements AllService{
//
//    private final HomeworkDao homeworkDao;
//    private final StudentDao studentDao;
//    private final StudentHomeworkDao studentHomeworkDao;
//
//    @Autowired
//    public Service(HomeworkDao homeworkDao, StudentDaoImpl studentDao, StudentHomeworkDaoImpl studentHomeworkDao) {
//        this.homeworkDao = homeworkDao;
//        this.studentDao = studentDao;
//        this.studentHomeworkDao = studentHomeworkDao;
//    }
//
//    private static String url = "jdbc:mysql://127.0.0.1:3306/school?serverTimezone=UTC";
//    private static String driverName = "com.mysql.cj.jdbc.Driver";
//
//    @Bean(name = "jdbc")
//    public Service getJDBC() {
//        return new Service();
//    }
//
//    //添加学生
//    public boolean addstudent(Student student){
//
//        try {
//            Class.forName(driverName);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        String sqlString = "insert into s_student (sid,name,create_time) values(?,?,?)";
//
//        int resultSet = 0;
//        try (Connection connection = DriverManager.getConnection(url, "root", "wh13716612056")) {
//            connection.setAutoCommit(false);//不自动commit
//            try (PreparedStatement a = connection.prepareStatement(sqlString)) {
//                a.setLong(1,student.getId());
//                a.setString(2,student.getName());
//                a.setTimestamp(3,new Timestamp(student.getCreateTime().getTime()));
//                resultSet = a.executeUpdate();
//                connection.commit();
//            }catch (SQLException exception){
//                System.out.println("数据写入失败");
//                connection.rollback();   //事务回滚
//            }
//
//        } catch (SQLException e) {
//
//            e.printStackTrace();
//        }
//        return resultSet >0;
//    }
//
//    //老师布置作业
//    @Override
//    public boolean addhomework(Homework homework){
//
//        try {
//            Class.forName(driverName);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        String sqlString = "insert into s_homework (hid,title,create_time) values(?,?,?)";
//
//        int resultSet = 0;
//        try (Connection connection = DriverManager.getConnection(url, "root", "wh13716612056")) {
//            connection.setAutoCommit(false);//设置不自动提交
//
//            try (PreparedStatement a = connection.prepareStatement(sqlString)) {
//                a.setLong(1,homework.getId());
//                a.setString(2,homework.getTitle());
//                a.setTimestamp(3,new Timestamp(homework.getCreateTime().getTime()));
//                resultSet = a.executeUpdate();
//            }catch (SQLException exception){
//                System.out.println("数据写入失败");
//                connection.rollback();   //失败后事务回滚
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return resultSet >0;
//    }
//
//    //学生提交作业
//    @Override
//    public boolean addstudenthomework(StudentHomework studentHomework){
//
//        try {
//            Class.forName(driverName);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        String sqlString = "insert into s_student_homework (sid,hid,htitle,hcontent,create_time) values(?,?,?,?,?)";
//
//        int resultSet = 0;
//        try (Connection connection = DriverManager.getConnection(url, "root", "wh13716612056")) {
//            connection.setAutoCommit(false);//不自动commit
//            try (PreparedStatement a = connection.prepareStatement(sqlString)) {
//                a.setLong(1,studentHomework.getStudentId());
//                a.setLong(2,studentHomework.getHomeworkId());
//                a.setString(3,studentHomework.getHomeworkTitle());
//                a.setString(4,studentHomework.getHomeworkContent());
//                a.setTimestamp(5,new Timestamp(studentHomework.getCreateTime().getTime()));
//                resultSet = a.executeUpdate();
//            }catch (SQLException exception){
//                System.out.println("数据写入失败");
//                connection.rollback();   //事务回滚
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return resultSet >0;
//    }
//
//    //获取全部学生作业信息
//    public static List<StudentHomework> selectstudenthomework(){
//        String sqlString = "SELECT * FROM s_student_homework";
//        try {
//            // 加载驱动
//            Class.forName(driverName);
//
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        List<StudentHomework> list = new ArrayList<>();
//        try(Connection connection =  DriverManager.getConnection(url, "root", "wh13716612056")) {
//            try(Statement statement = connection.createStatement()){
//                try(ResultSet resultSet = statement.executeQuery(sqlString)){
//                    // 获取执行结果
//                    while (resultSet.next()){
//                        StudentHomework sh = new StudentHomework();
//                        sh.setStudentId(resultSet.getLong("sid"));
//                        sh.setHomeworkId(resultSet.getLong("hid"));
//                        sh.setHomeworkTitle(resultSet.getString("htitle"));
//                        sh.setHomeworkContent(resultSet.getString("hcontent"));
//                        sh.setCreateTime(resultSet.getTimestamp("create_time"));
//                        list.add(sh);
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//    //获取全部学生信息
//    public static List<Student> selectstudent(){
//        String sqlString = "SELECT * FROM s_student";
//        try {
//            // 加载驱动
//            Class.forName(driverName);
//
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        List<Student> list = new ArrayList<>();
//        try(Connection connection =  DriverManager.getConnection(url, "root", "wh13716612056")) {
//            try(Statement statement = connection.createStatement()){
//                try(ResultSet resultSet = statement.executeQuery(sqlString)){
//                    // 获取执行结果
//                    while (resultSet.next()){
//                        Student s = new Student();
//                        s.setId(resultSet.getLong("sid"));
//                        s.setName(resultSet.getString("name"));
//                        s.setCreateTime(resultSet.getDate("create_time"));
//                        s.setUpdateTime(resultSet.getDate("update_time"));
//                        list.add(s);
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//
//    //获取所有布置的作业信息
//    public static List<Homework> selecthomework(){
//        String sqlString = "SELECT * FROM s_homework";
//        try {
//            // 加载驱动
//            Class.forName(driverName);
//
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        List<Homework> list = new ArrayList<>();
//        try(Connection connection =  DriverManager.getConnection(url, "root", "wh13716612056")) {
//            try(Statement statement = connection.createStatement()){
//                try(ResultSet resultSet = statement.executeQuery(sqlString)){
//                    // 获取执行结果
//                    while (resultSet.next()){
//                        Homework s = new Homework();
//                        s.setId(resultSet.getLong("hid"));
//                        s.setTitle(resultSet.getString("title"));
//                        s.setCreateTime(resultSet.getDate("create_time"));
//
//                        list.add(s);
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//
//
//}
