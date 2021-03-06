package sprmvc.web;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import sprmvc.user.User;
import sprmvc.user.UserRepo;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

public class UserControllerTest {

    @Test
    public void registerViewTest() throws Exception {
        UserController controller = new UserController();
        String expectedView = "registerForm";

        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(get("/user/register")).andExpect(view().name(expectedView));
    }

    @Test
    public void registerFormTest() throws Exception {
        UserRepo mockRepo = mock(UserRepo.class);
        User unsaved = new User("dpotera","dominik","Dominik","Kowalski","mail@x.o");

        UserController controller = new UserController(mockRepo);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(post("/user/register")
                .param("firstName","Dominik")
                .param("lastName","Kowalski")
                .param("userName","dpotera")
                .param("password","dominik")
                .param("email","mail@x.o"))
            .andExpect(redirectedUrl("/user/dpotera"));

        verify(mockRepo, atLeastOnce()).save(unsaved);
    }

}