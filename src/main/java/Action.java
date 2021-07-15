import entity.Tree;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Action {

    public static void move() throws IOException {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("sql");
        EntityManager manager = managerFactory.createEntityManager();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        TypedQuery<Tree> treeTypedQuery = manager.createQuery("select t from Tree t order by t.treeLeft", Tree.class);
        List<Tree> treeList = treeTypedQuery.getResultList();

        for (Tree tree : treeList) {
            System.out.println(tree);
        }

        System.out.print("Введите Id товара, которую вы хотите переместить: ");
        long idProductIn;
        while (true) {
            try {
                idProductIn = Long.parseLong(bufferedReader.readLine());
                break;
            } catch (NumberFormatException | IOException e) {
                System.out.print("Неверное введен Id! Пожалуйста введите снова: ");
            }
        }

        System.out.print("Введите Id товара, куда вы хотите переместить: ");
        long idProductPlaceIn;
        while (true) {
            try {
                idProductPlaceIn = Long.parseLong(bufferedReader.readLine());
                break;
            } catch (NumberFormatException e) {
                System.out.print("Неверное введен Id! Пожалуйста введите снова: ");
            }
        }

        try {
            manager.getTransaction().begin();

            Tree treeIn = manager.find(Tree.class, idProductIn);
            int treeInRight = treeIn.getRight();
            int treeInLeft = treeIn.getLeft();
            int treeInLevel = treeIn.getLevel();
            int differenceIn = treeInRight - treeInLeft + 1;
            for (Tree tree : treeList) {
                if (tree.getRight() <= treeInRight && tree.getLeft() >= treeInLeft) {
                    tree.setLeft(tree.getLeft() * (-1));
                    tree.setRight(tree.getRight() * (-1));
                }
            }
            for (Tree tree : treeList) {
                if (tree.getRight() > treeInRight) {
                    tree.setRight(tree.getRight() - differenceIn);
                    if (tree.getLeft() > treeInRight) {
                        tree.setLeft(tree.getLeft() - differenceIn);
                    }
                }
            }

            Tree treeToPlace = manager.find(Tree.class, idProductPlaceIn);
            int treeToPlaceRight = treeToPlace.getRight();
            int differenceTo = treeToPlace.getRight() - treeInLeft;
            int differenceLevel = treeToPlace.getLevel() - treeInLevel + 1;
            System.out.println(differenceTo);
            for (Tree tree : treeList) {
                if (tree.getRight() >= treeToPlaceRight) {
                    tree.setRight(tree.getRight() + differenceIn);
                    if (tree.getLeft() > treeToPlaceRight) {
                        tree.setLeft(tree.getLeft() + differenceIn);
                    }
                }

            }

            for (Tree tree : treeList) {
                if (tree.getRight() < 0) {
                    tree.setLeft(tree.getLeft() * (-1) + differenceTo);
                    tree.setRight(tree.getRight() * (-1) + differenceTo);
                    tree.setLevel(tree.getLevel() + differenceLevel);
                }
            }

            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
        } finally {
            manager.close();
        }
    }

    public static void add() throws IOException {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("sql");
        EntityManager manager = managerFactory.createEntityManager();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));


        TypedQuery<Tree> treeTypedQuery = manager.createQuery("select t from Tree t", Tree.class);
        List<Tree> treeList = treeTypedQuery.getResultList();

        for (Tree tree : treeList) {
            System.out.println(tree);
        }

        System.out.print("Введите Id товара, куда нужно добавить товар: ");
        long idIn;
        while (true) {
            try {
                idIn = Long.parseLong(bufferedReader.readLine());
                break;
            } catch (NumberFormatException e) {
                System.out.print("Неверно введен Id товара! Пожалуйста введите снова: ");
            }
        }

        System.out.print("Введите название товара: ");
        String nameIn = bufferedReader.readLine();

        try {
            manager.getTransaction().begin();

//            Tree tree = manager.find(Tree.class, idIn);
//            int treeRight = tree.getRight();
//
//            CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
//            CriteriaQuery<Tree> criteriaQuery = criteriaBuilder.createQuery(Tree.class);
//            Root<Tree> treeRoot = criteriaQuery.from(Tree.class);
//
//            List<Predicate> predicateList = new ArrayList<>();
//
//            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(treeRoot.get("treeRight"), treeRight));
//
//            criteriaQuery.where(predicateList.toArray(new Predicate[0]));
//            List<Tree> treeListToChange = manager.createQuery(criteriaQuery).getResultList();
//
//            for (Tree treeFromList : treeListToChange) {
//                if (treeFromList.getLeft() >= treeRight) {
//                    treeFromList.setLeft(treeFromList.getLeft() + 2);
//                }
//                treeFromList.setRight(treeFromList.getRight() + 2);
//            }
//
//            Tree newTree = new Tree();
//            newTree.setName(nameIn);
//            newTree.setLeft(treeRight);
//            newTree.setRight(treeRight + 1);
//            newTree.setLevel(tree.getLevel() + 1);
//
//            manager.persist(newTree);

            Tree treeToPlace = manager.find(Tree.class, idIn);
            int treeToPlaceRight = treeToPlace.getRight();

            for (Tree tree : treeList) {
                if (tree.getRight() >= treeToPlaceRight) {
                    tree.setRight(tree.getRight() + 2);
                }
                if (tree.getLeft() >= treeToPlaceRight) {
                    tree.setLeft(tree.getLeft() + 2);
                }
            }

            Tree newTree = new Tree();
            newTree.setName(nameIn);
            newTree.setLeft(treeToPlaceRight);
            newTree.setRight(treeToPlaceRight + 1);
            newTree.setLevel(treeToPlace.getLevel() + 1);
            manager.persist(newTree);

            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
        } finally {
            manager.close();
        }
    }

    public static void delete() throws IOException {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("sql");
        EntityManager manager = managerFactory.createEntityManager();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));


        TypedQuery<Tree> treeTypedQuery = manager.createQuery("select t from Tree t", Tree.class);
        List<Tree> treeList = treeTypedQuery.getResultList();

        for (Tree tree : treeList) {
            System.out.println(tree);
        }

        System.out.print("Введите Id товара, которую нужно удалить: ");
        long idIn;
        while (true) {
            try {
                idIn = Long.parseLong(bufferedReader.readLine());
                break;
            } catch (NumberFormatException e) {
                System.out.print("Неверно введен Id! Пожалуйста введите снова: ");
            }
        }

        try {
            manager.getTransaction().begin();

//            Tree tree = manager.find(Tree.class, idIn);
//
//            int treeLeft = tree.getLeft();
//            int treeRight = tree.getRight();
//
//            CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
//            CriteriaQuery<Tree> criteriaQuery = criteriaBuilder.createQuery(Tree.class);
//            Root<Tree> treeRoot = criteriaQuery.from(Tree.class);
//
//            List<Predicate> predicateList = new ArrayList<>();
//            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(treeRoot.get("treeLeft"), treeLeft));
//            predicateList.add(criteriaBuilder.lessThanOrEqualTo(treeRoot.get("treeRight"), treeRight));
//
//            criteriaQuery.where(predicateList.toArray(new Predicate[0]));
//            List<Tree> trees = manager.createQuery(criteriaQuery).getResultList();
//
//            for (Tree treeFromTrees : trees) {
//                manager.remove(treeFromTrees);
//            }
//
//            int deletedCount = trees.size() * 2;
//
//            predicateList.clear();
//
//            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(treeRoot.get("treeRight"), treeRight));
//
//            criteriaQuery.where(predicateList.toArray(new Predicate[0]));
//            List<Tree> treesAfterDeletedTree = manager.createQuery(criteriaQuery).getResultList();
//
//            for (Tree treeFromAfterDeleted : treesAfterDeletedTree) {
//                if (treeFromAfterDeleted.getLeft() > treeLeft) {
//                    treeFromAfterDeleted.setLeft(treeFromAfterDeleted.getLeft() - deletedCount);
//                }
//                treeFromAfterDeleted.setRight(treeFromAfterDeleted.getRight() - deletedCount);
//            }
            Tree treeToDelete = manager.find(Tree.class, idIn);
            int difference = treeToDelete.getRight() - treeToDelete.getLeft() + 1;

            for (Tree tree : treeList) {
                if (tree.getRight() > treeToDelete.getRight()) {
                    tree.setRight(tree.getRight() - difference);
                }
                if (tree.getLeft() > treeToDelete.getRight()) {
                    tree.setLeft(tree.getLeft() - difference);
                }
            }

            manager.remove(treeToDelete);

            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
        } finally {
            manager.close();
        }
    }
}
