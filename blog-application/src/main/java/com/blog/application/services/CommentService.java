package com.blog.application.services;

import com.blog.application.payloads.CommentDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto commenDto,Integer postId);
	
	void deleteComment(Integer commentId);

}
