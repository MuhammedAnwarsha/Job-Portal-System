package com.jb.js.service;

import java.util.List;
import com.jb.dto.request.JobRequest;
import com.jb.dto.response.JobResponse;
import com.jb.js.payload.JobSearchRequest;

public interface JobService {

  JobResponse createJob(Long employerId, JobRequest req);

  JobResponse getJobById(Long id) throws Exception;

  List<JobResponse> getJobs(JobSearchRequest request);

  List<JobResponse> getJobsByCompany(Long companyId);

  JobResponse updateJob(Long jobId, Long employerId, JobRequest req);

  JobResponse publishJob(Long jobId, Long employerId);

  JobResponse closeJob(Long jobId, Long employerId);

  JobResponse deleteJob(Long jobId, Long employerId);

  List<JobResponse> getAllJobsAdmin();
}
