package telran.java38.user.dto.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -323430171596868430L;
	
	public  UserNotFoundException (String login) {
		super("User " + login + " not found");
	}
	
	
	
	

}
