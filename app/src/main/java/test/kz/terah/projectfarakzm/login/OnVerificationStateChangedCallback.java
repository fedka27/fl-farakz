package test.kz.terah.projectfarakzm.login;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

/**
 * Created by cad1l on 03.03.2018.
 */

interface OnVerificationStateChangedCallback {

    void onVerificationCompleted(PhoneAuthCredential credential);

    void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token);

    void onVerificationFailed(FirebaseException e);

}
