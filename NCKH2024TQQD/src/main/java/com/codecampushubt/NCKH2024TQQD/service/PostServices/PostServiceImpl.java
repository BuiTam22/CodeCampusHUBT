package com.codecampushubt.NCKH2024TQQD.service.PostServices;

import com.codecampushubt.NCKH2024TQQD.dao.PostRepository;
import com.codecampushubt.NCKH2024TQQD.dto.BlogDTO.BlogPostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<BlogPostDTO> getAllBlogPosts() {
        return postRepository.findAllBlogPosts();
    }

    @Override
    public List<BlogPostDTO> getPopularPosts(int limit) {
        return postRepository.findPopularPosts(PageRequest.of(0, limit));
    }

    @Override
    public List<BlogPostDTO> getLatestPosts(int limit) {
        return postRepository.findLatestPosts(PageRequest.of(0, limit));
    }
}
