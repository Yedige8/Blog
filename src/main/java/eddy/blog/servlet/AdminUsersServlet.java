package eddy.blog.servlet;

import eddy.blog.entity.Role;
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


@WebServlet("/adminUsers")
public class AdminUsersServlet extends HttpServlet {

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
        List<User> users = manager
                .createQuery("""
                        select u
                        from User u
                        """, User.class)
                .getResultList();
        List<Role> roles = manager
                .createQuery("""
                        select r
                        from Role r
                        """, Role.class)
                .getResultList();
        request.setAttribute("users", users);
        request.setAttribute("roles", roles);
        request.getRequestDispatcher("/adminUsers.jsp").forward(request, response);
        manager.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        long userId = Long.parseLong(request.getParameter("userId"));
        long roleId = Long.parseLong(request.getParameter("role_id"));
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        manager
                .createQuery("""
                        update User u
                        set u.role.id = :roleId
                        where u.id  = :userId
                        """)
                .setParameter("userId", userId)
                .setParameter("roleId", roleId)
                .executeUpdate();
        manager.getTransaction().commit();
        response.sendRedirect("adminUsers");
        manager.close();
    }
}
