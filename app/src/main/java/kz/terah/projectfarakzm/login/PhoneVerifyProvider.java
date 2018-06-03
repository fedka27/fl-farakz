package kz.terah.projectfarakzm.login;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

//Адапртер проверки номера
class PhoneVerifyProvider extends PhoneAuthProvider.OnVerificationStateChangedCallbacks {

    private final OnVerificationStateChangedCallback callback;

    private PhoneVerifyProvider(OnVerificationStateChangedCallback callback) {
        this.callback = callback;
    }

    static PhoneVerifyProvider getInstance(OnVerificationStateChangedCallback callback) {
        return new PhoneVerifyProvider(callback);
    }


    @Override
    public void onVerificationCompleted(PhoneAuthCredential credential) {
        callback.onVerificationCompleted(credential);
    }

    @Override
    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
        callback.onCodeSent(verificationId, token);
    }

    @Override
    public void onVerificationFailed(FirebaseException e) {
        callback.onVerificationFailed(e);
    }
}
