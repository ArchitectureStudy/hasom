package com.hasom.mvc.IssueDetail.model;

import android.util.Log;

import com.hasom.mvc.util.Define;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by leejunho on 2017. 1. 14..
 */

public class IssueCommentModel {

    private List<IssueCommentDTO> issueCommentDTOList = null;
    private IssueCommentDTO issueCommentDTO = null;

    private IssueCommentModel.CommentModelDataChange modelDataChange;

    /**
     * Retrofit2
     */
    interface IssueCommentListService {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        @GET("repos/{owner}/{repo}/issues/{number}/comments")
        Call<List<IssueCommentDTO>> repoIssueCommentList(
                @Path("owner") String owner,
                @Path("repo") String repo,
                @Path("number") int number);

        @POST("repos/{owner}/{repo}/issues/{number}/comments")
        Call<IssueCommentDTO> repoCreateIssueComment(
                @Path("owner") String owner,
                @Path("repo") String repo,
                @Path("number") int number,
                @Body Map<String, String> body,
                @HeaderMap Map<String, String> headers);
//                @Query("access_token") String token);

    }

    /**
     * update Listener
     */
    public interface CommentModelDataChange {
        void update(List<IssueCommentDTO> model);
        void createIssueComment(IssueCommentDTO issueComment);
    }

    /**
     * Set Listener
     * @param dataChange
     */
    public void setOnChangeListener(IssueCommentModel.CommentModelDataChange dataChange) {
        modelDataChange = dataChange;
    }

    /**
     * IssueDetail Github Open API
     */
    public void callIssueComment(int issueNum) {

        IssueCommentModel.IssueCommentListService issueCommentService = IssueCommentModel.IssueCommentListService.retrofit.create(IssueCommentModel.IssueCommentListService.class);

        Call<List<IssueCommentDTO>> call = issueCommentService.repoIssueCommentList(Define.SEARCH_OWNER, Define.SEARCH_REPO, issueNum);

        call.enqueue(issueCommentListCallBackListener);
    }


    public void createIssueComment(int issueNum, String token, String comment) {
        IssueCommentModel.IssueCommentListService issueCommentService = IssueCommentModel.IssueCommentListService.retrofit.create(IssueCommentModel.IssueCommentListService.class);

        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "token " + token);

        Map<String, String> body = new HashMap<>();
        body.put("body", comment);

        Call<IssueCommentDTO> call = issueCommentService.repoCreateIssueComment(Define.SEARCH_OWNER, Define.SEARCH_REPO, issueNum, body, map);

        call.enqueue(createIssueCommentCallBackListener);
    }

    /**
     * Retrofit2 CallbackListener
     */
    private Callback<List<IssueCommentDTO>> issueCommentListCallBackListener = new Callback<List<IssueCommentDTO>>() {
        @Override
        public void onResponse(Call<List<IssueCommentDTO>> call, Response<List<IssueCommentDTO>> response) {
            if (response.isSuccessful() == true) {
                if (response == null || response.body() == null) {
                   return;
                }
                issueCommentDTOList = new ArrayList<>();
                issueCommentDTOList.addAll(response.body());

                if (modelDataChange != null) {
                    modelDataChange.update(issueCommentDTOList);
                }
            }
        }

        @Override
        public void onFailure(Call<List<IssueCommentDTO>> call, Throwable t) {

        }
    };


    private Callback<IssueCommentDTO> createIssueCommentCallBackListener = new Callback<IssueCommentDTO>() {
        @Override
        public void onResponse(Call<IssueCommentDTO> call, Response<IssueCommentDTO> response) {
            Log.e("", "success response");
            if (response.isSuccessful() == true) {
                if (response == null || response.body() == null) {
                    return;
                }
                issueCommentDTO = new IssueCommentDTO();
                issueCommentDTO = response.body();

                if (modelDataChange != null) {
                    modelDataChange.createIssueComment(issueCommentDTO);
                }
            }
        }

        @Override
        public void onFailure(Call<IssueCommentDTO> call, Throwable t) {
            Log.e("", "success onFailure");
        }
    };

}
