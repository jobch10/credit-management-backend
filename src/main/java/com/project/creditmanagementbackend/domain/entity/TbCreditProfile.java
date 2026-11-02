package com.project.creditmanagementbackend.domain.entity;

import com.project.creditmanagementbackend.domain.enumerado.EnumEmploymentType;
import com.project.creditmanagementbackend.domain.enumerado.EnumInfocorpStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_credit_profile")
@SQLDelete(sql = "UPDATE tb_credit_profile SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TbCreditProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "financial_subject_id", nullable = false, unique = true)
    private TbFinancialSubject financialSubject;

    @Column(name = "dependents_count", nullable = false)
    private Integer dependentsCount;

    @Column(name = "housing_type", nullable = false, length = 20)
    private String housingType;

    @Column(name = "monthly_income", nullable = false)
    private BigDecimal monthlyIncome;

    @Column(name = "fixed_expenses", nullable = false)
    private BigDecimal fixedExpenses;

    @Column(name = "external_debt_payments", nullable = false)
    private BigDecimal externalDebtPayments;

    // ---- Situación laboral ----
    @Column(length = 100)
    private String occupation;

    @Column(length = 150)
    private String employer;

    @Column(name = "work_seniority_months")
    private Integer workSeniorityMonths;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type", length = 20)
    private EnumEmploymentType employmentType;

    // ---- Verificación externa ----
    @Enumerated(EnumType.STRING)
    @Column(name = "infocorp_status", length = 20)
    private EnumInfocorpStatus infocorpStatus;

    @Column(name = "infocorp_score")
    private Integer infocorpScore;

    @Builder.Default
    @Column(name = "has_police_records")
    private Boolean hasPoliceRecords = false;

    @Builder.Default
    @Column(name = "has_judicial_records")
    private Boolean hasJudicialRecords = false;

    @Builder.Default
    @Column(name = "has_criminal_records")
    private Boolean hasCriminalRecords = false;

    // ---- Control interno ----
    @Builder.Default
    @Column(name = "total_loans_approved")
    private Integer totalLoansApproved = 0;

    @Builder.Default
    @Column(name = "max_days_in_arrears")
    private Integer maxDaysInArrears = 0;

    @Builder.Default
    @Column(name = "times_refinanced")
    private Integer timesRefinanced = 0;

    @Column(name = "credit_score")
    private Integer creditScore;

    @Column(name = "risk_level", length = 20)
    private String riskLevel;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}