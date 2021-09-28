package telran.java38.user.service;

import java.util.Set;

import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import telran.java38.user.dao.AccountRepository;
import telran.java38.user.dto.UserProfileDto;
import telran.java38.user.dto.UserRegDto;
import telran.java38.user.dto.UserUpdateDto;
import telran.java38.user.dto.exceptions.UserBadReqException;
import telran.java38.user.dto.exceptions.UserConflictException;
import telran.java38.user.dto.exceptions.UserNotFoundException;
import telran.java38.user.model.UserProfile;

@Service
public class AccountServiceImpl implements AccountService {
	
	AccountRepository accountrepository;
	ModelMapper modelMapper;

	@Autowired
	public AccountServiceImpl(AccountRepository accountrepository, ModelMapper modelMapper) {
		this.accountrepository = accountrepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public UserProfileDto addUser(UserRegDto userRegDto) {
		if (	userRegDto.getLogin() == null ||
				userRegDto.getFirstName() == null ||
				userRegDto.getLastName() == null ||
				userRegDto.getPassword() == null) {
			throw new UserBadReqException();					
		}		
		
		if (accountrepository.existsById(userRegDto.getLogin())) {
			throw new UserConflictException(userRegDto.getLogin());					
		}
		String hashPassword = BCrypt.hashpw(userRegDto.getPassword(), BCrypt.gensalt()); 
		UserProfile userProfile = new UserProfile(userRegDto.getLogin(), hashPassword, userRegDto.getFirstName(), userRegDto.getLastName());		
		accountrepository.save(userProfile);		
		return modelMapper.map(userProfile, UserProfileDto.class);
	}

	@Override
	public UserProfileDto findUserById(String login) {
		UserProfile userProfile = accountrepository.findById(login)
				.orElseThrow(()-> new UserNotFoundException(login));
		return modelMapper.map(userProfile, UserProfileDto.class);
	}

	@Override
	public UserProfileDto updateUser(String login, UserUpdateDto userUpdateDto) {
		UserProfile userProfile = accountrepository.findById(login)
				.orElseThrow(()-> new UserNotFoundException(login));
		if (userUpdateDto.getFirstName() != null) {
			userProfile.setFirstName(userUpdateDto.getFirstName());			
		}
		if (userUpdateDto.getLastName() != null) {
			userProfile.setLastName(userUpdateDto.getLastName());			
		}
		accountrepository.save(userProfile);
		return modelMapper.map(userProfile, UserProfileDto.class);
	}

	@Override
	public UserProfileDto removeUser(String login) {
		UserProfile userProfile = accountrepository.findById(login)
				.orElseThrow(()-> new UserNotFoundException(login));
		accountrepository.deleteById(login);		
		return modelMapper.map(userProfile, UserProfileDto.class);
	}

	@Override
	public void changePassword(String login, String password) {
		
		UserProfile userProfile = accountrepository.findById(login)
				.orElseThrow(()-> new UserNotFoundException(login));
		if (password != null) {
			String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt()); 
			userProfile.setPassword(hashPassword);
			accountrepository.save(userProfile);			
		}
		}

	@Override
	public Set<String> updateRolesList(String login, String role, boolean ifSet) {
		if (role == null) {
			throw  new UserBadReqException();			
		}
		UserProfile userProfile = accountrepository.findById(login)
				.orElseThrow(()-> new UserNotFoundException(login));
		if (ifSet) {
			userProfile.getRoles().add(role);		
		} else {
			userProfile.getRoles().remove(role);
		}
		accountrepository.save(userProfile);
		return userProfile.getRoles();
	}

}
