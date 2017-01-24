package com.hasom.mvc.IssueList.model;

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
 * Created by leejunho on 2017. 1. 7..
 */
public class IssueModel {

    private ArrayList<IssueDTO> issueList = new ArrayList<>();
    private ModelDataChange modelDataChange;


    /**
     * update Listener
     */
    public interface ModelDataChange {
        void onSuccess(List<IssueDTO> list);
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
    public void callIssueList(int currentPage) {

        GithubService issueService = GithubService.retrofit.create(GithubService.class);

        Map<String, String> map = new HashMap<>();
        map.put("Cache-Control", "max-age=0, private, must-revalidate");

        Call<List<IssueDTO>> call = issueService.repoIssue(Define.SEARCH_OWNER, Define.SEARCH_REPO, currentPage, map);

        call.enqueue(callBackListener);
    }


    /**
     * Retrofit2 CallbackListener
     */
    private Callback<List<IssueDTO>> callBackListener = new Callback<List<IssueDTO>>() {
        @Override
        public void onResponse(Call<List<IssueDTO>> call, Response<List<IssueDTO>> response) {
            if (response.isSuccessful() == true) {
                issueList.clear();
                issueList.addAll(response.body());

                if (modelDataChange != null) {
                    modelDataChange.onSuccess(issueList);
                }
            }
        }

        @Override
        public void onFailure(Call<List<IssueDTO>> call, Throwable t) {
            modelDataChange.onFail();

        }
    };

}
