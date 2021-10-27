package eddy.blog.servlet;

import eddy.blog.entity.Article;
import eddy.blog.entity.Category;
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

@WebServlet("/article")
public class ArticleServlet extends HttpServlet {

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
        List<Category> categories = manager
                .createQuery("""
                        select category
                        from Category category
                        """, Category.class)
                .getResultList();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/article.jsp").forward(request, response);
        manager.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        Article article = new Article();
        article.setName(request.getParameter("name"));
        article.setText(request.getParameter("text"));
        long categoryId = Long.parseLong(request.getParameter("category_id"));
        Category category = manager.find(Category.class, categoryId);
        article.setCategory(category);
        article.setPublished(false);
        long userId = (Long) session.getAttribute("userId");
        User user = manager.find(User.class, userId);
        article.setUser(user);
        manager.persist(article);
        manager.getTransaction().commit();
        response.sendRedirect(request.getContextPath() + "/article");
        manager.close();
    }
}
