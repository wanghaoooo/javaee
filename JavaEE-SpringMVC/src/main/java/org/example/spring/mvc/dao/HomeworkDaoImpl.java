package org.example.spring.mvc.dao;

import org.example.spring.mvc.service.DatabasePool;
import org.example.spring.mvc.model.Homework;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HomeworkDaoImpl implements HomeworkDao{
    @Override
    public boolean add(Homework homework) {

        String sqlString = "insert into s_homework (title,content,create_time) values(?,?,?)";

        int resultSet = 0;
        try (Connection connection = DatabasePool.getHikariDataSource().getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement ps = connection.prepareStatement(sqlString)) {
                ps.setString(1,homework.getTitle());
                ps.setTimestamp(3,new Timestamp(homework.getCreateTime().getTime()));
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
    public boolean delete(Homework homework) {
        return false;
    }

    @Override
    public boolean change(Homework homework) {
        return false;
    }

    @Override
    public List<Homework> selectAll() {
        String sqlString = "SELECT * FROM s_homework";

        List<Homework> list = new ArrayList<>();
        try (Connection connection = DatabasePool.getHikariDataSource().getConnection()) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sqlString)) {
                    //获取执行结果
                    while (resultSet.next()) {
                        Homework homework = new Homework();
                        homework.setId(resultSet.getLong("id"));
                        homework.setTitle(resultSet.getString("title"));
                        homework.setCreateTime(resultSet.getTimestamp("create_time"));
                        list.add(homework);
                    }
                    connection.commit();
                }
            }catch (SQLException exception){
                System.out.println("数据读取失败");
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return list;
    }

    @Override
    public Homework select(String id) {
        String sqlString = "SELECT * FROM s_homework WHERE id=" + id;

        Homework homework = new Homework();
        try (Connection connection = DatabasePool.getHikariDataSource().getConnection()) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sqlString)) {
                    //获取执行结果
                    while (resultSet.next()) {
                        homework.setId(resultSet.getLong("id"));
                        homework.setTitle(resultSet.getString("title"));
                        homework.setCreateTime(resultSet.getTimestamp("create_time"));
                    }

                    connection.commit();
                }
            }catch (SQLException exception){
                System.out.println("数据读取失败");
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return homework;
    }
}
