package com.bnpl.aplazo.repository;

import com.bnpl.aplazo.model.PayScheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaySchemeRespository extends JpaRepository<PayScheme, Long> {
}
