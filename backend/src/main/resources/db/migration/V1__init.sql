CREATE TABLE users (
    id            BIGSERIAL PRIMARY KEY,
    email         VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    display_name  VARCHAR(100) NOT NULL
);

CREATE TABLE boards (
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    owner_id BIGINT NOT NULL REFERENCES users(id)
);

CREATE TABLE board_members (
    board_id BIGINT NOT NULL REFERENCES boards(id) ON DELETE CASCADE,
    user_id  BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    PRIMARY KEY (board_id, user_id)
);

CREATE TABLE tasks (
    id          BIGSERIAL PRIMARY KEY,
    board_id    BIGINT NOT NULL REFERENCES boards(id) ON DELETE CASCADE,
    title       VARCHAR(150) NOT NULL,
    description TEXT,
    status      VARCHAR(20) NOT NULL DEFAULT 'TODO',
    position    INT NOT NULL DEFAULT 0,
    due_date    DATE,
    assignee_id BIGINT REFERENCES users(id) ON DELETE SET NULL
);

CREATE INDEX idx_tasks_board ON tasks(board_id);