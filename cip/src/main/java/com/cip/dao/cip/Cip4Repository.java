package com.cip.dao.cip;

import com.cip.model.cip.Cip;
import com.cip.model.cip.Cip4;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Cip4Repository extends JpaRepository<Cip4,Long> {
    List<Cip> findByDateTimeBetween(String dateTime, String dateTime2);
}
