package com.blog.application.controllers;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.application.config.AppConstants;
import com.blog.application.payloads.ApiResponse;
import com.blog.application.payloads.PostDto;
import com.blog.application.payloads.PostResponse;
import com.blog.application.services.FileService;
import com.blog.application.services.PostService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@SecurityRequirement(name = "scheme1")
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	
	@Value("${project.image}")
	private String path;

	// create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<PostDto> (createPost, HttpStatus.CREATED);
	}
	
	//get post by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
		List<PostDto> posts = this.postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	// get post by category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
		List<PostDto> posts = this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	
  }
	//get all posts
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGA_NUMBER, required = false)Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = AppConstants.PAGA_SIZE,required = false) Integer pageSize,
	        @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
	        @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir){
		
		PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize, sortBy,sortDir);
		
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
		
	}
	
	// get post by post Id
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostByPostId(@PathVariable Integer postId){
		PostDto post = this.postService.getPostsById(postId);
		return new ResponseEntity<PostDto>(post,HttpStatus.OK);
		
	}
	
	// delete post by post Id
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePostById(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>( new ApiResponse("Post Deleted Successfully", true), HttpStatus.OK);
	}
	
	
	// update post by post Id
		@PutMapping("/posts/{postId}")
		public ResponseEntity<PostDto> updatePostByPostId(@RequestBody PostDto postDto ,@PathVariable Integer postId) {
			PostDto updatedPost = this.postService.updatePost(postDto, postId);
			return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
		}
		
	// search
		@GetMapping("/posts/search/{keywords}")
		public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords){
			List<PostDto> searchPosts = this.postService.searchPosts(keywords);
	     	return new ResponseEntity<List<PostDto>>(searchPosts,HttpStatus.OK);
	
 }
		
		
		// post image upload
		
		
		@PostMapping("/post/image/upload/{postId}")
		public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,@PathVariable Integer postId) throws IOException{
			
			
			PostDto postDto = this.postService.getPostsById(postId);
			String fileName = this.fileService.uploadImage(path, image);			
			postDto.setImageName(fileName);
			PostDto updatePost = this.postService.updatePost(postDto, postId);
			
			return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
			
			
		}
		
		//method to serve files 
		
		
		@GetMapping(value = "post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
		public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
			    InputStream resource = this.fileService.getResource(path, imageName);
		        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		        StreamUtils.copy(resource,response.getOutputStream())   ;
			
		}
		

}