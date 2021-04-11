package ph.apper.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

@SpringBootApplication

public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @RestController
    @RequestMapping("account")
    public static class AccountController {
        private final RestTemplate restTemplate;

        @Autowired
        private Environment env;

        public AccountController(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
        }

        public ResponseEntity register(@RequestBody CreateAccountRequest request){

            Activity activity = new Activity();
            activity.setAction("REGISTRATION");
            activity.setIdentifier("email= "+request.getEmail());
            ResponseEntity<Object> response =
                    restTemplate.postForEntity(env.getProperty("url"), activity, Object.class);

            if (response.getStatusCode().is2xxSuccessful()){
                System.out.println("success");
            }else{
                System.out.println("err: " + response.getStatusCode());
            }

            return ResponseEntity.ok().build();
        }

    }
    @Data
    public static class Activity{
        private String action;
        private String identifier;
    }
    @Data
    public static class CreateAccountRequest{
        private String firstName;
        private String LastName;
        private String email;
        private String password;
    }

}

