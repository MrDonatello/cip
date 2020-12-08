package com.cip.dao.cip;

import com.cip.model.cip.Warning;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarningRepository extends JpaRepository<Warning, Long> {
}
