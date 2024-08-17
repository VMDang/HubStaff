package model.employee;

public class Role {
    private int id;
    private String name;

    public Role() {
    }

    public Role(int id, String name) {
    	this.id = id;
    	this.name = name;
    }

    public int getId() {
    	return id;
    }

    public String getName() {
    	return name;
    }

    public void setId(int id) {
    	this.id = id;
    }

    public void setName(String name) {
    	this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static final Role Worker = new Role(1, "Worker");
    public static final Role Officer = new Role(2, "Officer");
    public static final Role WorkerUnitManager = new Role(3, "WorkerUnitManager");
    public static final Role OfficerUnitManager = new Role(4, "OfficerUnitManager");
    public static final Role HRManager = new Role(5, "HRManager");
}
