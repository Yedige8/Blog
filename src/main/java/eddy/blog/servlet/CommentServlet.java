package eddy.blog.servlet;

import eddy.blog.entity.Article;
import eddy.blog.entity.Comment;
import eddy.blog.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {

    private EntityManagerFactory factory;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        factory = (EntityManagerFactory) context.getAttribute("emf");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        long articleId = Long.parseLong(request.getParameter("articleId"));
        switch (request.getParameter("action")) {
            case "add" -> {
                String text = request.getParameter("text");
                if (text.isEmpty()) {
                    session.setAttribute("comment_error", "Вы не оставили комментарий");
                } else {
                    Comment comment = new Comment();
                    Article article = manager.find(Article.class, articleId);
                    comment.setArticle(article);
                    long userId = (long) session.getAttribute("userId");
                    User user = manager.find(User.class, userId);
                    comment.setUser(user);
                    comment.setText(text);
                    manager.persist(comment);
                }
            }
            case "remove" -> {
                long commentId = Long.parseLong(request.getParameter("commentId"));
                Comment comment = manager.find(Comment.class, commentId);
                manager.remove(comment);
            }
        }

        manager.getTransaction().commit();
        response.sendRedirect(request.getContextPath() + "/view?id=" + articleId);
        manager.close();
    }
}
