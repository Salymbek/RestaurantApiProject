package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.UserResponse;
import peaksoft.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    @Query("select new peaksoft.dto.response.UserResponse" +
            "(u.id, concat(u.firstName, ' ', u.lastName), u.dateOfBirth, u.email," +
            " u.phoneNumber, u.role, u.experience)" +
            " from User u where u.accept = true ")
    List<UserResponse> getAllUsers();

    @Query("select new peaksoft.dto.response.UserResponse(u.id, concat(u.firstName,' ', u.lastName), u.dateOfBirth, u.email,u.phoneNumber, u.role, u.experience)from User u where u.accept = false ")
    List<UserResponse> getAllApplication();

    @Override
    Page<User> findAll(Pageable pageable);

}