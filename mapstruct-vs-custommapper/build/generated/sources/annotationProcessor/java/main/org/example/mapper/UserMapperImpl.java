package org.example.mapper;

import javax.annotation.processing.Generated;
import org.example.client.AddressDto;
import org.example.client.UserDto;
import org.example.model.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-16T16:17:21+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.jar, environment: Java 17.0.4.1 (Amazon.com Inc.)"
)
public class UserMapperImpl extends UserMapper {

    @Override
    public UserDto userToDto(User user) {
        if ( user == null ) {
            return null;
        }

        String name = null;
        String surname = null;
        String email = null;
        int salary = 0;
        String title = null;
        String grade = null;
        String role = null;
        String department = null;

        name = user.getName();
        surname = user.getSurname();
        email = user.getEmail();
        salary = user.getSalary();
        title = user.getTitle();
        grade = user.getGrade();
        role = user.getRole();
        department = user.getDepartment();

        AddressDto addressDto = addressMapper.addressToDto(user.getAddress());

        UserDto userDto = new UserDto( name, surname, email, salary, title, grade, role, department, addressDto );

        return userDto;
    }
}
