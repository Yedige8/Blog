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

@WebServlet("/create")
public class RegisterServlet extends HttpServlet {

    private EntityManagerFactory factory;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        factory = (EntityManagerFactory) context.getAttribute("emf");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        EntityManager manager = factory.createEntityManager();
        String surname = request.getParameter("surname");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        boolean hasErrors = false;
        if (surname == null) {
            session.setAttribute("surname_error", "ВЫ не передавали параметр Surname");
            hasErrors = true;
        }
        if (name == null) {
            session.setAttribute("name_error", "ВЫ не передавали параметр Name");
            hasErrors = true;
        }
        if (email == null) {
            session.setAttribute("email_error", "ВЫ не передавали параметр Email");
            hasErrors = true;
        } else if (!email.matches("^([A-Za-z_0-9])+@([a-z]+\\.[a-z]{2,3})$")) {
            session.setAttribute("email_error", "Email не коректный");
            hasErrors = true;
        } else {
            long emailCount = manager
                    .createQuery("""
                        select count(u.id)
                        from User u
                        where u.email = :email
                        """, Long.class)
                    .setParameter("email", email)
                    .getSingleResult();
            if (emailCount > 0) {
                session.setAttribute("email_error", "Вы передали сущществующий Email");
                hasErrors = true;
            }
        }
        if (login == null) {
            session.setAttribute("login_error", "ВЫ не передавали параметр Login");
            hasErrors = true;
        } else if (!login.matches("^([A-Za-z_])([0-9A-Za-z_])*$")) {
            session.setAttribute("login_error", "Login не коректный");
            hasErrors = true;
        } else {
            long loginCount = manager
                    .createQuery("""
                        select count(u.id)
                        from User u
                        where u.login = :login
                        """, Long.class)
                    .setParameter("login", login)
                    .getSingleResult();
            if (loginCount > 0) {
                session.setAttribute("login_error", "Вы передали сущществующий login");
                hasErrors = true;
            }
        }
        if (password == null) {
            session.setAttribute("comment_error", "ВЫ не передавали параметр Password");
            hasErrors = true;
        }
        if (hasErrors) {
            response.sendRedirect(request.getContextPath() + "/register.jsp");
        } else {
            manager.getTransaction().begin();
            User user = new User();
            user.setName(name);
            user.setSurname(surname);
            user.setEmail(email);
            user.setLogin(login);
            user.setPassword(password);
            Role role = manager.find(Role.class, 3L);
            user.setRole(role);
            manager.persist(user);
            manager.getTransaction().commit();
            session.setAttribute("userId", user.getId());
            request.getRequestDispatcher("/profile").forward(request, response);
        }
        manager.close();
    }
}
