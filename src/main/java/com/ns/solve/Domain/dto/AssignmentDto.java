package com.ns.solve.Domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AssignmentDto {
    private Long membershipId;
    private Long problemId;
    private String detail;
    private String comment; // 회고록, 느낀 점
    private String gitDirectory;
}
