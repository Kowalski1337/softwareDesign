package com.ifmo.vbaydyuk.repository;

import com.ifmo.vbaydyuk.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByListId(long listId);

    @Transactional
    @Modifying
    @Query("update Task set completed = ?2 where id = ?1")
    void updateCompleted(@Param("id") long id, @Param("completed") boolean completed);
}
