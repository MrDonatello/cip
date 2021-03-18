package com.cip.dao.cip;

import com.cip.model.cip.Cip;
import com.cip.model.cip.Cip2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Cip2Repository extends JpaRepository<Cip2, Long> {
    List<Cip> findByDateTimeBetween(String dateTime, String dateTime2);
}
