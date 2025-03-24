package com.ns.solve.controller;

import com.ns.solve.domain.User;
import com.ns.solve.domain.dto.*;
import com.ns.solve.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Operation(summary = "새로운 사용자 생성", description = "제공된 등록 정보를 바탕으로 새로운 사용자를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자가 성공적으로 생성되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 사용자 데이터가 제공되었습니다.")
    })
    @PostMapping
    public ResponseEntity<MessageEntity> createUser(@RequestBody RegisterUserDto registerUserDto) {
        User createdUser = userService.createUser(registerUserDto);
        return ResponseEntity.ok(new MessageEntity("User created successfully", createdUser));
    }

    @Operation(summary = "ID로 사용자 조회", description = "고유한 ID를 통해 사용자를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자가 성공적으로 조회되었습니다."),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MessageEntity> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(new MessageEntity("User retrieved successfully", user));
    }

    @Operation(summary = "점수별로 정렬된 사용자 목록 조회", description = "점수에 따라 정렬된 사용자 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 목록이 성공적으로 조회되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    @GetMapping("/users/sorted-by-score")
    public ResponseEntity<MessageEntity> getUsersSortedByScore(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<UserRankDto> users = userService.getUsersSortedByScore(page, size);
        return new ResponseEntity<>(new MessageEntity("Users fetched successfully", users), HttpStatus.OK);
    }

    @Operation(summary = "모든 사용자 조회", description = "모든 사용자의 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모든 사용자가 성공적으로 조회되었습니다.")
    })
    @GetMapping
    public ResponseEntity<MessageEntity> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(new MessageEntity("Users retrieved successfully", users));
    }

    @Operation(summary = "사용자 수정", description = "주어진 ID에 해당하는 사용자의 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자가 성공적으로 수정되었습니다."),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MessageEntity> updateUser(@PathVariable Long id, @RequestBody ModifyUserDto modifyUserDto) {
        User updatedUser = userService.updateUser(id, modifyUserDto);
        return ResponseEntity.ok(new MessageEntity("User updated successfully", updatedUser));
    }

    @Operation(summary = "사용자 삭제", description = "주어진 ID에 해당하는 사용자를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자가 성공적으로 삭제되었습니다."),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageEntity> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new MessageEntity("User deleted successfully", null));
    }
}
