package com.example.kafkalogbackxmlv2.mapper;

import com.example.kafkalogbackxmlv2.client.dto.request.CreateCustomerRequest;
import com.example.kafkalogbackxmlv2.client.dto.request.UpdateCustomerRequest;
import com.example.kafkalogbackxmlv2.client.dto.response.CustomerDTO;
import com.example.kafkalogbackxmlv2.model.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(implementationName = "CustomerMapperImpl", componentModel = "spring")
public interface CustomerMapper {


    @Mapping(target ="id", ignore = true)
    Customer toCustomerFromCreateCustomerRequest(CreateCustomerRequest customerRequest);

    @Mapping(target ="id", ignore = true)
    @Mapping(target ="email", ignore = true)
    Customer toCustomerFromUpdateCustomerRequest(@MappingTarget Customer customer, UpdateCustomerRequest customerRequest);

    CustomerDTO toCustomerDTO(Customer customer);
}
