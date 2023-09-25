package org.yujin.myproc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yujin.myproc.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
