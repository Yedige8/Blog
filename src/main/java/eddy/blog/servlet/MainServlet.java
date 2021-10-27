package eddy.blog.servlet;

import eddy.blog.entity.Article;
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


@WebServlet("/main")
public class MainServlet extends HttpServlet {

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
        Long userId = (Long) session.getAttribute("userId");
        if (userId != null) {
            User user = manager.find(User.class, userId);
            request.setAttribute("user", user);
        }
        List<Article> articles = manager
                .createQuery("""
                        select a
                        from Article a
                        where a.published = true and
                              a.user.role.id <> 4
                        """, Article.class)
                .getResultList();
        request.setAttribute("articles", articles);
        request.getRequestDispatcher("/main.jsp").forward(request, response);
        manager.close();
    }
}
