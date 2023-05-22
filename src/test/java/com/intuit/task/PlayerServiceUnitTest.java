package com.intuit.task;

import com.intuit.task.dao.PlayerRepository;
import com.intuit.task.domain.Player;
import com.intuit.task.dto.PlayerDto;
import com.intuit.task.logic.PlayerService;
import com.intuit.task.mapper.PlayerMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ContextConfiguration(classes = {PlayerService.class})
@ExtendWith(SpringExtension.class)
public class PlayerServiceUnitTest {

    @MockBean
    private PlayerRepository playerRepository;

    @MockBean
    private PlayerMapper playerMapper;

    @Autowired
    private PlayerService playerService;

    @Test
    public void testGetAllPlayers() {
        // Prepare test data
        List<Player> players = Arrays.asList(
                new Player("1", 1990, 1, 1, "Country1", "State1", "City1", null, null, null,
                        null, null, null, "First1", "Last1", "Given1", 80, 180, "Bats1", "Throws1",
                        LocalDate.of(2010, 1, 1), LocalDate.of(2020, 12, 31), "Retro1", "Bbref1"),
                new Player("2", 1995, 2, 2, "Country2", "State2", "City2", null, null, null,
                        null, null, null, "First2", "Last2", "Given2", 85, 190, "Bats2", "Throws2",
                        LocalDate.of(2012, 1, 1), LocalDate.of(2022, 12, 31), "Retro2", "Bbref2")
        );

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Player> playerPage = new PageImpl<>(players, pageRequest, players.size());

        // Mock the repository method
        Mockito.when(playerRepository.findAll(Mockito.any(Pageable.class))).thenReturn(playerPage);

        // Mock the mapper
        List<PlayerDto> playerDtos = players.stream()
                .map(player -> new PlayerDto(player.getPlayerID(), player.getBirthYear(),
                        player.getBirthMonth(), player.getBirthDay(), player.getBirthCountry(),
                        player.getBirthState(), player.getBirthCity(), player.getDeathYear(),
                        player.getDeathMonth(), player.getDeathDay(), player.getDeathCountry(),
                        player.getDeathState(), player.getDeathCity(), player.getNameFirst(),
                        player.getNameLast(), player.getNameGiven(), player.getWeight(),
                        player.getHeight(), player.getBats(), player.getThrows_(), player.getDebut(),
                        player.getFinalGame(), player.getRetroID(), player.getBbrefID()))
                .collect(Collectors.toList());
        Mockito.when(playerMapper.toDto(Mockito.any(Player.class))).thenReturn(playerDtos.get(0), playerDtos.get(1));

        // Perform the service call
        Page<PlayerDto> result = playerService.getAllPlayers(0, 10);

        // Verify the results
        assertEquals(playerDtos, result.getContent());
        assertEquals(players.size(), result.getTotalElements());
        assertEquals(pageRequest, result.getPageable());
    }

    @Test
    public void testGetPlayer() {
        // Prepare test data
        String playerID = "1";
        Player player = new Player(playerID, 1990, 1, 1, "Country1", "State1", "City1", null, null, null,
                null, null, null, "First1", "Last1", "Given1", 80, 180, "Bats1", "Throws1",
                LocalDate.of(2010, 1, 1), LocalDate.of(2020, 12, 31), "Retro1", "Bbref1");
        PlayerDto playerDto = new PlayerDto(playerID, 1990, 1, 1, "Country1", "State1", "City1", null, null, null,
                null, null, null, "First1", "Last1", "Given1", 80, 180, "Bats1", "Throws1",
                LocalDate.of(2010, 1, 1), LocalDate.of(2020, 12, 31), "Retro1", "Bbref1");

        // Mock the repository method
        Mockito.when(playerRepository.findById(playerID)).thenReturn(Optional.of(player));

        // Mock the mapper
        Mockito.when(playerMapper.toDto(player)).thenReturn(playerDto);

        // Perform the service call
        PlayerDto result = playerService.getPlayer(playerID);

        // Verify the result
        assertEquals(playerDto, result);
    }
}
