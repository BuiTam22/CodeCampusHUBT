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

        // 4 bai viet moi nhat cho section-six
        List<BlogPostDTO> latestPosts = postService.getLatestPosts(4);
        model.addAttribute("latestPosts", latestPosts);

        // Tat ca bai viet cho section-eight (danh sach chinh)
        List<BlogPostDTO> blogPosts = postService.getAllBlogPosts();
        model.addAttribute("blogPosts", blogPosts);

        // 3 bai viet pho bien nhat cho sidebar
        List<BlogPostDTO> popularPosts = postService.getPopularPosts(3);
        model.addAttribute("popularPosts", popularPosts);


        LOGGER.info("BlogController showHome latestPosts: {}" , JsonUtil.toJson(latestPosts));
        LOGGER.info("BlogController showHome blogPosts: {}" , JsonUtil.toJson(blogPosts));
        LOGGER.info("BlogController showHome popularPosts: {}" , JsonUtil.toJson(popularPosts));

        return "ClientTemplates/blog/blog";
    }
}
