package com.jb.cs.service;

import java.util.List;
import com.jb.cs.model.Company;
import com.jb.domain.CompanyStatus;
import com.jb.domain.CompanyType;
import com.jb.domain.IndustryType;
import com.jb.dto.request.CompanyRequest;
import com.jb.dto.response.CompanyResponse;

public interface CompanyService {

  CompanyResponse createCompany(Long ownerId, CompanyRequest req) throws Exception;

  CompanyResponse getCompanyById(Long id) throws Exception;

  CompanyResponse getMyCompany(Long ownerId) throws Exception;

  List<CompanyResponse> getAllCompanies(CompanyType companyType, IndustryType industryType,
      CompanyStatus companyStatus);

  CompanyResponse updateCompany(Long companyId, Long ownerId, CompanyRequest req) throws Exception;

  CompanyResponse verifyCompany(Long companyId) throws Exception;

  void deleteCompany(Long companyId, Long ownerId) throws Exception;

  CompanyResponse deactivateCompany(Long companyId) throws Exception;

  Company getCompanyEntityById(Long Id) throws Exception;
}
