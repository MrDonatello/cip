package com.cip.dao.cip;

import com.cip.model.cip.Cip;
import com.cip.model.cip.Cip3;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Cip3Repository extends JpaRepository<Cip3,Long> {
    List<Cip> findByDateTimeBetween(String dateTime, String dateTime2);
}
