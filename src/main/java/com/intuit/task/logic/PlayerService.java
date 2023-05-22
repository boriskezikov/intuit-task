package com.intuit.task.logic;

import com.intuit.task.dao.PlayerRepository;
import com.intuit.task.domain.Player;
import com.intuit.task.dto.PlayerDto;
import com.intuit.task.mapper.PlayerMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;


    public Page<PlayerDto> getAllPlayers(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Page and size must be positive");
        }
        PageRequest paged = PageRequest.of(page, size);
        Page<Player> result = playerRepository.findAll(paged);
        List<PlayerDto> playerDtos = result.getContent()
                .stream()
                .map(playerMapper::toDto)
                .toList();
        return new PageImpl<>(playerDtos, result.getPageable(), result.getTotalElements());
    }


    public PlayerDto getPlayer(String playerID) {
        return playerRepository.findById(playerID)
                .map(playerMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Player with id " + playerID + " not found"));
    }
}
