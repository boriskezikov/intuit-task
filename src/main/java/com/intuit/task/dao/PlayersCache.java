package com.intuit.task.dao;

import com.intuit.task.model.Player;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PlayersCache {

    private final Map<String, Player> playerCache = new ConcurrentHashMap<>();

    @PostConstruct
    private void loadPlayers() {
        try {
            Resource resource = new ClassPathResource("player.csv");
            InputStream inputStream = resource.getInputStream();

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            CSVReader reader = new CSVReader(inputStreamReader);
            HeaderColumnNameMappingStrategy<Player> ms = new HeaderColumnNameMappingStrategy<>();
            ms.setType(Player.class);
            CsvToBean<Player> cb = new CsvToBeanBuilder<Player>(reader)
                    .withMappingStrategy(ms)
                    .withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_SEPARATORS)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .build();

            List<Player> players = cb.parse();
            for (Player player : players) {
                try {
                    playerCache.put(player.getPlayerID(), player);
                } catch (Exception e) {
                    System.out.println("Error loading player: " + player.getPlayerID());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error loading players from file", e);
        }
    }

    public Player get(String playerID) {
        return playerCache.get(playerID);
    }

    public Collection<Player> getAll() {
        return  playerCache.values();
    }

}
