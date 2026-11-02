package com.project.creditmanagementbackend.domain.entity;
import com.project.creditmanagementbackend.domain.enumerado.ModuleType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "TbModule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TbModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String code;

    private String name;

    private String description;

    private String icon;

    private String route;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ModuleType type;

    @Column(name = "order_num")
    private Integer orderNum;

    @Builder.Default
    private boolean state = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private TbModule parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<TbModule> children;

    @OneToMany(mappedBy = "modulo", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TbModuleAction> actions;

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