package com.project.creditmanagementbackend.domain.entity;

import com.project.creditmanagementbackend.domain.enumerado.EnumNivelRiesgo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "TbClient")
@SQLDelete(sql = "UPDATE tb_client SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TbClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id", nullable = false)
    private TbEntity entity;

    @Column(name = "client_code", nullable = false)
    private String clientCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level")
    private EnumNivelRiesgo riskLevel;

    @Column(name = "credit_score")
    private Integer creditScore;

    @Column(name = "monthly_income")
    private BigDecimal monthlyIncome;

    @Column(name = "economic_activity")
    private String economicActivity;

    @Column(name = "credit_limit", nullable = false)
    private BigDecimal creditLimit;

    @Column(name = "available_credit", nullable = false)
    private BigDecimal availableCredit;

    @Column(name = "last_evaluation_date")
    private LocalDate lastEvaluationDate;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Builder.Default
    private boolean state = true;

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
