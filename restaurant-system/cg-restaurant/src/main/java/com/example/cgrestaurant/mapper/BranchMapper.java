package com.example.cgrestaurant.mapper;

import com.example.cgrestaurant.dto.request.CreateBranchRequestDto;
import com.example.cgrestaurant.dto.response.BranchResponseDto;
import com.example.cgrestaurant.model.Branch;
import org.mapstruct.Mapper;

@Mapper(implementationName = "BranchMapperImpl", componentModel = "spring", imports = Branch.class)
public interface BranchMapper {

    Branch convertBranchFromCreateBranchRequestDto(CreateBranchRequestDto request);

    BranchResponseDto convertBranchResponseDtoFromBrach(Branch branch);
}
