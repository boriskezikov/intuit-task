package com.intuit.task.api;

import com.intuit.task.logic.PlayerService;
import com.intuit.task.model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping
    public Page<Player> getAllPlayers(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        return playerService.getAllPlayers(page, size);
    }

    @GetMapping("/{playerID}")
    public Player getPlayer(@PathVariable String playerID) {
        return playerService.getPlayer(playerID);
    }
}