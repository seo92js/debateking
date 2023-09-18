package com.seojs.debateking.web;

import com.seojs.debateking.config.security.PrincipalDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean(JpaMetamodelMappingContext.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = LoginController.class)
class LoginControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private PrincipalDetailsService principalDetailsService;

    @Test
    public void login_controller_테스트() throws Exception{
        mvc.perform(get("/login"))
                .andExpect(status().isOk());
    }
}