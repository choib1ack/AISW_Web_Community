package com.aisw.community.service;

import com.aisw.community.ifs.AuthService;
import com.aisw.community.model.LoginParam;
import com.aisw.community.model.entity.User;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.UserApiRequest;
import com.aisw.community.model.network.response.UserApiResponse;
import com.aisw.community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthLogicService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder encoder;

//    @Override
    public Header<UserApiResponse> signUpUser(Header<UserApiRequest> request){
        // 1. request data
        UserApiRequest userApiRequest = request.getData();

        // 2. user create
        User user = User.builder()
                .name(userApiRequest.getName())
                .email(userApiRequest.getEmail())
                .password(userApiRequest.getPassword())
                .phoneNumber(userApiRequest.getPhoneNumber())
                .grade(userApiRequest.getGrade())
                .studentId(userApiRequest.getStudentId())
                .level(userApiRequest.getLevel())
                .job(userApiRequest.getJob())
                .gender(userApiRequest.getGender())
                .university(userApiRequest.getUniversity())
                .collegeName(userApiRequest.getCollegeName())
                .departmentName(userApiRequest.getDepartmentName())
//                .role(userApiRequest.getRole())
                .build();

        System.out.println(user.getPassword());

        User newUser = userRepository.save(user);

        return Header.OK(response(newUser));
    }

//    @Override
    public Header<UserApiResponse> loginUser(Header<LoginParam> request) {
        String email = request.getData().getEmail();
        String password = request.getData().getPassword();
        password = encoder.encode(password);
        Optional<User> optional = userRepository.findByEmail(email);

        if(optional.isPresent()){
            String finalPassword = password;
            return optional.map(user -> loginCheck(user, finalPassword))
                    .map(Header::OK)
                    .orElseGet(() -> Header.ERROR("Wrong Password"));
        }
        else
            return Header.ERROR("Email Not Exists");
    }

    public boolean emailDoubleCheck(String email){
        Optional<User> optional = userRepository.findByEmail(email);

        if(optional.isPresent())
            return false;
        else
            return true;
    }

    public boolean sidDoubleCheck(Integer studentId){
        Optional<User> optional = userRepository.findByStudentId(studentId);

        if(optional.isPresent())
            return false;
        else
            return true;

    }
    public UserApiResponse loginCheck(User user, String password){
        String pw = user.getPassword();
        pw = encoder.encode(pw);
        if(pw.equals(password))
            return response(user);
        else
            return null;
    }

    private UserApiResponse response(User user){
        // user -> userApiResponse return
        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .grade(user.getGrade())
                .studentId(user.getStudentId())
                .createdAt(user.getCreatedAt())
                .createdBy(user.getCreatedBy())
                .updatedAt(user.getUpdatedAt())
                .updatedBy(user.getUpdatedBy())
                .level(user.getLevel())
                .job(user.getJob())
                .gender(user.getGender())
                .university(user.getUniversity())
                .collegeName(user.getCollegeName())
                .departmentName(user.getDepartmentName())
//                .role(user.getRole())
                .build();

        // Header + data
        return userApiResponse;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optional = userRepository.findByEmail(email);


        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("STUDENT"));

//        return new User(user);

        return null;
    }
}
