package com.cip.service;

import com.cip.dao.cip.*;
import com.cip.dao.user.UserRepository;
import com.cip.model.cip.Cip;
import com.cip.model.cip.Cip1;
import com.cip.model.cip.Cip2;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URISyntaxException;
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

    public Map<Integer, String[]> getCipLogForDate(Calendar start, Calendar end) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Cip1> cip1s = cip1Repository.findByDateTimeBetween(f.format(start.getTime()), f.format(end.getTime()));
        Map<Integer, String[]> data = new HashMap<>();
        return data;
    }

    public String whatIsProgram(Cip cip) {
        if (cip.isOutputFlowRinseWaterValve() && cip.isDrainValve() ) {
            return "Предворительное ополаскивание";
        }
        if (cip.isOutputFlowLyeValve() && cip.isDrainValve()) {
            return "Заполнение щелочью";
        }
        if (cip.isOutputFlowLyeValve() && cip.isInputFlowLyeValve()) {
            return "Мойка щелочью";
        }
        if (cip.isOutputFlowPureWaterValve() && cip.isInputFlowLyeValve()) {
            return "Вытеснение щелочи";
        }
        if (cip.isOutputFlowAcidValve() && cip.isDrainValve()) {
            return "Заполнение кислотой";
        }
        if (cip.isOutputFlowAcidValve() && cip.isInputFlowAcidValve()) {
            return "Мойка кислотой";
        }
        if (cip.isInputFlowAcidValve() && cip.isOutputFlowPureWaterValve()) {
            return "Вытеснение кислоты";
        }
        if (cip.isOutputFlowPureWaterValve() && cip.isDrainValve()|| cip.isOutputFlowPureWaterValve() && cip.isInputFlowRinseWaterValve()) {
            return "Последнее ополаскивание";
        }
        if (cip.isOutputFlowPureWaterValve() && cip.isCirculationValve()) {
            return "Стерилизация";
        }
        return null;
    }

    public Map<Integer, String[]> getCipLogOneDay() {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DAY_OF_MONTH, -1);
        List<Cip1> cip1List = cip1Repository.findByDateTimeBetween(f.format(calendar1.getTime()), f.format(Calendar.getInstance().getTime()));

        List<String[]> strings = new ArrayList<>();
        String[] dateTimeStart, dateTimeEnd, fullObject;
        Map<Integer, String[]> map = new HashMap<>();

        String program, s2, s3;
        int count = 0;
        int count2 = 0;
        Cip start = null;
        Cip lastObject = null;

        for (Cip1 cip1 : cip1List) {// возможно сделать фор и ленс минус один для последнего элемента и убрать count
            count2++;
            if (start == null) {

                start = cip1;
            } else {
                if (!(start.equals(cip1))|| count2 == cip1List.size()) {// добавлять еще один объект с по низу всей мойки старт это если предворительное началось конец это когда последне опаласкивание закончилось
                    dateTimeStart = start.getDateTime().split("[,\\s\\-:]");
                    assert lastObject != null;
                    dateTimeEnd = lastObject.getDateTime().split("[,\\s\\-:]");
                    fullObject = new String[]{"CIP1", whatIsProgram(start), null, dateTimeStart[0], dateTimeStart[1], dateTimeStart[2], dateTimeStart[3], dateTimeStart[4], dateTimeStart[5],
                            dateTimeEnd[0], dateTimeEnd[1], dateTimeEnd[2], dateTimeEnd[3], dateTimeEnd[4], dateTimeEnd[5]};
                    map.put(count, fullObject);
                    count++;
                    start = cip1;
                }
                lastObject = cip1;
            }

        }
        String[] s = {"CIP1", "Щелочь", "null", "2020", "5", "5", "5", "5", "5", "2020", "5", "5", "5", "10", "5"};


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
