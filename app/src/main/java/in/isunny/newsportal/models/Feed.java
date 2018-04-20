package in.isunny.newsportal.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunny on 4/17/2018.
 */

public class Feed {

    private String status, totalResults;
    private List<Articles> articles = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public List<Articles> getArticles() {
        return articles;
    }

    public void setArticles(List<Articles> articles) {
        this.articles = articles;
    }
}
