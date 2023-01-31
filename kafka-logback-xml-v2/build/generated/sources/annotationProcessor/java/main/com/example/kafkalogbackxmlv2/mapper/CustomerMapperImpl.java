package com.example.kafkalogbackxmlv2.mapper;

import com.example.kafkalogbackxmlv2.client.dto.request.CreateCustomerRequest;
import com.example.kafkalogbackxmlv2.client.dto.request.UpdateCustomerRequest;
import com.example.kafkalogbackxmlv2.client.dto.response.CustomerDTO;
import com.example.kafkalogbackxmlv2.model.entity.Customer;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-31T09:28:18+0300",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.jar, environment: Java 17.0.4.1 (Oracle Corporation)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public Customer toCustomerFromCreateCustomerRequest(CreateCustomerRequest customerRequest) {
        if ( customerRequest == null ) {
            return null;
        }

        Customer.CustomerBuilder customer = Customer.builder();

        customer.firstname( customerRequest.firstname() );
        customer.lastname( customerRequest.lastname() );
        customer.email( customerRequest.email() );

        return customer.build();
    }

    @Override
    public Customer toCustomerFromUpdateCustomerRequest(Customer customer, UpdateCustomerRequest customerRequest) {
        if ( customerRequest == null ) {
            return customer;
        }

        customer.setFirstname( customerRequest.firstname() );
        customer.setLastname( customerRequest.lastname() );

        return customer;
    }

    @Override
    public CustomerDTO toCustomerDTO(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        Long id = null;
        String firstname = null;
        String lastname = null;
        String email = null;

        id = customer.getId();
        firstname = customer.getFirstname();
        lastname = customer.getLastname();
        email = customer.getEmail();

        LocalDate birthdate = null;

        CustomerDTO customerDTO = new CustomerDTO( id, firstname, lastname, birthdate, email );

        return customerDTO;
    }
}
