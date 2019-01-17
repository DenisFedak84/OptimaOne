package com.aimprosoft.android.optima.centralizedApp.activity.optilife;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.app.receiver.LogoutTimeReceiver;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.ActivationCostDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.AgreementsDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.BundleDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyMeterDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDateIntervalDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDetailsDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasDetailOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.InternetDetailOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.LineNumbersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.LineTypesDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.NetworksDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.ServicesDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TlcDetailsOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.WlrOfferDetailsDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.InternetDetailOffers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.LineTypes;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Networks;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Services;
import com.aimprosoft.android.optima.centralizedApp.db.entity.WlrOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.service.net.EnergyAdditionalWebServices;
import com.aimprosoft.android.optima.centralizedApp.util.AlarmUtils;
import com.aimprosoft.android.optima.centralizedApp.util.LocalSharedPreferencesManager;
import com.google.analytics.tracking.android.EasyTracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseActivity extends Activity {
    public final String NUMERIC = "^\\d+$";
    private final String TAG = this.getClass().getName();

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);

    protected EasyTracker tracker;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.regular_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tracker = EasyTracker.getInstance(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        tracker.activityStart(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tracker.activityStop(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (isLogoutNeeded()) {
//            startActivityWithTopFlag(LoginActivity.class);
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.esci: {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }

            case R.id.impostazioni: {
                startChildActivity(ConfigActivity.class);
                break;
            }

            case R.id.autotest: {
                startChildActivity(AutoTestActivity.class);
                break;
            }
        }
        return true;
    }

    public boolean checkForLogout() {
        boolean isUserLoggined = LocalSharedPreferencesManager.getInstance().getSharedPreferencesBooleanValue(MyApplication.getContext(),
                LocalSharedPreferencesManager.IS_USER_LOGGINED);
        if (!isUserLoggined) {
            AlarmUtils.cancelAlarm(this, LogoutTimeReceiver.class);
            startActivityWithTopFlag(LoginActivity.class);
        }
        return isUserLoggined;
    }

    public Date parseDate(String dateStr) {
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            Log.e(this.getClass().getName(), "can't parse date", e);
        }
        return date;
    }

    public EditText getEmailTextField(LinearLayout mainLinearLayout, int indexChildView) {
        RelativeLayout ll = (RelativeLayout) mainLinearLayout.getChildAt(indexChildView);
        return (EditText) ll.getChildAt(0);
    }

    public boolean isLastEmailValid(LinearLayout mainLinearLayout) {
        RelativeLayout llBoxEmail = (RelativeLayout) mainLinearLayout.getChildAt(mainLinearLayout.getChildCount() - 1);
        EditText tvCheckMail = (EditText) llBoxEmail.getChildAt(0);
        boolean isMailValid = checkMail(tvCheckMail, llBoxEmail);
        if (isMailValid) {
            tvCheckMail.setEnabled(false);
            llBoxEmail.findViewById(R.id.removeButton).setVisibility(View.VISIBLE);
        }
        return isMailValid;
    }

    public void colorLengthValidation(EditText editText, CharSequence text, int length) {
        if (text.length() >= length) {
            editText.setTextColor(getResources().getInteger(R.integer.text_black));
        } else {
            editText.setTextColor(getResources().getInteger(R.integer.text_red));
        }
    }

    public void setSpinner(Spinner spinner, List<? extends Object> list) {
        try {
            ArrayAdapter<Object> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list.toArray());
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter);
        } catch (Exception e) {
            Log.w(TAG, "setSpinner has false options", e);
        }
    }

    public boolean checkRadioGroupForSelection(boolean isAnimated, RadioGroup radioGroup) {
        boolean flag = true;
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            flag = false;
            if (isAnimated)
                radioGroup.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
        }
        return flag;
    }

    public void removeAllTableContent(LinearLayout tableLayout) {
        int childCount = tableLayout.getChildCount();
        if (childCount > 1) {
            for (int i = childCount - 1; i > 0; i--) {
                tableLayout.removeViewAt(i);
            }
        }
    }

    public boolean isCheckBoxChecked(boolean isAnimated, int checkBoxId) {
        boolean result = true;
        CheckBox checkBox = (CheckBox) findViewById(checkBoxId);
        if (!checkBox.isChecked()) {
            if (isAnimated) {
                checkBox.requestFocus();
                checkBox.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
            }
            result = false;
        }
        return result;
    }

    public boolean isAllMailValid(boolean animatedFlag, View view, Integer... ids) {
        boolean result = true;
        for (Integer id : ids) {
            EditText editText = (EditText) view.findViewById(id);
            if (editText.getText() == null || editText.getText().toString().trim().length() == 0) {
                if (animatedFlag) {
                    editText.requestFocus();
                    Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
                    view.findViewById(id).startAnimation(shake);
                }
                result = false;
            }
        }
        return result;
    }

    public boolean checkMail(EditText mail, View view) {
        boolean result = !isAllMailValid(true, view, mail.getId()) && !mail.getText().toString().trim().isEmpty();
        if (isAllMailValid(true, view, mail.getId())) {
            result = isMailAdressValid(mail.getText().toString().trim());
            if (!result) {
                mail.requestFocus();
                mail.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
//                Toast.makeText(this, "Inserire un valore valido in E-mail", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, getString(R.string.valid_email), Toast.LENGTH_SHORT).show();
            }
        }
        return result;
    }

    public boolean isMailAdressValid(String mail) {
//        String emailRegExp = ".+[.][a-z]+$";
        String emailRegExp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        Pattern p = Pattern.compile(emailRegExp, Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(mail.trim());
        return matcher.matches();
    }

    public String getAllServiceDesc(WlrOfferDetails wlrOfferDetails) {
        String result = "";
        if (wlrOfferDetails.getServicesId() != null) {
            List<Services> serviceses =
                    com.aimprosoft.android.optima.centralizedApp.db.configuratoreEngineDb.configuratoreEngineDAO.ServicesDAOImpl.
                            getServicesByIdRange(TextUtils.join(",", wlrOfferDetails.getServicesId().split(", ")));
            for (Services services : serviceses) {
                result += services.getServiceDesc() + "\n";
            }
        }
        return result.length() > 0 ? result.substring(0, result.length() - 1) : result;
    }

    public boolean checkSpinnerSelection(boolean animatedFlag, int id) {
        boolean result = true;
        Spinner spinner = (Spinner) findViewById(id);
        if (spinner.getSelectedItemPosition() == 0 | spinner.getSelectedItemPosition() == -1) {
            if (animatedFlag) {
                spinner.requestFocus();
                Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
                spinner.startAnimation(shake);
            }
            result = false;
        }
        return result;
    }

    public String getPotenzaDescription(EnergyOfferDetails energyOfferDetails, boolean isDetailPage) {
        String potenzaDesc;
//        if (energyOfferDetails.getTensione() != Constants.BT && energyOfferDetails.getTensione() != Constants.ZERO && !energyOfferDetails.isQuestionarioUsing()) {
        if (energyOfferDetails.getTensione() != Constants.BT && !energyOfferDetails.isQuestionarioUsing()) {
            if (!energyOfferDetails.isExistingClientOffer()) {
                potenzaDesc = getResultPotenza(energyOfferDetails, isDetailPage);
            } else {
                potenzaDesc = energyOfferDetails.getPotenzaReal() + Constants.ENERGY_UNITS;
            }
        } else {
            potenzaDesc = new EnergyMeterDAOImpl().getEnergyMeterDescById(energyOfferDetails.getEnergyMeter()) +
                    (!energyOfferDetails.isExistingClientOffer() && energyOfferDetails.getTipologiaContratto() == Constants.DOMESTICO ? Constants.ENERGY_UNITS : Constants.EMPTY_STRING);
        }
        return potenzaDesc;
    }

    private String getResultPotenza(EnergyOfferDetails energyOfferDetails, boolean isFinalResult) {
        String result = "";
        if (!isFinalResult) {
            String[] extremum = new EnergyOfferDateIntervalDAOImpl().getMinAndMaxPotenzaImpegnataOfPod(energyOfferDetails.getEnergyDetailOfferId());
            if (extremum != null && extremum.length > 1 && extremum[0] != null) {
                result = extremum[1] != null && !extremum[0].equals(extremum[1]) ?
                        extremum[0] + " - " + extremum[1] + Constants.ENERGY_UNITS :
                        extremum[0] + Constants.ENERGY_UNITS;
            }
        } else {
            result = energyOfferDetails.getPotenzaStimata() + Constants.ENERGY_UNITS;
        }
        return result;
    }

    public void backAction(View v) {
        finish();
        LocalSharedPreferencesManager.getInstance().storeSharedPreferencesBooleanValue(this, LocalSharedPreferencesManager.IS_APP_CHECKED_FOR_UPDATE, false);
    }

    public int uniqueMergedListItemSize(List<Integer> primaryList, List<Integer> secondaryList) {
        int uniqueItemsCount = primaryList.size();
        for (int i = 0; i < secondaryList.size(); i++) {
            if (!primaryList.contains(secondaryList.get(i))) {
                uniqueItemsCount += 1;
            }
        }
        return uniqueItemsCount;
    }

    public void closeAll(View v) {
        new TlcDetailsOfferDAOImpl().deleteTlcOfferDetailsWitTlcOfferId(0);
        new EnergyOfferDetailsDAOImpl().deleteEnergyDetailslByEnergyOfferId(0);
        new GasDetailOffersDAOImpl().deleteGasDetailslByGasOfferId(0);
        new WlrOfferDetailsDAOImpl().deleteWlrOfferDetailsByMainWlrOfferId(0);
        new InternetDetailOffersDAOImpl().deleteInternetDetailslByInternetOfferId(0);
        LocalSharedPreferencesManager.getInstance().storeSharedPreferencesBooleanValue(this, LocalSharedPreferencesManager.IS_APP_CHECKED_FOR_UPDATE, false);
//        autorizationCheck();
        startActivityWithTopFlag(MainActivity.class);
        finish();
    }

//    public void autorizationCheck() {
//        if (isLogoutNeeded()) {
//            LocalSharedPreferencesManager.getInstance().storeSharedPreferencesBooleanValue(this,
//                    LocalSharedPreferencesManager.IS_USER_LOGGINED,
//                    false);
//            startActivityWithTopFlag(LoginActivity.class);
//            finish();
//        }
//    }

    private void startActivityWithTopFlag(Class clas) {
        Intent intent = new Intent(this, clas);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public int getEnergyRemoteAlgoritmResult(Map<String, Long> map, Offer offer) {
        offer.setLocalArg(false);
        return map.get(EnergyAdditionalWebServices.SUM_ATTIVA).intValue();
    }

//    public boolean isLogoutNeeded() {
//        long loginTimestamp = LocalSharedPreferencesManager.getInstance().getSharedPreferencesLongValue(this, LocalSharedPreferencesManager.LOGIN_TIMESTAMP);
//        Date loginDate = new Date(loginTimestamp);
//        Date logoutDate = LogoutUtils.getMidnightTime(loginDate);
//        Date now = new Date();
//        return now.before(loginDate) || now.after(logoutDate);
//    }

    protected void startChildActivity(Class<?> activityClass) {
        Intent intent = new Intent(BaseActivity.this, activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
        startActivity(intent);
    }

    public boolean isValid(boolean animatedFlag, Integer... ids) {
        boolean result = true;
        for (Integer id : ids) {
            EditText editText = (EditText) findViewById(id);
            if (editText.getText() == null || editText.getText().toString().trim().length() == 0) {
                if (animatedFlag) {
                    editText.requestFocus();
                    Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
                    findViewById(id).startAnimation(shake);
                }
                result = false;
            }
        }
        return result;
    }

    public void setVisibilityToGroupOfViews(int visibility, View... views) {
        for (View v : views) {
            v.setVisibility(visibility);
        }
    }

    public boolean validateInputByColor(boolean isAnimated, TextView targetView) {
        boolean result = true;
        if (targetView == null || targetView.getCurrentTextColor() != getResources().getInteger(R.integer.text_black)) {
            if (isAnimated && targetView != null) {
                targetView.requestFocus();
                Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
                targetView.startAnimation(shake);
            }
            result = false;
        }
        return result;
    }

    public boolean isTextViewValid(boolean animatedFlag, Integer... ids) {
        boolean result = true;
        for (Integer id : ids) {
            if (TextViewGetText(id) == null || TextViewGetText(id).trim().length() == 0) {
                if (animatedFlag) {
                    findViewById(id).requestFocus();
                    Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
                    findViewById(id).startAnimation(shake);
                }
                result = false;
            }
        }
        return result;
    }

    public boolean startNextActivity(Integer currentPageNumber) {
        try {
            Map navigationMap = MyApplication.get("navigationMap", Map.class);
            if (navigationMap != null) {

                for (int i = 1; i < 9; i++) {
                    Integer nextPage = currentPageNumber + i;
                    if (navigationMap.get(nextPage) != null) {
//                    String str = String.valueOf(navigationMap.get(nextPage));
//                    Class activity = Class.forName(str);
                        Class activity = (Class) navigationMap.get(nextPage);
                        startChildActivity(activity);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            Log.w(TAG, e.getMessage(), e);
        }
        return false;
    }


    public void startConfigurationOffer(Map navigationMap) {
        for (int i = 1; i < 8; i++) {
            if (navigationMap.get(i) != null) {
                Class activity = (Class) navigationMap.get(i);
                startChildActivity(activity);
                return;
            }
        }
    }

    public void validationByColor(EditText editText, CharSequence text, int decimalDigits) {
        Pattern pattern = text.toString().contains(".") ? Pattern.compile("[0-9]+?[\\.][0-9]{1," + decimalDigits + "}")
                : Pattern.compile("[0-9]*");
        Matcher matcher = pattern.matcher(text);
        if (matcher.matches()) {
            editText.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else {
            editText.setTextColor(ContextCompat.getColor(this, R.color.red));
        }
    }

    public boolean isLastPage(int currentPage) {
        if (!startNextActivity(currentPage)) {
            Intent intent = new Intent(this, Canone.class);
            intent.putExtra("ChoiceCode", 1);
            startActivity(intent);
//            new SelectWhatToShowDialog(this);
//            startChildActivity(Canone.class);
            return true;
        }
        return false;
    }

    public void checkContinuaButton(int continuaButtonId, int calcolaButtonId, int pageId) {
        if (isContinuaNeeded(pageId)) {
            findViewById(continuaButtonId).setVisibility(View.VISIBLE);
            findViewById(calcolaButtonId).setVisibility(View.GONE);
        } else {
            findViewById(continuaButtonId).setVisibility(View.GONE);
            findViewById(calcolaButtonId).setVisibility(View.VISIBLE);
        }
    }

    private boolean isContinuaNeeded(int classConstant) {
        boolean result = false;
        Map navigationMap = MyApplication.get("navigationMap", Map.class);
        if (navigationMap != null) {
            switch (classConstant) {
                case Constants.ENERGETICI:
                    result = navigationMap.size() > 1;
                    break;
                case Constants.GAS:
                    result = (navigationMap.size() > 2 && navigationMap.containsKey(1)) ||
                            (navigationMap.size() > 1 && !navigationMap.containsKey(1));
                    break;
                case Constants.TLC:
                    result = navigationMap.containsKey(6);
                    break;
            }
        }
        return result;
    }

    public void calcola(View view) {
        startChildActivity(Canone.class);
    }

    public void checkChoiceStatus(String mapKey) {
        if (MyApplication.get(mapKey, Boolean.class) != null) {
            MyApplication.remove(mapKey);
        }
    }

    public String TextViewGetText(int id) {
        TextView textView = (TextView) findViewById(id);
        return textView.getText().toString();
    }

    public String editTextGetTextString(int resourseId) {
        EditText editText = (EditText) findViewById(resourseId);
        if (editText != null)
            return editText.getText().toString().trim();
        return null;
    }

    public void changeViewVisibility(int viewVisibilityGoneId, int viewVisibilityVisibleId) {
        findViewById(viewVisibilityGoneId).setVisibility(View.GONE);
        findViewById(viewVisibilityVisibleId).setVisibility(View.VISIBLE);
    }

    public void setTextToTextView(int id, String text) {
        TextView textView = (TextView) findViewById(id);
        if (textView != null && !TextUtils.isEmpty(text))
            textView.setText(text);
    }

    public void startArchivioActivity(View v) {
        startChildActivity(Archivio.class);
    }

    public String getEnergyOfferKwl(EnergyOffer energyOffer) {
        return String.valueOf(energyOffer != null ? energyOffer.getYearlyConsumption() : 0);
    }

    public String getGasOfferSmc(GasOffer gasOffer) {
        return String.valueOf(gasOffer != null ? gasOffer.getYearlyConsumption() : 0);
    }

    public void showCompleteOffer(int energeticiId, int gasId, int telefoniaId, int internetId, int mobileId) {
        Offer offer = MyApplication.get("offerEntity", Offer.class);

        findViewById(energeticiId).setVisibility(offer != null && offer.getEnergyOfferId() == null ? View.GONE : View.VISIBLE);
        findViewById(gasId).setVisibility(offer != null && offer.getGasOfferId() == null ? View.GONE : View.VISIBLE);
        findViewById(internetId).setVisibility(offer != null && offer.getInternetOfferId() == null ? View.GONE : View.VISIBLE);
        findViewById(mobileId).setVisibility(offer != null && offer.getMobileOfferId() == null ? View.GONE : View.VISIBLE);
        findViewById(telefoniaId).setVisibility(offer != null && offer.getTlcOfferId() == null && offer.getWlrOfferId() == null ? View.GONE : View.VISIBLE);
    }

//    public void openFileInSeparateApp(String fileName) {
//        File fileToOpen = new File(android.os.Environment.getExternalStorageDirectory().getAbsolutePath() +
//                File.separator + "OptiLifeDoc" + File.separator + fileName);
//        if (!fileToOpen.exists()) {
//            Toast.makeText(this, "isn't configured", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        String extension = fileToOpen.getName().substring(
//                fileToOpen.getName().lastIndexOf(".") + 1);
//        String type = mime.getMimeTypeFromExtension(extension);
//        intent.setAction(Intent.ACTION_VIEW);
//        intent.setDataAndType(Uri.fromFile(fileToOpen), type);
//        startActivity(intent);
//    }

    public String convertDate(Date dateStr) {
        return simpleDateFormat.format(dateStr);
    }

    public String getNextDay(String date) {
        String newDate = "";
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(simpleDateFormat.parse(date));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            newDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.getTimeInMillis());
        } catch (Exception e) {
            Log.e(TAG, "can't format date", e);
        }
        return newDate;
    }


    public String getLastDayOfMonth(String date, int plusMonth) {
        try {
            Date currentDate = simpleDateFormat.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentDate);
            cal.add(Calendar.MONTH, plusMonth);
            cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
            return simpleDateFormat.format(cal.getTime());
        } catch (ParseException ignored) {
        }
        return "";
    }

    public double getCostoDiAttivazioneUnaTantum(Offer offer) {
        ActivationCostDAOImpl activationCostDAO = new ActivationCostDAOImpl();
        ServicesDAOImpl servicesDAO = new ServicesDAOImpl();
        LineNumbersDAOImpl lineNumbersDAO = new LineNumbersDAOImpl();
        NetworksDAOImpl networksDAO = new NetworksDAOImpl();
        List<Object[]> listOfCosts = activationCostDAO.getActivationCost(getOfferCoedLine(offer));
        Double activationCost = 0.0;
        if (listOfCosts.size() != 0) {
            Object[] objects = listOfCosts.get(0);
            activationCost += (Double) objects[0];
//            activationCost += offer.getTlcOfferId() != null ?
//                    (offer.getOldTlcOfferId() == 0 ? activationCostDAO.getActivationCostByServiceId(1) : 0) : 0;
            List<WlrOfferDetails> wlrOfferDetailses = new WlrOfferDetailsDAOImpl().getWlrOfferDetailsByWlrOfferId(offer.getWlrOfferId() != null ? offer.getWlrOfferId() : 0, true);
            LineTypesDAOImpl lineTypesDAO = new LineTypesDAOImpl();
//            if ((!new WlrOfferDetailsDAOImpl().isExistingOffersUsed(offer.getWlrOfferId() != null ? offer.getWlrOfferId() : 0)) &&
//                    (!new TlcDetailsOfferDAOImpl().isExistingOffersUsed(offer.getTlcOfferId() != null ? offer.getTlcOfferId() : 0))) {
            for (WlrOfferDetails offerDetails : wlrOfferDetailses) {
                Networks networks = networksDAO.getNetworkById(offerDetails.getNetworkId());
                LineTypes lineTypes = lineTypesDAO.getLineTypesById(offerDetails.getLineId());
                activationCost += (double) lineNumbersDAO.getLineNumbersById(offerDetails.getNumLines()).getLineNumberDesc() *
                        (lineTypes.getActivationCost() +
                                lineTypes.getActivationCostTLC() +
                                networks.getActivationCost());
                List<Services> serviceses = servicesDAO.getServicesByIds(offerDetails.getServicesId().split(", "));
                for (Services services : serviceses) {
                    activationCost += ((double) lineNumbersDAO.getLineNumbersById(offerDetails.getNumLines()).getLineNumberDesc() * services.getActivationCost());
                }
            }

            BundleDAOImpl bundleDAO = new BundleDAOImpl();
            List<InternetDetailOffers> internetDetailOfferses = new InternetDetailOffersDAOImpl().getInternetDetailsOfferByInternetOfferId(
                    offer.getInternetOfferId() != null ? offer.getInternetOfferId() : 0);
            for (InternetDetailOffers internetDetailOffers : internetDetailOfferses) {
                activationCost += bundleDAO.getBundleById(!internetDetailOffers.isExistingClientOffer() ? internetDetailOffers.getBundleId() : 0).getActivationCost();
            }
        }

        double agreementDiscount = offer.getAgreementId() != null ? (double) new AgreementsDAOImpl().getAgreementsById(offer.getAgreementId()).getDiscountPerc() / 100 : 1;
        return activationCost * agreementDiscount;
    }

//    public double getRelaxCost(double canone, Offer offer) {
//        double contoRelax = canone;
//        AdditionalNumbersDAOImpl additionalNumbersDAO = new AdditionalNumbersDAOImpl();
//        LineTypesDAOImpl lineTypesDAO = new LineTypesDAOImpl();
//        BundleDAOImpl bundleDAO = new BundleDAOImpl();
//        ServicesDAOImpl servicesDAO = new ServicesDAOImpl();
//        WlrOffersDAOImpl wlrOffersDAO = new WlrOffersDAOImpl();
//        LineNumbersDAOImpl lineNumbersDAO = new LineNumbersDAOImpl();
//        NetworksDAOImpl networksDAO = new NetworksDAOImpl();
//
//        TlcDetailsOfferDAOImpl tlcDetailsOfferDAO = new TlcDetailsOfferDAOImpl();
//        WlrOfferDetailsDAOImpl wlrOfferDetailsDAO = new WlrOfferDetailsDAOImpl();
//
//        ClientServices existingClient = new ClientServicesDAOImpl().getClientServicesByClientId(offer.getOldTlcOfferId());
//
//        int wlrOfferId = offer.getWlrOfferId() != null ? offer.getWlrOfferId() : 0;
//        int tlcOfferId = offer.getTlcOfferId() != null ? offer.getTlcOfferId() : 0;
//        WlrOffers wlrOffers = wlrOffersDAO.getWlrOfferById(wlrOfferId);
//        List<TlcDetailsOffer> newTlcDetailsOfferList = tlcDetailsOfferDAO.getNewClientDetailsByTlcOfferId(tlcOfferId);
//        List<WlrOfferDetails> newWlrOfferDetailses = wlrOfferDetailsDAO.getNewClientDetailsByOfferId(wlrOfferId, true);
//        List<TlcDetailsOffer> oldTlcDetailsOfferList = tlcDetailsOfferDAO.getExistingClientDetailsByTlcOfferId(tlcOfferId);
//        List<WlrOfferDetails> oldWlrOfferDetailses = wlrOfferDetailsDAO.getOldClientDetailsByOfferId(wlrOfferId, true);
//        List<WlrOfferDetails> allWlrOfferDetailses = wlrOfferDetailsDAO.getWlrOfferDetailsByWlrOfferId(wlrOfferId, true);
//
//        for (WlrOfferDetails offerDetails : allWlrOfferDetailses) {
//            Networks networks = networksDAO.getNetworkById(offerDetails.getNetworkId());
//            if (offerDetails.getLineId() != 0) {
//                LineTypes lineTypes = lineTypesDAO.getLineTypesById(offerDetails.getLineId());
//                contoRelax -= lineTypes.getRelax() == 0 ? (double) lineNumbersDAO.getLineNumbersById(offerDetails.getNumLines()).getLineNumberDesc() * lineTypes.getLineCost() : 0;
//                contoRelax -= networks.getRelax() == 0 ? (double) lineNumbersDAO.getLineNumbersById(offerDetails.getNumLines()).getLineNumberDesc() * networks.getCost() : 0;
//                List<Services> serviceses = servicesDAO.getServicesByIds(offerDetails.getServicesId().split(", "));
//                for (Services services : serviceses) {
//                    contoRelax -= services.getRelax() == 0 ?
//                            ((double) lineNumbersDAO.getLineNumbersById(offerDetails.getNumLines()).getLineNumberDesc() *
//                                    (services.getServiceDesc().equals("Numerazione aggiuntiva") ?
//                                            additionalNumbersDAO.getAdditionalNumbersById(offerDetails.getServiceAddictionNumber()).getAdditionalNumberCost()
//                                            : services.getCost()))
//                            : 0;
//                }
//            }
//        }
//
//        if (oldTlcDetailsOfferList.size() > 0 | oldWlrOfferDetailses.size() > 0) {
//            ClientTlcOffers clientTlcOffers = new ClientTlcOffersDAOImpl().getClientTlcOfferById(existingClient.getTlcServiceId());
//            if (clientTlcOffers.getRelax() == 0) {
//                for (TlcDetailsOffer oldTlc : oldTlcDetailsOfferList) {
//                    contoRelax -= oldTlc.getOfferCost();
//                }
//                ClientDetailsTlcOffersDAOImpl clientDetailsTlcOffersDAO = new ClientDetailsTlcOffersDAOImpl();
//                for (WlrOfferDetails oldWlrOffer : oldWlrOfferDetailses) {
//                    ClientDetailTlcOffers currentClientDetailsTlcOffer = clientDetailsTlcOffersDAO.getClientDetailTlcOffersByLine(oldWlrOffer.getWlrName());
//                    contoRelax -= currentClientDetailsTlcOffer.getMobileOfferCost() + currentClientDetailsTlcOffer.getLocalOfferCost();
//                }
//            }
//        }
//
//        if (newWlrOfferDetailses.size() != 0 || newTlcDetailsOfferList.size() != 0) {
//            Integer localBundleId = newTlcDetailsOfferList.size() != 0 ? newTlcDetailsOfferList.get(0).getLocalBundleId() : wlrOffers.getLocalBundleId();
//            Integer mobileBundleId = newTlcDetailsOfferList.size() != 0 ? newTlcDetailsOfferList.get(0).getMobileBundleId() : wlrOffers.getMobileBundleId();
//            Bundle localBundle = bundleDAO.getBundleById(localBundleId != null ? localBundleId : 0);
//            Bundle mobileBundle = bundleDAO.getBundleById(mobileBundleId != null ? mobileBundleId : 0);
//            contoRelax -= (localBundle.getRelax() == 0 ? localBundle.getBundleCost() : 0) + (mobileBundle.getRelax() == 0 ? mobileBundle.getBundleCost() : 0);
//        }
//
//        List<InternetDetailOffers> internetOffers = new InternetDetailOffersDAOImpl().getInternetDetailsOfferByInternetOfferId(offer.getInternetOfferId() != null ? offer.getInternetOfferId() : 0);
//        for (InternetDetailOffers internetDetailOffers : internetOffers) {
//            if (!internetDetailOffers.isExistingClientOffer()) {
//                Bundle internetBundle = bundleDAO.getBundleById(internetDetailOffers.getBundleId());
//                contoRelax -= internetBundle.getRelax() == 0 ? internetBundle.getBundleCost() : 0;
//                List<Services> internetServices = servicesDAO.getServicesByIds(internetDetailOffers.getServiceId().split(", "));
//                for (Services services : internetServices) {
//                    contoRelax -= services.getRelax() == 0 ? services.getCost() : 0;
//                }
//                contoRelax -= internetDetailOffers.getRouter() == 1 ? servicesDAO.getServicesByFieldName("Router").getCost() : 0;
//            } else {
//                contoRelax -= new ClientAdslOffersDAOImpl().getClientAdslOfferById(existingClient.getAdslServiceId()).getRelax() == 0 ?
//                        internetDetailOffers.getCost() : 0;
//            }
//        }
//        return contoRelax;
//    }

    public void cleanTable(LinearLayout tableView) {
        for (int i = tableView.getChildCount() - 1; i > 0; i--) {
            tableView.removeViewAt(i);
        }
    }

    private String getOfferCoedLine(Offer offer) {
        String codeLine = "";
        if (offer != null) {
            codeLine = ((offer.getEnergyOfferId() != null && new EnergyOfferDetailsDAOImpl().getNewClientDetailsByOfferId(offer.getEnergyOfferId() != null ? offer.getEnergyOfferId() : 0).size() > 0) ? "2," : "") +
                    ((offer.getGasOfferId() != null && new GasDetailOffersDAOImpl().getNewClientDetailsByOfferId(offer.getGasOfferId() != null ? offer.getGasOfferId() : 0).size() > 0) ? "4," : "") +
                    ((offer.getInternetOfferId() != null && new InternetDetailOffersDAOImpl().getNewClientDetailsByOfferId(offer.getInternetOfferId() != null ? offer.getInternetOfferId() : 0).size() > 0) ? "3," : "") +
                    ((offer.getWlrOfferId() != null || offer.getTlcOfferId() != null) &&
                            (new TlcDetailsOfferDAOImpl().getNewClientDetailsByTlcOfferId(offer.getTlcOfferId() != null ? offer.getTlcOfferId() : 0).size() > 0 |
                                    new WlrOfferDetailsDAOImpl().getNewClientDetailsByOfferId(offer.getWlrOfferId() != null ? offer.getWlrOfferId() : 0, true).size() > 0)
                            ? "1," : "") +
                    (offer.getMobileOfferId() != null ? "5," : "");
        }
        return codeLine;
    }

    public String getConfiguratorType(int configuratorType) {
        return configuratorType == Constants.BUSINESS_CONFIGURATOR
                ? Constants.BUSINESS_CONFIGURATOR_FLAG
                : Constants.CONSUMER_CONFIGURATOR_FLAG;
    }

    public String getConsumerFiscalClassDesc(int tipoContratto) {
        String tipoContrattoDesc;
        switch (tipoContratto) {
            case 1:
                tipoContrattoDesc = getString(R.string.altri_usi);
                break;
            case 2:
                tipoContrattoDesc = getString(R.string.domestico);
                break;
            case 3:
                tipoContrattoDesc = getString(R.string.condominio);
                break;
            default:
                tipoContrattoDesc = "";
        }
        return tipoContrattoDesc;
    }
}