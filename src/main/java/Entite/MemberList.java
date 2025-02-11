package Entite;

import java.util.List;

public class MemberList {

    private List<Members> members ;
    public MemberList(List<Members> members) {
       this.members = members;
    }
    public List<Members> getCoaches() {
        return members.stream().filter(
                        m -> m.getRole().equalsIgnoreCase("coach")
                )
                .toList();
    }
    public String getNameById(int id) {
        return members.stream()
                .filter(m -> m.getRole().equalsIgnoreCase("coach") && id == m.getMemberId())
                .map(m -> m.getFirstName() + " " + m.getLastName())
                .findFirst()
                .orElse("Coach non trouvé");
    }

}
