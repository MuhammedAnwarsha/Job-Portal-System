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
    Company company = companyRepository.findById(id)
        .orElseThrow(() -> new Exception("Company not found with id: " + id));
    return CompanyMapper.toResponse(company);
  }

  @Override
  public CompanyResponse getMyCompany(Long ownerId) throws Exception {
    Company company = companyRepository.findByOwnerId(ownerId)
        .orElseThrow(() -> new Exception("Company not exist for owner: " + ownerId));
    return CompanyMapper.toResponse(company);
  }

  @Override
  public List<CompanyResponse> getAllCompanies(CompanyType companyType, IndustryType industryType,
      CompanyStatus companyStatus) {
    return companyRepository.findByFilters(companyType, industryType, companyStatus).stream()
        .map(CompanyMapper::toResponse).collect(Collectors.toList());
  }

  @Override
  public CompanyResponse updateCompany(Long companyId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public CompanyResponse verifyCompany(Long companyId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void deleteCompany(Long companyId) {
    // TODO Auto-generated method stub

  }

  @Override
  public CompanyResponse deactivateCompany(Long companyId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Company getCompanyEntityById(Long Id) {
    // TODO Auto-generated method stub
    return null;
  }


}
