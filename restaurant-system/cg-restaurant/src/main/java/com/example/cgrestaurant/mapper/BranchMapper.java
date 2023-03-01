package com.example.cgrestaurant.mapper;

import com.example.cgrestaurant.dto.request.CreateBranchRequest;
import com.example.cgrestaurant.dto.response.BranchDto;
import com.example.cgrestaurant.model.Branch;
import org.mapstruct.Mapper;

@Mapper(implementationName = "BranchMapperImpl", componentModel = "spring", imports = Branch.class)
public interface BranchMapper {

    Branch toBranchFromCreateBranchRequest(CreateBranchRequest request);

    BranchDto toBranchDto(Branch branch);
}
