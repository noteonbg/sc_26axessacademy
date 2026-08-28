package com.example.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("f3 should be accessible by anyone (unauthenticated)")
    public void f3_shouldBeAccessibleByAnyone() throws Exception {
        mockMvc.perform(get("/api/f3"))
                .andExpect(status().isOk())
                .andExpect(content().string("f3 at work"));
    }

    @Test
    @DisplayName("f1 should return 401 Unauthorized when unauthenticated")
    public void f1_shouldReturn401_whenUnauthenticated() throws Exception {
        mockMvc.perform(get("/api/f1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("f2 should return 401 Unauthorized when unauthenticated")
    public void f2_shouldReturn401_whenUnauthenticated() throws Exception {
        mockMvc.perform(get("/api/f2"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("f1 should return 200 OK when called by ADMIN user A")
    public void f1_shouldReturn200_forAdminUser() throws Exception {
        mockMvc.perform(get("/api/f1").with(httpBasic("admin", "admin123")))
                .andExpect(status().isOk())
                .andExpect(content().string("f1 at work"));
    }

    @Test
    @DisplayName("f1 should return 403 Forbidden when called by normal USER")
    public void f1_shouldReturn403_forNormalUser() throws Exception {
        mockMvc.perform(get("/api/f1").with(httpBasic("user", "user123")))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("f2 should return 200 OK when called by normal USER")
    public void f2_shouldReturn200_forNormalUser() throws Exception {
        mockMvc.perform(get("/api/f2").with(httpBasic("user", "user123")))
                .andExpect(status().isOk())
                .andExpect(content().string("f2 at work"));
    }

    @Test
    @DisplayName("f2 should return 403 Forbidden when called by ADMIN user")
    public void f2_shouldReturn403_forAdminUser() throws Exception {
        mockMvc.perform(get("/api/f2").with(httpBasic("admin", "admin123")))
                .andExpect(status().isForbidden());
    }
}
