package by.bsuir.backend.repository;

import by.bsuir.backend.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    // Метод для поиска по username
    Optional<Account> findByUsername(String username);

    // Метод для поиска по email
    Optional<Account> findByEmail(String email);

    // Метод для поиска по username и email
    Optional<Account> findByUsernameOrEmail(String username, String email);
}
