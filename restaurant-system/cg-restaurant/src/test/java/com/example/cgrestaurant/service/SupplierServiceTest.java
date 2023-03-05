package com.example.cgrestaurant.service;

import com.example.cgrestaurant.dto.request.CreateSupplierRequestDto;
import com.example.cgrestaurant.dto.request.UpdateSupplierRequestDto;
import com.example.cgrestaurant.dto.response.SupplierResponseDto;
import com.example.cgrestaurant.exception.SupplierNotFoundException;
import com.example.cgrestaurant.mapper.SupplierMapper;
import com.example.cgrestaurant.model.Supplier;
import com.example.cgrestaurant.repository.SupplierRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SupplierServiceTest {

    @InjectMocks
    private SupplierService service;

    @Mock private SupplierRepository supplierRepository;

    @Mock private SupplierMapper supplierMapper;

    @Test
    void shouldReturnSupplierResponseWhenExistById() {
        UUID id = UUID.randomUUID();
        Supplier supplier = Supplier.builder()
                .supplierId(id)
                .supplierName("Test Supplier")
                .build();

        when(supplierRepository.findById(id)).thenReturn(java.util.Optional.of(supplier));
        when(supplierRepository.findById(id)).thenReturn(Optional.of(supplier));
        when(supplierMapper.convertSupplierResponseDtoFromSupplier(supplier)).thenReturn(new SupplierResponseDto(supplier.getSupplierName()));

        // Act
        SupplierResponseDto responseDto = service.getSupplierByIdConvertedSupplierResponseDto(id);

        // Assert
        assertEquals(supplier.getSupplierName(), responseDto.supplierName());
        verify(supplierRepository).findById(id);
    }

    @Test
    void shouldThrowExceptionWhenSupplierIdDoesNotExist() {

        when(supplierRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.empty());
        when(supplierRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(SupplierNotFoundException.class, () -> service.getSupplierByIdConvertedSupplierResponseDto(UUID.randomUUID()));
    }

    @Test
    void shouldUpdateSupplier() {
        UUID id = UUID.randomUUID();
        String updatedSupplierName = RandomStringUtils.randomAlphabetic(10);
        UpdateSupplierRequestDto updateSupplierRequestDto = new UpdateSupplierRequestDto(updatedSupplierName);
        Supplier supplier = Supplier.builder()
                .supplierId(id)
                .supplierName(RandomStringUtils.randomAlphabetic(10))
                .build();

        when(supplierRepository.findById(id)).thenReturn(Optional.of(supplier));
        when(supplierRepository.save(supplier)).thenReturn(supplier);
        when(supplierMapper.convertSupplierResponseDtoFromSupplier(supplier)).thenReturn(new SupplierResponseDto(updatedSupplierName));

        SupplierResponseDto responseDto = service.updateSupplier(id, updateSupplierRequestDto);

        assertEquals(id, supplier.getKd());
        assertEquals(updatedSupplierName, supplier.getSupplierName());
        assertEquals(updatedSupplierName, responseDto.supplierName());

        verify(supplierRepository).findById(id);
        verify(supplierRepository).save(supplier);
    }

    @Test
    void shouldReturnGetAllSupplier() {
        Supplier supplier = Supplier.builder()
                .supplierId(UUID.randomUUID())
                .supplierName(RandomStringUtils.randomAlphabetic(10))
                .build();


        ArrayList<Supplier> suppliers = new ArrayList<>();
        suppliers.add(supplier);

        when(supplierRepository.findAll()).thenReturn(suppliers);
        when(supplierMapper.convertSupplierResponseDtoFromSupplier(supplier))
                .thenReturn(new SupplierResponseDto(supplier.getSupplierName()));


        List<SupplierResponseDto> responseDtos = service.getAllSupplier();

        assertEquals(1, responseDtos.size());
        assertEquals(supplier.getSupplierName(), responseDtos.get(0).supplierName());

        verify(supplierRepository).findAll();
    }

    @Test
    void shouldReturnSupplierResponseDtoWhenCreateSupplier() {
        CreateSupplierRequestDto requestDto = CreateSupplierRequestDto.builder()
                .supplierName("Test Supplier")
                .build();
        Supplier created = Supplier.builder()
                .supplierName(requestDto.supplierName())
                .build();

        SupplierResponseDto responseDtos = new SupplierResponseDto(created.getSupplierName());


        when(supplierMapper.convertSupplierFromCreateSupplierRequestDto(any(CreateSupplierRequestDto.class))).thenReturn(created);
        when(supplierRepository.save(any(Supplier.class))).thenReturn(created);
        when(supplierMapper.convertSupplierResponseDtoFromSupplier(any(Supplier.class))).thenReturn(responseDtos);
        ArgumentCaptor<Supplier> supplierArgumentCaptor = ArgumentCaptor.forClass(Supplier.class);


        SupplierResponseDto responseDto = service.createSupplier(requestDto);

        assertEquals(created.getSupplierName(), responseDto.supplierName());

        verify(supplierRepository).save(supplierArgumentCaptor.capture());
        Supplier capturedSupplier = supplierArgumentCaptor.getValue();
        assertEquals(requestDto.supplierName(), capturedSupplier.getSupplierName());
    }

    @Test
    void shouldDeleteSupplier() {
        UUID id = UUID.randomUUID();
        service.deleteSupplier(id);
        verify(supplierRepository).deleteById(id);
    }

}
