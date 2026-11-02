package com.project.creditmanagementbackend.domain.entity;

import com.project.creditmanagementbackend.domain.enumerado.EnumLoanFundingStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_loan_funding")
@SQLDelete(sql = "UPDATE tb_loan_funding SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TbLoanFunding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id", nullable = false)
    private TbLoan loan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "investor_subject_id", nullable = false)
    private TbFinancialSubject investorSubject;

    @Column(name = "funded_amount", nullable = false)
    private BigDecimal fundedAmount;

    @Column(name = "funded_percentage", nullable = false, precision = 5, scale = 2)
    private BigDecimal fundedPercentage;

    @Column(name = "expected_rate", precision = 5, scale = 2)
    private BigDecimal expectedRate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private EnumLoanFundingStatus status = EnumLoanFundingStatus.ACTIVO;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PrePersist
    protected void onCreate() { this.createdAt = LocalDateTime.now(); }

    @PreUpdate
    protected void onUpdate() { this.updatedAt = LocalDateTime.now(); }
}