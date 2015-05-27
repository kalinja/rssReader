package cz.fit.ctu.rssreader.articles;

/**
 * Created by Jakub on 19. 3. 2015.
 */
public class Feed {
    private int id;
    private String name;
    private String url;
    private String auth;
    public static enum State{
        NONE, FAILED_WITHOUT_LOGIN, FAILED_WITH_LOGIN, COMPLETED
    };
    private State state;

    public Feed(){
        id = -1;
        name = "";
        url = "";
        auth = "";
        state = State.NONE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
