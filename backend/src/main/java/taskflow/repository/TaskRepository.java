package taskflow.repository;

import taskflow.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByBoardIdOrderByStatusAscPositionAsc(Long boardId);

    // Filtrer par (id, boardId) empêche de lire la tâche d'un autre tableau
    // en devinant son numéro (faille de référence directe d'objet).
    Optional<Task> findByIdAndBoardId(Long id, Long boardId);
}