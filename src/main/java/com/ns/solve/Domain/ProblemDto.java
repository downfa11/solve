package com.ns.solve.Domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProblemDto {
    private Long membershipId;
    private String title;
    private String detail;
    private LocalDateTime deadline;

    private String input;
    private String expectedOutput;
}
