package com.jb.js.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.jb.domain.ExperienceLevel;
import com.jb.domain.JobStatus;
import com.jb.domain.JobType;
import com.jb.domain.WorkMode;
import com.jb.js.model.embeddable.JobLocation;
import com.jb.js.model.embeddable.SalaryRange;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "jobs")
public class Job {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private String requirements;

  private String responsibilities;

  private String benefits;

  @Column(nullable = false)
  private Long companyId;

  // private JobCategory category;

  // private Set<JobSkill> skills;

  // private Set<JobTag> tags;

  @Embedded
  private JobLocation location;

  @Embedded
  private SalaryRange salaryRange;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private JobType jobType;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private WorkMode workMode;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ExperienceLevel experienceLevel;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private JobStatus status = JobStatus.DRAFT;

  private Integer openings = 1;

  private LocalDate applicationDeadLine;

  private LocalDate expiresAt;

  private Boolean active = true;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(nullable = false)
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  private LocalDateTime publishedAt;

  private LocalDateTime closedAt;

}
