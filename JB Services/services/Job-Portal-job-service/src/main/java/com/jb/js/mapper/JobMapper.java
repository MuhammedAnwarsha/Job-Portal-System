package com.jb.js.mapper;

import com.jb.dto.response.CompanyResponse;
import com.jb.dto.response.JobResponse;
import com.jb.js.model.Job;
import com.jb.js.model.embeddable.JobLocation;
import com.jb.js.model.embeddable.SalaryRange;

public class JobMapper {

  public static JobResponse toResponse(Job job, CompanyResponse companyResponse) {
    
    JobLocation loc = job.getLocation();
    SalaryRange sal = job.getSalaryRange();
    
    return JobResponse.builder()
        .id(job.getId())
        .title(job.getTitle())
        .description(job.getDescription())
        .requirements(job.getRequirements())
        .responsibilities(job.getResponsibilities())
        .benefits(job.getBenefits())
        .company(companyResponse)
//      .category(toCategoryResponse(job.category))
//      .skills(skills)
//      .tags(tags)
        .address(loc != null ? loc.getAddress() : null).city(loc != null ? loc.getCity() : null)
        .state(loc != null ? loc.getState() : null).country(loc != null ? loc.getCountry() : null)
        .zipCode(loc != null ? loc.getZipCode() : null)
        .minSalary(sal != null ? sal.getMinSalary() : null)
        .maxSalary(sal != null ? sal.getMaxSalary() : null).jobType(job.getJobType())
        .workMode(job.getWorkMode()).experienceLevel(job.getExperienceLevel())
        .jobStatus(job.getStatus()).openings(job.getOpenings())
        .applicationDeadline(job.getApplicationDeadLine()).expiresAt(job.getExpiresAt())
        .active(job.getActive()).createdAt(job.getCreatedAt()).updatedAt(job.getUpdatedAt())
        .publishedAt(job.getPublishedAt()).closedAt(job.getClosedAt())
        .build();
  }
}
