package ru.ulmc.ui;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.server.VaadinServletConfiguration;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import ru.ulmc.ui.views.TweetsView;

import javax.servlet.annotation.WebServlet;

@Push
@Theme(value = Lumo.class, variant = Lumo.DARK)
@Route
@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes")
//@PWA(name = "Project Base for Vaadin Flow with Spring", shortName = "Project Base")
public class MainView extends Div implements RouterLayout {

    public MainView() {
        RouterLink tweets = new RouterLink("Tweets", TweetsView.class);
        VerticalLayout vl = new VerticalLayout();
        vl.add(new Label("Hello, World"));
        vl.add(tweets);
        add(vl);
    }

}
