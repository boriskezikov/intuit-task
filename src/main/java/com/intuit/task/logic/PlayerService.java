package com.intuit.task.logic;

import com.intuit.task.dao.PlayerRepository;
import com.intuit.task.dao.PlayersCache;
import com.intuit.task.model.Player;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;


    public Page<Player> getAllPlayers(int page, int size) {
        PageRequest of = PageRequest.of(page, size);
        return playerRepository.findAll(of);
    }


    public Player getPlayer(String playerID) {
        return playerRepository.findById(playerID)
                .orElseThrow(EntityNotFoundException::new);
    }
}
