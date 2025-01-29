package Entite;

import java.util.ArrayList;
import java.util.List;

public class MemberList {

    private List<Member> members ;
    public MemberList(List<Member> members) {
       this.members = members;
    }
    public List<Member> getCoaches() {
        return members.stream().filter(
                        m -> m.getRole().equalsIgnoreCase("coach")
                )
                .toList();
    }
    public String getNameById(int id) {
        return members.stream()
                .filter(m -> m.getRole().equalsIgnoreCase("coach") && m.getMemberId() == id)
                .map(m -> m.getFirstName() + " " + m.getLastName())
                .findFirst()
                .orElse("Coach non trouvé");
    }

}
