package org.step.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
// do not write user
@Table(name = "users")
public class User {

    // GenerationType.TABLE - hibernate is responsible for generation ids by hibernate_sequence
    // GenerationType.IDENTITY - table must have auto increment property on id column
    // GenerationType.SEQUENCE - (@SequenceGenerator) - hibernate creates sequence as we declared
    // or it is uses our sequence which we have created
    // GenerationType.AUTO - allow hibernate decide what strategy to use (not recommended)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
//    @SequenceGenerator(allocationSize = 1, sequenceName = "user_seq", name = "user_gen")
//    @SequenceGenerator(name = "user_gen", allocationSize = 1, sequenceName = "user_seq", initialValue = 50)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false, insertable = true, updatable = true, length = 128)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "age", precision = 2, scale = 0)
    private Integer age;

    @OneToOne(mappedBy = "user")
    private Profile profile;

    // Wont be declared in DB
    @Transient
    private String someCode;

    public User() {
    }

    private User(Long id, String username, String password, Integer age) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.age = age;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSomeCode() {
        return someCode;
    }

    public void setSomeCode(String someCode) {
        this.someCode = someCode;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static class UserBuilder {
        private Long id;
        private String username;
        private String password;
        private Integer age;

        private UserBuilder() {
        }

        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder age(Integer age) {
            this.age = age;
            return this;
        }

        public User build() {
            return new User(id, username, password, age);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
