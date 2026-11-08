package taskflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taskflow.domain.Board;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    // Custom query methods can be defined here if needed

    @Query("""
        select b from Board b
        join fetch b.members m
        where m.id = :userId
        order by b.id
        """)
    List<Board> findAllByMemberId(@Param("userId") Long userId);

    @Query("""
        select b from Board b
        join fetch b.members m
        where b.id = :boardId and m.id = :userId
        """)
    Board findByIdAndMemberId(@Param("boardId") Long boardId, @Param("userId") Long userId);

}
