package com.jb.js.model.embeddable;

import java.math.BigDecimal;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SalaryRange {

  private BigDecimal minSalary;

  private BigDecimal maxSalary;

}
