package com.amny.IndigeneshipCertificate.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class AttachmentFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_type", nullable = false)
    private String fileType;

    @Column(name = "proof_of_identity", nullable = false)
    @Lob
    private byte[] proofOfIdentity;

    @ManyToOne
    @JoinColumn(
            name = "indigene_fk",
            referencedColumnName = "id"
    )
    private Indigene indigene;


    public AttachmentFile(String fileName, String fileType, byte[] proofOfIdentity) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.proofOfIdentity = proofOfIdentity;
    }
}
