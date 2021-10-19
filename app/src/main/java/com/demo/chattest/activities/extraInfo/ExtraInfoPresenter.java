package com.demo.chattest.activities.extraInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class ExtraInfoPresenter {

    private final ExtraInfoViewModel view;
    private final FirebaseAuth mAuth;

    public ExtraInfoPresenter(ExtraInfoViewModel view) {
        this.view = view;
        mAuth = FirebaseAuth.getInstance();
    }

    public void changeUserNickName(String login) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            if (!login.isEmpty()) {
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(login)
                        .build();

                user.updateProfile(profileUpdates);

                String msg = String.format("Your new nickname is %s", login);
                view.showInfo(msg);
                view.userSuccessNickNameChange();
            } else view.showInfo("NickName cannot be empty");
        } else view.userIsNotRegistered();
    }

    public void logOutUser() {
        if (mAuth.getCurrentUser() != null) {
            mAuth.signOut();
        }
        view.logOut();
    }

    public boolean hasUser() {
        return mAuth.getCurrentUser() != null;
    }

    public boolean hasUserNickName() {
        return mAuth.getCurrentUser().getDisplayName() != null;
    }

    public String getUserNickName() {
        return mAuth.getCurrentUser().getDisplayName();
    }
}
