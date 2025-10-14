package com.springBoot.MbakaraBlogApp.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity //Making this class a JPA entity by using @Entity annotation.
@Table(name = "consumers") // To provide a table name for this clas

public class Consumers {
    // The fields below are also known as Instance variables
    // Defining the primary key for this class.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String consumersName;

    // Using @Column annotation to provide column for a field
    @Column(nullable = false, unique = true)
    private String consumersUsername;

    @Column(nullable = false, unique = true)
    private String consumersEmail;

    @Column(nullable = false)
    private String consumersPassword;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)// Fetch type means whenever we load Users(consumers) entity, it will also load its roles along.
    @JoinTable(name = "consumers_roles",
    joinColumns = @JoinColumn(name = "consumers_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))

    private Set<RolePlayed> roles;

}
