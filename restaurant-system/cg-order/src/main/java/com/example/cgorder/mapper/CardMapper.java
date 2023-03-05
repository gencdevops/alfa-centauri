package com.example.cgorder.mapper;

import com.example.cgcommon.model.CardInfoDto;
import com.example.cgorder.model.Card;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardMapper {
    Card convertCardFromCardInfoDto(CardInfoDto cardInfoDto);

    CardInfoDto convertCardInfoDtoFromCard(Card card);
}
