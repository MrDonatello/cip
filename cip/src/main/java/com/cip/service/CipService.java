package com.cip.service;

import com.cip.dao.cip.*;
import com.cip.dao.user.UserRepository;
import com.cip.model.cip.Cip1;
import com.cip.model.cip.Cip2;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CipService {

    private final UserRepository userRepository;
    private final Cip1Repository cip1Repository;
    private final Cip2Repository cip2Repository;
    private final Cip3Repository cip3Repository;
    private final Cip4Repository cip4Repository;
    private final CommonRepository commonRepository;
    private final WarningRepository warningRepository;

    public CipService(UserRepository userRepository, Cip1Repository cip1Repository, Cip2Repository cip2Repository, Cip3Repository cip3Repository, Cip4Repository cip4Repository, CommonRepository commonRepository, WarningRepository warningRepository) {
        this.userRepository = userRepository;
        this.cip1Repository = cip1Repository;
        this.cip2Repository = cip2Repository;
        this.cip3Repository = cip3Repository;
        this.cip4Repository = cip4Repository;
        this.commonRepository = commonRepository;
        this.warningRepository = warningRepository;
    }


    public Map<Integer, List<Integer>> readConfigureFile() {
        Map<Integer, List<Integer>> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(CipService.class.getResource("/static/docs/config.txt").toURI().getPath()))))) {
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(" ");
                for (String s : data) {
                    list.add(Integer.parseInt(s));
                }
                map.put(i, new ArrayList<>(list));
                list.clear();
                i++;
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return map;
    }

    public Map<Integer, String[]> getAllCip() throws ParseException {
        //List<Cip1> cip1List = cip1Repository.findAll();
        // List<Cip2> cip2List = cip2Repository.findAll();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, 1, 0, 0, 1);
        Calendar calendar2 = new GregorianCalendar(2021, Calendar.MARCH, 1, 0, 0, 5);

        List<Cip1> cip1s = cip1Repository.findByDateTimeBetween(f.format(calendar.getTime()), f.format(calendar2.getTime()));
        Map<Integer, String[]> map = new HashMap<>();

        return map;
    }

    public Long TestDataBaseCip(Long id, int program, int cipNumber, int route, int day, int hour, int minute, int second) { // to check if the database is full, delete later
        switch (cipNumber) {

            case (1): //cip1
                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                int j;
                switch (program) {
                    case (1): // lye
                        j = 0;
                        for (long i = 0; i < 120; i++) { // ополаскивание
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j);
                            Cip1 cip1 = new Cip1(i + id, f.format(calendar.getTime()), route, true, true,
                                    1, 1, true,
                                    1, 1, 1,
                                    false, false, false,
                                    false, true,
                                    false, true, false, false, false);
                            cip1Repository.save(cip1);
                            j++;
                        }
                        id = id + 120;
                        for (long i = 0; i < 60; i++) { // мойка наполнение щелочи 1мин
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j);
                            Cip1 cip1 = new Cip1(i + id, f.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, false, false,
                                    false, true,
                                    false, false, false, true, false);
                            cip1Repository.save(cip1);
                            j++;
                        }
                        id = id + 60;
                        for (long i = 0; i < 240; i++) { // мойка циркуляция  щелочи
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j);
                            Cip1 cip1 = new Cip1(i + id, f.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    true, false, false,
                                    false, false,
                                    false, false, false, true, false);
                            cip1Repository.save(cip1);
                            j++;
                        }
                        id = id + 240;
                        for (long i = 0; i < 120; i++) { // вытеснение щелочи
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j);
                            Cip1 cip1 = new Cip1(i + id, f.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    true, false, false,
                                    false, false,
                                    true, false, false, false, false);
                            cip1Repository.save(cip1);
                            j++;
                        }
                        id = id + 120;
                        for (long i = 0; i < 180; i++) { // ополаскивание
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j);
                            Cip1 cip1 = new Cip1(i + id, f.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, false, true,
                                    false, false,
                                    true, false, false, false, false);
                            cip1Repository.save(cip1);
                            j++;
                        }
                        id = id + 180;
                        break;

                    case (2): //acid
                        j = 0;
                        for (long i = 0; i < 120; i++) { // ополаскивание
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j);
                            Cip1 cip1 = new Cip1(i + id, f.format(calendar.getTime()), route, true, true,
                                    1, 1, true,
                                    1, 1, 1,
                                    false, false, false,
                                    false, true,
                                    false, true, false, false, false);
                            cip1Repository.save(cip1);
                            j++;
                        }
                        id = id + 120;
                        for (long i = 0; i < 60; i++) { // мойка наполнение кислоты 1мин
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j);
                            Cip1 cip1 = new Cip1(i + id, f.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, false, false,
                                    false, true,
                                    false, false, true, false, false);
                            cip1Repository.save(cip1);
                            j++;
                        }
                        id = id + 60;
                        for (long i = 0; i < 240; i++) { // мойка циркуляция  кислоты
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j);
                            Cip1 cip1 = new Cip1(i + id, f.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, true, false,
                                    false, false,
                                    false, false, true, false, false);
                            cip1Repository.save(cip1);
                            j++;
                        }
                        id = id + 240;
                        for (long i = 0; i < 120; i++) { // вытеснение кислоты
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j);
                            Cip1 cip1 = new Cip1(i + id, f.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, true, false,
                                    false, false,
                                    true, false, false, false, false);
                            cip1Repository.save(cip1);
                            j++;
                        }
                        id = id + 120;
                        for (long i = 0; i < 180; i++) { // ополаскивание
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j);
                            Cip1 cip1 = new Cip1(i + id, f.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, false, true,
                                    false, false,
                                    true, false, false, false, false);
                            cip1Repository.save(cip1);
                            j++;
                        }
                        id = id + 180;
                        break;

                    case (3): // стерилизация
                        j = 0;
                        for (long i = 0; i < 120; i++) { // ополаскивание
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j);
                            Cip1 cip1 = new Cip1(i + id, f.format(calendar.getTime()), route, true, true,
                                    1, 1, true,
                                    1, 1, 1,
                                    false, false, false,
                                    false, true,
                                    false, true, false, false, false);
                            cip1Repository.save(cip1);
                            j++;
                        }
                        id = id + 120;
                        for (long i = 0; i < 160; i++) { // циркуляция и нагрев воды
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j);
                            Cip1 cip1 = new Cip1(i + id, f.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, false, false,
                                    true, false,
                                    true, false, false, false, false);
                            cip1Repository.save(cip1);
                            j++;
                        }
                        id = id + 160;
                        for (long i = 0; i < 300; i++) { // стерилизация
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j);
                            Cip1 cip1 = new Cip1(i + id, f.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, false, false,
                                    true, false,
                                    true, false, false, false, false);
                            cip1Repository.save(cip1);
                            j++;
                        }
                        id = id + 300;

                        id = id + 120;
                        for (long i = 0; i < 180; i++) { // охлаждение
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j);
                            Cip1 cip1 = new Cip1(i + id, f.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, false, true,
                                    false, false,
                                    true, false, false, false, false);
                            cip1Repository.save(cip1);
                            j++;
                        }
                        id = id + 180;
                        break;
                }
                break;


            case (2): //cip2
                int j2;
                SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                switch (program) {
                    case (1): // lye
                        j2 = 0;
                        for (long i = 0; i < 120; i++) { // ополаскивание
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip2 cip2 = new Cip2(i + id, f2.format(calendar.getTime()), route, true, true,
                                    1, 1, true,
                                    1, 1, 1,
                                    false, false, false,
                                    false, true,
                                    false, true, false, false);
                            cip2Repository.save(cip2);
                            j2++;
                        }
                        id = id + 120;
                        for (long i = 0; i < 60; i++) { // мойка наполнение щелочи 1мин
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip2 cip2 = new Cip2(i + id, f2.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, false, false,
                                    false, true,
                                    false, false, false, true);
                            cip2Repository.save(cip2);
                            j2++;
                        }
                        id = id + 60;
                        for (long i = 0; i < 240; i++) { // мойка циркуляция  щелочи
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip2 cip2 = new Cip2(i + id, f2.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    true, false, false,
                                    false, false,
                                    false, false, false, true);
                            cip2Repository.save(cip2);
                            j2++;
                        }
                        id = id + 240;
                        for (long i = 0; i < 120; i++) { // вытеснение щелочи
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip2 cip2 = new Cip2(i + id, f2.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    true, false, false,
                                    false, false,
                                    true, false, false, false);
                            cip2Repository.save(cip2);
                            j2++;
                        }
                        id = id + 120;
                        for (long i = 0; i < 180; i++) { // ополаскивание
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip2 cip2 = new Cip2(i + id, f2.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, false, true,
                                    false, false,
                                    true, false, false, false);
                            cip2Repository.save(cip2);
                            j2++;
                        }
                        id = id + 180;
                        break;

                    case (2): //acid
                        j2 = 0;
                        for (long i = 0; i < 120; i++) { // ополаскивание
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip2 cip2 = new Cip2(i + id, f2.format(calendar.getTime()), route, true, true,
                                    1, 1, true,
                                    1, 1, 1,
                                    false, false, false,
                                    false, true,
                                    false, true, false, false);
                            cip2Repository.save(cip2);
                            j2++;
                        }
                        id = id + 120;
                        for (long i = 0; i < 60; i++) { // мойка наполнение кислоты 1мин
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip2 cip2 = new Cip2(i + id, f2.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, false, false,
                                    false, true,
                                    false, false, true, false);
                            cip2Repository.save(cip2);
                            j2++;
                        }
                        id = id + 60;
                        for (long i = 0; i < 240; i++) { // мойка циркуляция  кислоты
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip2 cip2 = new Cip2(i + id, f2.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, true, false,
                                    false, false,
                                    false, false, true, false);
                            cip2Repository.save(cip2);
                            j2++;
                        }
                        id = id + 240;
                        for (long i = 0; i < 120; i++) { // вытеснение кислоты
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip2 cip2 = new Cip2(i + id, f2.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, true, false,
                                    false, false,
                                    true, false, false, false);
                            cip2Repository.save(cip2);
                            j2++;
                        }
                        id = id + 120;
                        for (long i = 0; i < 180; i++) { // ополаскивание
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip2 cip2 = new Cip2(i + id, f2.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, false, true,
                                    false, false,
                                    true, false, false, false);
                            cip2Repository.save(cip2);
                            j2++;
                        }
                        id = id + 180;
                        break;

                    case (3): // стерилизация
                        j2 = 0;
                        for (long i = 0; i < 120; i++) { // ополаскивание
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip2 cip2 = new Cip2(i + id, f2.format(calendar.getTime()), route, true, true,
                                    1, 1, true,
                                    1, 1, 1,
                                    false, false, false,
                                    false, true,
                                    false, true, false, false);
                            cip2Repository.save(cip2);
                            j2++;
                        }
                        id = id + 120;
                        for (long i = 0; i < 160; i++) { // циркуляция и нагрев воды
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip2 cip2 = new Cip2(i + id, f2.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, false, false,
                                    true, false,
                                    true, false, false, false);
                            cip2Repository.save(cip2);
                            j2++;
                        }
                        id = id + 160;
                        for (long i = 0; i < 300; i++) { // стерилизация
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip2 cip2 = new Cip2(i + id, f2.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, false, false,
                                    true, false,
                                    true, false, false, false);
                            cip2Repository.save(cip2);
                            j2++;
                        }
                        id = id + 300;

                        id = id + 120;
                        for (long i = 0; i < 180; i++) { // охлаждение
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip2 cip2 = new Cip2(i + id, f2.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, false, true,
                                    false, false,
                                    true, false, false, false);
                            cip2Repository.save(cip2);
                            j2++;
                        }
                        id = id + 180;
                        id = id + 180;
                        break;
                }
                break;
        }
        return id;
    }
}
