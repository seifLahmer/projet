package Entite;

import java.util.List;

public class ActivityList {
    private List<Activity> activityList;
    private int id;

    public ActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }
    public List<Activity> getActivityList() {
        return activityList.stream().toList();
    }
    public String getNameById(int id ,int member_id) {

        return activityList.stream()
                .filter(m ->  m.getActivityId() == id && m.getMemberId() == member_id )
                .map(Activity::getActivityName)
                .findFirst()
                .orElse("activite non trouvé");
    }
}
