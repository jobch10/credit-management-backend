package com.project.creditmanagementbackend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TbAttachmentType")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TbAttachmentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;
}
