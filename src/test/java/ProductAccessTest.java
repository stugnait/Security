import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = com.example.crud.CrudApplication.class)
@AutoConfigureMockMvc
class ProductAccessTest {
    @Autowired
    private MockMvc mvc;

    // USER

    @Test
    @DisplayName("1️⃣ USER — GET all (200)")
    @WithMockUser(username = "user", roles = "USER")
    void userGetAll() throws Exception {
        mvc.perform(get("/api/products"))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("3️⃣ USER — POST create (403)")
    @WithMockUser(username = "user", roles = "USER")
    void userPostDenied() throws Exception {
        mvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"U test\",\"price\":1,\"stock\":1,\"description\":\"deny\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("4️⃣ USER — DELETE (403)")
    @WithMockUser(username = "user", roles = "USER")
    void userDeleteDenied() throws Exception {
        mvc.perform(delete("/api/products/1"))
                .andExpect(status().isForbidden());
    }

    // MANAGER

    @Test
    @DisplayName("5️⃣ MANAGER — GET all (200)")
    @WithMockUser(username = "manager", roles = "MANAGER")
    void managerGetAll() throws Exception {
        mvc.perform(get("/api/products"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("6️⃣ MANAGER — POST create (201)")
    @WithMockUser(username = "manager", roles = "MANAGER")
    void managerPostAllowed() throws Exception {
        mvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"M test\",\"price\":9.9,\"stock\":10,\"description\":\"ok\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("7️⃣ MANAGER — PUT update (403)")
    @WithMockUser(username = "manager", roles = "MANAGER")
    void managerPutAllowed() throws Exception {
        mvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"M upd\",\"price\":10.0,\"stock\":20,\"description\":\"edit\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("8️⃣ MANAGER — DELETE (403)")
    @WithMockUser(username = "manager", roles = "MANAGER")
    void managerDeleteDenied() throws Exception {
        mvc.perform(delete("/api/products/1"))
                .andExpect(status().isForbidden());
    }

    // ADMIN

    @Test
    @DisplayName("9️⃣ ADMIN — POST /seed (201)")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void adminSeedAllowed() throws Exception {
        mvc.perform(post("/api/products/seed"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("🔟 ADMIN — DELETE /clear (204)")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void adminClearAllowed() throws Exception {
        mvc.perform(delete("/api/products/clear"))
                .andExpect(status().isForbidden());
    }
}
