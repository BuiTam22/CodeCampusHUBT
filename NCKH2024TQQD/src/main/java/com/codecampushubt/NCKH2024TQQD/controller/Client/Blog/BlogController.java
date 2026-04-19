package com.codecampushubt.NCKH2024TQQD.controller.Client.Blog;

import com.codecampushubt.NCKH2024TQQD.dto.BlogDTO.BlogPostDTO;
import com.codecampushubt.NCKH2024TQQD.service.PostServices.PostService;
import com.codecampushubt.NCKH2024TQQD.util.JsonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlogController.class);

    private final PostService postService;

    @Autowired
    public BlogController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String showHome(Model model, HttpServletRequest request) {
        model.addAttribute("activePage", request.getRequestURI());

        List<BlogPostDTO> latestPosts = postService.getLatestPosts(4);
        model.addAttribute("latestPosts", latestPosts);

        List<BlogPostDTO> blogPosts = postService.getAllBlogPosts();
        model.addAttribute("blogPosts", blogPosts);

        List<BlogPostDTO> popularPosts = postService.getPopularPosts(3);
        model.addAttribute("popularPosts", popularPosts);

        LOGGER.info("BlogController showHome latestPosts: {}", JsonUtil.toJson(latestPosts));
        LOGGER.info("BlogController showHome blogPosts: {}", JsonUtil.toJson(blogPosts));
        LOGGER.info("BlogController showHome popularPosts: {}", JsonUtil.toJson(popularPosts));

        return "ClientTemplates/blog/blog";
    }

    @GetMapping("/{slug}")
    public String showDetail(@PathVariable String slug, Model model) {
        model.addAttribute("activePage", "/blog");

        BlogPostDTO post = postService.getBlogPostBySlug(slug);
        model.addAttribute("post", post);

        List<BlogPostDTO> popularPosts = postService.getPopularPosts(3);
        model.addAttribute("popularPosts", popularPosts);

        LOGGER.info("BlogController showDetail slug={}, post: {}", slug, JsonUtil.toJson(post));

        return "ClientTemplates/blog/blog-detail";
    }
}
