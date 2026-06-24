-- Sample tasks for development
INSERT INTO tasks (id, title, description, status, priority, created_at, updated_at)
VALUES
    ('a1b2c3d4-0001-0001-0001-000000000001', 'Set up CI/CD pipeline', 'Configure GitHub Actions for build and deploy', 'OPEN', 'HIGH', NOW(), NOW()),
    ('a1b2c3d4-0002-0002-0002-000000000002', 'Write unit tests', 'Achieve 80% code coverage', 'IN_PROGRESS', 'MEDIUM', NOW(), NOW()),
    ('a1b2c3d4-0003-0003-0003-000000000003', 'Update documentation', 'Refresh README and API docs', 'DONE', 'LOW', NOW(), NOW()),
    ('a1b2c3d4-0004-0004-0004-000000000004', 'Fix login bug', 'Users cannot log in with SSO', 'OPEN', 'HIGH', NOW(), NOW()),
    ('a1b2c3d4-0005-0005-0005-000000000005', 'Refactor payment module', 'Extract shared logic into a utility class', 'IN_PROGRESS', 'MEDIUM', NOW(), NOW());