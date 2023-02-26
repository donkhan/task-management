package runner;
import task.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Main {

    public static GregorianCalendar getCalendar(int date, int month,int year){
        GregorianCalendar s = new GregorianCalendar();
        s.set(Calendar.MONTH,month-1); s.set(Calendar.DATE,date);s.set(Calendar.YEAR,year);
        s.set(Calendar.HOUR_OF_DAY,1); s.set(Calendar.MINUTE,0); s.set(Calendar.SECOND,0);
        s.set(Calendar.MILLISECOND,0);
        return s;
    }

    private static GregorianCalendar getDate(String s){
        StringTokenizer tokenizer = new StringTokenizer(s,"-");
        return getCalendar(Integer.parseInt(tokenizer.nextToken()),Integer.parseInt(tokenizer.nextToken()),Integer.parseInt(tokenizer.nextToken()));
    }

    public static void addTaskToDateMap(GregorianCalendar st,Map<Date,List<Task>> conflictMap, Task task){
        List<Task> l = conflictMap.get(st.getTime());
        if(l == null){
            l = new ArrayList<Task>();
            conflictMap.put(st.getTime(),l);
        }
        l.add(task);
    }

    private static void readFromFile(List<Task> tasks) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new java.io.File("input.txt")));
            String s = "";
            while((s = reader.readLine()) != null){
                StringTokenizer tokenizer = new StringTokenizer(s,",");
                String taskName = "";
                taskName = tokenizer.nextToken();
                String startDate = tokenizer.nextToken();
                String endDate = tokenizer.nextToken();
                tasks.add(new Task(taskName,getDate(startDate), getDate(endDate)));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String args[]){
        List<Task> tasks = new ArrayList<>();
        /*
        tasks.add(new Task("P1",getCalendar(2,1,2023),getCalendar(2,28,2023)));
        tasks.add(new Task("P2",getCalendar(2,15,2023),getCalendar(3,15,2023)));
        tasks.add(new Task("P3",getCalendar(2,13,2023),getCalendar(2,25,2023)));
        */
        readFromFile(tasks);
        Map<Date,List<Task>> conflictMap = new HashMap<>();
        for(Task task : tasks){
            GregorianCalendar st = (GregorianCalendar) task.startDate.clone();
            GregorianCalendar en = (GregorianCalendar) task.endDate.clone();
            while(st.getTime().before(en.getTime())){
                addTaskToDateMap(st,conflictMap,task);
                st.add(Calendar.DATE,1);
            }
            addTaskToDateMap(st,conflictMap,task);
        }

        Map<String,Task> map = new HashMap<String, Task>();
        Iterator<Date> iterator = conflictMap.keySet().iterator();;
        while(iterator.hasNext()){
            Date key = iterator.next();
            List<Task> list = conflictMap.get(key);
            if(list.size() == 1) continue;;
            String mapKey = getMapKey(list);
            if(!map.containsKey(mapKey)){
                GregorianCalendar g1 = new GregorianCalendar();
                g1.setTime(key);
                GregorianCalendar g2 = new GregorianCalendar();
                g2.setTime(key);
                Task t = new Task(mapKey, g1,g2);
                map.put(mapKey, t);
            }else{
                Task t = map.get(mapKey);
                if(t.startDate.getTime().after(key)){
                    t.startDate.setTime(key);
                }
                if(t.endDate.getTime().before(key)){
                    t.endDate.setTime(key);
                }
            }
        }

        Iterator<Task> i = map.values().iterator();
        while(i.hasNext()){
            System.out.println(i.next());
        }
    }

    private static String getMapKey(List<Task> list){
        String s = "";
        for(Task t : list){
            s = s + t.name + "-";
        }
        s = s.substring(0,s.length()-1);
        return s;
    }
}
