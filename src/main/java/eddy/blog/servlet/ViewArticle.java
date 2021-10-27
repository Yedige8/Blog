package eddy.blog.servlet;

import eddy.blog.entity.Article;
import eddy.blog.entity.Rating;
import eddy.blog.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/view")
public class ViewArticle extends HttpServlet {

    private EntityManagerFactory factory;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        factory = (EntityManagerFactory) context.getAttribute("emf");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        EntityManager manager = factory.createEntityManager();
        long articleId = Long.parseLong(request.getParameter("id"));
        Long userId = (Long) session.getAttribute("userId");
        if (userId != null) {
            User user = manager.find(User.class, userId);
            List<Rating> ratings = manager
                    .createQuery("""
                            select r
                            from Rating r
                            where r.article.id = :articleId and
                                  r.user.id = :userId
                            """, Rating.class)
                    .setParameter("articleId", articleId)
                    .setParameter("userId", user.getId())
                    .setMaxResults(1)
                    .getResultList();
            long chosenCount = manager
                    .createQuery("""
                            select count(c.id)
                            from Choosen c
                            where c.article.id = :article_id and
                                  c.user.id = :user_id
                            """, Long.class)
                    .setParameter("article_id", articleId)
                    .setParameter("user_id", user.getId())
                    .getSingleResult();
            request.setAttribute("chosen", chosenCount > 0);
            request.setAttribute("is_rated", ratings.size() > 0);
            if (ratings.size() > 0) {
                request.setAttribute("set_rating", ratings.get(0).getRating());
            }
            request.setAttribute("user", user);
        }
        Article article = manager.find(Article.class, articleId);
        List<Rating> ratings = article.getRatings();
        int totalRating = 0;
        for (Rating rating : ratings) {
            totalRating += rating.getRating();
        }
        int averageRating = 0;
        if (ratings.size() != 0) {
            averageRating = totalRating / ratings.size();
        }
        request.setAttribute("article", article);
        request.setAttribute("average_rating", averageRating);
        request.getRequestDispatcher("/view.jsp").forward(request, response);
        manager.close();
    }
}
