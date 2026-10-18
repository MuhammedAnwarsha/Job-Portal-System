package com.jb.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import com.jb.domain.CompanySize;
import com.jb.domain.CompanyStatus;
import com.jb.domain.CompanyType;
import com.jb.domain.IndustryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyResponse {

  private Long id;

  private String name;

  private String slug;

  private String tagline;

  private String description;

  private String logoUrl;

  private String coverImageUrl;

  private String website;

  private String email;

  private String phone;

  private Integer foundedYear;

  private CompanySize companySize;

  private CompanyType companyType;

  private IndustryType industryType;

  private CompanyStatus status;

  private Boolean isVerified;

  private Boolean active;

  private Long ownerId;

  private List<SocialLinkResponse> socialLinks;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private LocalDateTime verifiedAt;
}
