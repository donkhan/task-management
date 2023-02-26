package core;

import task.Task;

import java.util.*;

public class ConflictResolver {

    public List<Task> process(List<Task> tasks) {

        Map<Date, List<Task>> conflictMap = new HashMap<>();
        GregorianCalendar begin = new GregorianCalendar();
        GregorianCalendar end = new GregorianCalendar();

        for (Task task : tasks) {
            GregorianCalendar st = (GregorianCalendar) task.startDate.clone();
            GregorianCalendar en = (GregorianCalendar) task.endDate.clone();
            if (begin.getTime().after(st.getTime())) {
                begin.setTime(st.getTime());
            }
            if (end.getTime().before(en.getTime())) {
                end.setTime(en.getTime());
            }
            while (st.getTime().before(en.getTime())) {
                addTaskToDateMap(st, conflictMap, task);
                st.add(Calendar.DATE, 1);
            }

            addTaskToDateMap(st, conflictMap, task);
        }
        Map<String, Task> map = new HashMap<String, Task>();
        while (begin.getTime().before(end.getTime())) {
            Date key = begin.getTime();
            List<Task> list = conflictMap.get(key);
            String mapKey = getMapKey(list);
            if (!map.containsKey(mapKey)) {
                GregorianCalendar g1 = new GregorianCalendar();
                g1.setTime(key);
                GregorianCalendar g2 = new GregorianCalendar();
                g2.setTime(key);
                Task t = new Task(mapKey, g1, g2);
                map.put(mapKey, t);
            } else {
                Task t = map.get(mapKey);
                if (t.startDate.getTime().after(key)) {
                    t.startDate.setTime(key);
                }
                if (t.endDate.getTime().before(key)) {
                    t.endDate.setTime(key);
                }
            }
            begin.add(Calendar.DATE, 1);
        }

        List<Task> orderedList = new ArrayList<>();
        Iterator<Task> i = map.values().iterator();
        while (i.hasNext()) {
            orderedList.add(i.next());
        }
        return orderedList;
    }

    public static void addTaskToDateMap(GregorianCalendar st, Map<Date, List<Task>> conflictMap, Task task) {
        List<Task> l = conflictMap.get(st.getTime());
        if (l == null) {
            l = new ArrayList<Task>();
            conflictMap.put(st.getTime(), l);
        }
        l.add(task);
    }

    private String getMapKey(List<Task> list) {
        String s = "";
        for (Task t : list) {
            s = s + t.name + "-";
        }
        s = s.substring(0, s.length() - 1);
        return s;
    }

}
