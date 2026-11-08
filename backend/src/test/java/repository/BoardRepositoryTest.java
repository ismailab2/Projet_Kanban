package taskflow.repository;

import taskflow.domain.Board;
import taskflow.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BoardRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");

    @Autowired UserRepository userRepository;
    @Autowired BoardRepository boardRepository;

    @Test
    void findAccessibleBy_returnsOnlyBoardsWhereUserIsMember() {
        // Arrange
        User alice = userRepository.save(new User("alice@test.com", "hash", "Alice"));
        User bob   = userRepository.save(new User("bob@test.com", "hash", "Bob"));
        User carol = userRepository.save(new User("carol@test.com", "hash", "Carol"));

        Board board = new Board("Sprint 1", alice);
        board.addMember(bob);
        boardRepository.save(board);

        // Act
        List<Board> forAlice = boardRepository.findAccessibleBy(alice.getId());
        List<Board> forBob   = boardRepository.findAccessibleBy(bob.getId());
        List<Board> forCarol = boardRepository.findAccessibleBy(carol.getId());

        // Assert
        assertThat(forAlice).extracting(Board::getName).containsExactly("Sprint 1");
        assertThat(forBob).extracting(Board::getName).containsExactly("Sprint 1");
        assertThat(forCarol).isEmpty();
    }
}