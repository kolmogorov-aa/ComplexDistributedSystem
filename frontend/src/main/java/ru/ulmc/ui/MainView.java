package ru.ulmc.ui;

import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.server.VaadinServletConfiguration;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.jetbrains.annotations.NotNull;
import ru.ulmc.ui.views.TweetsView;

import javax.servlet.annotation.WebServlet;

@Push
@Theme(value = Lumo.class, variant = Lumo.DARK)
@Route
@HtmlImport("frontend://styles/shared-styles.html")
@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes")
//@PWA(name = "Project Base for Vaadin Flow with Spring", shortName = "Project Base")
public class MainView extends Div implements RouterLayout {

    public MainView() {
        H2 title = new H2("Trump's Twitter Monitor");
        title.addClassName("root-layout__title");

        Div appNavigation = getAppNav();

        Div header = new Div(title, appNavigation);
        header.addClassName("root-layout__header");
        add(header);

        addClassName("root-layout");
        setSizeFull();
    }

    @NotNull
    private Div getAppNav() {
        RouterLink main = new RouterLink("Main page", MainView.class);
        main.addClassName("root-layout__nav-item");
        main.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink tweets = new RouterLink("Tweets", TweetsView.class);
        tweets.addClassName("root-layout__nav-item");
        tweets.setHighlightCondition(HighlightConditions.sameLocation());
        Div appNavigation = new Div();
        appNavigation.addClassName("root-layout__nav");
        appNavigation.add(main);
        appNavigation.add(tweets);
        return appNavigation;
    }

}
