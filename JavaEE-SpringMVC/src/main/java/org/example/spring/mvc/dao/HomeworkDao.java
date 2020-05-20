package org.example.spring.mvc.dao;

import org.example.spring.mvc.model.Homework;

import java.util.List;


public interface HomeworkDao {
    List<Homework> selectAll();
    Homework select(String id);
    boolean add(Homework homework);
    boolean delete(Homework homework);
    boolean change(Homework homework);

}
