package com.codecampushubt.NCKH2024TQQD.service.UserServices;

import java.security.PublicKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.cloudinary.Cloudinary;
import com.codecampushubt.NCKH2024TQQD.dao.RoleRepository;
import com.codecampushubt.NCKH2024TQQD.dao.UserRoleRepository;
import com.codecampushubt.NCKH2024TQQD.dto.UserDTO.UserCreateDTO;
import com.codecampushubt.NCKH2024TQQD.dto.UserDTO.UserShowDTO;
import com.codecampushubt.NCKH2024TQQD.dto.UserDTO.UserUpdateDTO;
import com.codecampushubt.NCKH2024TQQD.entity.Role;
import com.codecampushubt.NCKH2024TQQD.entity.UserRole;
import com.codecampushubt.NCKH2024TQQD.entity.UserRoleId;
import com.codecampushubt.NCKH2024TQQD.service.Cloudinary.CloudinaryService;
import com.codecampushubt.NCKH2024TQQD.util.BCryptPasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Service;

import com.codecampushubt.NCKH2024TQQD.dao.UserRepository;
import com.codecampushubt.NCKH2024TQQD.dto.LoginDTO.LoginBasicDTO;
import com.codecampushubt.NCKH2024TQQD.dto.UserDTO.UserBasicInfoDTO;
import com.codecampushubt.NCKH2024TQQD.entity.User;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           UserRoleRepository userRoleRepository,
                           CloudinaryService cloudinaryService
    ) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public ArrayList<UserBasicInfoDTO> getUserBasicInfo(Long userID) {
        return userRepository.getUserBasicInfo(userID);
    }

    @Override
    public ArrayList<User> findAll() {
        return (ArrayList<User>) userRepository.findAll();
    }

    @Override
    public LoginBasicDTO getLoginBasicDTO(String userName) {
        return (LoginBasicDTO) userRepository.getLoginBasicDTO(userName);
    }

    //show user
    @Override
    public List<UserShowDTO> getAllUsers() {
        return userRepository.findAll().stream().map(user -> {
                    List<String> rolename = user.getUserRoles().stream()
                            .map(userRole -> userRole.getRole().getRoleName())
                            .collect(Collectors.toList());
                    return new UserShowDTO(
                            user.getUserId(),
                            user.getuserName(),
                            user.getEmail(),
                            rolename
                    );

                })
                .collect(Collectors.toList());
    }

    //    end show user
//    create user
    @Override
    public User addUser(UserCreateDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UsernameNotFoundException("Email đã tồn tại");
        }
        if (userRepository.findByUserName(dto.getUserName()).isPresent()) {
            throw new RuntimeException("Tên người dùng đã tồn tại");
        }


        // Khởi tạo user
        User user = new User();
        user.setuserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setFullName(dto.getFullName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setAddress(dto.getAddress());
        user.setStatus("ACTIVE");
        user.setAccountStatus("ACTIVE");
        user.setProvider("LOCAL");
        user.setEmailVerified(false);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // Lưu user trước để có ID
        User savedUser = userRepository.save(user);

        // Tìm Role
        Role role = roleRepository.findByRoleName(dto.getRoleName())
                .orElseThrow(() -> new RuntimeException("Role không tồn tại: " + dto.getRoleName()));

        // Tạo UserRoleId và UserRole
        UserRoleId userRoleId = new UserRoleId(savedUser.getUserId(), role.getRoleID());

        UserRole userRole = new UserRole();
        userRole.setId(userRoleId);
        userRole.setUser(savedUser);
        userRole.setRole(role);

        userRoleRepository.save(userRole);

        return savedUser;
    }

//end create user
    public String getFullName(String userName) {
        return userRepository.getFullName(userName);
    }
    //    update user ------------------------------------------------------------------------------------
  @Override
  public UserUpdateDTO getUserUpdateDTOById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Khong Tim Thay User"));
        List<String> roles = user.getUserRoles().stream()
                .map(userRole -> userRole.getRole().getRoleName())
                .collect(Collectors.toList());
        return new UserUpdateDTO(
                user.getuserName(),             // userName
                user.getEmail(),                // email
                "",                             // password (để trống khi hiển thị form)
                user.getFullName(),             // fullName
                user.getDateOfBirth(),          // dateOfBirth
                user.getPhoneNumber(),          // phoneNumber
                user.getAddress(),              // address
                user.getImage(),                // image (lưu URL ảnh)
                roles


        );
  }





//    end update user ---------------------------------------------------------------------------------------

}
