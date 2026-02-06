package ca.sfu.cmpt276.staffrating;

import ca.sfu.cmpt276.staffrating.controller.StaffRatingController;
import ca.sfu.cmpt276.staffrating.design.StaffProfileResolver;
import ca.sfu.cmpt276.staffrating.exception.RatingNotFoundException;
import ca.sfu.cmpt276.staffrating.model.RoleType;
import ca.sfu.cmpt276.staffrating.model.StaffRating;
import ca.sfu.cmpt276.staffrating.service.StaffRatingService;
import ca.sfu.cmpt276.staffrating.validation.StaffRatingValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StaffRatingController.class)
class StaffRatingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StaffRatingService service;

    @MockBean
    private StaffProfileResolver profileResolver;

    @MockBean
    private StaffRatingValidator validator;

    @Test
    void indexReturns200AndModel() throws Exception {
        when(service.listAll()).thenReturn(List.of());

        mockMvc.perform(get("/ratings"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ratings"))
                .andExpect(view().name("ratings/index"));
    }

    @Test
    void createSuccessRedirects() throws Exception {
        StaffRating saved = buildRating();
        saved.setId(10L);

        when(service.isEmailInUse(null, "alex@example.com")).thenReturn(false);
        when(service.saveRating(org.mockito.ArgumentMatchers.any(StaffRating.class))).thenReturn(saved);

        mockMvc.perform(post("/ratings")
                        .param("name", "Alex Example")
                        .param("email", "alex@example.com")
                        .param("roleType", "TA")
                        .param("clarity", "8")
                        .param("niceness", "7")
                        .param("knowledgeableScore", "9")
                        .param("comment", "Good support."))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ratings/10"));
    }

    @Test
    void createFailureReturnsFormWithErrors() throws Exception {
        when(service.isEmailInUse(null, "")).thenReturn(false);

        mockMvc.perform(post("/ratings")
                        .param("name", "")
                        .param("email", "")
                        .param("roleType", "TA")
                        .param("clarity", "0")
                        .param("niceness", "0")
                        .param("knowledgeableScore", "0")
                        .param("comment", ""))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("rating", "name", "email", "clarity", "niceness", "knowledgeableScore"))
                .andExpect(view().name("ratings/form"));
    }

    @Test
    void updateSuccessRedirects() throws Exception {
        StaffRating saved = buildRating();
        saved.setId(5L);

        when(service.saveRating(org.mockito.ArgumentMatchers.any(StaffRating.class))).thenReturn(saved);

        mockMvc.perform(post("/ratings/5")
                        .param("name", "Alex Example")
                        .param("email", "alex@example.com")
                        .param("roleType", "TA")
                        .param("clarity", "8")
                        .param("niceness", "7")
                        .param("knowledgeableScore", "9")
                        .param("comment", "Good support."))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ratings/5"));
    }

    @Test
    void deleteRedirectsToIndex() throws Exception {
        mockMvc.perform(post("/ratings/8/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ratings"));
    }

    @Test
    void showMissingRatingReturnsNotFound() throws Exception {
        when(service.getById(999L)).thenThrow(new RatingNotFoundException("Rating not found"));

        mockMvc.perform(get("/ratings/999"))
                .andExpect(status().isNotFound());
    }

    private StaffRating buildRating() {
        StaffRating rating = new StaffRating();
        rating.setName("Alex Example");
        rating.setEmail("alex@example.com");
        rating.setRoleType(RoleType.TA);
        rating.setClarity(8);
        rating.setNiceness(7);
        rating.setKnowledgeableScore(9);
        rating.setComment("Good support.");
        return rating;
    }
}
