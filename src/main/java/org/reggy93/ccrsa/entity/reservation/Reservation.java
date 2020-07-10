package org.reggy93.ccrsa.entity.reservation;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.reggy93.ccrsa.entity.Comment;
import org.reggy93.ccrsa.entity.car.Car;
import org.reggy93.ccrsa.entity.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entity class for {@code Reservation} representation.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 08 Jul 2020
 */
@Entity
@Table(name = "reservations")
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Enumerated(value = EnumType.STRING)
    @NotEmpty
    private Status status;

    @Temporal(TemporalType.TIMESTAMP)
    @NotEmpty
    private LocalDateTime from;

    @Temporal(TemporalType.TIMESTAMP)
    @NotEmpty
    private LocalDateTime to;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    @NotEmpty
    private User user;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "car_id")
    @NotEmpty
    private Car car;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Reservation)) return false;

        Reservation that = (Reservation) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(status, that.status)
                .append(from, that.from)
                .append(to, that.to)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(status)
                .append(from)
                .append(to)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("status", status)
                .append("from", from)
                .append("to", to)
                .append("user", user)
                .append("car", car)
                .toString();
    }
}
