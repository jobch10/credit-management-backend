package com.project.creditmanagementbackend.domain.entity;

import com.project.creditmanagementbackend.domain.enumerado.EnumSubjectStatus;
import com.project.creditmanagementbackend.domain.enumerado.EnumSubjectType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_financial_subject")
@SQLDelete(sql = "UPDATE tb_financial_subject SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TbFinancialSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "subject_type", nullable = false, length = 20)
    private EnumSubjectType subjectType;

    @Column(nullable = false)
    private String address;

    private String email;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private EnumSubjectStatus status = EnumSubjectStatus.ACTIVO;

    @Builder.Default
    private boolean state = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToOne(mappedBy = "financialSubject", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private TbPerson person;

    @OneToOne(mappedBy = "financialSubject", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private TbCreditProfile creditProfile;

    @OneToOne(mappedBy = "financialSubject", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private TbInvestorProfile investorProfile;

    @OneToMany(mappedBy = "financialSubject", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TbSubjectRole> roles;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
