package com.jb.cs.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.jb.cs.mapper.CompanyMapper;
import com.jb.cs.model.Company;
import com.jb.cs.model.SocialLink;
import com.jb.cs.repository.CompanyRepository;
import com.jb.cs.service.CompanyService;
import com.jb.domain.CompanyStatus;
import com.jb.domain.CompanyType;
import com.jb.domain.IndustryType;
import com.jb.dto.request.CompanyRequest;
import com.jb.dto.response.CompanyResponse;
import com.jb.dto.response.SocialLinkResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {

  private final CompanyRepository companyRepository;

  @Override
  public CompanyResponse createCompany(Long ownerId, CompanyRequest req) throws Exception {
    log.info("Starting company creation for ownerId: {}", ownerId);

    if (companyRepository.existsByOwnerId(ownerId)) {
      log.warn("Company creation rejected: ownerId {} already has a company", ownerId);
      throw new Exception(
          "You already have a company registered. Only one company per account is allowed.");
    }
    if (companyRepository.existsByName(req.getName())) {
      log.warn("Company creation rejected: company name already exists");
      throw new Exception("Company already exists. Please choose a different name.");
    }
    if (req.getRegistrationNumber() != null
        && companyRepository.existsByRegistrationNumber(req.getRegistrationNumber())) {
      log.warn("Company creation rejected: registration number already exists");
      throw new Exception("Company already exists. Please choose a different registration number.");
    }

    String slug = generateUniqueSlug(req.getName());

    Company company = Company.builder().name(req.getName()).slug(slug).tagline(req.getTagline())
        .description(req.getDescription()).logoUrl(req.getLogoUrl())
        .coverImageUrl(req.getCoverImageUrl()).website(req.getWebsite()).email(req.getEmail())
        .phone(req.getPhone()).foundedYear(req.getFoundedYear()).companySize(req.getCompanySize())
        .companyType(req.getCompanyType()).industryType(req.getIndustryType())
        .registrationNumber(req.getRegistrationNumber()).ownerId(ownerId)
        .socialLinks(mapSocailLinks(req.getSocialLinks())).build();

    Company savedCompany = companyRepository.save(company);

    log.info("Company created successfully. companyId: {}, ownerId: {}", savedCompany.getId(),
        ownerId);

    return CompanyMapper.toResponse(savedCompany);
  }

  private List<SocialLink> mapSocailLinks(List<SocialLinkResponse> socialLinks) {
    if (socialLinks == null || socialLinks.isEmpty()) {
      return new ArrayList<SocialLink>();
    }
    log.debug("Mapping {} social links", socialLinks.size());
    return socialLinks.stream()
        .map(e -> SocialLink.builder().platform(e.getPlatform()).url(e.getUrl()).build())
        .collect(Collectors.toList());
  }

  private String generateUniqueSlug(String name) {
    String base =
        name.toLowerCase().replaceAll("[^a-z0-9\\s-]", "").trim().replaceAll("[\\s-]", "-");

    if (!companyRepository.existsBySlug(base)) {
      log.debug("Generated unique company slug");
      return base;
    }
    int counter = 1;
    while (companyRepository.existsBySlug(base + "-" + counter)) {
      counter++;
    }

    String uniqueSlug = base + "-" + counter;
    log.debug("Generated alternative company slug using suffix: {}", counter);
    return uniqueSlug;
  }

  @Override
  public CompanyResponse getCompanyById(Long id) throws Exception {
    log.info("Fetching company by ID: {}", id);

    Company company = companyRepository.findById(id)
        .orElseThrow(() -> {
          log.warn("Company not found with ID: {}", id);
          return new Exception("Company not found with id: " + id);
        });

    log.info("Company fetched successfully with ID: {}", id);
    return CompanyMapper.toResponse(company);
  }

  @Override
  public CompanyResponse getMyCompany(Long ownerId) throws Exception {
    log.info("Fetching company for owner ID: {}", ownerId);

    Company company = companyRepository.findByOwnerId(ownerId)
        .orElseThrow(() -> {
          log.warn("No company found for owner ID: {}", ownerId);
          return new Exception("Company not exist for owner: " + ownerId);
        });

    log.info("Company fetched successfully for owner ID: {}", ownerId);
    return CompanyMapper.toResponse(company);
  }

  @Override
  public List<CompanyResponse> getAllCompanies(CompanyType companyType, IndustryType industryType,
      CompanyStatus companyStatus) {
    log.info("Fetching companies with filters - type: {}, industry: {}, status: {}", companyType,
        industryType, companyStatus);

    List<CompanyResponse> companies =
        companyRepository.findByFilters(companyType, industryType, companyStatus).stream()
            .map(CompanyMapper::toResponse).collect(Collectors.toList());

    log.info("Successfully fetched {} companies", companies.size());
    return companies;
  }

  @Override
  public CompanyResponse updateCompany(Long companyId, Long ownerId, CompanyRequest req)
      throws Exception {
    log.info("Updating company ID: {} for owner ID: {}", companyId, ownerId);

    Company company = getCompanyEntityById(companyId);

    /* Verify ownership before allowing changes. */
    assertOwner(company, ownerId);

    if (!company.getName().equals(req.getName()) && companyRepository.existsByName(req.getName())) {
      log.warn("Company update rejected: company name already exists");
      throw new Exception("Company already exists. Please choose a different name.");
    }
    if (req.getRegistrationNumber() != null
        && !req.getRegistrationNumber().equals(company.getRegistrationNumber())
        && companyRepository.existsByRegistrationNumber(req.getRegistrationNumber())) {
      log.warn("Company update rejected: registration number already exists");
      throw new Exception("Company already exists. Please choose a different registration number.");
    }

    company.setName(req.getName());
    company.setTagline(req.getTagline());
    company.setDescription(req.getDescription());
    company.setLogoUrl(req.getLogoUrl());
    company.setCoverImageUrl(req.getCoverImageUrl());
    company.setWebsite(req.getWebsite());
    company.setEmail(req.getEmail());
    company.setPhone(req.getPhone());
    company.setFoundedYear(req.getFoundedYear());
    company.setCompanySize(req.getCompanySize());
    company.setCompanyType(req.getCompanyType());
    company.setIndustryType(req.getIndustryType());
    company.setRegistrationNumber(req.getRegistrationNumber());
    company.setSocialLinks(mapSocailLinks(req.getSocialLinks()));

    log.info("Company updated successfully. Company ID: {}", companyId);
    return CompanyMapper.toResponse(companyRepository.save(company));
  }

  @Override
  public CompanyResponse verifyCompany(Long companyId) throws Exception {
    log.info("Verifying company ID: {}", companyId);

    Company company = getCompanyEntityById(companyId);
    company.setStatus(CompanyStatus.ACTIVE);
    company.setVerified(true);
    Company verifiedCompany = companyRepository.save(company);

    log.info("Company verified successfully. Company ID: {}", companyId);
    return CompanyMapper.toResponse(verifiedCompany);
  }

  @Override
  public void deleteCompany(Long companyId, Long ownerId) throws Exception {
    log.info("Deleting company ID: {} requested by owner ID: {}", companyId, ownerId);

    Company company = getCompanyEntityById(companyId);
    assertOwner(company, ownerId);
    companyRepository.delete(company);
    log.info("Company deleted successfully. Company ID: {}", companyId);
  }

  private void assertOwner(Company company, Long ownerId) throws Exception {
    if (!company.getOwnerId().equals(ownerId)) {
      log.warn("Company ownership validation failed for company ID: {}", company.getId());
      throw new Exception("You are not the owner of this company");
    }
  }

  @Override
  public CompanyResponse deactivateCompany(Long companyId) throws Exception {
    log.info("Deactivating company ID: {}", companyId);

    Company company = getCompanyEntityById(companyId);
    company.setStatus(CompanyStatus.SUSPENDED);
    company.setVerified(false);
    Company savedCompany = companyRepository.save(company);

    log.info("Company deactivated successfully. Company ID: {}", companyId);
    return CompanyMapper.toResponse(savedCompany);
  }

  @Override
  public Company getCompanyEntityById(Long Id) throws Exception {
    log.debug("Loading company entity by ID: {}", Id);

    return companyRepository.findById(Id)
        .orElseThrow(() -> {
          log.warn("Company entity not found with ID: {}", Id);
          return new Exception("Company not found with id: " + Id);
        });
  }

}
