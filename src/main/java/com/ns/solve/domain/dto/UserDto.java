package com.ns.solve.domain.dto;

import java.time.LocalDateTime;

public record UserDto(String nickname, Long score, LocalDateTime lastActived) {
}
