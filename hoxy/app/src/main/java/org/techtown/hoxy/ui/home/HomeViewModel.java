package org.techtown.hoxy.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> text_title, text_content1, text_content2, text_content3, text_content1_time,
            text_content2_time, text_content3_time;

    public HomeViewModel() {
        text_title = new MutableLiveData<>();

        text_content1 = new MutableLiveData<>();
        text_content2 = new MutableLiveData<>();
        text_content3 = new MutableLiveData<>();

        text_content1_time = new MutableLiveData<>();
        text_content2_time = new MutableLiveData<>();
        text_content3_time = new MutableLiveData<>();

        text_title.setValue("혹시 어떻게 버리지..?");

        text_content1.setValue("1번 게시글");
        text_content2.setValue("2번 게시글");
        text_content3.setValue("3번 게시글");

        text_content1_time.setValue("2020-04-08");
        text_content2_time.setValue("2020-04-09");
        text_content3_time.setValue("2020-04-10");

    }

    public LiveData<String> getTitle() {
        return text_title;
    }

    public LiveData<String> getContent1() {
        return text_content1;
    }
    public LiveData<String> getContent2() {
        return text_content2;
    }
    public LiveData<String> getContent3() {
        return text_content3;
    }

    public LiveData<String> getContent1Time() {
        return text_content1_time;
    }
    public LiveData<String> getContent2Time() {
        return text_content2_time;
    }  public LiveData<String> getContent3Time() {
        return text_content3_time;
    }




}