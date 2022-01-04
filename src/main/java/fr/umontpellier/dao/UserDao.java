package fr.umontpellier.dao;

import org.springframework.stereotype.Repository;
import packageTestHibernate.User;
@Repository

public interface UserDao {
    User findByUserName(String username);
    User findByIdUser(String idUser);
}
