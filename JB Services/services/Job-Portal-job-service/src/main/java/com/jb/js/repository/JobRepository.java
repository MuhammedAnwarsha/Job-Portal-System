package com.jb.js.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jb.js.model.Job;

public interface JobRepository extends JpaRepository<Job, Long> {

}
