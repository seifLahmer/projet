package Entite;

import java.util.List;

public class ActivityList {
    private List<Activity> activityList;
    public ActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }
    public List<Activity> getActivityList() {
        return activityList.stream().toList();
    }
    public String getNameById(int id) {
        return activityList.stream()
                .map(a->a.getActivityName())
                .findFirst()
                .orElse("activite non trouvé");
    }
}
