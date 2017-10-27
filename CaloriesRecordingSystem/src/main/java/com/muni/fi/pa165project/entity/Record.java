package com.muni.fi.pa165project.entity;

import com.muni.fi.pa165project.enums.Difficulty;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Peter Krasnan
 */
@Entity
public class Record implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    private Activity activity;

    @ManyToOne(optional=false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime time;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    private int weight;

    private int burnedCalories;

    private int distance;

    private int duration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getBurnedCalories() {
        return burnedCalories;
    }

    public void setBurnedCalories(int burnedCalories) {
        this.burnedCalories = burnedCalories;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        result = prime * result + ((time == null) ? 0 : time.hashCode());
        result = prime * result + ((activity == null) ? 0 : activity.hashCode());
        result = prime * result + ((difficulty == null) ? 0 : difficulty.hashCode());
        result = prime * result + weight;
        result = prime * result + burnedCalories;
        result = prime * result + distance;
        result = prime * result + duration;
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Record)) {
            return false;
        }

        final Record other = (Record) obj;

        if (activity == null) {
            return false;
        } else {
            if (!activity.equals(other.activity)) {
                return false;
            }
        }

        if (user == null) {
            return false;
        } else {
            if (!user.equals(other.user)) {
                return false;
            }
        }

        if (time == null) {
            return false;
        } else {
            if (!time.equals(other.time)) {
                return false;
            }
        }

        if (user == null) {
            return false;
        } else {
            if (!user.equals(other.user)) {
                return false;
            }
        }

        if (difficulty == null) {
            return false;
        } else {
            if (!difficulty.equals(other.difficulty)) {
                return false;
            }
        }

        if (weight != other.weight) {
            return false;
        }

        if (distance != other.distance) {
            return false;
        }

        if (duration != other.duration) {
            return false;
        }

        if (burnedCalories != other.burnedCalories) {
            return false;
        }

        return true;
    }
}
