package com.amny.IndigeneshipCertificate.repository;

import com.amny.IndigeneshipCertificate.entity.Indigene;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.function.Supplier;

@Repository
public interface IndigeneRepository extends JpaRepository<Indigene, Long> {
   public Indigene findByCertificateNumber(String certificateNumber);

}
