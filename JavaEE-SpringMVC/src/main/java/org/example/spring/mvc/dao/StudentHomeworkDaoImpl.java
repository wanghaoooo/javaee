package org.example.spring.mvc.dao;

import org.example.spring.mvc.service.DatabasePool;
import org.example.spring.mvc.model.StudentHomework;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentHomeworkDaoImpl implements StudentHomeworkDao {

    @Override
    public boolean add(StudentHomework studentHomework) {
        String sqlString = "insert into s_student_homework (student_id,homework_id," +
                "homework_title,homework_content,create_time) values(?,?,?,?,?)";

        int resultSet = 0;

        try (Connection connection = DatabasePool.getHikariDataSource().getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement ps = connection.prepareStatement(sqlString)) {
                ps.setLong(1,studentHomework.getStudentId());
                ps.setLong(2,studentHomework.getHomeworkId());
                ps.setString(3,studentHomework.getHomeworkTitle());
                ps.setString(4,studentHomework.getHomeworkContent());
                ps.setTimestamp(5,new Timestamp(studentHomework.getCreateTime().getTime()));
                resultSet = ps.executeUpdate();
                connection.commit();
            }catch (SQLException exception){
                System.out.println("数据写入失败");
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet > 0;
    }

    @Override
    public boolean delete(StudentHomework studentHomework) {
        return false;
    }

    @Override
    public List<StudentHomework> getAll(String id) {
        String sqlString = "SELECT * FROM s_student_homework WHERE homework_id=" + id;

        List<StudentHomework> list = new ArrayList<>();

        try (Connection connection = DatabasePool.getHikariDataSource().getConnection()) {
            //不自动commit
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sqlString)) {
                    //获取执行结果
                    while (resultSet.next()) {
                        StudentHomework sh = new StudentHomework();
                        sh.setStudentId(resultSet.getLong("student_id"));
                        sh.setHomeworkId(resultSet.getLong("homework_id"));
                        sh.setHomeworkTitle(resultSet.getString("homework_title"));
                        sh.setHomeworkContent(resultSet.getString("homework_content"));
                        sh.setCreateTime(resultSet.getTimestamp("create_time"));
                        sh.setUpdateTime(resultSet.getTimestamp("update_time"));
                        list.add(sh);
                    }
                    connection.commit(); //提交事务
                }catch (SQLException e1){
                    System.out.println("数据读取失败");
                    connection.rollback(); //回滚事务
                }

            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return list;
    }
}
