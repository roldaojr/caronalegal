package br.edu.ifrn.tads.caronas.data;

public class User extends Entity {
    private String name;
    private String phone;
    private String email;
    private String password;

    public User() {
    }

    public User(String id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValidPassword(String password) {
        if(this.password != null) {
            return this.password.equals(password);
        }
        return false;
    }
}
