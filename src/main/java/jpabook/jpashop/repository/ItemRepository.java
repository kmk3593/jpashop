// package jpabook.jpashop.repository;

// import java.util.List;

// import javax.persistence.EntityManager;
// import org.springframework.stereotype.Repository;

// import jpabook.jpashop.domain.item.Item;
// import lombok.RequiredArgsConstructor;

// @Repository
// @RequiredArgsConstructor
// public class ItemRepository {

//     private final EntityManager em;

//     public void save(Item item) { // update 비슷한 기능
//         if (item.getId() == null) {
//             em.persist(item);
//         } else {
//             em.merge(item);
//         }
//     }

//     public Item findOne(Long id) {
//         return em.find(Item.class, id);
//     }

//     public List<Item> findAll() {
//         return em.createQuery("select i form Item i", Item.class)
//                 .getResultList();
//     }

// }
package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
