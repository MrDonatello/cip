package com.cip.dao.cip;

import com.cip.model.cip.Cip;
import com.cip.model.cip.Cip1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sun.print.resources.serviceui_zh_TW;

import java.util.List;

public interface Cip1Repository extends JpaRepository<Cip1, Long> {
    //@Query(value = "")
    List<Cip> findByDateTimeBetween(String dateTime, String dateTime2);
    List<Cip> findByDateTimeBetweenAndRoute(String dateTime, String dateTime2, int route);
}
