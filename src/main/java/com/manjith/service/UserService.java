package com.manjith.service;

import com.manjith.entity.User;

public interface UserService {

     User findUserByJwtToken(String jwt) throws Exception;
     User findUserByEmail(String email) throws Exception;

}
