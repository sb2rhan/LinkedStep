package org.step.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "abilities")
    private String abilities;

    @Column(name = "graduation")
    private String graduation;

    @Column(name = "work_experience")
    private String workExperience;

    // name of property should be matched to mappedBy in User entity
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            foreignKey = @ForeignKey(name = "profile_user_fk")
    )
    private User user;

    public Profile() {
    }

    private Profile(Long id, String abilities, String graduation, String workExperience, String fullName) {
        this.abilities = abilities;
        this.graduation = graduation;
        this.workExperience = workExperience;
        this.id = id;
        this.fullName = fullName;
    }

    public static ProfileBuilder builder() {
        return new ProfileBuilder();
    }

    // static - вложенный класс, not static - внутренний класс
    public static class ProfileBuilder {
        private Long id;
        private String abilities;
        private String graduation;
        private String workExperience;
        private String fullName;

        public ProfileBuilder() {
        }

        public ProfileBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ProfileBuilder abilities(String abilities) {
            this.abilities = abilities;
            return this;
        }

        public ProfileBuilder graduation(String graduation) {
            this.graduation = graduation;
            return this;
        }

        public ProfileBuilder workExperience(String workExperience) {
            this.workExperience = workExperience;
            return this;
        }

        public ProfileBuilder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Profile build() {
            return new Profile(id, abilities, graduation, workExperience, fullName);
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbilities() {
        return abilities;
    }

    public void setAbilities(String abilities) {
        this.abilities = abilities;
    }

    public String getGraduation() {
        return graduation;
    }

    public void setGraduation(String graduation) {
        this.graduation = graduation;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(id, profile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", abilities='" + abilities + '\'' +
                ", graduation='" + graduation + '\'' +
                ", workExperience='" + workExperience + '\'' +
                '}';
    }
}
