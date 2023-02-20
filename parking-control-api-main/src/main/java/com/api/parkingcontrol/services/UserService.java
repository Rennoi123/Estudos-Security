package com.api.parkingcontrol.services;

import com.api.parkingcontrol.Responses.UserResponse;
import com.api.parkingcontrol.dtos.UserDto;
import com.api.parkingcontrol.models.UserModel;
import com.api.parkingcontrol.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ResponseEntity<UserResponse> createUser(UserDto userDto) {

        UserModel userModel = new UserModel();

        userModel.setName(userDto.getName());
        userModel.setPhone(userDto.getPhone());
        userModel.setEmail(userDto.getEmail());
        userModel.setUsername(userDto.getUsername());
        userModel.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userModel.setActive(true);

        userRepository.save(userModel);


        return ResponseEntity.ok(new UserResponse(userModel));
    }


    public ResponseEntity<UserResponse> getUserById(UUID id) throws Exception {
        try {
            UserModel userModel = userRepository.findById(id).get();

            UserResponse response = new UserResponse(userModel);

            return ResponseEntity.ok(response);
        }catch (Exception e){
            throw new Exception("Erro, favor informar o id de um usuario valido para consulta "+e.getMessage());
        }
    }
    public ResponseEntity<List<UserResponse>> getAllUser()throws Exception {
        try {
            List<UserModel> userModel = userRepository.findAll()
                    .stream().filter(UserModel::isActive).toList();

            List<UserResponse> responses = new ArrayList<>();
            userModel.forEach(x -> {
                UserResponse response = new UserResponse(x);

                responses.add(response);
            });

            return ResponseEntity.ok(responses);
        }catch (Exception e){
            throw new Exception("Erro, infelizmente não conseguimos encontrar todos os usuarios cadastrado"+e.getMessage());
        }
    }

    @Transactional
    public ResponseEntity<UserModel> updateUser(UserDto userDto){
        UserModel userModel = userRepository.getById(userDto.getUserId());

        if (userModel.getUserId()==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Erro, favor informar usuario valido para atualizar");
        if (userDto.getUsername()!=null)userModel.setUsername(userDto.getUsername());
        if (userDto.getEmail()!=null)userModel.setEmail(userDto.getEmail());
        if (userDto.getPhone()!=null)userModel.setLocked(userDto.isLocked());

        userRepository.save(userModel);

        return ResponseEntity.ok(userModel);
    }

    @Transactional
    public ResponseEntity<UserResponse> deleteUserById(UUID id){
         UserModel userModel = userRepository.findById(id)
                 .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Erro ao buscar usuario para deletar"));
         userModel.setActive(false);
         return ResponseEntity.ok().build();
    }
}
