package com.hasom.mvc.CreateIssue.model;

import com.hasom.mvc.base.network.GithubService;
import com.hasom.mvc.base.util.Define;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by leejunho on 2017. 1. 7..
 */
public class CreateIssueModel {

    private CreateIssueDTO createIssueDTO = new CreateIssueDTO();
    private ModelDataChange modelDataChange;


    /**
     * update Listener
     */
    public interface ModelDataChange {
        void onSuccess(CreateIssueDTO model);
        void onFail();
    }

    /**
     * Set Listener
     * @param dataChange
     */
    public void setOnChangeListener(ModelDataChange dataChange) {
        modelDataChange = dataChange;
    }

    /**
     * Search Github Open API
     */
    public void callCreateIssue(String token, String title, String body) {

        GithubService issueService = GithubService.retrofit.create(GithubService.class);

        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "token " + token);

        Map<String, String> sendParam = new HashMap<>();
        sendParam.put("body", body);
        sendParam.put("title", title);


        Call<CreateIssueDTO> call = issueService.repoCreateIssue(Define.SEARCH_OWNER, Define.SEARCH_REPO, map, sendParam);

        call.enqueue(callBackListener);
    }


    /**
     * Retrofit2 CallbackListener
     */
    private Callback<CreateIssueDTO> callBackListener = new Callback<CreateIssueDTO>() {
        @Override
        public void onResponse(Call<CreateIssueDTO> call, Response<CreateIssueDTO> response) {
            if (response.isSuccessful() == true) {
                createIssueDTO = response.body();

                if (modelDataChange != null) {
                    modelDataChange.onSuccess(createIssueDTO);
                }
            }
        }

        @Override
        public void onFailure(Call<CreateIssueDTO> call, Throwable t) {
            modelDataChange.onFail();

        }
    };

}
