package com.example.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.example.backend.models.Museum;
import com.example.backend.models.Users;
import com.example.backend.repositories.MuseumRepository;
import com.example.backend.repositories.UserRepository;

import java.util.*;

@RestController
@RequestMapping("api/v1")
public class UsersController {
    // По аналогии здесь используется два репозитория
    @Autowired
    UserRepository usersRepository;

    @Autowired
    MuseumRepository museumRepository;

    @GetMapping("/users")
    public List getAllUsers() {
        return usersRepository.findAll();
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUsers(@RequestBody Users users) throws Exception {
        try {
            Users nc = usersRepository.save(users);
            return new ResponseEntity<Object>(nc, HttpStatus.OK);
        } catch (Exception exception) {
            // Указываем тип ошибки
            String error;
            if (exception.getMessage().contains("ConstraintViolationException")) {
                error = "artistAlreadyExists";
            } else {
                error = exception.getMessage();
            }

            Map<String, String> map = new HashMap<>();
            map.put("error", error + "\n");

            return ResponseEntity.ok(map);
        }
    }

    @PostMapping("/users/{id}/addmuseums")
    public ResponseEntity<Object> addMuseums(@PathVariable(value = "id") Long userID,
                                             @Validated @RequestBody Set<Museum> museums) {
        // Извлекаем пользователя по конкретному ID-шнику
        Optional<Users> uu = usersRepository.findById(userID);
        int cnt = 0;

        if (uu.isPresent()) {
            Users u = uu.get();

            // Если музеев несколько (а такое может быть вполне, то тогда добавляем их поочерёдно)
            for(Museum m: museums) {
                // Если есть музей, то мы, конечно, добавим его. Защита от дурака
                Optional<Museum> mm = museumRepository.findById(m.id);
                if (mm.isPresent()) {
                    u.addMuseum(mm.get());
                    ++cnt;
                }
            }

            // Сохраняем
            usersRepository.save(u);
        }

        // Формируем
        Map<String, String> response = new HashMap<>();
        response.put("added", String.valueOf(cnt));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/users/{id}/removemuseums")
    public ResponseEntity<Object> removeMuseums(@PathVariable(value = "id") Long userId,
                                                @Validated @RequestBody Set<Museum> museums) {
        Optional<Users> uu = usersRepository.findById(userId);
        int cnt = 0;

        if (uu.isPresent()) {
            Users u = uu.get();
            for (Museum m: museums) {
                u.removeMuseum(m);
                ++cnt;
            }

            usersRepository.save(u);
        }

        // Формируем ответ
        Map<String, String> response = new HashMap<>();
        response.put("count", String.valueOf(cnt));

        return ResponseEntity.ok(response);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Users> updateUsers(@PathVariable(value = "id") Long userId,
                                               @RequestBody Users userDetails) {
        Users user = null;
        Optional<Users> uu = usersRepository.findById(userId);
        if (uu.isPresent()) {
            // Заполняем пользовательские данные
            user = uu.get();
            user.login = userDetails.login;
            user.email = userDetails.email;

            usersRepository.save(user);
            return ResponseEntity.ok(user);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUsers(@PathVariable(value = "id") Long userId) {
        Optional<Users> users = usersRepository.findById(userId);
        Map<String, Boolean> resp = new HashMap<>();

        // Возвратит true, если объект существует (не пустой)
        if (users.isPresent()) {
            usersRepository.delete(users.get());
            resp.put("deleted", Boolean.TRUE);
        } else {
            resp.put("deleted", Boolean.FALSE);
        }

        return ResponseEntity.ok(resp);
    }
}
