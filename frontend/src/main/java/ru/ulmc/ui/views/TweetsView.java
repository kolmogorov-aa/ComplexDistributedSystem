package ru.ulmc.ui.views;

import com.google.common.collect.EvictingQueue;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.EventListener;
import ru.ulmc.school.api.entity.TweetMsg;
import ru.ulmc.events.TweetEvent;
import ru.ulmc.ui.MainView;

import java.util.Queue;

@Route(value ="tweets", layout = MainView.class)
@HtmlImport("frontend://src/component/tweet-info.html")
public class TweetsView extends Div implements BeforeEnterObserver, ApplicationListener<TweetEvent> {

    private Grid<TweetMsg> grid;
    private UI ui;
    private Queue<TweetMsg> tweets = EvictingQueue.create(10);

    public TweetsView(@Qualifier("tweetsMulticaster") ApplicationEventMulticaster multicaster) {
        multicaster.addApplicationListener(this);
        grid = new Grid<>();
        grid.addColumn(TweetMsg::getDate)
        .setWidth("150px")
        .setFlexGrow(1);
        grid.addColumn(TweetMsg::getText)
        .setFlexGrow(5);
        add(grid);
    }


    @Override
    protected void onAttach(AttachEvent attachEvent) {
        ui = attachEvent.getUI();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {

    }

    @Override
    @EventListener
    public void onApplicationEvent(TweetEvent event) {
        tweets.add(event.getTweet());
        ui.access(() -> grid.setItems(tweets));
    }
}
