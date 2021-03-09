package com.cip.dao.cip;

import com.cip.model.cip.Cip1;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Cip1Repository extends JpaRepository<Cip1, Long> {
    List<Cip1> findByDateTimeBetween(String dateTime, String dateTime2);
}
