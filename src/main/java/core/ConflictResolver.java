package core;

import task.Output;
import task.Task;
import java.util.*;

public class ConflictResolver {

    private String application;
    private String env;

    public ConflictResolver(String env,String application) {
        this.application = application;
        this.env = env;
    }

    public ConflictResolver(){
        this.env = "";
        this.application  = "";
    }

    public List<Output> process(List<Task> tasks) {
        String sep = ",";
        Map<Date, List<Task>> conflictMap = new HashMap<>();
        GregorianCalendar begin = new GregorianCalendar();
        GregorianCalendar end = new GregorianCalendar();
        begin.set(Calendar.HOUR_OF_DAY,1);
        begin.set(Calendar.MINUTE,0);
        begin.set(Calendar.SECOND,0);
        begin.set(Calendar.MILLISECOND,0);
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
        while(os.equals("")){
            begin.add(Calendar.DATE,1);
            skey = begin.getTime();
            os = getMapKey(conflictMap.get(skey));
        }
        List<Output> orderedList = new ArrayList<>();
        String ns = "";

        while (begin.getTime().before(end.getTime())) {
            Date key = begin.getTime();
            List<Task> list = conflictMap.get(key);
            ns = getMapKey(list);
            if(!ns.equals(os)){
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(key);
                gc.add(Calendar.DATE,-1);
                if(this.moreTasks(os))
                    orderedList.add(new Output(application,env,skey,gc,os));
                os = ns;
                skey = key;
            }
            begin.add(Calendar.DATE,1);
        }
        if(this.moreTasks(os)) {
            orderedList.add(new Output(application, env, skey, end, os));
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
        if(list == null) return s;
        for (Task t : list) {
            s = s + t.name + " | ";
        }
        s = s.substring(0, s.length() - 2);
        return s;
    }

    public boolean moreTasks(String tasks) {
        return tasks.split("\\|").length > 1;
    }
}
