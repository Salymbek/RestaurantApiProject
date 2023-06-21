package peaksoft.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.config.JwtService;
import peaksoft.dto.request.ApplicationRequest;
import peaksoft.dto.request.RegisterRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.*;
import peaksoft.dto.response.UserResponse;
import peaksoft.enums.Role;
import peaksoft.exception.AlreadyExistException;
import peaksoft.exception.BadCredentialException;
import peaksoft.exception.NotFoundException;
import peaksoft.model.User;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.UserService;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder, AuthenticationManager authenticationManager, JwtService jwtService, RestaurantRepository restaurantRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.restaurantRepository = restaurantRepository;
    }


    @PostConstruct
    public void init() {
        User user = new User();
        user.setFirstName("Admin");
        user.setLastName("admin");
        user.setEmail("admin@gmail.com");
        user.setPassword(encoder.encode("admin"));
        user.setRole(Role.ADMIN);
        user.setAccept(true);
        if (!userRepository.existsByEmail("admin@gmail.com")) {
            userRepository.save(user);
        }
    }

    @Override
    public TokenResponse authenticate(UserRequest userRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.email(),
                        userRequest.password()
                )
        );

        User user = userRepository.findByEmail(userRequest.email())
                .orElseThrow(() -> new NotFoundException(String.format
                        ("User with email: %s doesn't exists", userRequest.email())));
        String token = jwtService.generateToken(user);

        return TokenResponse.builder()
                .token(token)
                .email(user.getEmail())
                .build();
    }

    @Override
    public SimpleResponse saveUser(RegisterRequest request) {
        if (userRepository.getAllUsers().size() > 15) {
            throw new AlreadyExistException("There are currently no open vacancies");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new AlreadyExistException(String.format(
                    "User with email: %s is exists", request.email()
            ));
        }
        if (request.role().equals(Role.ADMIN)){
            throw new BadCredentialException(String.format(
                    "Unable to save - %s role",request.role()));
        }
        if (userRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new AlreadyExistException(String.format(
                    "User with phone number: %s is exists", request.phoneNumber()
            ));
        }
        if (request.experience()<0){
            throw new BadCredentialException("Experience is not negative!");
        }


        checkingForAge(request);
        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        long l = ChronoUnit.YEARS.between(request.dateOfBirth(), LocalDate.now());
        user.setDateOfBirth(l);
        user.setEmail(request.email());
        user.setPassword(encoder.encode(request.password()));
        user.setPhoneNumber(request.phoneNumber());
        user.setRole(request.role());
        user.setExperience(request.experience());
        user.setAccept(false);


        userRepository.save(user);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Your application has been successfully sent!")
                .build();
    }


    @Override
    public List<UserResponse> findAll() {
        return userRepository.getAllUsers();
    }


    @Override
    public SimpleResponse update(Long id, RegisterRequest request) {
        for (User user : userRepository.findAll()) {
            if (!user.getId().equals(id) && user.getEmail().equals(request.email())) {
                throw new NotFoundException(String.format(
                        "User with login: %s is exists", request.email()
                ));
            }
        }
        checkingForAge(request);
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(
                "Waiter with id: %d doesn't exist", id)));
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        long u = ChronoUnit.YEARS.between(request.dateOfBirth(), LocalDate.now());
        user.setDateOfBirth(u);
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setPhoneNumber(request.phoneNumber());
        user.setRole(request.role());
        user.setExperience(request.experience());

        userRepository.save(user);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("User with id - %s is updated!",id))
                .build();
    }

    private void checkingForAge(RegisterRequest registerRequest) {
        LocalDate now = LocalDate.now();
        int age = Period.between(registerRequest.dateOfBirth(), now).getYears();
        if (registerRequest.role() == Role.CHEF) {
            if (age < 25 || age > 45) {
                throw new BadCredentialException("Chef must be between 25 and 45 years of age");
            }
            if (registerRequest.experience() < 2) {
                throw new BadCredentialException("Chef experience must be more than 2 years");
            }
        } else if (registerRequest.role() == Role.WAITER) {
            if (age < 18 || age > 30) {
                throw new BadCredentialException("Waiter must be between 18 and 30 years of age");
            }
            if (registerRequest.experience() < 1) {
                throw new BadCredentialException("Waiter experience must be more than 1 year");
            }
        }
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            return SimpleResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message(String.format("User with id - %s is not found!", id))
                    .build();
        }
        userRepository.deleteById(id);
        return new SimpleResponse(
                HttpStatus.OK,
                String.format("User with id - %s is deleted!", id)
        );
    }

    @Override
    public List<UserResponse> findAllUsers(Long restaurantId) {
        return userRepository.getAllUsers();
    }


    @Override
    public SimpleResponse applicationAccept(Long restId, ApplicationRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() ->{
                    throw new NotFoundException(String.format("User with id - %s is not found!",
                            request.userId()));
                });
        if (request.accept()){
            user.setAccept(true);
            user.setRestaurant(restaurantRepository.findById(restId).orElseThrow(()->{
                throw new NotFoundException("Restaurant not found!");
            }));
            return new SimpleResponse(
                    HttpStatus.ACCEPTED,
                    String.format("User - %s is accepted!", user.getEmail())
            );
        } else {
            userRepository.delete(user);
            return new SimpleResponse(
                    HttpStatus.NOT_ACCEPTABLE,
                    String.format("User - %s is rejected!", user.getEmail())
            );
        }
    }

    @Override
    public List<UserResponse> getAllApplications() {
        return userRepository.getAllApplication();
    }




}