package ru.fds.tavrzcms3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CostHistoryDto{

    private Long costHistoryId;

    @NotNull(message = "Обязательно для заполнения")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateConclusion;

    @NotNull(message = "Обязательно для заполнения")
    @PositiveOrZero(message = "Значение должно быть больше или ровно нулю")
    private BigDecimal zsDz;

    @NotNull(message = "Обязательно для заполнения")
    @PositiveOrZero(message = "Значение должно быть больше или ровно нулю")
    private BigDecimal zsZz;

    @NotNull(message = "Обязательно для заполнения")
    @PositiveOrZero(message = "Значение должно быть больше или ровно нулю")
    private BigDecimal rsDz;

    @NotNull(message = "Обязательно для заполнения")
    @PositiveOrZero(message = "Значение должно быть больше или ровно нулю")
    private BigDecimal rsZz;

    @NotNull(message = "Обязательно для заполнения")
    @PositiveOrZero(message = "Значение должно быть больше или ровно нулю")
    private BigDecimal ss;

    private String appraiser;

    private String appraisalReportNum;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate appraisalReportDate;

    private Long pledgeSubjectId;
}
