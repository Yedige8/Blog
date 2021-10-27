package eddy.blog.servlet;

import eddy.blog.entity.Article;
import eddy.blog.entity.Choosen;
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

@WebServlet("/choosen")
public class ChosenServlet extends HttpServlet {

    private EntityManagerFactory factory;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        factory = (EntityManagerFactory) context.getAttribute("emf");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager manager = factory.createEntityManager();
        HttpSession session = request.getSession();
        long userId = (long) session.getAttribute("userId");
        List<Choosen> chosen = manager
                .createQuery("""
                        select c
                        from Choosen c
                        where c.user.id = :userId
                        """, Choosen.class)
                .setParameter("userId", userId)
                .getResultList();
        request.setAttribute("chosen", chosen);
        request.getRequestDispatcher("/choosens.jsp").forward(request, response);
        manager.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession();
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        long userId = (long) session.getAttribute("userId");
        long articleId = Long.parseLong(request.getParameter("article_id"));
        switch (request.getParameter("action")) {
            case "add" -> {
                User user = manager.find(User.class, userId);
                Article article = manager.find(Article.class, articleId);
                Choosen choosen = new Choosen();
                choosen.setArticle(article);
                choosen.setUser(user);
                manager.persist(choosen);
            }
            case "remove" -> {
                Choosen chosen = manager
                        .createQuery("""
                                select c
                                from Choosen c
                                where c.user.id = :userId
                                and c.article.id = :article
                                """, Choosen.class)
                        .setParameter("userId", userId)
                        .setParameter("article", articleId)
                        .getSingleResult();
                manager.remove(chosen);
            }
        }
        manager.getTransaction().commit();
        response.sendRedirect("view?id=" + articleId);
        manager.close();
    }
}
