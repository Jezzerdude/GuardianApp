package com.example.guardapp.service;

import java.util.List;

import com.example.guardapp.model.Article;
import com.example.guardapp.network.interceptors.CacheInterceptor;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.Interceptor;

import static com.example.guardapp.util.date.DateMethods.dateToFromEpoch;

public class GuardNetworkService implements NetworkService {
    private TheGuardianAPIBuilder theGuardianAPIBuilder;
    private TheGuardianAPI networkAPI;

    public GuardNetworkService(Interceptor connectivityInterceptor, CacheInterceptor cacheInterceptor) {
        this.theGuardianAPIBuilder = new TheGuardianAPIBuilder(connectivityInterceptor, cacheInterceptor);
        this.networkAPI = theGuardianAPIBuilder.create(TheGuardianAPI.class);
    }

    @Override
    public Single<List<Article>> getHeadlineArticles() {
        return networkAPI.getHeadlineArticles("headline",
                20,
                "headline,thumbnail,byline,wordcount")
                .flatMap((articlesResponse) -> Observable.fromIterable(articlesResponse.getResponse().getResults())
                        .map((resultsItem -> new Article(resultsItem.getId(),
                                resultsItem.getSectionName(),
                                dateToFromEpoch(resultsItem.getWebPublicationDate()),
                                resultsItem.getWebTitle(),
                                resultsItem.getWebUrl(),
                                resultsItem.getApiUrl(),
                                resultsItem.getFields().getHeadline(),
                                resultsItem.getFields().getByline(),
                                resultsItem.getFields().getWordcount(),
                                resultsItem.getFields().getThumbnail(),
                                true
                        ))).toList());

    }

//    @Override
//    public Single<List<Article>> getSectionsArticles(List<String> sections) {
//        return networkAPI.getSectionsArticles(
//                sections.stream().collect(Collectors.joining("|")),
//                30,
//                "headline,thumbnail,byline,wordcount"
//        )
//                .flatMap((articlesResponse) -> Observable.fromIterable(articlesResponse.getResponse().getResults())
//                        .map((resultsItem -> new Article(resultsItem.getId(),
//                                resultsItem.getSectionName(),
//                                dateToFromEpoch(resultsItem.getWebPublicationDate()),
//                                resultsItem.getWebTitle(),
//                                resultsItem.getWebUrl(),
//                                resultsItem.getApiUrl(),
//                                resultsItem.getFields().getHeadline(),
//                                resultsItem.getFields().getByline(),
//                                resultsItem.getFields().getWordcount(),
//                                resultsItem.getFields().getThumbnail(),
//                                true
//                        ))).toList());
//    }
//
//    @Override
//    public Single<ArticleWithBody> getArticle(String apiUrl) {
//        return networkAPI.getArticle(
//                apiUrl,
//                "headline,thumbnail,byline,wordcount,body"
//        ).flatMap((articleResponse) -> {
//            Content articleContent = articleResponse.getResponse().getContent();
//            return Single.just(new ArticleWithBody(
//                    articleContent.getId(),
//                    articleContent.getSectionName(),
//                    dateToFromEpoch(articleContent.getWebPublicationDate()),
//                    articleContent.getWebTitle(),
//                    articleContent.getWebUrl(),
//                    articleContent.getApiUrl(),
//                    articleContent.getFields().getHeadline(),
//                    articleContent.getFields().getByline(),
//                    articleContent.getFields().getWordcount(),
//                    articleContent.getFields().getThumbnail(),
//                    articleContent.getFields().getIsLive(),
//                    articleContent.getFields().getBody()
//            ));
//        });
//
//    }
}
