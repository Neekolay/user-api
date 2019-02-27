package org.neekolay.userapi.repository;

import org.neekolay.userapi.model.User;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

/**
 * Created by user on 27/02/2019.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    User findByEmail(String email);

}
