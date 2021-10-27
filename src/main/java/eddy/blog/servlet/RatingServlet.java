package eddy.blog.servlet;

import eddy.blog.entity.Article;
import eddy.blog.entity.Rating;
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

@WebServlet("/rating")
public class RatingServlet extends HttpServlet {

    private EntityManagerFactory factory;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        factory = (EntityManagerFactory) context.getAttribute("emf");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession();
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        Rating rating = new Rating();
        rating.setRating(Integer.parseInt(request.getParameter("rating")));
        long articleId = Long.parseLong(request.getParameter("articleId"));
        Article article = manager.find(Article.class, articleId);
        rating.setArticle(article);
        long userId = (long) session.getAttribute("userId");
        User user = manager.find(User.class, userId);
        rating.setUser(user);
        manager.persist(rating);
        manager.getTransaction().commit();
        response.sendRedirect("view?id=" + request.getParameter("articleId"));
        manager.close();
    }
}
