package org.step.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "groups")
public class Group {
    @Id
    private String id;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "description")
    private String description;

    @Column(name = "category")
    private String category;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "profile_group",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id")
    )
    private Set<Profile> profileSet = new HashSet<>();

    public Group() {
    }

    private Group(String id, String groupName, String description, String category) {
        this.id = id;
        this.groupName = groupName;
        this.description = description;
        this.category = category;
    }

    public static GroupBuilder builder() {
        return new GroupBuilder();
    }

    public static class GroupBuilder {
        private String id;
        private String groupName, description, category;

        public GroupBuilder() {}

        public GroupBuilder id() {
            this.id = UUID.randomUUID().toString();
            return this;
        }

        public GroupBuilder groupName(String groupName) {
            this.groupName = groupName;
            return this;
        }

        public GroupBuilder description(String description) {
            this.description = description;
            return this;
        }

        public GroupBuilder category(String category) {
            this.category = category;
            return this;
        }

        public Group build() {
            return new Group(id, groupName, description, category);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<Profile> getProfileSet() {
        return profileSet;
    }

    public void setProfileSet(Set<Profile> profileSet) {
        this.profileSet = profileSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(id, group.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Group{" +
                "id='" + id + '\'' +
                ", groupName='" + groupName + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
