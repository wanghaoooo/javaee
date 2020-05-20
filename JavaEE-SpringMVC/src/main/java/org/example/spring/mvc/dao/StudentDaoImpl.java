package org.example.spring.mvc.dao;

import org.example.spring.mvc.service.DatabasePool;
import org.example.spring.mvc.model.Student;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;


@Repository
public class StudentDaoImpl implements StudentDao {

    @Override
    public boolean add(Student student) {
        String sqlString = "insert into s_student (id,name,create_time) values(?,?,?)";

        int resultSet = 0;
        try (Connection connection = DatabasePool.getHikariDataSource().getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement ps = connection.prepareStatement(sqlString)) {
                ps.setLong(1,student.getId());
                ps.setString(2,student.getName());
                ps.setTimestamp(3,new Timestamp(student.getCreateTime().getTime()));
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
    public boolean delete(Student student) {
        return false;
    }

    @Override
    public List<Student> select() {
        return null;
    }
}
