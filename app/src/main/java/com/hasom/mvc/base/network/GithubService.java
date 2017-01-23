package com.hasom.mvc.base.network;

import com.hasom.mvc.CreateIssue.model.CreateIssueDTO;
import com.hasom.mvc.IssueDetail.model.IssueCommentDTO;
import com.hasom.mvc.IssueDetail.model.IssueDetailDTO;
import com.hasom.mvc.IssueList.model.IssueDTO;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by leejunho on 2017. 1. 22..
 */

public interface GithubService {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("repos/{owner}/{repo}/issues")
    Call<List<IssueDTO>> repoIssue(
            @Path("owner") String owner,
            @Path("repo") String repo,
            @Query("page") int currentPage);

    @POST("repos/{owner}/{repo}/issues")
    Call<CreateIssueDTO> repoCreateIssue(
            @Path("owner") String owner,
            @Path("repo") String repo,
            @HeaderMap Map<String, String> headers,
            @Body Map<String, String> body
            );


    @GET("repos/{owner}/{repo}/issues/{number}/comments")
    Call<List<IssueCommentDTO>> repoIssueCommentList(
            @Path("owner") String owner,
            @Path("repo") String repo,
            @Path("number") int number,
            @Query("page") int currentPage);

    @POST("repos/{owner}/{repo}/issues/{number}/comments")
    Call<IssueCommentDTO> repoCreateIssueComment(
            @Path("owner") String owner,
            @Path("repo") String repo,
            @Path("number") int number,
            @Body Map<String, String> body,
            @HeaderMap Map<String, String> headers);

    @GET("repos/{owner}/{repo}/issues/{number}")
    Call<IssueDetailDTO> repoIssueDetail(
            @Path("owner") String owner,
            @Path("repo") String repo,
            @Path("number") int number);



}
