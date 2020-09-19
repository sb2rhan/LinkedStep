package org.step.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "views")
public class View {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "view_gen")
    @SequenceGenerator(name = "view_gen", sequenceName = "view_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "view_date")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "profile_id",
            foreignKey = @ForeignKey(name = "view_profile_fk")
    )
    private Profile profile;

    public View() { }

    private View(Long id, Date date) {
        this.id = id;
        this.date = date;
    }

    public static ViewBuilder builder() {
        return new ViewBuilder();
    }

    public static class ViewBuilder {
        private Long id;
        private Date date;

        ViewBuilder() {}

        public ViewBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ViewBuilder date(Date date) {
            this.date = date;
            return this;
        }

        public View build() {
            return new View(id, date);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        View view1 = (View) o;
        return Objects.equals(id, view1.id)
                && Objects.equals(date, view1.date);
    }

    @Override
    public String toString() {
        return String.format("View{'id'=%d, 'date'=%s}", this.id, this.date.toString());
    }
}
