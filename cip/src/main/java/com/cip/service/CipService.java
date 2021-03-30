package com.cip.service;

import com.cip.dao.cip.*;
import com.cip.dao.user.UserRepository;
import com.cip.model.cip.*;
import com.cip.model.dto.GanttDTO;
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

    public Map<Integer, String[]> getPropertyElement(GanttDTO ganttDTO) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String[] st = ganttDTO.getStart().split("[,\\s\\-:.T]");
        Calendar start = new GregorianCalendar(Integer.parseInt(st[0]), Integer.parseInt(st[1]) - 1, Integer.parseInt(st[2]), Integer.parseInt(st[3]), Integer.parseInt(st[4]), Integer.parseInt(st[5]));
        start.add(Calendar.HOUR_OF_DAY, 6);
        String[] e = ganttDTO.getEnd().split("[,\\s\\-:.T]");
        Calendar end = new GregorianCalendar(Integer.parseInt(e[0]), Integer.parseInt(e[1]) - 1, Integer.parseInt(e[2]), Integer.parseInt(e[3]), Integer.parseInt(e[4]), Integer.parseInt(e[5]));
        end.add(Calendar.HOUR_OF_DAY, 6);
        List<Cip> cipList = new ArrayList<>();
        switch (ganttDTO.getCipNumber()) {
            case ("CIP1"): {
                cipList = cip1Repository.findByDateTimeBetween(f.format(start.getTime()), f.format(end.getTime()));
                break;
            }
            case ("CIP2"): {
                cipList = cip2Repository.findByDateTimeBetween(f.format(start.getTime()), f.format(end.getTime()));
                break;
            }
            case ("CIP3"): {
                cipList = cip3Repository.findByDateTimeBetween(f.format(start.getTime()), f.format(end.getTime()));
                break;
            }
            case ("CIP4"): {
                cipList = cip4Repository.findByDateTimeBetween(f.format(start.getTime()), f.format(end.getTime()));
                break;
            }
        }
        Map<Integer, String[]> map = parsingAllCip(cipList);

        Map<Integer, List<String>> listMap = readConfigureFile();

        map.put(10, listMap.get(cipList.get(1).getRoute()).toArray(new String[0]));

        return map;
    }

    public Map<Integer, String[]> getAllCipLogForDate(Calendar start, Calendar end) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Cip> cipList = cip1Repository.findByDateTimeBetween(f.format(start.getTime()), f.format(end.getTime()));
        cipList.addAll(cip2Repository.findByDateTimeBetween(f.format(start.getTime()), f.format(end.getTime())));
        cipList.addAll(cip3Repository.findByDateTimeBetween(f.format(start.getTime()), f.format(end.getTime())));
        cipList.addAll(cip4Repository.findByDateTimeBetween(f.format(start.getTime()), f.format(end.getTime())));
        return parsingAllCip(cipList);
    }

    private double getRealTime(String[] dateTimeAllProgramStart, String[] dateTimeEnd) {
        double timeWorkRealHour = (Integer.parseInt(dateTimeEnd[3]) - Integer.parseInt(dateTimeAllProgramStart[3])) * 60;
        double timeWorkRealMinute = Integer.parseInt(dateTimeEnd[4]) - Integer.parseInt(dateTimeAllProgramStart[4]);
        double timeWorkRealSecond = (Integer.parseInt(dateTimeEnd[5]) - Integer.parseInt(dateTimeAllProgramStart[5])) / 60;// сделать правильно с остатком от деления и целым (все в сек)
        return timeWorkRealMinute + timeWorkRealHour + timeWorkRealSecond;
    }

    private Map<Integer, String[]> parsingAllCip(List<Cip> cipList) {
        Map<Integer, String[]> data = new HashMap<>();
        String[] dateTimeStart, dateTimeEnd, fullObject;
        String[] dateTimeAllProgramStart = new String[5];
        String program;
        int count = 0;
        int count2 = 0;
        Cip start = null;
        Cip lastObject = null;
        for (Cip cip : cipList) {
            count2++;
            if (start == null) {
                start = cip;
            } else {
                if (!(start.equals(cip)) || count2 == cipList.size()) {
                    dateTimeStart = start.getDateTime().split("[,\\s\\-:]");
                    assert lastObject != null;
                    dateTimeEnd = lastObject.getDateTime().split("[,\\s\\-:]");
                    program = whatProgram(start);
                    assert program != null;
                    String cipNumber = whatCipNumber(start.getRoute());
                    if (program.equals("Ополаскивание")) {
                        dateTimeAllProgramStart = dateTimeStart;
                    }
                    if (program.equals("Последнее ополаскивание")) {
                        String[] result = checkObjectToReferenceValues(data.get(count - 1)[1], lastObject.getRoute(), getRealTime(dateTimeAllProgramStart, dateTimeEnd));
                        fullObject = new String[]{cipNumber, result[0], result[1],
                                dateTimeAllProgramStart[0], dateTimeAllProgramStart[1], dateTimeAllProgramStart[2], dateTimeAllProgramStart[3], dateTimeAllProgramStart[4], dateTimeAllProgramStart[5],
                                dateTimeEnd[0], dateTimeEnd[1], dateTimeEnd[2], dateTimeEnd[3], dateTimeEnd[4], dateTimeEnd[5]};
                        data.put(count, fullObject);
                        count++;
                        fullObject = new String[]{cipNumber, "", "#ffffff",
                                dateTimeEnd[0], dateTimeEnd[1], dateTimeEnd[2], dateTimeEnd[3], dateTimeEnd[4], dateTimeEnd[5],
                                dateTimeEnd[0], dateTimeEnd[1], dateTimeEnd[2], dateTimeEnd[3], dateTimeEnd[4], dateTimeEnd[5]};
                        data.put(count, fullObject);
                        count++;
                        fullObject = new String[]{cipNumber, "", "#ffffff",
                                dateTimeAllProgramStart[0], dateTimeAllProgramStart[1], dateTimeAllProgramStart[2], dateTimeAllProgramStart[3], dateTimeAllProgramStart[4], String.valueOf(Integer.parseInt(dateTimeAllProgramStart[5]) - 1),
                                dateTimeAllProgramStart[0], dateTimeAllProgramStart[1], dateTimeAllProgramStart[2], dateTimeAllProgramStart[3], dateTimeAllProgramStart[4], dateTimeAllProgramStart[5]};
                        data.put(count, fullObject);
                        count++;
                    }
                    fullObject = new String[]{cipNumber, program, null, dateTimeStart[0], dateTimeStart[1], dateTimeStart[2], dateTimeStart[3], dateTimeStart[4], dateTimeStart[5],
                            dateTimeEnd[0], dateTimeEnd[1], dateTimeEnd[2], dateTimeEnd[3], dateTimeEnd[4], dateTimeEnd[5]};
                    data.put(count, fullObject);
                    count++;
                    start = cip;
                }
                lastObject = cip;
            }
        }
        return data;
    }

    private String whatProgram(Cip cip) {
        if (cip.isOutputFlowRinseWaterValve() && cip.isDrainValve()) {
            return "Ополаскивание";
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

    private String whatCipNumber(int rout) {
        if (rout <= 23) {
            return "CIP1";
        } else if (rout <= 41) {
            return "CIP2";
        } else if (rout <= 63) {
            return "CIP3";
        } else {
            return "CIP4";
        }
    }

    private String[] checkObjectToReferenceValues(String program, int rout, double timeWorkReal) {
        double timeWorkReference = 0;
        String[] result = new String[2];
        List<String> objectData = readConfigureFile().get(rout);
        result[0] = objectData.get(0).replaceAll("_", " ");

        switch (program) {
            case ("Стерилизация"):
                timeWorkReference = Double.parseDouble(objectData.get(2)) + Double.parseDouble(objectData.get(5)) + Double.parseDouble(objectData.get(4));
                break;
            case ("Вытеснение кислоты"):
                timeWorkReference = Double.parseDouble(objectData.get(2)) + Double.parseDouble(objectData.get(8)) + Double.parseDouble(objectData.get(3)) + Double.parseDouble(objectData.get(9)) + Double.parseDouble(objectData.get(4));
                break;
            case ("Вытеснение щелочи"):
                timeWorkReference = Double.parseDouble(objectData.get(2)) + Double.parseDouble(objectData.get(6)) + Double.parseDouble(objectData.get(1)) + Double.parseDouble(objectData.get(7)) + Double.parseDouble(objectData.get(4));
                break;
        }
        if (timeWorkReal > timeWorkReference) {
            result[1] = "#FFFF00";
        }
        if (timeWorkReal < timeWorkReference) {
            result[1] = "#FF0000";
        }
        if (timeWorkReal == timeWorkReference) {
            result[1] = "#049a00";
        }
        return result;
    }

    public Map<Integer, String[]> getAllCipLogOneDay() {
        Calendar dayAgo = Calendar.getInstance();
        dayAgo.add(Calendar.DAY_OF_MONTH, -2);
        return getAllCipLogForDate(dayAgo, Calendar.getInstance());
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

            case (3): //cip3

                SimpleDateFormat f3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                switch (program) {
                    case (1): // lye
                        j2 = 0;
                        for (long i = 0; i < 120; i++) { // ополаскивание
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip3 cip3 = new Cip3(i + id, f3.format(calendar.getTime()), route, true, true,
                                    1, 1, true,
                                    1, 1, 1,
                                    false, false, false,
                                    false, true,
                                    false, true, false, false, false);
                            cip3Repository.save(cip3);
                            j2++;
                        }
                        id = id + 120;
                        for (long i = 0; i < 60; i++) { // мойка наполнение щелочи 1мин
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip3 cip3 = new Cip3(i + id, f3.format(calendar.getTime()), route, true, true,
                                    1, 1, true,
                                    1, 1, 1,
                                    false, false, false,
                                    false, true,
                                    false, false, false, true, false);
                            cip3Repository.save(cip3);
                            j2++;
                        }
                        id = id + 60;
                        for (long i = 0; i < 240; i++) { // мойка циркуляция  щелочи
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip3 cip3 = new Cip3(i + id, f3.format(calendar.getTime()), route, true, true,
                                    1, 1, true,
                                    1, 1, 1,
                                    true, false, false,
                                    false, false,
                                    false, false, false, true, false);
                            cip3Repository.save(cip3);
                            j2++;
                        }
                        id = id + 240;
                        for (long i = 0; i < 120; i++) { // вытеснение щелочи
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip3 cip3 = new Cip3(i + id, f3.format(calendar.getTime()), route, true, true,
                                    1, 1, true,
                                    1, 1, 1,
                                    true, false, false,
                                    false, false,
                                    true, false, false, false, false);
                            cip3Repository.save(cip3);
                            j2++;
                        }
                        id = id + 120;
                        for (long i = 0; i < 180; i++) { // ополаскивание
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip3 cip3 = new Cip3(i + id, f3.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, false, true,
                                    false, false,
                                    true, false, false, false, false);
                            cip3Repository.save(cip3);
                            j2++;
                        }
                        id = id + 180;
                        break;

                    case (2): //acid
                        j2 = 0;
                        for (long i = 0; i < 120; i++) { // ополаскивание
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip3 cip3 = new Cip3(i + id, f3.format(calendar.getTime()), route, true, true,
                                    1, 1, true,
                                    1, 1, 1,
                                    false, false, false,
                                    false, true,
                                    false, true, false, false, false);
                            cip3Repository.save(cip3);
                            j2++;
                        }
                        id = id + 120;
                        for (long i = 0; i < 60; i++) { // мойка наполнение кислоты 1мин
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip3 cip3 = new Cip3(i + id, f3.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, false, false,
                                    false, true,
                                    false, false, true, false, false);
                            cip3Repository.save(cip3);
                            j2++;
                        }
                        id = id + 60;
                        for (long i = 0; i < 240; i++) { // мойка циркуляция  кислоты
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip3 cip3 = new Cip3(i + id, f3.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, true, false,
                                    false, false,
                                    false, false, true, false, false);
                            cip3Repository.save(cip3);
                            j2++;
                        }
                        id = id + 240;
                        for (long i = 0; i < 120; i++) { // вытеснение кислоты
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip3 cip3 = new Cip3(i + id, f3.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, true, false,
                                    false, false,
                                    true, false, false, false, false);
                            cip3Repository.save(cip3);
                            j2++;
                        }
                        id = id + 120;
                        for (long i = 0; i < 180; i++) { // ополаскивание
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip3 cip3 = new Cip3(i + id, f3.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, false, true,
                                    false, false,
                                    true, false, false, false, false);
                            cip3Repository.save(cip3);
                            j2++;
                        }
                        id = id + 180;
                        break;

                    case (3): // стерилизация
                        j2 = 0;
                        for (long i = 0; i < 120; i++) { // ополаскивание
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip3 cip3 = new Cip3(i + id, f3.format(calendar.getTime()), route, true, true,
                                    1, 1, true,
                                    1, 1, 1,
                                    false, false, false,
                                    false, true,
                                    false, true, false, false, false);
                            cip3Repository.save(cip3);
                            j2++;
                        }
                        id = id + 120;
                        for (long i = 0; i < 160; i++) { // циркуляция и нагрев воды
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip3 cip3 = new Cip3(i + id, f3.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, false, false,
                                    true, false,
                                    true, false, false, false, false);
                            cip3Repository.save(cip3);
                            j2++;
                        }
                        id = id + 160;
                        for (long i = 0; i < 300; i++) { // стерилизация
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip3 cip3 = new Cip3(i + id, f3.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, false, false,
                                    true, false,
                                    true, false, false, false, false);
                            cip3Repository.save(cip3);
                            j2++;
                        }
                        id = id + 300;

                        id = id + 120;
                        for (long i = 0; i < 180; i++) { // охлаждение
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip3 cip3 = new Cip3(i + id, f3.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, false, true,
                                    false, false,
                                    true, false, false, false, false);
                            cip3Repository.save(cip3);
                            j2++;
                        }
                        id = id + 180;
                        id = id + 180;
                        break;
                }
                break;

            case (4): //cip4
                f2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                switch (program) {
                    case (1): // lye
                        j2 = 0;
                        for (long i = 0; i < 120; i++) { // ополаскивание
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip4 cip4 = new Cip4(i + id, f2.format(calendar.getTime()), route, true, true,
                                    1, 1, true,
                                    1, 1, 1,
                                    false, false, false,
                                    false, true,
                                    false, true, false, false);
                            cip4Repository.save(cip4);
                            j2++;
                        }
                        id = id + 120;
                        for (long i = 0; i < 60; i++) { // мойка наполнение щелочи 1мин
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip4 cip4 = new Cip4(i + id, f2.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, false, false,
                                    false, true,
                                    false, false, false, true);
                            cip4Repository.save(cip4);
                            j2++;
                        }
                        id = id + 60;
                        for (long i = 0; i < 240; i++) { // мойка циркуляция  щелочи
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip4 cip4 = new Cip4(i + id, f2.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    true, false, false,
                                    false, false,
                                    false, false, false, true);
                            cip4Repository.save(cip4);
                            j2++;
                        }
                        id = id + 240;
                        for (long i = 0; i < 120; i++) { // вытеснение щелочи
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip4 cip4 = new Cip4(i + id, f2.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    true, false, false,
                                    false, false,
                                    true, false, false, false);
                            cip4Repository.save(cip4);
                            j2++;
                        }
                        id = id + 120;
                        for (long i = 0; i < 180; i++) { // ополаскивание
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip4 cip4 = new Cip4(i + id, f2.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, false, true,
                                    false, false,
                                    true, false, false, false);
                            cip4Repository.save(cip4);
                            j2++;
                        }
                        id = id + 180;
                        break;

                    case (2): //acid
                        j2 = 0;
                        for (long i = 0; i < 120; i++) { // ополаскивание
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip4 cip4 = new Cip4(i + id, f2.format(calendar.getTime()), route, true, true,
                                    1, 1, true,
                                    1, 1, 1,
                                    false, false, false,
                                    false, true,
                                    false, true, false, false);
                            cip4Repository.save(cip4);
                            j2++;
                        }
                        id = id + 120;
                        for (long i = 0; i < 60; i++) { // мойка наполнение кислоты 1мин
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip4 cip4 = new Cip4(i + id, f2.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, false, false,
                                    false, true,
                                    false, false, true, false);
                            cip4Repository.save(cip4);
                            j2++;
                        }
                        id = id + 60;
                        for (long i = 0; i < 240; i++) { // мойка циркуляция  кислоты
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip4 cip4 = new Cip4(i + id, f2.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, true, false,
                                    false, false,
                                    false, false, true, false);
                            cip4Repository.save(cip4);
                            j2++;
                        }
                        id = id + 240;
                        for (long i = 0; i < 120; i++) { // вытеснение кислоты
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip4 cip4 = new Cip4(i + id, f2.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, true, false,
                                    false, false,
                                    true, false, false, false);
                            cip4Repository.save(cip4);
                            j2++;
                        }
                        id = id + 120;
                        for (long i = 0; i < 180; i++) { // ополаскивание
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip4 cip4 = new Cip4(i + id, f2.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, false, true,
                                    false, false,
                                    true, false, false, false);
                            cip4Repository.save(cip4);
                            j2++;
                        }
                        id = id + 180;
                        break;

                    case (3): // стерилизация
                        j2 = 0;
                        for (long i = 0; i < 120; i++) { // ополаскивание
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip4 cip4 = new Cip4(i + id, f2.format(calendar.getTime()), route, true, true,
                                    1, 1, true,
                                    1, 1, 1,
                                    false, false, false,
                                    false, true,
                                    false, true, false, false);
                            cip4Repository.save(cip4);
                            j2++;
                        }
                        id = id + 120;
                        for (long i = 0; i < 160; i++) { // циркуляция и нагрев воды
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip4 cip4 = new Cip4(i + id, f2.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, false, false,
                                    true, false,
                                    true, false, false, false);
                            cip4Repository.save(cip4);
                            j2++;
                        }
                        id = id + 160;
                        for (long i = 0; i < 300; i++) { // стерилизация
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip4 cip4 = new Cip4(i + id, f2.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, false, false,
                                    true, false,
                                    true, false, false, false);
                            cip4Repository.save(cip4);
                            j2++;
                        }
                        id = id + 300;

                        id = id + 120;
                        for (long i = 0; i < 180; i++) { // охлаждение
                            Calendar calendar = new GregorianCalendar(2021, Calendar.MARCH, day, hour, minute, second + j2);
                            Cip4 cip4 = new Cip4(i + id, f2.format(calendar.getTime()), route, true,
                                    true, 1, 1, true,
                                    1, 1, 1,
                                    false, false, true,
                                    false, false,
                                    true, false, false, false);
                            cip4Repository.save(cip4);
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
