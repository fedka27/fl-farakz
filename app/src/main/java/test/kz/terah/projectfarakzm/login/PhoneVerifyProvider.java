package test.kz.terah.projectfarakzm.login;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

/**
 * Created by cad1l on 03.03.2018.
 */

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
