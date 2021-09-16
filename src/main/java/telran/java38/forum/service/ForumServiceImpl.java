package telran.java38.forum.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.java38.forum.dao.PostRepository;
import telran.java38.forum.dto.CommentDto;
import telran.java38.forum.dto.NewCommentDto;
import telran.java38.forum.dto.NewPostDto;
import telran.java38.forum.dto.PostDto;
import telran.java38.forum.dto.exception.PostNotFoundException;
import telran.java38.forum.model.Comment;
import telran.java38.forum.model.Post;


@Service
public class ForumServiceImpl implements ForumService {
	
	@Autowired	
	PostRepository forumRepository; 
	
	@Autowired
	ModelMapper modelMapper;
	
	
	@Override
	public PostDto addNewPost(NewPostDto newPost, String author) {
		Post post = new Post(newPost.getTitle(), newPost.getContent(), author, newPost.getTags());
		post = forumRepository.save(post);
		return modelMapper.map(post, PostDto.class);				
	}	

	@Override
	public PostDto getPost(String id) {
		Post post = forumRepository.findById(id)
			.orElseThrow(()-> new PostNotFoundException(id));
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto removePost(String id) {
		Post post = forumRepository.findById(id)
				.orElseThrow(()-> new PostNotFoundException(id));
		forumRepository.deleteById(id);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto updatePost(NewPostDto postUpdateDto, String id) {
		Post post = forumRepository.findById(id)
				.orElseThrow(()-> new PostNotFoundException(id));
		String title = postUpdateDto.getTitle();
		if (title != null) {
			post.setTitle(title);			
		}
		String content = postUpdateDto.getContent();
		if (content != null) {
			post.setContent(content);			
		}
		Set<String > tags = postUpdateDto.getTags();
		if (tags != null) {
			tags.forEach(post::addTag);			
		}		
		forumRepository.save(post);
		return modelMapper.map(post, PostDto.class);
	}	


	@Override
	public boolean addLike(String id) {
		Post post = forumRepository.findById(id)
				.orElseThrow(()-> new PostNotFoundException(id));
		post.addLike();
		forumRepository.save(post);
		return true;		
	}

	@Override
	public PostDto addComment(String id, String author, NewCommentDto newCommentDto) {
		Post post = forumRepository.findById(id)
				.orElseThrow(()-> new PostNotFoundException(id));
		post.addComment(new Comment(author, newCommentDto.getMessage()));
		forumRepository.save(post);
		return modelMapper.map(post, PostDto.class);
		}

	@Override
	public Iterable<PostDto> findPostsByAuthor(String author) {
		return forumRepository.findByAuthor(author)
				.map(p -> modelMapper.map(p, PostDto.class))
				.collect(Collectors.toList());
		
	}
	
	
//	private PostDto convertToPostDto(Post post) {
//		return modelMapper.map(post, PostDto.class);
	
//		return  PostDto.builder()
//				.id(post.getId())
//				.title(post.getTitle())
//				.content(post.getContent())
//				.author(post.getAuthor())
//				.dateCreated(post.getDateCreated())
//				.tags(post.getTags())
//				.likes(post.getLikes())
//				.comments(convertToListCommentsDto(post.getComments()))
//				.build();				
//	}
	
	private CommentDto convertToCommentDto(Comment comment) {
		return modelMapper.map(comment, CommentDto.class);
		
	}
	
				
	


}
