package hdang09.entities;

public class Account {
    private int id;
    private int firstName;
    private int lastName;
    private String email;
    private int role;
    private int status;
    private String avatar;

    public Account() {
    }

    public Account(int id, int firstName, int lastName, String email, int role, int status, String avatar) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.status = status;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFirstName() {
        return firstName;
    }

    public void setFirstName(int firstName) {
        this.firstName = firstName;
    }

    public int getLastName() {
        return lastName;
    }

    public void setLastName(int lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", status=" + status +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
