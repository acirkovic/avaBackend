import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RegisterData {
    /**
     * Register request body
     */

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String country;

    RegisterData() {
        username = UtilsClass.randomizeUsername();
        email = UtilsClass.randomizeEmail();
        password = UtilsClass.randomizePassword(10);
        firstName = "Andrija";
        lastName = "Cirkovic";
    }

    RegisterData(String country) {
        this();
        this.country = country;
    }

    RegisterData(String country, String password) {
        this(country);
        this.password = password;
    }
}
