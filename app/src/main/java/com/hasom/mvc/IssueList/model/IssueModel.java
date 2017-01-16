package com.hasom.mvc.IssueList.model;

import com.hasom.mvc.util.Define;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by leejunho on 2017. 1. 7..
 */
public class IssueModel {

    private ArrayList<IssueDTO> issueList = new ArrayList<>();
    private ModelDataChange modelDataChange;

    /**
     * Retrofit2
     */
    interface IssueService {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        @GET("repos/{owner}/{repo}/issues")
        Call<List<IssueDTO>> repoIssue(
                @Path("owner") String owner,
                @Path("repo") String repo);

    }

    /**
     * update Listener
     */
    public interface ModelDataChange {
        void update(List<IssueDTO> list);
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
    public void callIssueList() {

        IssueService issueService = IssueService.retrofit.create(IssueService.class);

        Call<List<IssueDTO>> call = issueService.repoIssue(Define.SEARCH_OWNER, Define.SEARCH_REPO);

        call.enqueue(callBackListener);
    }


    /**
     * Retrofit2 CallbackListener
     */
    private Callback<List<IssueDTO>> callBackListener = new Callback<List<IssueDTO>>() {
        @Override
        public void onResponse(Call<List<IssueDTO>> call, Response<List<IssueDTO>> response) {
            if (response.isSuccessful() == true) {
                issueList.addAll(response.body());

                if (modelDataChange != null) {
                    modelDataChange.update(issueList);
                }
            }
        }

        @Override
        public void onFailure(Call<List<IssueDTO>> call, Throwable t) {

        }
    };

}
