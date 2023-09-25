package org.yujin.myproc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yujin.myproc.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
