package com.ns.solve.domain.dto;

import java.time.LocalDateTime;

public record UserRankDto(String nickname, Long solvedCount, LocalDateTime registered, LocalDateTime lastActived) {
}
