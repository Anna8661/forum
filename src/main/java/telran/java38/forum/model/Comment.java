package telran.java38.forum.model;

import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode (of = {"user","dateCreated"})

public class Comment {
	String user;
	@Setter
	String message;
	LocalDateTime dateCreated;
	int likes;
	
	public Comment() {
		dateCreated = LocalDateTime.now();		
	}

	public Comment(String user, String message) {
		this();
		this.user = user;
		this.message = message;
	}
	
	public void addLike () {
		likes++;		
	}
	
		
	
}


