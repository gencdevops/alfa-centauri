package com.example.cgrestaurant.mapper;


import com.example.cgcommon.request.PlaceOrderRequestDTO;
import com.example.cgrestaurant.dto.response.BranchResponseDto;
import com.example.cgrestaurant.model.Branch;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Branch convertBranchFromCreateBranchRequestDto(PlaceOrderRequestDTO placeOrderRequestDTO);

    BranchResponseDto convertBranchResponseDtoFromBranch(Branch branch);
}
