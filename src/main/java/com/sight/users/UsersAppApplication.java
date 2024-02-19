package com.sight.users;
import com.github.javafaker.Faker;
import com.sight.users.entities.User;
import com.sight.users.entities.Profile;
import com.sight.users.repositories.UserRepository;
import com.sight.users.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class UsersAppApplication implements ApplicationRunner {

    @Autowired
    private Faker faker;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    public static void main(String[] args) {
        SpringApplication.run(UsersAppApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (int i = 0; i < 100000; i++) {
            User user = new User();
            user.setUsername(faker.name().username());
            user.setPassword(faker.dragonBall().character());

            Profile profile = new Profile();
            profile.setFirstname(faker.name().firstName());
            profile.setLastName(faker.name().lastName());
            profile.setBirthdate(faker.date().birthday());

            userRepository.save(user);
            profileRepository.save(profile);
        }
    }
}
