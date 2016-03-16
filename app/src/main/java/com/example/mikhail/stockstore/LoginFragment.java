package com.example.mikhail.stockstore;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mikhail.stockstore.AsyncClasses.AsyncRequestToServer;
import com.example.mikhail.stockstore.AsyncClasses.OnTaskCompleted;
import com.example.mikhail.stockstore.Classes.APIRequestConstructor;
import com.example.mikhail.stockstore.Classes.ServerResponseHandler;
import com.example.mikhail.stockstore.Classes.WorkWithResources;
import com.example.mikhail.stockstore.Constants.APIConstants;
import com.github.gorbin.asne.core.SocialNetwork;
import com.github.gorbin.asne.core.SocialNetworkManager;
import com.github.gorbin.asne.core.listener.OnLoginCompleteListener;
import com.github.gorbin.asne.vk.VkSocialNetwork;
import com.vk.sdk.VKScope;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class LoginFragment extends Fragment implements SocialNetworkManager.OnInitializationCompleteListener, OnLoginCompleteListener {

    public static SocialNetworkManager mSocialNetworkManager;
    /**
     * SocialNetwork Ids in ASNE:
     * 1 - Twitter
     * 2 - LinkedIn
     * 3 - Google Plus
     * 4 - Facebook
     * 5 - Vkontakte
     * 6 - Odnoklassniki
     * 7 - Instagram
     */

    private Button vk;

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        // init buttons and set Listener
        vk = (Button) rootView.findViewById(R.id.vk);
        vk.setOnClickListener(loginClick);

        //Get Keys for initiate SocialNetworks
        String VK_KEY = getActivity().getString(R.string.vk_app_id);

        //Chose permissions
        String[] vkScope = new String[] {
                VKScope.FRIENDS,
                VKScope.WALL,
                VKScope.PHOTOS,
                VKScope.NOHTTPS,
                VKScope.STATUS,
        };

        //Use manager to manage SocialNetworks
        mSocialNetworkManager = (SocialNetworkManager) getFragmentManager().findFragmentByTag(LoginActivity.SOCIAL_NETWORK_TAG);

        //Check if manager exist
        if (mSocialNetworkManager == null) {
            mSocialNetworkManager = new SocialNetworkManager();

            //Init and add to manager VkSocialNetwork
            VkSocialNetwork vkNetwork = new VkSocialNetwork(this, VK_KEY, vkScope);
            mSocialNetworkManager.addSocialNetwork(vkNetwork);

                       //Initiate every network from mSocialNetworkManager
            getFragmentManager().beginTransaction().add(mSocialNetworkManager, LoginActivity.SOCIAL_NETWORK_TAG).commit();
            mSocialNetworkManager.setOnInitializationCompleteListener(this);
        } else {
            //if manager exist - get and setup login only for initialized SocialNetworks
            if(!mSocialNetworkManager.getInitializedSocialNetworks().isEmpty()) {
                List<SocialNetwork> socialNetworks = mSocialNetworkManager.getInitializedSocialNetworks();
                for (SocialNetwork socialNetwork : socialNetworks) {
                    socialNetwork.setOnLoginCompleteListener(this);
                    initSocialNetwork(socialNetwork);
                }
            }
        }
        return rootView;
    }

    private void initSocialNetwork(SocialNetwork socialNetwork){
        if(socialNetwork.isConnected()){
            switch (socialNetwork.getID()){
                case VkSocialNetwork.ID:
                    vk.setText("ENTER VK profile");
                    break;
            }
        }
    }

    @Override
    public void onSocialNetworkManagerInitialized() {
        //when init SocialNetworks - get and setup login only for initialized SocialNetworks
        for (SocialNetwork socialNetwork : mSocialNetworkManager.getInitializedSocialNetworks()) {
            socialNetwork.setOnLoginCompleteListener(this);
            initSocialNetwork(socialNetwork);
        }
    }

    private View.OnClickListener loginClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int networkId = 0;
            switch (view.getId()){
                case R.id.vk:
                    networkId = VkSocialNetwork.ID;
                    break;
            }
            SocialNetwork socialNetwork = mSocialNetworkManager.getSocialNetwork(networkId);
            if(!socialNetwork.isConnected()) {
                if(networkId != 0) {
                    socialNetwork.requestLogin();
                    LoginActivity.showProgress("Loading social person");
                } else {
                    Toast.makeText(getActivity(), "Wrong networkId", Toast.LENGTH_LONG).show();
                }
            } else {
                startProfile(socialNetwork.getID());
            }
        }
    };

    @Override
    public void onLoginSuccess(int networkId) {
        Toast.makeText(getActivity(), "Success login! NetworkId = " + networkId, Toast.LENGTH_LONG).show();
    }

    private void startProfile(int networkId){
        // Вход в систему
        Toast.makeText(getActivity(), "Start profile. NetworkId = " + networkId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(int networkId, String requestID, String errorMessage, Object data) {
        LoginActivity.hideProgress();
        Toast.makeText(getActivity(), "ERROR: " + errorMessage, Toast.LENGTH_LONG).show();
    }

    public void onEnterBtnClick(View view) {
        String Login = ((EditText) getActivity().findViewById(R.id.loginField)).getText().toString();
        String Password = ((EditText) getActivity().findViewById(R.id.passwordField)).getText().toString();

        try {
            AsyncRequestToServer request = new AsyncRequestToServer(getActivity(), new OnTaskCompleted() {
                @Override
                public void onTaskCompleted(JSONObject result) {
                    handler.onAuthorize(result);
                }
            });
            request.setParameters(APIRequestConstructor.authParameters(Login, Password));
            request.execute(APIConstants.USER_AUTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void OnRegisterBtnClick(View view) {
        Intent intent = new Intent(this.getActivity(), RegisterActivity.class);
        startActivity(intent);
    }

    private ServerResponseHandler handler = new ServerResponseHandler() {
        @Override
        public void onError400(JSONObject response){
            Toast.makeText(getActivity(), "ошибка 400", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError403(JSONObject response){
            Toast.makeText(getActivity(), "ошибка 403", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError404(JSONObject response){
            Toast.makeText(getActivity(), "ошибка 404", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError500(JSONObject response){
            Toast.makeText(getActivity(), "ошибка 500", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAuthorize(JSONObject response) {
            try {
                JSONObject data = new JSONObject(response.get("data").toString());
                WorkWithResources.saveToken(data.get("token").toString());
                WorkWithResources.saveUserInfo(data.get("surname").toString(), data.get("name").toString());

                Intent intent = new Intent(getActivity(), StocksActivity.class);
                startActivity(intent);
                getActivity().finish();
                //WorkWithToken.saveToken(response.get("data").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            /*Toast.makeText(getApplicationContext(), "Токен получен!",
                    Toast.LENGTH_SHORT).show();*/
        }

    };
}
