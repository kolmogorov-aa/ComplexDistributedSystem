package ru.ulmc.ui.views;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ulmc.school.model.Role;
import ru.ulmc.school.model.User;
import ru.ulmc.school.repository.impl.UserCommentsDao;
import ru.ulmc.ui.MainView;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Collections.emptyList;

@Route(value = "users", layout = MainView.class)
public class UsersView extends Div {

    private final UserCommentsDao dao;
    private Grid<User> grid;
    private UI ui;
    private TextField login;
    private PasswordField password;

    @Autowired
    public UsersView(UserCommentsDao dao) {
        this.dao = dao;
        add(createForm());
        createGrid();
        add(grid);
    }

    private void createGrid() {
        grid = new Grid<>();
        grid.addColumn(User::getId);
        grid.addColumn(User::getLogin);
        grid.addColumn(User::getDateTime);
        grid.addColumn(User::getEnum);
        grid.addSelectionListener(event -> {
            event.getFirstSelectedItem().ifPresent(item -> {
                login.setValue(item.getLogin());
                password.setValue(item.getPassword());
            });
        });
    }

    private FormLayout createForm() {
        login = new TextField();
        password = new PasswordField();
        Button button = new Button("Save user");
        button.addClickListener(event -> {
            User user = new User(null,
                    LocalDateTime.now(),
                    login.getValue(),
                    password.getValue(),
                    Role.USER, emptyList());

            dao.persist(user);
            List<User> all = dao.findAll();
            grid.setItems(all);
        });
        FormLayout form = new FormLayout();
        form.addFormItem(login, "Login");
        form.addFormItem(password, "Password");
        form.add(button);
        return form;
    }


    @Override
    protected void onAttach(AttachEvent attachEvent) {
        ui = attachEvent.getUI();
    }
}
