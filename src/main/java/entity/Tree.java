package entity;

import javax.persistence.*;

@Entity
@Table(name = "tree")
public class Tree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "name")
    private String name;

    @JoinColumn(name = "treeLeft")
    private Integer treeLeft;

    @JoinColumn(name = "treeRight")
    private Integer treeRight;

    @JoinColumn(name = "level")
    private Integer level;

    public String toString() {
        return name + ":" + "\n\tId: " +
                id + "\n\tLeft: " +
                treeLeft + "\n\tRight: " +
                treeRight + "\n\tLevel: " +
                level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLeft() {
        return treeLeft;
    }

    public void setLeft(Integer left) {
        this.treeLeft = left;
    }

    public Integer getRight() {
        return treeRight;
    }

    public void setRight(Integer right) {
        this.treeRight = right;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
