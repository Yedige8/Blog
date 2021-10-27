package eddy.blog.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    private String surname;

    private String email;
    private String login;
    private String password;
    @Column(name = "date_registration")
    @CreationTimestamp
    private Date registrationDate;

    @OneToMany(mappedBy = "user")
    private List<Article> articles;


    public List<Choosen> getChoosens() {
        return choosens;
    }

    public void setChoosens(List<Choosen> choosens) {
        this.choosens = choosens;
    }

    @OneToMany(mappedBy = "user")
    private List<Choosen> choosens;


    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Rating> reitings;
    @OneToMany(mappedBy = "user")
    private List<Comment> comments;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String lastName) {
        this.surname = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Rating> getReitings() {
        return reitings;
    }

    public void setReitings(List<Rating> reitings) {
        this.reitings = reitings;
    }

    public List<Comment> getComents() {
        return comments;
    }

    public void setComents(List<Comment> comments) {
        this.comments = comments;
    }
}
