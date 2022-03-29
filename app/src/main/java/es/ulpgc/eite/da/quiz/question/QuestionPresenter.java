package es.ulpgc.eite.da.quiz.question;

import android.util.Log;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.quiz.app.AppMediator;
import es.ulpgc.eite.da.quiz.app.CheatToQuestionState;
import es.ulpgc.eite.da.quiz.app.QuestionToCheatState;

public class QuestionPresenter implements QuestionContract.Presenter {

  public static String TAG = QuestionPresenter.class.getSimpleName();

  private AppMediator mediator;
  private WeakReference<QuestionContract.View> view;
  private QuestionState state;
  private QuestionContract.Model model;

  public QuestionPresenter(AppMediator mediator) {
    this.mediator = mediator;
    state = mediator.getQuestionState();
  }

  @Override
  public void onStart() {
    Log.e(TAG, "onStart()");

    // call the model
    state.question = model.getQuestion();
    state.option1 = model.getOption1();
    state.option2 = model.getOption2();
    state.option3 = model.getOption3();

    // reset state to tests
    state.answerCheated=false;
    state.optionClicked = false;
    state.option = 0;

    // update the view
    disableNextButton();
    view.get().resetReply();
  }


  @Override
  public void onRestart() {
    Log.e(TAG, "onRestart()");

    //TODO: falta implementacion

  }


  @Override
  public void onResume() {
    Log.e(TAG, "onResume()");

    //TODO: falta implementacion

    // use passed state if is necessary
    CheatToQuestionState savedState = getStateFromCheatScreen();
    if (savedState != null) {

      // fetch the model
    }

    // update the view
    view.get().displayQuestion(state);
  }


  @Override
  public void onDestroy() {
    Log.e(TAG, "onDestroy()");
  }

  @Override
  public void onOptionButtonClicked(int option) {
    Log.e(TAG, "onOptionButtonClicked()");

    state.option = option;
    enableNextButton();
    state.optionClicked = true;
    state.optionEnabled = false;
    boolean isCorrect = model.isCorrectOption(option);
    if(isCorrect) {
      state.cheatEnabled=false;

    } else {
      state.cheatEnabled=true;
    }
    view.get().displayQuestion(state);
    view.get().updateReply(isCorrect);

  }

  @Override
  public void onNextButtonClicked() {
    Log.e(TAG, "onNextButtonClicked()");


  }

  @Override
  public void onCheatButtonClicked() {
    Log.e(TAG, "onCheatButtonClicked()");
    QuestionToCheatState questionToCheatState = new QuestionToCheatState();
    boolean isCorrect = model.isCorrectOption(1);
    if(isCorrect)
    {
      questionToCheatState.answer = state.option1;
    }else if(model.isCorrectOption(2)){
      questionToCheatState.answer = state.option2;
    }else{
      questionToCheatState.answer = state.option3;
    }

    mediator.setQuestionToCheatState(questionToCheatState);
    view.get().navigateToCheatScreen();
  }

  private void passStateToCheatScreen(QuestionToCheatState state) {

    //TODO: falta implementacion

  }

  private CheatToQuestionState getStateFromCheatScreen() {

    //TODO: falta implementacion

    return null;
  }

  private void disableNextButton() {
    state.optionEnabled=true;
    state.cheatEnabled=true;
    state.nextEnabled=false;

  }

  private void enableNextButton() {
    state.optionEnabled=false;

    if(!model.hasQuizFinished()) {
      state.nextEnabled=true;
    }
  }

  @Override
  public void injectView(WeakReference<QuestionContract.View> view) {
    this.view = view;
  }

  @Override
  public void injectModel(QuestionContract.Model model) {
    this.model = model;
  }

}
