package com.codecampushubt.NCKH2024TQQD.controller.Client.Blog;

import com.codecampushubt.NCKH2024TQQD.context.UserContext;
import com.codecampushubt.NCKH2024TQQD.dto.BlogDTO.BlogCommentDTO;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("activePage", "/blog");
        return "ClientTemplates/blog/blog-create";
    }

    @PostMapping("/create")
    public String createBlog(@RequestParam String title,
                             @RequestParam(required = false) String thumbnailUrl,
                             @RequestParam String content,
                             @RequestParam(defaultValue = "regular") String type,
                             RedirectAttributes redirectAttributes) {
        Long userId = UserContext.getUserID();
        if (userId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bạn cần đăng nhập để tạo bài viết.");
            return "redirect:/login/show";
        }

        if (title == null || title.isBlank() || content == null || content.isBlank()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tiêu đề và nội dung là bắt buộc.");
            return "redirect:/blog/create";
        }

        postService.createBlogPost(userId, title, thumbnailUrl, content, type);
        redirectAttributes.addFlashAttribute("successMessage", "Tạo bài viết thành công.");
        return "redirect:/blog";
    }

    @GetMapping("/{slug}")
    public String showDetail(@PathVariable String slug, Model model) {
        model.addAttribute("activePage", "/blog");
        Long currentUserId = UserContext.getUserID();

        BlogPostDTO post = postService.getBlogPostBySlug(slug);
        model.addAttribute("post", post);

        List<BlogPostDTO> popularPosts = postService.getPopularPosts(3);
        model.addAttribute("popularPosts", popularPosts);
        List<BlogCommentDTO> comments = postService.getCommentsByPostSlug(slug, currentUserId);
        model.addAttribute("comments", comments);
        model.addAttribute("canEdit", postService.isPostAuthor(slug, currentUserId));
        model.addAttribute("isLoggedIn", currentUserId != null);

        LOGGER.info("BlogController showDetail slug={}, post: {}", slug, JsonUtil.toJson(post));

        return "ClientTemplates/blog/blog-detail";
    }

    @PostMapping("/{slug}/comment")
    public String addComment(@PathVariable String slug,
                             @RequestParam String content,
                             RedirectAttributes redirectAttributes) {
        Long currentUserId = UserContext.getUserID();
        if (currentUserId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bạn cần đăng nhập để bình luận.");
            return "redirect:/login/show";
        }
        if (content == null || content.isBlank()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Nội dung bình luận không được để trống.");
            return "redirect:/blog/" + slug + "#comment-section";
        }

        postService.addComment(slug, currentUserId, content);
        return "redirect:/blog/" + slug + "#comment-section";
    }

    @PostMapping("/{slug}/comment/{commentId}/like")
    public String toggleCommentLike(@PathVariable String slug,
                                    @PathVariable Long commentId,
                                    RedirectAttributes redirectAttributes) {
        Long currentUserId = UserContext.getUserID();
        if (currentUserId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bạn cần đăng nhập để thích bình luận.");
            return "redirect:/login/show";
        }

        postService.toggleCommentLike(slug, commentId, currentUserId);
        return "redirect:/blog/" + slug + "#comment-section";
    }

    @GetMapping("/{slug}/edit")
    public String showEditPage(@PathVariable String slug,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        Long currentUserId = UserContext.getUserID();
        if (currentUserId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bạn cần đăng nhập để chỉnh sửa bài viết.");
            return "redirect:/login/show";
        }
        if (!postService.isPostAuthor(slug, currentUserId)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bạn không có quyền chỉnh sửa bài viết này.");
            return "redirect:/blog/" + slug;
        }

        model.addAttribute("activePage", "/blog");
        model.addAttribute("post", postService.getBlogPostBySlug(slug));
        return "ClientTemplates/blog/blog-edit";
    }

    @PostMapping("/{slug}/edit")
    public String updatePost(@PathVariable String slug,
                             @RequestParam String title,
                             @RequestParam(required = false) String thumbnailUrl,
                             @RequestParam String content,
                             @RequestParam(defaultValue = "regular") String type,
                             RedirectAttributes redirectAttributes) {
        Long currentUserId = UserContext.getUserID();
        if (currentUserId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bạn cần đăng nhập để chỉnh sửa bài viết.");
            return "redirect:/login/show";
        }
        if (title == null || title.isBlank() || content == null || content.isBlank()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tiêu đề và nội dung là bắt buộc.");
            return "redirect:/blog/" + slug + "/edit";
        }

        postService.updateBlogPost(slug, currentUserId, title, thumbnailUrl, content, type);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật bài viết thành công.");
        return "redirect:/blog/" + slug;
    }
}
