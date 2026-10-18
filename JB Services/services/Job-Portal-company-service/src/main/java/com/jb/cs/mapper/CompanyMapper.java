package com.jb.cs.mapper;

import java.util.Collections;
import java.util.List;
import com.jb.cs.model.Company;
import com.jb.cs.model.SocialLink;
import com.jb.dto.response.CompanyResponse;
import com.jb.dto.response.SocialLinkResponse;

public class CompanyMapper {

  public static SocialLinkResponse toSocailLinkResponse(SocialLink socialLinks) {
    return SocialLinkResponse.builder().platform(socialLinks.getPlatform())
        .url(socialLinks.getUrl()).build();
  }

  public static CompanyResponse toResponse(Company company) {
   
    List<SocialLinkResponse> socialLinks =
        company.getSocialLinks() == null ? Collections.emptyList()
        :company.getSocialLinks().stream().map(CompanyMapper::toSocailLinkResponse)
            .toList();
    
    return CompanyResponse.builder().id(company.getId()).name(company.getName())
        .slug(company.getSlug()).tagline(company.getTagline()).description(company.getDescription())
        .logoUrl(company.getLogoUrl()).coverImageUrl(company.getCoverImageUrl()).website(company.getWebsite())
        .email(company.getEmail()).phone(company.getPhone()).foundedYear(company.getFoundedYear()).companySize(company.getCompanySize())
        .companyType(company.getCompanyType()).industryType(company.getIndustryType())
        .status(company.getStatus()).isVerified(company.isVerified())
        .active(company.getActive()).ownerId(company.getOwnerId()).socialLinks(socialLinks).createdAt(company.getCreatedAt())
        .updatedAt(company.getUpdatedAt())
        .build();
  }
}
