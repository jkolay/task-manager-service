package com.task.controller;

import com.task.dto.TaskRequest;
import com.task.dto.TaskResponse;
import com.task.entity.TaskPriority;
import com.task.entity.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
@TestPropertySource(locations = "classpath:application.properties")
class TaskControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Autowired private ObjectMapper objectMapper;

    private TaskRequest validRequest() {
        TaskRequest r = new TaskRequest();
        r.setTitle("Integration Test Task");
        r.setDescription("Created in test");
        r.setStatus(TaskStatus.OPEN);
        r.setPriority(TaskPriority.HIGH);
        return r;
    }

    @Test
    void createTask_returns201() throws Exception {
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Integration Test Task"))
                .andExpect(jsonPath("$.status").value("OPEN"))
                .andExpect(jsonPath("$.priority").value("HIGH"));
    }

    @Test
    void createTask_invalidTitle_returns400() throws Exception {
        TaskRequest r = validRequest();
        r.setTitle("ab"); // too short
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(r)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Error"));
    }

    @Test
    void getTask_notFound_returns404() throws Exception {
        mockMvc.perform(get("/tasks/" + UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Task Not Found"));
    }

    @Test
    void fullCrudFlow() throws Exception {
        // CREATE
        MvcResult created = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest())))
                .andExpect(status().isCreated())
                .andReturn();

        TaskResponse createdTask = objectMapper.readValue(
                created.getResponse().getContentAsString(), TaskResponse.class);
        UUID id = createdTask.getId();

        // GET
        mockMvc.perform(get("/tasks/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));

        // UPDATE
        TaskRequest update = new TaskRequest();
        update.setTitle("Updated Title");
        update.setStatus(TaskStatus.IN_PROGRESS);
        update.setPriority(TaskPriority.LOW);

        mockMvc.perform(put("/tasks/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"))
                .andExpect(jsonPath("$.title").value("Updated Title"));

        // DELETE
        mockMvc.perform(delete("/tasks/" + id))
                .andExpect(status().isNoContent());

        // CONFIRM DELETED
        mockMvc.perform(get("/tasks/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void listTasks_filterByStatus() throws Exception {
        mockMvc.perform(get("/tasks").param("status", "OPEN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void listTasks_filterByPriority() throws Exception {
        mockMvc.perform(get("/tasks").param("priority", "HIGH"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").isNumber());
    }

    @Test
    void listTasks_invalidStatus_returns400() throws Exception {
        mockMvc.perform(get("/tasks").param("status", "INVALID_STATUS"))
                .andExpect(status().isBadRequest());
    }
}
