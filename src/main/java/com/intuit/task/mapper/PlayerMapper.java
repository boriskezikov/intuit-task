package com.intuit.task.mapper;

import com.intuit.task.dto.PlayerDto;
import com.intuit.task.domain.Player;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PlayerMapper {

    PlayerDto toDto(Player player);
}