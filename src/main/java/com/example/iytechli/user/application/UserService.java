package com.example.iytechli.user.application;

import com.example.iytechli.common.domain.http.ApiResponse;
import com.example.iytechli.user.domain.entity.ProfileStatus;
import com.example.iytechli.user.domain.entity.UniversityRole;
import com.example.iytechli.user.domain.entity.User;
import com.example.iytechli.user.domain.http.GetUserDetailResponse;
import com.example.iytechli.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> findUserById(String userId) {
        return userRepository.findById(userId);
    }



    public ResponseEntity<ApiResponse<GetUserDetailResponse>> getUserDetail(String userId,String hostUserId) throws Exception {

        Optional<User> optionalUser = checkUser(userId);
        User user = optionalUser.get();
        String universityRole = "Misafir";
        if(user.getUserSettings().getUniversityRole().equals(UniversityRole.STUDENT)) {
            universityRole = "Öğrenci";
        }else if(user.getUserSettings().getUniversityRole().equals(UniversityRole.EMPLOYEE)) {
            universityRole = "İyte Görevli";
        }
        String telephone = "*******";
        if(user.getId().equals(hostUserId)){
            telephone = user.getPhoneNumber();

        }else if(user.getUserSettings().getProfileStatus().equals(ProfileStatus.PUBLIC)) {
            telephone = user.getPhoneNumber();
        }

        GetUserDetailResponse getUserDetailResponse = GetUserDetailResponse.builder()
            .name(user.getName())
            .surname(user.getSurname())
            .profileStatus(
                    user.getUserSettings().getProfileStatus().equals(ProfileStatus.PRIVATE)
                            ? "Gizli"
                            : "Açık")
            .universityRole(universityRole)
            .telephone(telephone)
            .build();

        return new ResponseEntity<>(new ApiResponse<>(getUserDetailResponse,"User is returned successfully",200,true,new Date()), HttpStatus.OK);

    }
    private Optional<User> checkUser(String userId) {
        Optional<User> optionalUser = findUserById(userId);
        if(optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("There is no such a userId " + userId);
        }
        return optionalUser;
    }
}
