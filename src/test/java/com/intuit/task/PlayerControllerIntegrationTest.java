package com.intuit.task;

import com.intuit.task.api.PlayerController;
import com.intuit.task.dto.PlayerDto;
import com.intuit.task.logic.PlayerService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

@SpringBootTest
@ContextConfiguration(classes = {PlayerService.class})
public class PlayerControllerIntegrationTest {

    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new PlayerController(playerService)).build();
    }

    @Test
    public void testGetPlayer() throws Exception {
        String playerID = "1";
        PlayerDto playerDto = new PlayerDto(playerID, 1990, 1, 1, "Country1", "State1", "City1",
                null, null, null, null, null, null, "First1", "Last1", "Given1", 80, 180,
                "Bats1", "Throws1", LocalDate.of(2010, 1, 1), LocalDate.of(2020, 12, 31),
                "Retro1", "Bbref1");

        Mockito.when(playerService.getPlayer(playerID)).thenReturn(playerDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/players/{player_id}", playerID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.playerID").value(playerID));

    }

    @Test
    public void testGetPlayers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/players")
                        .queryParam("page", "0")
                        .queryParam("size", "10"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
}
