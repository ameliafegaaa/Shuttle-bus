package com.example.project;
import android.os.AsyncTask;
import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;

public class ArticleAsyncTask extends AsyncTask<Void, Void, List<Article>> {

    private ArticleCallback callback;

    public ArticleAsyncTask(ArticleCallback callback) {
        this.callback = callback;
    }

    @Override
    protected List<Article> doInBackground(Void... voids) {
        try {
            ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
            Call<NewsApiResponse> call = apiService.getTopHeadlines("id", ApiClient.getApiKey());
            Response<NewsApiResponse> response = call.execute();

            if (response.isSuccessful() && response.body() != null) {
                List<Article> articles = response.body().getArticles();
                return articles;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Article> articles) {
        if (callback != null) {
            callback.onArticlesReceived(articles);
        }
    }

    public interface ArticleCallback {
        void onArticlesReceived(List<Article> articles);
    }
}
