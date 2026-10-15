package com.jb.cs.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.jb.cs.model.Company;
import com.jb.domain.CompanyStatus;
import com.jb.domain.CompanyType;
import com.jb.domain.IndustryType;

public interface CompanyRepository extends JpaRepository<Company, Long> {

  Optional<Company> findByOwnerId(Long ownerId);

  boolean existsByOwnerId(Long ownerId);

  boolean existsByName(String name);

  boolean existsBySlug(String slug);

  boolean existsByRegistrationNumber(String registrationNumber);

  @Query("select c from Company c where"
      + "(:companyType Is NULL OR c.companyType=:companyType) AND"
      + "(:industryType Is NULL OR c.industryType=:industryType) AND"
      + "(:status Is NULL OR c.status=:status)")
  List<Company> findByFilters(@Param("companyType") CompanyType companyType,
      @Param("industryType") IndustryType industryType, @Param("status") CompanyStatus status);

}
