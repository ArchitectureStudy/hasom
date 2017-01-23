package com.hasom.mvc.IssueDetail.model;

import com.hasom.mvc.base.network.GithubService;
import com.hasom.mvc.base.util.Define;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by leejunho on 2017. 1. 14..
 */

public class IssueDetailModel {

    private IssueDetailDTO issueDetailDTO = new IssueDetailDTO();
    private IssueDetailModel.DetailModelDataChange modelDataChange;


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

        GithubService issueService = GithubService.retrofit.create(GithubService.class);

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
