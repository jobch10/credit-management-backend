package com.project.creditmanagementbackend.domain.entity;

import com.project.creditmanagementbackend.domain.enumerado.EnumRiskTolerance;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_investor_profile")
@SQLDelete(sql = "UPDATE tb_investor_profile SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TbInvestorProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "financial_subject_id", nullable = false, unique = true)
    private TbFinancialSubject financialSubject;

    @Column(name = "investment_capacity", nullable = false)
    private BigDecimal investmentCapacity;

    @Column(name = "preferred_rate", precision = 5, scale = 2)
    private BigDecimal preferredRate;

    @Column(name = "preferred_term_months")
    private Integer preferredTermMonths;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_tolerance", length = 20)
    private EnumRiskTolerance riskTolerance;

    @Column(name = "bank_name", length = 100)
    private String bankName;

    @Column(name = "bank_account", length = 30)
    private String bankAccount;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Builder.Default
    @Column(name = "total_invested")
    private BigDecimal totalInvested = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "active_investments")
    private Integer activeInvestments = 0;

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