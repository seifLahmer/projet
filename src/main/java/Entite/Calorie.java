// src/main/java/Entite/Calorie.java
package Entite;

public class Calorie {
    private int id;
    private int memberId;
    private String gender;
    private double height;
    private double weight;
    private int age;
    private String activityLevel;
    private String goal;
    private double calorieNeeds;
    private String createdAt;
    private String updatedAt;

    // Constructor for creating a new entry without an ID
    public Calorie(int memberId, String gender, double height, double weight, int age, String activityLevel, String goal) {
        this.memberId = memberId;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.activityLevel = activityLevel;
        this.goal = goal;
    }

    // Constructor for retrieving an entry with an ID
    public Calorie(int id, int memberId, String gender, double height, double weight, int age, String activityLevel, String goal, double calorieNeeds, String createdAt, String updatedAt) {
        this.id = id;
        this.memberId = memberId;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.activityLevel = activityLevel;
        this.goal = goal;
        this.calorieNeeds = calorieNeeds;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getActivityLevel() { return activityLevel; }
    public void setActivityLevel(String activityLevel) { this.activityLevel = activityLevel; }
    public String getGoal() { return goal; }
    public void setGoal(String goal) { this.goal = goal; }
    public double getCalorieNeeds() { return calorieNeeds; }
    public void setCalorieNeeds(double calorieNeeds) { this.calorieNeeds = calorieNeeds; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Calorie{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", gender='" + gender + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", age=" + age +
                ", activityLevel='" + activityLevel + '\'' +
                ", goal='" + goal + '\'' +
                ", calorieNeeds=" + calorieNeeds +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}