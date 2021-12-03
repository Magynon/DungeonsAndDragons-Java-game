package setup;

public class Credentials {
    final private String email;
    final private String password;

    public Credentials(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "email: '" + email +
                ", password: " + password;
    }
}
