package com.amny.IndigeneshipCertificate.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Indigene {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "certificate_number", nullable = false)
    private String certificateNumber;

    @Column(name = "application_status")
    @Enumerated(EnumType.STRING)
    private UserStatus applicationStatus;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "sur_name", nullable = false)
    private String surname;

    @Column(name = "fathers_tribe", nullable = false)
    private String fathersTribe;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "fathers_home_town", nullable = false)
    private String fathersHomeTown;

    @Column(name = "mothers_name", nullable = false)
    private String mothersName;

    @Column(name = "mothers_tribe")
    private String mothersTribe;

    @Column(name = "mothers_home_town", nullable = false)
    private String mothersHomeTown;

    @Column(name = "village", nullable = false)
    private String village;

    @Column(name = "place_of_birth", nullable = false)
    private String placeOfBirth;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "ward_in_local_government_area", nullable = false)
    private String wardInLocalGovernmentArea;

    @Column(name = "state_of_origin", nullable = false)
    private String stateOfOrigin;

    @Column(name = "country", nullable = false)
    private String nationality;

    @OneToMany(
            mappedBy = "indigene",
            cascade = CascadeType.ALL
    )
    private List<AttachmentFile> indigeneAttachment = new ArrayList<>();

}
