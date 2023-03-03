package com.example.cgorder.mapper;

import com.example.cgorder.client.CardDto;
import com.example.cgorder.model.Card;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardMapper {
    Card toEntity(CardDto cardDto);

    CardDto toDto(Card card);
}
