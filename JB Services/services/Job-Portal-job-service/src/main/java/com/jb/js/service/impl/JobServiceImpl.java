package com.jb.js.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.jb.dto.request.JobRequest;
import com.jb.dto.response.CompanyResponse;
import com.jb.dto.response.JobResponse;
import com.jb.js.mapper.JobMapper;
import com.jb.js.model.Job;
import com.jb.js.model.embeddable.JobLocation;
import com.jb.js.model.embeddable.SalaryRange;
import com.jb.js.payload.JobSearchRequest;
import com.jb.js.repository.JobRepository;
import com.jb.js.repository.JobSpecification;
import com.jb.js.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobServiceImpl implements JobService {
  
  private final JobRepository jobRepository;

  @Override
  public JobResponse createJob(Long employerId, JobRequest req) {
    // TODO fetch company by employerId
    Long companyId = 1L;
    
    Job job = Job.builder()
        .title(req.getTitle())
        .description(req.getDescription())
        .requirements(req.getRequirements())
        .responsibilities(req.getResponsibilities())
        .benefits(req.getBenefits())
        .companyId(companyId)
//        .category(category)
//        .skills(skills)
//        .tags(tags)
        .location(buildLocation(req))
        .salaryRange(buildSalaryRange(req)).jobType(req.getJobType()).workMode(req.getWorkMode())
        .experienceLevel(req.getExperienceLevel())
        .openings(req.getOpenings() != null ? req.getOpenings() : 1)
        .applicationDeadLine(req.getApplicationDeadline()).expiresAt(req.getExpiresAt())
        .build();

    Job savedJob = jobRepository.save(job);
    return convertToResponse(savedJob);
  }

  private JobResponse convertToResponse(Job savedJob) {
    // TODO fetch company response
    CompanyResponse companyResponse = CompanyResponse.builder().id(savedJob.getCompanyId()).build();
    return JobMapper.toResponse(savedJob, companyResponse);
  }

  private JobLocation buildLocation(JobRequest req) {
    return JobLocation.builder().address(req.getAddress()).city(req.getCity()).state(req.getState())
        .country(req.getCountry()).zipCode(req.getZipCode()).build();
  }

  private SalaryRange buildSalaryRange(JobRequest req) {
    return SalaryRange.builder().minSalary(req.getMinSalary()).maxSalary(req.getMaxSalary())
        .build();
  }

  @Override
  public JobResponse getJobById(Long id) throws Exception {
    Job job = jobRepository.findById(id).orElseThrow(() -> new Exception("job not found"));
    return convertToResponse(job);
  }

  @Override
  public List<JobResponse> getJobs(JobSearchRequest request) {

    List<Job> jobs = jobRepository.findAll(JobSpecification.build(request));
    return jobs.stream().map(this::convertToResponse).collect(Collectors.toList());
  }

  @Override
  public List<JobResponse> getJobsByCompany(Long companyId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public JobResponse updateJob(Long jobId, Long employerId, JobRequest req) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public JobResponse publishJob(Long jobId, Long employerId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public JobResponse closeJob(Long jobId, Long employerId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public JobResponse deleteJob(Long jobId, Long employerId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<JobResponse> getAllJobsAdmin() {
    // TODO Auto-generated method stub
    return null;
  }


}
