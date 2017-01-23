package com.hasom.mvc.IssueDetail.model;

import com.hasom.mvc.base.network.GithubService;
import com.hasom.mvc.base.util.Define;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by leejunho on 2017. 1. 14..
 */

public class IssueCommentModel {

    private List<IssueCommentDTO> issueCommentDTOList = new ArrayList<>();
    private IssueCommentDTO issueCommentDTO = new IssueCommentDTO();

    private IssueCommentModel.CommentModelDataChange modelDataChange;

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
    public void callIssueComment(int issueNum, int currentPage) {

        GithubService issueCommentService = GithubService.retrofit.create(GithubService.class);

        Call<List<IssueCommentDTO>> call = issueCommentService.repoIssueCommentList(Define.SEARCH_OWNER, Define.SEARCH_REPO, issueNum, currentPage);

        call.enqueue(issueCommentListCallBackListener);
    }


    public void createIssueComment(int issueNum, String token, String comment) {
        GithubService issueCommentService = GithubService.retrofit.create(GithubService.class);

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
                issueCommentDTOList.clear();
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
            if (response.isSuccessful() == true) {
                if (response == null || response.body() == null) {
                    return;
                }
                issueCommentDTO = response.body();

                if (modelDataChange != null) {
                    modelDataChange.createIssueComment(issueCommentDTO);
                }
            }
        }

        @Override
        public void onFailure(Call<IssueCommentDTO> call, Throwable t) {
        }
    };

}
