package core;

import task.Task;

import java.text.SimpleDateFormat;
import java.util.*;

public class ConflictResolver {

    public List<String> process(List<Task> tasks) {

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
        Date skey = begin.getTime();
        String os = getMapKey(conflictMap.get(skey));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        List<String> orderedList = new ArrayList<>();
        String ns = "";

        while (begin.getTime().before(end.getTime())) {
            Date key = begin.getTime();
            List<Task> list = conflictMap.get(key);
            ns = getMapKey(list);
            if(!ns.equals(os)){
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(key);
                gc.add(Calendar.DATE,-1);
                orderedList.add(sdf.format(skey) + "  " + sdf.format(gc.getTime())  + "   " + os);
                os = ns;
                skey = key;
            }
            begin.add(Calendar.DATE,1);
        }
        orderedList.add(sdf.format(skey) + "  " + sdf.format(end.getTime())  + "   " + os);
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
