package br.com.codenation.repositories;

import br.com.codenation.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, UUID>, JpaSpecificationExecutor<Application> {
}
