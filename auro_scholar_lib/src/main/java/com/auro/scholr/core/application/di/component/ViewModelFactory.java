package com.auro.scholr.core.application.di.component;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.auro.scholr.home.domain.usecase.HomeDbUseCase;
import com.auro.scholr.home.domain.usecase.HomeRemoteUseCase;
import com.auro.scholr.home.domain.usecase.HomeUseCase;
import com.auro.scholr.home.presentation.view.fragment.QuizTestFragment;
import com.auro.scholr.home.presentation.viewmodel.CardViewModel;
import com.auro.scholr.home.presentation.viewmodel.DemographicViewModel;
import com.auro.scholr.home.presentation.viewmodel.HomeViewModel;
import com.auro.scholr.home.presentation.viewmodel.KYCViewModel;
import com.auro.scholr.home.presentation.viewmodel.QuizTestViewModel;
import com.auro.scholr.home.presentation.viewmodel.QuizViewModel;
import com.auro.scholr.home.presentation.viewmodel.ScholarShipViewModel;
import com.auro.scholr.payment.domain.PaymentRemoteUseCase;
import com.auro.scholr.payment.domain.PaymentUseCase;
import com.auro.scholr.payment.presentation.view.fragment.SendMoneyFragment;
import com.auro.scholr.payment.presentation.viewmodel.SendMoneyViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {


    /*Home Module*/
    private HomeUseCase homeUseCase;
    private HomeDbUseCase homeDbUseCase;
    private HomeRemoteUseCase homeRemoteUseCase;

    /*Payment Module*/
    private PaymentUseCase paymentUseCase;
    private PaymentRemoteUseCase paymentRemoteUseCase;


    public ViewModelFactory(Object objectOne, Object objectTwo) {
        if (objectOne instanceof PaymentUseCase && objectTwo instanceof PaymentRemoteUseCase) {
            this.paymentUseCase = (PaymentUseCase) objectOne;
            this.paymentRemoteUseCase = (PaymentRemoteUseCase) objectTwo;
        }

    }

    public ViewModelFactory(Object objectOne, Object objectTwo, Object objectThree) {

        if (objectOne instanceof HomeUseCase && objectTwo instanceof HomeDbUseCase && objectThree instanceof HomeRemoteUseCase) {
            this.homeUseCase = (HomeUseCase) objectOne;
            this.homeDbUseCase = (HomeDbUseCase) objectTwo;
            this.homeRemoteUseCase = (HomeRemoteUseCase) objectThree;
        }

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {

            return (T) new HomeViewModel(homeUseCase, homeDbUseCase, homeRemoteUseCase);

        } else if (modelClass.isAssignableFrom(CardViewModel.class)) {

            return (T) new CardViewModel(homeUseCase, homeDbUseCase, homeRemoteUseCase);

        } else if (modelClass.isAssignableFrom(QuizViewModel.class)) {

            return (T) new QuizViewModel(homeUseCase, homeDbUseCase, homeRemoteUseCase);

        } else if (modelClass.isAssignableFrom(KYCViewModel.class)) {

            return (T) new KYCViewModel(homeUseCase, homeDbUseCase, homeRemoteUseCase);

        } else if (modelClass.isAssignableFrom(ScholarShipViewModel.class)) {

            return (T) new ScholarShipViewModel(homeUseCase, homeDbUseCase, homeRemoteUseCase);

        } else if (modelClass.isAssignableFrom(DemographicViewModel.class)) {

            return (T) new DemographicViewModel(homeUseCase, homeDbUseCase, homeRemoteUseCase);

        } else if (modelClass.isAssignableFrom(QuizTestViewModel.class)) {

            return (T) new QuizTestViewModel(homeUseCase, homeDbUseCase, homeRemoteUseCase);

        }else if (modelClass.isAssignableFrom(SendMoneyViewModel.class)) {

            return (T) new SendMoneyViewModel(paymentUseCase, paymentRemoteUseCase);

        }


        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}


