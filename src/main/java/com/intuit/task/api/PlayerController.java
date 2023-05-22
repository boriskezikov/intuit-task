package com.intuit.task.api;

import com.intuit.task.logic.PlayerService;
import com.intuit.task.model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping
    public Flux<Player> getAllPlayers(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        return playerService.getAllPlayers(page, size);
    }
    @GetMapping("/{playerID}")
    public Mono<Player> getPlayer(@PathVariable String playerID) {
        return playerService.getPlayer(playerID);
    }
}