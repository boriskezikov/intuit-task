package com.intuit.task.api;

import com.intuit.task.dto.PlayerDto;
import com.intuit.task.logic.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping
    public Page<PlayerDto> getAllPlayers(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        return playerService.getAllPlayers(page, size);
    }

    @GetMapping("/{player_id}")
    public PlayerDto getPlayer(@PathVariable("player_id") String playerID) {
        return playerService.getPlayer(playerID);
    }
}