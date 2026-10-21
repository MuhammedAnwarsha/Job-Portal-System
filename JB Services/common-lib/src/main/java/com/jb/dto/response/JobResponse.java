package com.jb.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class JobResponse {

  private Long id;

  private String title;

  private String description;

  private String requirements;

  private String responsibilities;

  private String benefits;

  private CompanyResponse company;

  private Long employerId;

  // private JobCategoryResponse category;
  //
  // private Set<JobSkillResponse> skills;
  //
  // private Set<JobTagResponse> tags;

  private String address;

  private String city;

  private String state;

  private String country;

  private String zipCode;

  private BigDecimal minSalary;

  private BigDecimal maxSalary;

  private JobType jobType;

  private WorkMode workMode;

  private ExperienceLevel experienceLevel;

  private JobStatus jobStatus;

  private Integer openings;

  private LocalDate applicationDeadline;

  private LocalDate expiresAt;

  private Boolean active;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private LocalDateTime publishedAt;

  private LocalDateTime closedAt;

}
