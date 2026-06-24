CREATE TABLE tasks (
                       id          UUID         NOT NULL PRIMARY KEY,
                       title       VARCHAR(255) NOT NULL,
                       description TEXT,
                       status      VARCHAR(20)  NOT NULL,
                       priority    VARCHAR(10)  NOT NULL,
                       created_at  TIMESTAMP    NOT NULL,
                       updated_at  TIMESTAMP    NOT NULL
);

CREATE INDEX idx_tasks_status   ON tasks(status);
CREATE INDEX idx_tasks_priority ON tasks(priority);