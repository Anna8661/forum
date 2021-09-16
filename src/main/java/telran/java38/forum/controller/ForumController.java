package telran.java38.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.java38.forum.dto.NewCommentDto;
import telran.java38.forum.dto.NewPostDto;
import telran.java38.forum.dto.PostDto;
import telran.java38.forum.service.ForumService;

@RestController
@RequestMapping("/forum")
public class ForumController {
	
	@Autowired
	ForumService forumService;
	
	@PostMapping ("/post/{author}")
	public PostDto addPost(@RequestBody NewPostDto newPost, @PathVariable String author) {
		return forumService.addNewPost(newPost, author);		
	} 
	
	@GetMapping ("/post/{id}")
	public PostDto getPost(@PathVariable String id) {
		return forumService.getPost(id);		
	}
	
	@DeleteMapping ("/post/{id}")
	public PostDto removePost (@PathVariable String id) {
		return forumService.removePost(id);
	}
	
	@PutMapping ("/post/{id}")
	public PostDto updatePost (@RequestBody NewPostDto postUpdateDto, @PathVariable String id) {
		return forumService.updatePost(postUpdateDto, id);
	}
	
	@PutMapping ("/post/{id}/like")
	public boolean addLike(@PathVariable String id) {
		return forumService.addLike(id);
	}
	
	@PutMapping ("/post/{id}/comment/{author}")
	public PostDto addComment(@PathVariable String id,@PathVariable String author,@RequestBody NewCommentDto newCommentDto) {
		return forumService.addComment(id, author, newCommentDto);
	}
	
	@GetMapping ("/posts/author/{author}")
	public Iterable<PostDto> getPostsByAuthor(@PathVariable String author) {
		return forumService.findPostsByAuthor(author);
	}
	

}
