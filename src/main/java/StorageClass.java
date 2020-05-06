import lombok.Getter;
import lombok.Setter;
import org.apache.http.HttpResponse;

import java.util.Map;

@Getter
@Setter
public class StorageClass {
    /**
     * Class used to store body responses
     */
    private int statusCode;
    private HttpResponse currentResponse;
    private RegisterData registerData;

}
