package com.jb.js.payload;

import java.math.BigDecimal;
import java.util.List;
import com.jb.domain.ExperienceLevel;
import com.jb.domain.JobStatus;
import com.jb.domain.JobType;
import com.jb.domain.WorkMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JobSearchRequest {

  private String keyword;

  private Long categoryId;

  private List<Long> skillIds;

  private List<Long> tagIds;

  private Long companyId;

  private String location;

  private BigDecimal minSalary;

  private BigDecimal maxSalary;

  private JobType jobType;

  private WorkMode workMode;

  private ExperienceLevel experienceLevel;

  private JobStatus status;

  private Integer minOpenings;

  private Integer maxOpenings;

}
