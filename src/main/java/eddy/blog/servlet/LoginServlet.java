package eddy.blog.servlet;

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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private EntityManagerFactory factory;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        factory = (EntityManagerFactory) context.getAttribute("emf");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        EntityManager manager = factory.createEntityManager();
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        boolean hasErrors = false;
        long loginCount = manager
                .createQuery("""
                        select count(u.id)
                        from User u
                        where u.login = :login
                        """, Long.class)
                .setParameter("login", login)
                .getSingleResult();
        if (loginCount == 0) {
            session.setAttribute("login_error", "Вы передали не сущществующий login");
            hasErrors = true;
        }
        List<User> users = manager
                .createQuery("""
                        select u
                        from User u
                        where u.login = :login and
                              u.password = :password
                        """, User.class)
                .setParameter("password", password)
                .setParameter("login", login)
                .setMaxResults(1)
                .getResultList();
        if (users.size() == 0) {
            session.setAttribute("comment_error", "Вы передали не верный пароль");
            hasErrors = true;
        }
        if (hasErrors) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        } else {
            session.setAttribute("userId", users.get(0).getId());
            response.sendRedirect(request.getContextPath() + "/profile");
        }
        manager.close();
    }
}
