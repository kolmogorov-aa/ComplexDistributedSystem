package ru.ulmc.school.repository.impl

import org.springframework.stereotype.Component
import ru.ulmc.school.model.Comment
import ru.ulmc.school.model.User
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Suppress("JpaQlInspection")
@Component
open class UserCommentsDao(
        @PersistenceContext
        val entityManager: EntityManager
) {

    @Transactional
    open fun persist(user: User) : User {
        return entityManager.merge(user)
    }

    @Transactional
    open fun persist(comment: Comment) {
        entityManager.merge(comment)
    }

    @Transactional
    open fun findUserBy(uuid: String): User {
        val query = entityManager.createQuery("SELECT u from User u where u.id = :id")
        query.setParameter("id", uuid)
        return query.singleResult as User
    }


    @Transactional
    open fun findAll(): List<User> {
        val query = entityManager.createQuery("SELECT u from User u ")
        return query.resultList as List<User>
    }
}
