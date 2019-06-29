package ru.ulmc.school.repository.impl

import junit.framework.TestCase.assertNotNull
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.ulmc.school.model.Role
import ru.ulmc.school.model.User
import java.time.LocalDateTime.now

@SpringBootTest
class UserCommentsDaoTest {
    @Autowired
    lateinit var dao: UserCommentsDao

    @Test
    fun test() {
        var user = User(
                id= null,
                dateTime = now(),
                login = "login",
                password = "password",
                enum = Role.USER)

        dao?.persist(user)

        val userDb = dao?.findUserBy(user.id.toString())
        assertNotNull(userDb)
    }
}