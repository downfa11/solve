package com.ns.solve.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AssignmentDto {
    private Long membershipId;
    private Long problemId;
    private String comment; // 회고록, 느낀 점
    private String gitDirectory;
}
