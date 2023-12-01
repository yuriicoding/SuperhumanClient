package ca.cmpt213;

public class Superhuman {
    private int id;
    private String name;
    private double weight;
    private double height;
    private String pictureURL;
    private String category;
    private int overallAbility;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getOverallAbility() {
        return overallAbility;
    }

    public void setOverallAbility(int overallAbility) {
        this.overallAbility = overallAbility;
    }

    public Superhuman() {
    }

    public Superhuman(String name, double weight, double height, String pictureURL, String category, int overallAbility) {
        this.id = -1;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.pictureURL = pictureURL;
        this.category = category;
        this.overallAbility = overallAbility;
    }

    public Superhuman(String name, String weight, String height, String pictureURL, String category, String overallAbility) {
        this.id = -1;
        this.name = name;
        this.weight = Double.parseDouble(weight);
        this.height = Double.parseDouble(height);
        this.pictureURL = pictureURL;
        this.category = category;
        this.overallAbility = Integer.parseInt(overallAbility);
    }

    @Override
    public String toString() {
        return "Superhuman{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                ", pictureURL='" + pictureURL + '\'' +
                ", category='" + category + '\'' +
                ", overallAbility=" + overallAbility +
                '}';
    }
}
