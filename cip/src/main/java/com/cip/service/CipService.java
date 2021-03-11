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


    private Map<Integer, List<String>> readConfigureFile() {
        Map<Integer, List<String>> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(CipService.class.getResource("/static/docs/config.txt").toURI().getPath()))))) {
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                list.addAll(Arrays.asList(line.split(" ")));
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

    private String whatIsProgram(Cip cip) {
        if (cip.isOutputFlowRinseWaterValve() && cip.isDrainValve()) {
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
        if (cip.isOutputFlowPureWaterValve() && cip.isDrainValve() || cip.isOutputFlowPureWaterValve() && cip.isInputFlowRinseWaterValve()) {
            return "Последнее ополаскивание";
        }
        if (cip.isOutputFlowPureWaterValve() && cip.isCirculationValve()) {
            return "Стерилизация";
        }
        return null;
    }

    private String[] checkObjectToReferenceValues(String program, int rout, double timeWorkReal) {// округлить времена до минуты
        double timeWorkReference = 0;
        String[] result = new String[2];
        List<String> objectData = readConfigureFile().get(rout);
        result[0] = objectData.get(0);

        switch (program) {
            case ("Стерилизация"):
                timeWorkReference = Integer.parseInt(objectData.get(2) + objectData.get(4) + objectData.get(5));
                break;
            case ("Вытеснение кислоты"):
                timeWorkReference = Integer.parseInt(objectData.get(2) + objectData.get(8) + objectData.get(3) + objectData.get(9) + objectData.get(4));
                break;
            case ("Вытеснение щелочи"):
                timeWorkReference = Integer.parseInt(objectData.get(2) + objectData.get(6) + objectData.get(1) + objectData.get(7) + objectData.get(4));
                break;
        }
        if (timeWorkReal > timeWorkReference) {
            result[1] = "#ffe700";
        }
        if (timeWorkReal < timeWorkReference) {
            result[1] = "#ff2a3c";
        }
        if (timeWorkReal == timeWorkReference) {
            result[1] = "#e600";
        }
        return result;
    }

    public Map<Integer, String[]> getCipLogOneDay() {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DAY_OF_MONTH, -1);
        List<Cip1> cip1List = cip1Repository.findByDateTimeBetween(f.format(calendar1.getTime()), f.format(Calendar.getInstance().getTime()));

        String[] dateTimeStart, dateTimeEnd, fullObject, dateTimeAllProgramStart = new String[5];
        Map<Integer, String[]> data = new HashMap<>();

        String program;
        int count = 0;
        int count2 = 0;
        Cip start = null;
        Cip lastObject = null;

        for (Cip1 cip1 : cip1List) {// возможно сделать фор и ленс минус один для последнего элемента и убрать count
            count2++;
            if (start == null) {

                start = cip1;
            } else {
                if (!(start.equals(cip1)) || count2 == cip1List.size()) {// добавлять еще один объект с по низу всей мойки старт это если предворительное началось конец это когда последне опаласкивание закончилось
                    dateTimeStart = start.getDateTime().split("[,\\s\\-:]");
                    assert lastObject != null;
                    dateTimeEnd = lastObject.getDateTime().split("[,\\s\\-:]");
                    program = whatIsProgram(start);
                    assert program != null;
                    if (program.equals("Предворительное ополаскивание")) {
                        dateTimeAllProgramStart = dateTimeStart;
                    }
                    if (program.equals("Последнее ополаскивание")) {

                        double timeWorkRealHour = (Integer.parseInt(dateTimeEnd[3]) - Integer.parseInt(dateTimeAllProgramStart[3])) * 60;
                        double timeWorkRealMinute = Integer.parseInt(dateTimeEnd[4]) - Integer.parseInt(dateTimeAllProgramStart[4]);
                        double timeWorkRealSecond = (Integer.parseInt(dateTimeEnd[5]) - Integer.parseInt(dateTimeAllProgramStart[5])) / 60;
                        timeWorkRealMinute = timeWorkRealMinute + timeWorkRealHour + timeWorkRealSecond;

                        String programLastStep = data.get(count - 1)[1];
                        int rout = lastObject.getRoute();

                        String[] result = checkObjectToReferenceValues(programLastStep, rout, timeWorkRealMinute);

                        fullObject = new String[]{"CIP1", result[0], result[1],
                                dateTimeAllProgramStart[0], dateTimeAllProgramStart[1], dateTimeAllProgramStart[2], dateTimeAllProgramStart[3], dateTimeAllProgramStart[4], dateTimeAllProgramStart[5],
                                dateTimeEnd[0], dateTimeEnd[1], dateTimeEnd[2], dateTimeEnd[3], dateTimeEnd[4], dateTimeEnd[5]};
                        data.put(count, fullObject);
                        count++;
                    }
                    fullObject = new String[]{"CIP1", program, null, dateTimeStart[0], dateTimeStart[1], dateTimeStart[2], dateTimeStart[3], dateTimeStart[4], dateTimeStart[5],
                            dateTimeEnd[0], dateTimeEnd[1], dateTimeEnd[2], dateTimeEnd[3], dateTimeEnd[4], dateTimeEnd[5]};
                    data.put(count, fullObject);
                    count++;
                    start = cip1;
                }
                lastObject = cip1;
            }

        }
        //String[] s = {"CIP1", "Щелочь", "null", "2020", "5", "5", "5", "5", "5", "2020", "5", "5", "5", "10", "5"};


        return data;
    }

    public Long TestDataBaseCip(Long id, int program, int cipNumber, int route, int day, int hour, int minute, int second) { // to check  database test only, delete later
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
