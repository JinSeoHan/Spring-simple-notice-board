package com.kw4.repository;

import com.kw4.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


//Access DB using BoardEntity
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    @Modifying
    @Query("update BoardEntity b set b.views = b.views + 1 where b.id = :id")
    int countView(Long id);
}
