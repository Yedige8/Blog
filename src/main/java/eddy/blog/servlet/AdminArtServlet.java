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


@WebServlet("/adminArt")
public class AdminArtServlet extends HttpServlet {

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
        long userId = (long) session.getAttribute("userId");
        User user = manager.find(User.class, userId);
        request.setAttribute("user", user);
        if ((request.getParameter("filter") == null)) {
            List<Article> articles = manager
                    .createQuery("""
                            select a
                            from Article a
                            """, Article.class)
                    .getResultList();
            request.setAttribute("article", articles);

        }else if(request.getParameter("filter").equals("published")) {
                List<Article> articles = manager
                        .createQuery("""
                                select a
                                from Article a where a.published = true
                                """, Article.class)
                        .getResultList();
                request.setAttribute("article", articles);
            } else if (request.getParameter("filter").equals("unpublished")) {
                List<Article> articles = manager
                        .createQuery("""
                                select a
                                from Article a where a.published = false
                                """, Article.class)
                        .getResultList();
                request.setAttribute("article", articles);
            } else{
            List<Article> articles = manager
                    .createQuery("""
                            select a
                            from Article a
                            """, Article.class)
                    .getResultList();
            request.setAttribute("article", articles);
        }

        request.getRequestDispatcher("/adminArt.jsp").forward(request, response);
        manager.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        long articleId = Long.parseLong(request.getParameter("articleId"));
        Article article = manager.find(Article.class, articleId);
        switch (request.getParameter("action")) {
            case "add" -> article.setPublished(true);
            case "remove" -> article.setPublished(false);
        }
        manager.getTransaction().commit();
        response.sendRedirect(request.getContextPath() + "/adminArt");
        manager.close();
    }
}
