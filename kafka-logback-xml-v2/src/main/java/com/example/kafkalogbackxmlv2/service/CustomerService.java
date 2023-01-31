package com.example.kafkalogbackxmlv2.service;

import com.example.kafkalogbackxmlv2.client.dto.request.CreateCustomerRequest;
import com.example.kafkalogbackxmlv2.client.dto.request.UpdateCustomerRequest;
import com.example.kafkalogbackxmlv2.client.dto.response.CustomerDTO;
import com.example.kafkalogbackxmlv2.exception.GeneralException;
import com.example.kafkalogbackxmlv2.mapper.CustomerMapper;
import com.example.kafkalogbackxmlv2.model.entity.Customer;
import com.example.kafkalogbackxmlv2.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public String createCustomer(CreateCustomerRequest customerRequest) {
        Customer customer = customerMapper.toCustomerFromCreateCustomerRequest(customerRequest);
        this.customerRepository.save(customer);
        return "Başarıyla Kayıt olundu.";
    }

    public CustomerDTO getCustomer(Long id) {
        return  this.customerRepository.findById(id)
                .map(customerMapper::toCustomerDTO)
                .orElseThrow(() -> new GeneralException("Müşteri bulunamadı."));
    }
    public String updateCustomer(Long id, UpdateCustomerRequest customerRequest) {
        Customer updatedCustomer = customerRepository.findById(id)
                .map(customer -> customerMapper.toCustomerFromUpdateCustomerRequest(customer, customerRequest))
                .orElseThrow(() -> new GeneralException("Müşteri güncellenemedi."));
        customerRepository.save(updatedCustomer);
        return "Kullanıcı başarıyla güncellendi.";
    }
    public String deleteCustomer(Long id) {
        boolean isCustomerExist = customerRepository.existsById(id);
        if (isCustomerExist) {
            customerRepository.deleteById(id);
            return "Kullanıcı başarıyla silindi.";
        }
        throw new GeneralException("Silinecek kullanıcı bulunamadı.");
    }
}
