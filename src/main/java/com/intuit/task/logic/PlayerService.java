package com.intuit.task.logic;

import com.intuit.task.dao.PlayersCache;
import com.intuit.task.model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayersCache playerCache;


    public Flux<Player> getAllPlayers(int page, int size) {
        List<Player> allPlayers = playerCache.getAll();
        int start = page * size;
        if (start > allPlayers.size()) {
            return Flux.empty();
        }
        int end = Math.min(start + size, allPlayers.size());
        return Flux.fromIterable(allPlayers.subList(start, end));
    }


    public Mono<Player> getPlayer(String playerID) {
        Player player = playerCache.get(playerID);
        if (player == null) {
            return Mono.empty();
        }
        return Mono.just(player);
    }
}
