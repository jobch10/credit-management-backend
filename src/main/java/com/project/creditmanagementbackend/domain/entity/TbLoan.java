package com.project.creditmanagementbackend.domain.entity;

import com.project.creditmanagementbackend.domain.enumerado.EnumGuaranteeType;
import com.project.creditmanagementbackend.domain.enumerado.EnumLoanStatus;
import com.project.creditmanagementbackend.domain.enumerado.EnumPaymentFrequency;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_loan")
@SQLDelete(sql = "UPDATE tb_loan SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TbLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrower_subject_id", nullable = false)
    private TbFinancialSubject borrowerSubject;

    @Column(name = "loan_number", unique = true, nullable = false, length = 20)
    private String loanNumber;

    @Column(name = "principal_amount", nullable = false)
    private BigDecimal principalAmount;

    @Column(name = "interest_rate", nullable = false, precision = 5, scale = 2)
    private BigDecimal interestRate;

    @Column(name = "term_months", nullable = false)
    private Integer termMonths;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_frequency", nullable = false, length = 20)
    private EnumPaymentFrequency paymentFrequency;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "total_interest", nullable = false)
    private BigDecimal totalInterest;

    @Column(name = "total_to_pay", nullable = false)
    private BigDecimal totalToPay;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private EnumLoanStatus status = EnumLoanStatus.PENDIENTE;

    @Enumerated(EnumType.STRING)
    @Column(name = "guarantee_type", length = 20)
    private EnumGuaranteeType guaranteeType;

    @Column(name = "guarantee_description", columnDefinition = "TEXT")
    private String guaranteeDescription;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "loan", fetch = FetchType.LAZY, cascade =  CascadeType.REMOVE)
    private List<TbInstallment> installments;

    @OneToMany(mappedBy = "loan", fetch = FetchType.LAZY, cascade =  CascadeType.REMOVE)
    private List<TbLoanFunding> fundings;

    @PrePersist
    protected void onCreate() { this.createdAt = LocalDateTime.now(); }

    @PreUpdate
    protected void onUpdate() { this.updatedAt = LocalDateTime.now(); }
}