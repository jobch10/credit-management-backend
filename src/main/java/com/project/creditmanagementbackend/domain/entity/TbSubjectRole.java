package com.project.creditmanagementbackend.domain.entity;

import com.project.creditmanagementbackend.domain.enumerado.EnumSubjectRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "tb_subject_role",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_subject_role",
                columnNames = {"financial_subject_id", "role"}
        )
)
@SQLDelete(sql = "UPDATE tb_subject_role SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TbSubjectRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "financial_subject_id", nullable = false)
    private TbFinancialSubject financialSubject;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EnumSubjectRole role;

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