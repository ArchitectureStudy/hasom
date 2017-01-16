package com.hasom.mvc.IssueDetail.model;

import com.hasom.mvc.util.Define;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by leejunho on 2017. 1. 14..
 */

public class IssueDetailModel {

    private IssueDetailDTO issueDetailDTO = null;
    private IssueDetailModel.DetailModelDataChange modelDataChange;

    /**
     * Retrofit2
     */
    interface IssueDetailService {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        @GET("repos/{owner}/{repo}/issues/{number}")
        Call<IssueDetailDTO> repoIssueDetail(
                @Path("owner") String owner,
                @Path("repo") String repo,
                @Path("number") int number);
    }

    /**
     * update Listener
     */
    public interface DetailModelDataChange {
        void update(IssueDetailDTO model);
    }

    /**
     * Set Listener
     * @param dataChange
     */
    public void setOnChangeListener(IssueDetailModel.DetailModelDataChange dataChange) {
        modelDataChange = dataChange;
    }

    /**
     * IssueDetail Github Open API
     */
    public void callIssueDetail(int issueNum) {

        IssueDetailModel.IssueDetailService issueService = IssueDetailModel.IssueDetailService.retrofit.create(IssueDetailModel.IssueDetailService.class);

        Call<IssueDetailDTO> call = issueService.repoIssueDetail(Define.SEARCH_OWNER, Define.SEARCH_REPO, issueNum);

        call.enqueue(detailCallBackListener);
    }


    /**
     * Retrofit2 CallbackListener
     */
    private Callback<IssueDetailDTO> detailCallBackListener = new Callback<IssueDetailDTO>() {
        @Override
        public void onResponse(Call<IssueDetailDTO> call, Response<IssueDetailDTO> response) {
            if (response.isSuccessful() == true) {
                issueDetailDTO = response.body();

                if (modelDataChange != null) {
                    modelDataChange.update(issueDetailDTO);
                }
            }
        }

        @Override
        public void onFailure(Call<IssueDetailDTO> call, Throwable t) {

        }
    };

}
