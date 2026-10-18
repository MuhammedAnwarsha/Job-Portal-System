package com.jb.cs.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.jb.domain.CompanySize;
import com.jb.domain.CompanyStatus;
import com.jb.domain.CompanyType;
import com.jb.domain.IndustryType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "company")
public class Company {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  @Column(unique = true)
  private String slug;

  private String tagline;

  private String description;

  private String logoUrl;

  private String coverImageUrl;

  private String website;

  private String email;

  private String phone;

  private Integer foundedYear;

  @Enumerated(EnumType.STRING)
  private CompanySize companySize;

  @Enumerated(EnumType.STRING)
  private CompanyType companyType;

  @Enumerated(EnumType.STRING)
  private IndustryType industryType;

  @Enumerated(EnumType.STRING)
  private CompanyStatus status;

  private boolean isVerified = false;

  @Column(unique = true)
  private String registrationNumber;

  @Column(unique = true, nullable = false)
  private Long ownerId;

  @ElementCollection(fetch = FetchType.EAGER)
  @Builder.Default
  private List<SocialLink> socialLinks = new ArrayList<>();

  private Boolean active = true;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(nullable = false)
  @UpdateTimestamp
  private LocalDateTime updatedAt;

}