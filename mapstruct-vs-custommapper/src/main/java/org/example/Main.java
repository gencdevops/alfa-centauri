package org.example;

import org.example.client.UserDto;
import org.example.service.UserService;

import java.util.List;

/**
 * Edit configuration sekmesinden JIT devre dışı bırakılmıştır.
 */
public class Main {
    public static int CYCLE = 200;

    public static void main(String[] args) {
        System.out.println("-----------------------------------------------------------------------------");

        UserService userService = new UserService();
        long startTime = System.currentTimeMillis();

        tryStructMapper(userService);

        long endTime = System.currentTimeMillis();

        System.out.format("For struct elapsed time:%d\n\n", endTime - startTime);

        //-----------------------------------------------------------------------------

        startTime = System.currentTimeMillis();

        tryPureHandMapper(userService);

        endTime = System.currentTimeMillis();

        System.out.format("For pure hand elapsed time:%d\n\n", endTime - startTime);
        System.out.println("-----------------------------------------------------------------------------");
    }

    public static void tryStructMapper(UserService userService) {
        System.out.println("Mapping with mapstruct");

        for (int i = 0; i < CYCLE; i++) {
            List<UserDto> allUsers = userService.getAllUsers();
        }
    }

    public static void tryPureHandMapper(UserService userService) {
        System.out.println("Mapping with hand");

        for (int i = 0; i < CYCLE; i++) {
            List<UserDto> allUsers = userService.getAllUsersWithPureHand();
        }
    }
}