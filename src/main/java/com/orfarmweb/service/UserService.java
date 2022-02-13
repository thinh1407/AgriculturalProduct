package com.orfarmweb.service;

import com.orfarmweb.entity.User;
import com.orfarmweb.modelutil.PasswordDTO;

public interface UserService {
    boolean registerUser(User user);
    boolean checkExist(String email);
    User getCurrentUser();
    void updateUser(int id, User userRequest);
    boolean updatePassword(PasswordDTO passwordDTO);
}
