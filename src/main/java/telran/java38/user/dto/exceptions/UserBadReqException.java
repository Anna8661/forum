package telran.java38.user.dto.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UserBadReqException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5389464875641087056L;

	public UserBadReqException() {
		super("Not all fields are filled in");		
	}

	
}
