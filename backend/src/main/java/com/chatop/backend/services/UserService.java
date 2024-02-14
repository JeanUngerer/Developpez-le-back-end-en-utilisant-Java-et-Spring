package com.chatop.backend.services;

import com.chatop.backend.dtos.RegistrationDTO;
import com.chatop.backend.dtos.UserDTO;
import com.chatop.backend.entities.UserEntity;
import com.chatop.backend.exception.ExceptionHandler;
import com.chatop.backend.helpers.CycleAvoidingMappingContext;
import com.chatop.backend.mappers.UserMapper;
import com.chatop.backend.models.User;
import com.chatop.backend.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
@Transactional
public class UserService implements UserDetailsService {

	UserRepository userRepository;

	UserMapper userMapper;

	public List<User> findAllUser() {
		try {
			log.info("findAllUser");
			List<User> userList = new ArrayList<User>();
			userRepository.findAll().forEach(ct -> userList.add(userMapper.entityToModel(ct)));
			return userList;
		} catch (Exception e) {
			log.error("We could not find all user: " + e.getMessage());
			throw new ExceptionHandler("We could not find your users");
		}
	}

	public User findUserById(Long id) {
		try {
			log.info("findUserById - id: " + id.toString());
			User user = userMapper.entityToModel(userRepository.findById(id).orElseThrow(()
				-> new ExceptionHandler("We didn't find your user")));
			return user;
		} catch (Exception e) {
			log.error("We could not find all user: " + e.getMessage());
			throw new ExceptionHandler("We could not find your user");
		}
	}

	public User createUser(UserDTO dto) {
		try {
			log.info("createUser");
			User user = userMapper.dtoToModel(dto);
			userRepository.save(userMapper.modelToEntity(user));
			return user;
		} catch (Exception e) {
			log.error("Couldn't user user: " + e.getMessage());
			throw new ExceptionHandler("We could not create your user");
		}
	}

	public User updateUser(UserDTO dto) {
		try {
			log.info("updateUser - id: " + dto.getId().toString());
			User user = userMapper.entityToModel(userRepository.findById(dto.getId()).orElseThrow(()
				-> new ExceptionHandler("We could not find your user")));
			userMapper.updateFromDto(dto, user, new CycleAvoidingMappingContext());
			userRepository.save(userMapper.modelToEntity(user));
			return user;
		} catch (Exception e) {
			log.error("Couldn't update user: " + e.getMessage());
			throw new ExceptionHandler("We could not update your user");
		}
	}

	public String deleteUser(Long id) {
		try {
			log.info("deleteUser - id: " + id.toString());
			User user = userMapper.entityToModel(userRepository.findById(id).orElseThrow(()
				-> new ExceptionHandler("We could not find your user")));
			userRepository.delete(userMapper.modelToEntity(user));
			return "User deleted";
		} catch (Exception e) {
			log.error("Couldn't delete user: " + e.getMessage());
			throw new ExceptionHandler("We could not delete your user");
		}
	}

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity userEntity = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("No user with this email"));
    User myUser = userMapper.entityToModel(userEntity);
    List<SimpleGrantedAuthority> authi = new ArrayList<>();
    authi.add(new SimpleGrantedAuthority("SCOPE_ROLE_ADMIN"));
    return new org.springframework.security.core.userdetails.User(myUser.getEmail(), myUser.getPassword(), authi);
  }

  public User registerUser(RegistrationDTO dto) {
    return createUser(new UserDTO(null, dto.getName(), dto.getEmail(), dto.getPassword(), LocalDateTime.now(), LocalDateTime.now(), null, null));
  }

  public User findByEmail(String email) {
    return userMapper.entityToModel(userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No user with this email")));
  }
}
