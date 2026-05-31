package com.terraviva.repository;

import com.terraviva.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByDocumento(String documento);

    Optional<Cliente> findByEmail(String email);

    boolean existsByEmail(String email);
}