package com.aimprosoft.android.optima.centralizedApp.activity.optilife;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.configuratoreEngineDb.configuratoreEngineDAO.LineTypeDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.configuratoreEngineDb.configuratoreEngineDAO.MobileBundleDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.configuratoreEngineDb.configuratoreEngineDAO.ServicesDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.AssicurazioneOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.BundleDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.CarriersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.CategorieUsoDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.ClassePrelievoGasDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDateIntervalDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDetailsDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergySDCDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.FiscalClassDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasDetailOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasOfferDateIntervalDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasSDCDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.InternetDetailOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.InternetOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.LineNumbersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.MobileDetailsOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.MobileOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.NetworksDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDeviceDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TLCOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TlcDetailsOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TownDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.WlrOfferDetailsDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.WlrOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.AssicurazioneOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Bundle;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDateInterval;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergySDC;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasDetailOffers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasOfferDateInterval;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasSDC;
import com.aimprosoft.android.optima.centralizedApp.db.entity.InternetDetailOffers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.InternetOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.MobileBundle;
import com.aimprosoft.android.optima.centralizedApp.db.entity.MobileDetailsOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.MobileOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.OfferDevice;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Services;
import com.aimprosoft.android.optima.centralizedApp.db.entity.TLCOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.TlcDetailsOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Town;
import com.aimprosoft.android.optima.centralizedApp.db.entity.WlrOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.db.entity.WlrOffers;
import com.aimprosoft.android.optima.centralizedApp.event.EsportaDialog;
import com.aimprosoft.android.optima.centralizedApp.event.MyPopupView;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.AbstractJarUtil;
import com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils.IdCampganaUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Dettaglio extends BaseActivity implements View.OnClickListener {
    private String ENERGY_OFFER = "energyOffer";
    private String GAS_OFFER = "gasOffer";
    private String ADSL_OFFER = "adslOffer";
    private String TLC_OFFER = "tlcOffer";
    private String MOBILE_OFFER = "mobileOffer";
    private String SI = " SI";
    private final String ENERGY_SOGLIA_LABLE = "Energia elettrica - ";
    private final String GAS_SOGLIA_LABLE = " Gas - ";

    private boolean isSdcPresent;

    private Offer offer;
    private BundleDAOImpl bundleDAO = new BundleDAOImpl();
    private LinearLayout podPercentLayout;
    private LinearLayout pdrPercentLayout;
    private LinearLayout adslPercentLayout;
    private LinearLayout tlcPercentLayout;
    private LinearLayout newLineNameLayout;
    private LinearLayout mobileHeader;
    private LinearLayout energyHeader;
    private LinearLayout gasHeader;
    private LinearLayout tlcHeader;
    private LinearLayout internetHeader;
    private LinearLayout datiDaFatturaLayout;
    private LinearLayout devicePercentLayout;
    private LinearLayout energyDetailsLayout;
    private LinearLayout gasDetailsLayout;
    private LinearLayout tlcDetailsLayout;
    private LinearLayout adslDetailsLayout;
    private LinearLayout assicurazioneDetailsLayout;
    private LinearLayout mobileDetailsLayout;
    private LinearLayout mobileTotalCostContainer;
    private LinearLayout continuaButtonLayout;
    private LinearLayout dettaglioSection;
    private LinearLayout sdcSection;
    private LinearLayout sdcRadioGroupLayout;
    private LinearLayout canoneIvaEsclusaContainer;

    private DecimalFormat decimalFormat;
    private DecimalFormat decimalFormatForPercent;

    private EsportaDialog esportaDialog;
    private MyPopupView myPopupView;

    private int minutiFisso;
    private int minutiMobile;
    private int localBundleId;
    private int mobileBundleId;

    private Map<String, Integer> offerMap = new HashMap<>();
    private int number = 1;
    private DecimalFormatSymbols fts = new DecimalFormatSymbols();

    private TextView nomeECognomeCanone;
    private TextView nomeecogCanoneField;
    private TextView codiceFiscalePartitaIVA;
    private TextView codiceFiscalePartitaIVAField;
    private TextView comuneHead;
    private TextView comuneField;
    private TextView tipologiaclienteComune;
    private TextView tipologiaClienteComuneField;

    private TextView comuneIVAInclusa;
    private TextView canoneIVAInclusaValue;
    private TextView canoneIVAEsclusa;
    private TextView canoneIVAEsclusaValue;

    private TextView costoTotaleIvaInclusa;
    private TextView scontoCost;
    private TextView costScontato;

    private TextView rataCostoDiAttivazione;
    private TextView rateCostoDiAttivazioneField;
    private TextView bonusContoRelax;
    private TextView bonusContoRelaxField;

    private ViewFlipper energyViewFlipper;

    private ImageButton nextSlideButton;
    private ImageButton previousSlideButton;

    private List<LinearLayout> gasSdcPageList;
    private List<LinearLayout> energySdcPageList;
    private Map<String, String> ambitoTariffarioMap;
    private LayoutInflater inflater;
    private RadioGroup sdcRadioGroup;
    private RadioButton energyRadioButton;
    private RadioButton gasRadioButton;

    private EnergyOfferDetailsDAOImpl energyOfferDetailsDAO = new EnergyOfferDetailsDAOImpl();
    private GasDetailOffersDAOImpl gasDetailOffersDAO = new GasDetailOffersDAOImpl();
    private EnergySDCDAOImpl energySDCDAO = new EnergySDCDAOImpl();
    private GasSDCDAOImpl gasSDCDAO = new GasSDCDAOImpl();
    private TownDAOImpl townDAO = new TownDAOImpl();

    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dettaglio_layout);
        offer = MyApplication.get(Constants.OFFER_ENTITY, Offer.class);

        initAllMainViews();
        setUpSDCSection();
        setUpDettaglioSection();
        initializePopupView();

        findViewById(R.id.percentButtonEnabled).setOnClickListener(v -> {
            setPercentFieldVisibility(View.VISIBLE);
            changePercButtonVisibility(View.GONE, View.VISIBLE);
        });

        findViewById(R.id.percButtonDisabled).setOnClickListener(v -> {
            setPercentFieldVisibility(View.INVISIBLE);
            changePercButtonVisibility(View.VISIBLE, View.GONE);
        });

        findViewById(R.id.backButton).setOnClickListener(v -> {
            if (sdcSection.getVisibility() == View.GONE && isSdcPresent) {
                changeSectionVisibility(false);
            } else {
                finish();
            }
        });

        continuaButtonLayout.setOnClickListener(v -> {
            if (offer != null && offer.isPdfSendingRequired() && offer.getConfiguratorType() == Constants.CONSUMER_CONFIGURATOR) {
                showEsportaDialog();
            } else {
                changeSectionVisibility(true);
            }
        });

        sdcRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            energyViewFlipper.removeAllViews();
            switch (checkedId) {
                case R.id.energy:
                    fillFlipperWithPages(energySdcPageList);
                    break;
                case R.id.gas:
                    fillFlipperWithPages(gasSdcPageList);
                    break;
            }
        });
    }

    private void initializePopupView() {
        RelativeLayout customLayoutBottom = (RelativeLayout)
                getLayoutInflater().inflate(R.layout.popup_layout_bottom, null);

        myPopupView = new MyPopupView
                (this, R.style.PopupAnimation, customLayoutBottom);
    }

    private void showEsportaDialog() {
        if (esportaDialog == null) {
            esportaDialog = new EsportaDialog(Dettaglio.this, offer);
        }
        esportaDialog.showDialog();
    }

    private void setUpSDCSection() {
        fts.setDecimalSeparator('.');
        String PATTERN = "#####0.00";
        decimalFormat = new DecimalFormat(PATTERN, fts);

        if (isSDCSectionEnabled()) {
            gasSdcPageList = new ArrayList<>();
            energySdcPageList = new ArrayList<>();

            List<EnergyOfferDetails> energyDetailsList = energyOfferDetailsDAO.getEnergyOfferDetailsByEnergyOfferId(offer.getEnergyOfferId() != null ? offer.getEnergyOfferId() : 0);

            for (EnergyOfferDetails energyOfferDetails : energyDetailsList) {
                LinearLayout energyPage = buildEnergySDCPage(energyOfferDetails);
                if (energyPage != null) {
                    energySdcPageList.add(energyPage);
                }
            }

            buildGasAmbitoTarrifarioMap();
            List<GasDetailOffers> gasDetailDetailsList = gasDetailOffersDAO.getGasOfferDetailsByGasOfferId(offer.getGasOfferId() != null ? offer.getGasOfferId() : 0);

            for (GasDetailOffers gasDetailOffers : gasDetailDetailsList) {
                LinearLayout gasPage = buildGasSDCPage(gasDetailOffers);
                if (gasPage != null) {
                    gasSdcPageList.add(gasPage);
                }
            }

            previousSlideButton.setOnClickListener(v -> energyViewFlipper.showPrevious());

            nextSlideButton.setOnClickListener(v -> energyViewFlipper.showNext());

            boolean isEnergySdcValid = offer.getEnergyOfferId() != null && !energySdcPageList.isEmpty();
            boolean isGasSdcValid = offer.getGasOfferId() != null && !gasSdcPageList.isEmpty();
            if (isGasSdcValid) {
                gasRadioButton.setChecked(true);
                if (!isEnergySdcValid) {
                    fillFlipperWithPages(gasSdcPageList);
                }
            }
            gasRadioButton.setVisibility(isGasSdcValid ? View.VISIBLE : View.GONE);

            if (isEnergySdcValid) {
                energyRadioButton.setChecked(true);
                fillFlipperWithPages(energySdcPageList);
            }
            energyRadioButton.setVisibility(isEnergySdcValid ? View.VISIBLE : View.GONE);

            isSdcPresent = isEnergySdcValid || isGasSdcValid;
            if (!isSdcPresent) {
                changeSectionVisibility(true);
            }
        } else {
            changeSectionVisibility(true);
        }
    }

    private void buildGasAmbitoTarrifarioMap() {
        String[] ambitoTariffarioArray = getResources().getStringArray(R.array.ambito_tarrifario);
        ambitoTariffarioMap = new HashMap<>();
        String key = "A";
        for (int i = 0; i < ambitoTariffarioArray.length; i++) {
            ambitoTariffarioMap.put(key + (i + 1), ambitoTariffarioArray[i]);
        }
    }

    private void fillFlipperWithPages(List<LinearLayout> servicePageList) {
        if (servicePageList != null) {
            for (LinearLayout page : servicePageList) {
                energyViewFlipper.addView(page);
            }
        }
    }

    private LinearLayout buildEnergySDCPage(EnergyOfferDetails energyOfferDetails) {
        List<EnergySDC> energySDCs = energySDCDAO.getEnergySDCByDetailsId(energyOfferDetails.getEnergyDetailOfferId());
        LinearLayout energySDCPage = null;
        if (!energySDCs.isEmpty()) {
            energySDCPage = (LinearLayout) inflater.inflate(R.layout.sdc_energy_details, null);
            EnergySDC firstSDC = energySDCs.get(0);


            String dateInizio = AbstractJarUtil.changeSDCDateFormat(firstSDC.getDataInizio(),
                    AbstractJarUtil.formatterFullDateWithSlashAndTimeDetails,
                    AbstractJarUtil.formatterFullDateWithSlash);

            String dateFine = AbstractJarUtil.changeSDCDateFormat(firstSDC.getDataFine(),
                    AbstractJarUtil.formatterFullDateWithSlashAndTimeDetails,
                    AbstractJarUtil.formatterFullDateWithSlash);

            TextView corrispettiviPrevisti = (TextView) energySDCPage.findViewById(R.id.corrispettiviPrevisti);
            corrispettiviPrevisti.setText(getResources().getString(R.string.corrispettivi_previsti, dateInizio, dateFine));
            TextView IcorrispettiviPrevisti = (TextView) energySDCPage.findViewById(R.id.IcorrispettiviPrevisti);
            IcorrispettiviPrevisti.setText(getResources().getString(R.string.i_corrispettivi_previsti, dateInizio, dateFine));
            TextView energyConsumoAnnuo = (TextView) energySDCPage.findViewById(R.id.energyConsumoAnnuo);
            energyConsumoAnnuo.setText(getResources().getString(R.string.energy_consumo_annuo_part2, energyOfferDetails.getYearlyConsumption(), energyOfferDetails.getYearlyConsumption() * 12));
            TextView ilConfrontoE = (TextView) energySDCPage.findViewById(R.id.ilConfrontoE);
            ilConfrontoE.setText(getResources().getString(R.string.il_confronto_e, dateInizio, dateFine));
            TextView potenzaSdc = (TextView) energySDCPage.findViewById(R.id.potenzaSdc);
            potenzaSdc.setText(getResources().getString(R.string.cliente_con_potenza, getPotenzaSDCDesc(AbstractJarUtil.getPotenzaValue(offer, energyOfferDetails))));

            TextView A1 = (TextView) energySDCPage.findViewById(R.id.A1);
            A1.setText(decimalFormat.format(firstSDC.getCostoTotSenImpE()));
            TextView B1 = (TextView) energySDCPage.findViewById(R.id.B1);
            B1.setText(decimalFormat.format(firstSDC.getPrezzoMonorario()));
            TextView C1 = (TextView) energySDCPage.findViewById(R.id.C1);
            C1.setText(decimalFormat.format(firstSDC.getPrezzoBiorarioNoPunta()));
            TextView D1 = (TextView) energySDCPage.findViewById(R.id.D1);
            D1.setText(decimalFormat.format(firstSDC.getPrezzoBiorarioPunta()));

            EnergySDC energySDC2 = energySDCs.get(1);
            TextView A2 = (TextView) energySDCPage.findViewById(R.id.A2);
            A2.setText(decimalFormat.format(energySDC2.getCostoTotSenImpE()));
            TextView B2 = (TextView) energySDCPage.findViewById(R.id.B2);
            B2.setText(decimalFormat.format(energySDC2.getPrezzoMonorario()));
            TextView C2 = (TextView) energySDCPage.findViewById(R.id.C2);
            C2.setText(decimalFormat.format(energySDC2.getPrezzoBiorarioNoPunta()));
            TextView D2 = (TextView) energySDCPage.findViewById(R.id.D2);
            D2.setText(decimalFormat.format(energySDC2.getPrezzoBiorarioPunta()));

            EnergySDC energySDC3 = energySDCs.get(2);
            TextView A3 = (TextView) energySDCPage.findViewById(R.id.A3);
            A3.setText(decimalFormat.format(energySDC3.getCostoTotSenImpE()));
            TextView B3 = (TextView) energySDCPage.findViewById(R.id.B3);
            B3.setText(decimalFormat.format(energySDC3.getPrezzoMonorario()));
            TextView C3 = (TextView) energySDCPage.findViewById(R.id.C3);
            C3.setText(decimalFormat.format(energySDC3.getPrezzoBiorarioNoPunta()));
            TextView D3 = (TextView) energySDCPage.findViewById(R.id.D3);
            D3.setText(decimalFormat.format(energySDC3.getPrezzoBiorarioPunta()));

            EnergySDC energySDC4 = energySDCs.get(3);
            TextView A4 = (TextView) energySDCPage.findViewById(R.id.A4);
            A4.setText(decimalFormat.format(energySDC4.getCostoTotSenImpE()));
            TextView B4 = (TextView) energySDCPage.findViewById(R.id.B4);
            B4.setText(decimalFormat.format(energySDC4.getPrezzoMonorario()));
            TextView C4 = (TextView) energySDCPage.findViewById(R.id.C4);
            C4.setText(decimalFormat.format(energySDC4.getPrezzoBiorarioNoPunta()));
            TextView D4 = (TextView) energySDCPage.findViewById(R.id.D4);
            D4.setText(decimalFormat.format(energySDC4.getPrezzoBiorarioPunta()));
        }
        return energySDCPage;
    }

    private LinearLayout buildGasSDCPage(GasDetailOffers gasOfferDetails) {
        List<GasSDC> gasSDCs = gasSDCDAO.getGasSDCByDetailsId(gasOfferDetails.getGasDetailsOfferId());
        LinearLayout gasSDCPage = null;
        if (!gasSDCs.isEmpty()) {
            gasSDCPage = (LinearLayout) inflater.inflate(R.layout.sdc_gas_details, null);
            GasSDC firstSDC = gasSDCs.get(0);

            String dateInizio = AbstractJarUtil.changeSDCDateFormat(firstSDC.getDataInizio(),
                    AbstractJarUtil.formatterFullDateWithSlashAndTimeDetails,
                    AbstractJarUtil.formatterFullDateWithSlash);

            String dateFine = AbstractJarUtil.changeSDCDateFormat(firstSDC.getDataFine(),
                    AbstractJarUtil.formatterFullDateWithSlashAndTimeDetails,
                    AbstractJarUtil.formatterFullDateWithSlash);

            TextView corrispettiviPrevisti = (TextView) gasSDCPage.findViewById(R.id.corrispettiviPrevisti);
            corrispettiviPrevisti.setText(getResources().getString(R.string.corrispettivi_previsti, dateInizio, dateFine));
            TextView IcorrispettiviPrevisti = (TextView) gasSDCPage.findViewById(R.id.IcorrispettiviPrevisti);
            IcorrispettiviPrevisti.setText(getResources().getString(R.string.i_corrispettivi, dateInizio, dateFine));
            TextView energyConsumoAnnuo = (TextView) gasSDCPage.findViewById(R.id.energyConsumoAnnuo);
            energyConsumoAnnuo.setText(getResources().getString(R.string.sdc_gas_main_desc_part2, gasOfferDetails.getYearlyConsumption(), gasOfferDetails.getYearlyConsumption() * 12));
            TextView ilConfrontoE = (TextView) gasSDCPage.findViewById(R.id.ilConfrontoE);
            ilConfrontoE.setText(getResources().getString(R.string.il_confronto_e, dateInizio, dateFine));
            TextView potenzaSdc = (TextView) gasSDCPage.findViewById(R.id.town);
            potenzaSdc.setText(ambitoTariffarioMap.get(townDAO.getTownById(gasOfferDetails.getTownId()).getTariffZone()));

            double spesaAnnua = firstSDC.getCostoTotSenImpG();

            TextView A1 = (TextView) gasSDCPage.findViewById(R.id.A1);
            A1.setText(decimalFormat.format(spesaAnnua));
            double b1 = firstSDC.getStimaSpesaRif();
            TextView B1 = (TextView) gasSDCPage.findViewById(R.id.B1);
            B1.setText(decimalFormat.format(b1));
            TextView C1 = (TextView) gasSDCPage.findViewById(R.id.C1);
            C1.setText(decimalFormat.format(spesaAnnua - b1));
            TextView D1 = (TextView) gasSDCPage.findViewById(R.id.D1);
            double d1 = ((spesaAnnua - b1) / b1) * 100;
            D1.setText(decimalFormat.format(d1));

            GasSDC gasSDC2 = gasSDCs.get(1);
            double spesaAnnua2 = gasSDC2.getCostoTotSenImpG();

            TextView A2 = (TextView) gasSDCPage.findViewById(R.id.A2);
            A2.setText(decimalFormat.format(spesaAnnua2));
            double b2 = gasSDC2.getStimaSpesaRif();
            TextView B2 = (TextView) gasSDCPage.findViewById(R.id.B2);
            B2.setText(decimalFormat.format(b2));
            TextView C2 = (TextView) gasSDCPage.findViewById(R.id.C2);
            C2.setText(decimalFormat.format(spesaAnnua2 - b2));
            TextView D2 = (TextView) gasSDCPage.findViewById(R.id.D2);
            double d2 = ((spesaAnnua2 - b2) / b2) * 100;
            D2.setText(decimalFormat.format(d2));

            GasSDC gasSDC3 = gasSDCs.get(2);
            double spesaAnnua3 = gasSDC3.getCostoTotSenImpG();
            TextView A3 = (TextView) gasSDCPage.findViewById(R.id.A3);
            A3.setText(decimalFormat.format(spesaAnnua3));
            double b3 = gasSDC3.getStimaSpesaRif();
            TextView B3 = (TextView) gasSDCPage.findViewById(R.id.B3);
            B3.setText(decimalFormat.format(b3));
            TextView C3 = (TextView) gasSDCPage.findViewById(R.id.C3);
            C3.setText(decimalFormat.format(spesaAnnua3 - b3));
            TextView D3 = (TextView) gasSDCPage.findViewById(R.id.D3);
            double d3 = ((spesaAnnua3 - b3) / b3) * 100;
            D3.setText(decimalFormat.format(d3));

            GasSDC gasSDC4 = gasSDCs.get(3);
            double spesaAnnua4 = gasSDC4.getCostoTotSenImpG();
            TextView A4 = (TextView) gasSDCPage.findViewById(R.id.A4);
            A4.setText(decimalFormat.format(spesaAnnua4));
            double b4 = gasSDC4.getStimaSpesaRif();
            TextView B4 = (TextView) gasSDCPage.findViewById(R.id.B4);
            B4.setText(decimalFormat.format(b4));
            TextView C4 = (TextView) gasSDCPage.findViewById(R.id.C4);
            C4.setText(decimalFormat.format(spesaAnnua4 - b4));
            TextView D4 = (TextView) gasSDCPage.findViewById(R.id.D4);
            double d4 = ((spesaAnnua4 - b4) / b4) * 100;
            D4.setText(decimalFormat.format(d4));

            GasSDC gasSDC5 = gasSDCs.get(4);
            double spesaAnnua5 = gasSDC5.getCostoTotSenImpG();
            TextView A5 = (TextView) gasSDCPage.findViewById(R.id.A5);
            A5.setText(decimalFormat.format(spesaAnnua5));
            TextView B5 = (TextView) gasSDCPage.findViewById(R.id.B5);
            double b5 = gasSDC5.getStimaSpesaRif();
            B5.setText(decimalFormat.format(b5));
            TextView C5 = (TextView) gasSDCPage.findViewById(R.id.C5);
            C5.setText(decimalFormat.format(spesaAnnua5 - b5));
            TextView D5 = (TextView) gasSDCPage.findViewById(R.id.D5);
            double d5 = ((spesaAnnua5 - b5) / b5) * 100;
            D5.setText(decimalFormat.format(d5));

            GasSDC gasSDC6 = gasSDCs.get(5);
            double spesaAnnua6 = gasSDC6.getCostoTotSenImpG();
            TextView A6 = (TextView) gasSDCPage.findViewById(R.id.A6);
            A6.setText(decimalFormat.format(spesaAnnua6));
            double b6 = gasSDC6.getStimaSpesaRif();
            TextView B6 = (TextView) gasSDCPage.findViewById(R.id.B6);
            B6.setText(decimalFormat.format(b6));
            TextView C6 = (TextView) gasSDCPage.findViewById(R.id.C6);
            C6.setText(decimalFormat.format(spesaAnnua6 - b6));
            TextView D6 = (TextView) gasSDCPage.findViewById(R.id.D6);
            double d6 = ((spesaAnnua6 - b6) / b6) * 100;
            D6.setText(decimalFormat.format(d6));
        }
        return gasSDCPage;
    }

    private String getPotenzaSDCDesc(float potenzaValue) {
        String potenza = "";
        if (potenzaValue > 6.0) {
            potenza = offer.getClientTypeId() == Constants.RESIDENZIALE ?
                    Constants.POTENZA_6R : Constants.POTENZA_6NR;
        } else {
            if (potenzaValue <= 1.5) {
                if (offer.getClientTypeId() == Constants.RESIDENZIALE) {
                    potenza = Constants.POTENZA_1_5_R;
                } else {
                    potenza = Constants.POTENZA_1_5_NR;
                }
            }

            if (potenzaValue >= 1.6 && potenzaValue <= 3.0) {
                if (offer.getClientTypeId() == Constants.RESIDENZIALE) {
                    potenza = Constants.POTENZA_3_R;
                } else {
                    potenza = Constants.POTENZA_3_NR;
                }
            }

            if (potenzaValue >= 3.1 && potenzaValue <= 6.0) {
                potenza = offer.getClientTypeId() == Constants.RESIDENZIALE ?
                        Constants.POTENZA_6R : Constants.POTENZA_6NR;
            }
        }
        return potenza;
    }

    private void setUpDettaglioSection() {
        energyHeader = (LinearLayout) findViewById(R.id.energyHeaderDettaglio);
        mobileHeader = (LinearLayout) findViewById(R.id.mobileHeaderDettaglio);
        gasHeader = (LinearLayout) findViewById(R.id.gasHeaderDettaglio);
        tlcHeader = (LinearLayout) findViewById(R.id.tlcHeaderDettaglio);
        internetHeader = (LinearLayout) findViewById(R.id.internetHeaderDettaglio);
        newLineNameLayout = (LinearLayout) findViewById(R.id.newLineNameLayout);
        datiDaFatturaLayout = (LinearLayout) findViewById(R.id.datiDaFattura);
        energyDetailsLayout = (LinearLayout) findViewById(R.id.energyDetails);
        gasDetailsLayout = (LinearLayout) findViewById(R.id.gasDetails);
        tlcDetailsLayout = (LinearLayout) findViewById(R.id.tlcDetails);
        adslDetailsLayout = (LinearLayout) findViewById(R.id.adslDetails);
        assicurazioneDetailsLayout = (LinearLayout) findViewById(R.id.assicurazioneDetails);
        //sogliaTableLayout = (LinearLayout) findViewById(R.id.sogliaTableLayout);

        podPercentLayout = (LinearLayout) findViewById(R.id.podDetailsLayout);
        pdrPercentLayout = (LinearLayout) findViewById(R.id.pdrDetailsLayout);
        adslPercentLayout = (LinearLayout) findViewById(R.id.adslDetailsLayout);
        tlcPercentLayout = (LinearLayout) findViewById(R.id.tlcDetailsLayout);

        showCompleteOffer(R.id.energiaElettricaDettaglio, R.id.gasMetanoDettaglio, R.id.telefonaFissa,
                R.id.internetDettaglio, R.id.mobileDettaglio);

        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.UK);
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormatForPercent = new DecimalFormat("0.0", decimalFormatSymbols);

        if (offer != null) {
            if (offer.getConfiguratorType() == Constants.CONSUMER_CONFIGURATOR) {
                initConsumer();
            } else {
                initBusines();
            }

            setComuneIVAField();
            setAssicurazioneSection();

            rateCostoDiAttivazioneField.setText(getString(R.string.euro_value,
                    String.valueOf(decimalFormat.format(
                            offer.getConfiguratorType() == Constants.CONSUMER_CONFIGURATOR ?
                                    offer.getOfferCostExtra()
                                    : offer.getOfferCostExtra().multiply(new BigDecimal(4))))));

            bonusContoRelaxField.setText(getString(R.string.euro_value, decimalFormat.format(offer.getConto_relax())));

            if (offer.getEnergyOfferId() != null)
                offerMap.put(ENERGY_OFFER, offer.getEnergyOfferId());
            if (offer.getGasOfferId() != null) offerMap.put(GAS_OFFER, offer.getGasOfferId());
            if (offer.getInternetOfferId() != null)
                offerMap.put(ADSL_OFFER, offer.getInternetOfferId());
            if (offer.getMobileOfferId() != null)
                offerMap.put(MOBILE_OFFER, offer.getMobileOfferId());
            if (offer.getTlcOfferId() != null || offer.getWlrOfferId() != null)
                offerMap.put(TLC_OFFER, offer.getTlcOfferId() != null ? offer.getTlcOfferId() : offer.getWlrOfferId());

            drawGasDettaglio();
            drawEnergyDettaglio();
            drawADSLDettaglio();
            drawTLCDettaglio();
            drawMobileDettaglio();
        }
    }

    private void drawMobileDettaglio() {
        if (offerMap.get(MOBILE_OFFER) != null) {
            MobileOffer mobileOffer = new MobileOfferDAOImpl().getMobileOfferById(offer.getMobileOfferId());

            costoTotaleIvaInclusa.setText(getString(R.string.euro_value, decimalFormat.format(mobileOffer.getOfferCostIva())));
            double sconto = mobileOffer.getPromoCost();
            scontoCost.setText(getString(R.string.euro_value, decimalFormat.format(sconto)));
            costScontato.setText(getString(R.string.euro_value, decimalFormat.format(mobileOffer.getOfferCostConPromo())));

            List<MobileDetailsOffer> mobileDetailsOffers = new MobileDetailsOfferDAOImpl().getMobileOfferDetailsByMobileOfferId(mobileOffer.getMobileOfferId());
            for (int i = 0; i < mobileDetailsOffers.size(); i++) {
                MobileDetailsOffer mobileDetailsOffer = mobileDetailsOffers.get(i);
                if (mobileDetailsOffers.size() - 1 == i) offerMap.remove(MOBILE_OFFER);
                drawMobileDetails(mobileDetailsOffer);
            }
        }
    }

    private void setAssicurazioneSection() {
        List<AssicurazioneOffer> assicurazioneOffers = new AssicurazioneOfferDAOImpl().getAssicurazioneByOfferId(offer.getOfferId());
        if (!assicurazioneOffers.isEmpty()) {
            assicurazioneDetailsLayout.setVisibility(View.VISIBLE);
            for (AssicurazioneOffer assicurazioneOffer : assicurazioneOffers) {
                drawAssicurazioneDetails(assicurazioneOffer);
            }
        }
    }

    private void setComuneIVAField() {
        List<OfferDevice> offerDeviceList = new OfferDeviceDAOImpl().getDevicesByOfferId(offer.getOfferId());

        double montlyCost = offer.getOfferCost().doubleValue();
        double montlyCostIva = offer.getOfferCostIVA().doubleValue();
        for (OfferDevice device : offerDeviceList) {
            montlyCost -= device.getDeviceCost();
            montlyCostIva -= device.getDeviceCostIva();
        }

        List<AssicurazioneOffer> assicurazioneOffers = new AssicurazioneOfferDAOImpl().getAssicurazioneByOfferId(offer.getOfferId());
        if (!assicurazioneOffers.isEmpty()) {
            for (AssicurazioneOffer assicurazioneOffer : assicurazioneOffers) {
                montlyCost -= (assicurazioneOffer.getAssicurazioneCost() / 12) + assicurazioneOffer.getCostoGestioneIntegrata();
                montlyCostIva -= (assicurazioneOffer.getAssicurazioneCost() / 12) + assicurazioneOffer.getCostoGestioneIntegrataIva();
            }
        }

        canoneIVAInclusaValue.setText(getString(R.string.euro_value, String.valueOf(decimalFormat.format(
                offer.getConfiguratorType() == Constants.CONSUMER_CONFIGURATOR ? montlyCostIva : montlyCost))));
        canoneIVAEsclusaValue.setText(getString(R.string.euro_value, String.valueOf(decimalFormat.format(montlyCost))));
        drawDeviceDettaglio(offerDeviceList);
    }

    private void drawDeviceDettaglio(List<OfferDevice> offerDeviceList) {
        findViewById(R.id.deviceDettaglio).setVisibility(!offerDeviceList.isEmpty() ? View.VISIBLE : View.GONE);
        if (!offerDeviceList.isEmpty()) {
            for (OfferDevice offerDevice : offerDeviceList) {
                drawDeviceDettaglioRow(offerDevice.getDeviceName(), offerDevice.getDeviceDesc(),
                        getString(R.string.value_euro, decimalFormat.format(offerDevice.getDeviceCostIva())), devicePercentLayout);
            }
        }
    }


    private void drawDeviceDettaglioRow(String deviceName, String deviceDesc, String deviceCost, LinearLayout targetLayout) {
        LinearLayout row = (LinearLayout) getLayoutInflater().inflate(R.layout.device_dettaglio_item, null);

        TextView deviceNameView = (TextView) row.findViewById(R.id.deviceName);
        TextView deviceDescView = (TextView) row.findViewById(R.id.deviceDesc);
        TextView deviceCostView = (TextView) row.findViewById(R.id.deviceCost);

        deviceNameView.setText(deviceName);
        deviceCostView.setText(deviceCost);
        deviceDescView.setText(deviceDesc);
        targetLayout.addView(row);
    }

    private void changeSectionVisibility(boolean isSDCNowVisible) {
        dettaglioSection.setVisibility(isSDCNowVisible ? View.VISIBLE : View.GONE);
        sdcSection.setVisibility(!isSDCNowVisible ? View.VISIBLE : View.GONE);
        nextSlideButton.setVisibility(!isSDCNowVisible ? View.VISIBLE : View.GONE);
        previousSlideButton.setVisibility(!isSDCNowVisible ? View.VISIBLE : View.GONE);
        continuaButtonLayout.setVisibility(!isSDCNowVisible ? View.VISIBLE : View.GONE);
        if (isSDCNowVisible) {
            changePercButtonVisibility(View.VISIBLE, View.GONE);
        } else {
            changePercButtonVisibility(View.GONE, View.GONE);
        }
        sdcRadioGroupLayout.setVisibility(isSDCNowVisible ? View.GONE : View.VISIBLE);
    }

    private void initAllMainViews() {
        nomeECognomeCanone = (TextView) findViewById(R.id.nomeECogCanone);
        nomeecogCanoneField = (TextView) findViewById(R.id.nomeECogCanoneField);
        codiceFiscalePartitaIVA = (TextView) findViewById(R.id.codiceFPartitaIVA);
        codiceFiscalePartitaIVAField = (TextView) findViewById(R.id.codiceFPartitaIVAField);
        comuneHead = (TextView) findViewById(R.id.comuneHead);
        comuneField = (TextView) findViewById(R.id.comuneField);
        tipologiaclienteComune = (TextView) findViewById(R.id.tipologiaclienteComune);
        tipologiaClienteComuneField = (TextView) findViewById(R.id.tipologiaClienteComuneField);
        comuneIVAInclusa = (TextView) findViewById(R.id.canoneIVAInclusa);
        canoneIVAInclusaValue = (TextView) findViewById(R.id.canoneIVAInclusaValue);
        rataCostoDiAttivazione = (TextView) findViewById(R.id.rateCostodiCostoDiAttivazione);
        rateCostoDiAttivazioneField = (TextView) findViewById(R.id.rateCostoDiCostoDiAttivField);
        bonusContoRelax = (TextView) findViewById(R.id.bonusContoRelax);
        bonusContoRelaxField = (TextView) findViewById(R.id.bonusContoRelaxField);
        devicePercentLayout = (LinearLayout) findViewById(R.id.devicePercentLayout);
        continuaButtonLayout = (LinearLayout) findViewById(R.id.continuaButtonLayout);
        sdcSection = (LinearLayout) findViewById(R.id.sdcSection);
        dettaglioSection = (LinearLayout) findViewById(R.id.dettaglioSection);
        sdcRadioGroupLayout = (LinearLayout) findViewById(R.id.sdcRadioGroupLayout);
        sdcRadioGroup = (RadioGroup) findViewById(R.id.sdcRadioGroup);
        gasRadioButton = (RadioButton) findViewById(R.id.gas);
        energyRadioButton = (RadioButton) findViewById(R.id.energy);
        previousSlideButton = (ImageButton) findViewById(R.id.previouseSlideButton);
        nextSlideButton = (ImageButton) findViewById(R.id.nextSlideButton);
        energyViewFlipper = (ViewFlipper) findViewById(R.id.energySdcFlipper);
        canoneIVAEsclusa = (TextView) findViewById(R.id.canoneIVAEsclusa);
        canoneIVAEsclusaValue = (TextView) findViewById(R.id.canoneIVAEsclusaValue);
        canoneIvaEsclusaContainer = (LinearLayout) findViewById(R.id.canoneIVAEsclusaContainer);
        mobileDetailsLayout = (LinearLayout) findViewById(R.id.mobileDetailsLayout);
        mobileTotalCostContainer = (LinearLayout) findViewById(R.id.mobileTotalCostContainer);
        costoTotaleIvaInclusa = (TextView) findViewById(R.id.costoTotaleIvaInclusa);
        scontoCost = (TextView) findViewById(R.id.sconto);
        costScontato = (TextView) findViewById(R.id.costoMobileScontato);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private void initBusines() {
        nomeECognomeCanone.setText(getString(R.string.ragione_sociale_p));
        codiceFiscalePartitaIVA.setText(getString(R.string.partita_iva_p));
        comuneHead.setVisibility(View.GONE);
        tipologiaclienteComune.setText(getString(R.string.comune));

        nomeecogCanoneField.setText(offer.getName());
        codiceFiscalePartitaIVAField.setText(offer.getPiva());
        canoneIvaEsclusaContainer.setVisibility(View.GONE);
        comuneField.setVisibility(View.GONE);
        Town town = new TownDAOImpl().getTownById(offer.getTownId());
        tipologiaClienteComuneField.setText(town.getTownDesc() + "(" + town.getProvinceDesc() + ")");
        comuneIVAInclusa.setText(getString(R.string.canone_p));
        rataCostoDiAttivazione.setText(getString(R.string.costo_di_attivazione_p));
        bonusContoRelax.setText(getString(R.string.bonus_conto_relax_p));
    }

    private void initConsumer() {
        nomeECognomeCanone.setText(getString(R.string.nome_e_cognome_p));
        codiceFiscalePartitaIVA.setText(getString(R.string.codice_fiscale_p));
        comuneHead.setVisibility(View.VISIBLE);
        comuneHead.setText(getString(R.string.comune_p));
        tipologiaclienteComune.setText(getString(R.string.tipologia_cliente_p));

        nomeecogCanoneField.setText(offer.getNome() + " " + offer.getSurname());
        codiceFiscalePartitaIVAField.setText(offer.getCodiceFiscale());
        comuneField.setVisibility(View.VISIBLE);
        Town town = new TownDAOImpl().getTownById(offer.getTownId());
        comuneField.setText(town.toString());
        tipologiaClienteComuneField.setText(offer.getClientTypeId() == 1 ? getString(R.string.residenziale) : getString(R.string.non_residenziale));
        comuneIVAInclusa.setText(getString(R.string.canone_iva_inclusa_p));
        canoneIVAEsclusa.setText(getString(R.string.canone_iva_esclusa_p));
        rataCostoDiAttivazione.setText(getString(R.string.rata_costo_di_attivazione_iva_inclusa_p));
        bonusContoRelax.setText(getString(R.string.bonus_conto_relax_p));

        TextView regimeOrTipoContrattoHeader = (TextView) findViewById(R.id.regimeOrTipoContrattoHeader);
        regimeOrTipoContrattoHeader.setText(getString(R.string.tipologia_contratto));
        TextView comuneHeader = (TextView) findViewById(R.id.comuneHeader);
        comuneHeader.setVisibility(View.INVISIBLE);
    }

    private void drawTLCDettaglio() {
        if (offerMap.get(TLC_OFFER) != null) {
            minutiFisso = 0;
            minutiMobile = 0;

            TLCOffer tlcOffer = new TLCOfferDAOImpl().getTLCOfferById(offer.getTlcOfferId() != null ? offer.getTlcOfferId() : 0);
            WlrOffers wlrOffers = new WlrOffersDAOImpl().getWlrOfferById(offer.getWlrOfferId() != null ? offer.getWlrOfferId() : 0);
            TlcDetailsOfferDAOImpl tlcDetailsOffersDao = new TlcDetailsOfferDAOImpl();
            WlrOfferDetailsDAOImpl wlrOfferDetailsDAO = new WlrOfferDetailsDAOImpl();
            bundleDAO = new BundleDAOImpl();
            List<TlcDetailsOffer> existingTlcDetailsOffers = tlcDetailsOffersDao.getExistingClientDetailsByTlcOfferId(tlcOffer.getTlcOfferId());
            List<WlrOfferDetails> existingWlrDetailsOffers = wlrOfferDetailsDAO.getOldClientDetailsByOfferId(wlrOffers.getWlrOfferId(), true);
            List<TlcDetailsOffer> newTlcDetailsOffers = tlcDetailsOffersDao.getNewClientDetailsByTlcOfferId(tlcOffer.getTlcOfferId());
            List<WlrOfferDetails> newWlrDetailsOffers = wlrOfferDetailsDAO.getNewClientDetailsByOfferId(wlrOffers.getWlrOfferId(), true);

            TextView localBundleView = (TextView) findViewById(R.id.newLocalBundle);
            TextView mobileBundleView = (TextView) findViewById(R.id.newMobileBundle);
            LinearLayout existingClientLayout = (LinearLayout) findViewById(R.id.existingLineLayout);
//            LinearLayout existingClientInfoLayout = (LinearLayout) findViewById(R.id.existingClientInfoLayout);
            LinearLayout newClientLayout = (LinearLayout) findViewById(R.id.newLineLayout);

            int existingLineCount = existingTlcDetailsOffers.size() + existingWlrDetailsOffers.size();
            if ((existingLineCount) > 0) {
                existingClientLayout.setVisibility(View.VISIBLE);
                for (TlcDetailsOffer detailsOffer : existingTlcDetailsOffers) {
                    drawCpsOfferDetails(detailsOffer);
                    drawTlcDettaglioLayout(detailsOffer.getTlcName());
                    minutiFisso += detailsOffer.getLocalBundleId();
                    minutiMobile += detailsOffer.getMobileBundleId();
//                    drawPercentViewForSeparateService(String.valueOf(detailsOffer.getLocalBundleId()), 0,
//                            detailsOffer.getTlcName(), "", String.valueOf(detailsOffer.getMobileBundleId()), String.valueOf(detailsOffer.getNumLines()), existingClientInfoLayout);
//                    oldClientTlcCost += detailsOffer.getOfferCost();
                }
                for (WlrOfferDetails detailsOffer : existingWlrDetailsOffers) {
                    drawWlrOfferDetails(detailsOffer);
                    drawTlcDettaglioLayout(detailsOffer.getWlrName());
                    minutiFisso += detailsOffer.getOwnLocalBundleId();
                    minutiMobile += detailsOffer.getOwnMobileBundleId();
//                    drawPercentViewForSeparateService(String.valueOf(detailsOffer.getOwnLocalBundleId()), 0,
//                            detailsOffer.getWlrName(), "", String.valueOf(detailsOffer.getOwnMobileBundleId()), String.valueOf(detailsOffer.getNumLines()), existingClientInfoLayout);
//                    oldClientTlcCost += detailsOffer.getCost();
                }
//                if ((newTlcDetailsOffers.size() + newWlrDetailsOffers.size()) > 0) {
//                    setPercToView(R.id.existingClientPerc, oldClientTlcCost);
//                    offerMap.remove(TLC_OFFER);
//                } else {
//                    offerMap.remove(TLC_OFFER);
//                    setPercToView(R.id.existingClientPerc, oldClientTlcCost);
//                }
            }

            if ((newTlcDetailsOffers.size() + newWlrDetailsOffers.size()) > 0) {
                localBundleId = 0;
                mobileBundleId = 0;
//                newClientLayout.setVisibility(View.VISIBLE);

                for (TlcDetailsOffer detailsOffer : newTlcDetailsOffers) {
                    drawCpsOfferDetails(detailsOffer);
                    drawTlcDettaglioLayout("");
//                    newClientTlcCost += detailsOffer.getOfferCost();
                    localBundleId = detailsOffer.getLocalBundleId() != null ? detailsOffer.getLocalBundleId() : 0;
                    mobileBundleId = detailsOffer.getMobileBundleId() != null ? detailsOffer.getMobileBundleId() : 0;
                }

                for (WlrOfferDetails detailsOffer : newWlrDetailsOffers) {
                    drawWlrOfferDetails(detailsOffer);
                    drawTlcDettaglioLayout("");
//                    newClientTlcCost += detailsOffer.getCost();
                    localBundleId = wlrOffers.getLocalBundleId() != null ? wlrOffers.getLocalBundleId() : 0;
                    mobileBundleId = wlrOffers.getMobileBundleId() != null ? wlrOffers.getMobileBundleId() : 0;
                }
//
//                if ((existingTlcDetailsOffers.size() + existingWlrDetailsOffers.size()) > 0) {
//                    setPercToView(R.id.newLinePerc, newClientTlcCost);
//                } else {
//                    offerMap.remove(TLC_OFFER);
//                    setPercToView(R.id.newLinePerc, newClientTlcCost);
//                }
            }

            Bundle localBundle = bundleDAO.getBundleById(localBundleId);
            boolean isFlatPresent = localBundle.getBundleDesc() != null && localBundle.getBundleDesc().equals("Flat");
            int localBundleMinutes = localBundle.getBundleMinutes() != null ? localBundle.getBundleMinutes() : 0;
            int minutiVersoFisso = localBundleMinutes + minutiFisso;
            Bundle mobileBundle = bundleDAO.getBundleById(mobileBundleId);
            boolean isMobileFlatPresent = mobileBundle.getBundleDesc() != null && mobileBundle.getBundleDesc().equals("Flat");
            int mobileBundleMinutes = mobileBundle.getBundleMinutes() != null ? mobileBundle.getBundleMinutes() : 0;
            int minutiVersoMobile = mobileBundleMinutes + minutiMobile;

            localBundleView.setText(!isFlatPresent ?
                    (minutiVersoFisso != 0 ? String.valueOf(minutiVersoFisso) : "") : "Flat");
            mobileBundleView.setText(!isMobileFlatPresent ?
                    (minutiVersoMobile != 0 ? String.valueOf(minutiVersoMobile) : "") : "Flat");
            newClientLayout.setVisibility(View.VISIBLE);
            offerMap.remove(TLC_OFFER);
            TextView totalTlcPerc = (TextView) findViewById(R.id.totalPercTlc);
//            double tlcPerc = getPercentOfOfferPod(oldClientTlcCost + newClientTlcCost + (offer.getTlcOfferId() == null ? wlrOffers.getBundleCost() : 0.0));
//            sumOfPercent += tlcPerc;
//            totalTlcPerc.setText("[" + tlcPerc + "%]");
//            totalTlcPerc.setText("[" + (tlcOffer.getTlcOfferId() != 0 ? tlcOffer.getPercentage() : wlrOffers.getPercentage()) + "%]");
            String percent = getFormattedPercent(tlcOffer.getTlcOfferId() != 0 ? tlcOffer.getPercentage() : wlrOffers.getPercentage());
            totalTlcPerc.setText(("[" + percent + "%]"));
            totalTlcPerc.setVisibility(percent.isEmpty() || percent.equals("0") || offer.getOfferCost().doubleValue() == 0 ? View.INVISIBLE : View.VISIBLE);

//            String voceCodice = tlcOffer.getTlcOfferId() != 0 ? tlcOffer.getCodicePmcPS() : wlrOffers.getCodicePmcPS();
            TextView codiceTextView = (TextView) findViewById(R.id.codiceTlc);
//            codiceTextView.setText(getString(R.string.codice_pmc_ps, voceCodice));
//            codiceTextView.setVisibility(!voceCodice.isEmpty() ? View.VISIBLE : View.GONE);
//
//            String vocePS = tlcOffer.getTlcOfferId() != 0 ? tlcOffer.getPs() : wlrOffers.getPs();
//            TextView codicePSTextView = (TextView) findViewById(R.id.codiceTlcPS);
//            codicePSTextView.setText(getString(R.string.codice_prezzo_sforamento, vocePS));
//            codicePSTextView.setVisibility(!vocePS.isEmpty() ? View.VISIBLE : View.GONE);
//
//            String vocePMC = tlcOffer.getTlcOfferId() != 0 ? tlcOffer.getPmc() : wlrOffers.getPmc();
//            TextView codicePMCTextView = (TextView) findViewById(R.id.codiceTlcPMC);
//            codicePMCTextView.setText(getString(R.string.codice_prezzo_mancato, vocePMC));
//            codicePMCTextView.setVisibility(!vocePMC.isEmpty() ? View.VISIBLE : View.GONE);
        }
    }

    private void drawADSLDettaglio() {
        if (offerMap.get(ADSL_OFFER) != null) {
            InternetOffer internetOffer = new InternetOfferDAOImpl().getInternetOfferById(offer.getInternetOfferId());

            List<InternetDetailOffers> internetDetailOfferses = new InternetDetailOffersDAOImpl().getInternetDetailsOfferByInternetOfferId(internetOffer.getInternetOfferId());
            for (int i = 0; i < internetDetailOfferses.size(); i++) {
                InternetDetailOffers internetDetailOffers = internetDetailOfferses.get(i);
                if (internetDetailOfferses.size() - 1 == i) offerMap.remove(ADSL_OFFER);
                drawAdslDetails(internetDetailOffers);
                drawPercentViewForSeparateService(bundleDAO.getBundleDescByBundleId(internetDetailOffers.getBundleId()),
                        getFormattedPercent(internetDetailOffers.getPercentage()),
                        internetDetailOffers.isExistingClientOffer() ? internetDetailOffers.getAdsl() : "Linea dati " + number, "", "", "", null, "", adslPercentLayout);
                number += internetDetailOffers.isExistingClientOffer() ? 0 : 1;
            }
            number = 1;
        }
    }

    private void drawGasDettaglio() {
        if (offerMap.get(GAS_OFFER) != null) {
            GasOffer gasOffer = new GasOfferDAOImpl().getGasOfferById(offer.getGasOfferId());
            TownDAOImpl townDAO = new TownDAOImpl();
            List<GasDetailOffers> gasDetailOfferses = gasDetailOffersDAO.getGasOfferDetailsByGasOfferId(gasOffer.getGasOfferId());
            for (int i = 0; i < gasDetailOfferses.size(); i++) {
                GasDetailOffers gasDetailOffers = gasDetailOfferses.get(i);
                drawGasOfferDetails(gasDetailOffers);
                drawSogliaTableRow(GAS_SOGLIA_LABLE + "PDR " + number, gasDetailOffers.getPs(), gasDetailOffers.getPmc());
                String descFiscalClass = offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR ?
                        new FiscalClassDAOImpl().getFiscalClassById(gasDetailOffers.getFiscalClass()).getDescFiscalClass() :
                        getConsumerFiscalClassDesc(gasDetailOffers.getTipoContratto());
                if (gasDetailOfferses.size() - 1 == i) offerMap.remove(GAS_OFFER);
                Map<String, String> prezzoValueMap = new HashMap<>();
                prezzoValueMap.put("codice", gasDetailOffers.getCodicePmcPS());
                prezzoValueMap.put("prezzoMancatoConsumo", gasDetailOffers.getPmc());
                prezzoValueMap.put("prezzoSforamento", gasDetailOffers.getPs());
                drawPercentViewForSeparateService(String.valueOf(gasDetailOffers.getYearlyConsumption()),
                        getFormattedPercent(gasDetailOffers.getPercentage()),
                        gasDetailOffers.isExistingClientOffer() ? gasDetailOffers.getPdr() : "PDR " + number, "Smc", descFiscalClass,
                        gasDetailOffers.getTownId() != 0 && offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR ?
                                townDAO.getTownById(gasDetailOffers.getTownId()).toString() : "",
                        prezzoValueMap, "", pdrPercentLayout);
                number += gasDetailOffers.isExistingClientOffer() ? 0 : 1;
            }
            number = 1;
        }
    }

    private void setAnnuoValue(TextView textView, LinearLayout annoLayout, Integer kwh) {
        if (kwh != null) {
            textView.setText(getString(R.string.energy_value_with_units, kwh));
            annoLayout.setVisibility(View.VISIBLE);
        }
    }

    private void drawSogliaTableRow(String name, String ps, String pmc) {
        LinearLayout currentLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.dettaglio_soglia_item, null);

        TextView nameView = (TextView) currentLayout.findViewById(R.id.name);
        TextView sogliaPS = (TextView) currentLayout.findViewById(R.id.sogliaPs);
        TextView sogliaPMC = (TextView) currentLayout.findViewById(R.id.sogliaPMC);

        nameView.setText(name);
        sogliaPS.setText(getString(R.string.value_with_euro, ps));
        sogliaPMC.setText(getString(R.string.value_with_euro, pmc));
    }

    private void drawEnergyOfferDetails(EnergyOfferDetails energyOfferDetails) {
        LinearLayout currentLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.pod_dati_da_fattura, null);

        TextView annuoKwh1 = (TextView) currentLayout.findViewById(R.id.consumoKwh1);
        TextView annuoKwh2 = (TextView) currentLayout.findViewById(R.id.consumoKwh2);
        TextView annuoKwh3 = (TextView) currentLayout.findViewById(R.id.consumoKwh3);
        LinearLayout anno1Layout = (LinearLayout) currentLayout.findViewById(R.id.anno1Layout);
        LinearLayout anno2Layout = (LinearLayout) currentLayout.findViewById(R.id.anno2Layout);
        LinearLayout anno3Layout = (LinearLayout) currentLayout.findViewById(R.id.anno3Layout);
        TextView tittle = (TextView) currentLayout.findViewById(R.id.tittle);

        setAnnuoValue(annuoKwh1, anno1Layout, energyOfferDetails.getYearlyKwh());
        setAnnuoValue(annuoKwh2, anno2Layout, energyOfferDetails.getYearlyKwh2());
        setAnnuoValue(annuoKwh3, anno3Layout, energyOfferDetails.getYearlyKwh3());
        tittle.setText(energyOfferDetails.getPod());

        List<EnergyOfferDateInterval> energyOfferDateIntervals = new EnergyOfferDateIntervalDAOImpl().getOfferDateIntervalByDetailsId(energyOfferDetails.getEnergyDetailOfferId());
        for (EnergyOfferDateInterval energyOfferDateInterval : energyOfferDateIntervals) {
            LinearLayout intervalItem = (LinearLayout) getLayoutInflater().inflate(R.layout.dati_da_fattura_pod_interval_item, null);
            LinearLayout intervalLayout = (LinearLayout) currentLayout.findViewById(R.id.intervals_layout);

            TextView intervalKwh1 = (TextView) intervalItem.findViewById(R.id.intervaliKwh1);
            TextView intervalKwh2 = (TextView) intervalItem.findViewById(R.id.intervaliKwh2);
            TextView intervalKwh3 = (TextView) intervalItem.findViewById(R.id.intervaliKwh3);
            TextView potenzaImpegnata = (TextView) intervalItem.findViewById(R.id.potenzaImpegnata);
            LinearLayout datiIntervali = (LinearLayout) intervalItem.findViewById(R.id.datiIntervalliKwh1);
            LinearLayout datiIntervali2 = (LinearLayout) intervalItem.findViewById(R.id.datiIntervalliKwh2);
            LinearLayout datiIntervali3 = (LinearLayout) intervalItem.findViewById(R.id.datiIntervalliKwh3);
            TextView periodoDa = (TextView) intervalItem.findViewById(R.id.periodoDa);
            TextView periodoA = (TextView) intervalItem.findViewById(R.id.periodoA);

            setAnnuoValue(intervalKwh1, datiIntervali, energyOfferDateInterval.getKwh1() != Constants.NO_VALUE ? energyOfferDateInterval.getKwh1() : null);
            setAnnuoValue(intervalKwh2, datiIntervali2, energyOfferDateInterval.getKwh2() != Constants.NO_VALUE ? energyOfferDateInterval.getKwh2() : null);
            setAnnuoValue(intervalKwh3, datiIntervali3, energyOfferDateInterval.getKwh3() != Constants.NO_VALUE ? energyOfferDateInterval.getKwh3() : null);
            if (energyOfferDateInterval.getPotenzaImpegnata() != 0) {
                potenzaImpegnata.setVisibility(View.VISIBLE);
                potenzaImpegnata.setText(getString(R.string.potenza_kw_with_arg, String.valueOf(energyOfferDateInterval.getPotenzaImpegnata())));
            }
            periodoDa.setText(energyOfferDateInterval.getDateFrom() != null ? convertDate(energyOfferDateInterval.getDateFrom()) : "");
            periodoA.setText(energyOfferDateInterval.getDateTo() != null ? convertDate(energyOfferDateInterval.getDateTo()) : "");
            intervalLayout.addView(intervalItem);
        }
        energyDetailsLayout.addView(currentLayout);
    }

    private void drawGasOfferDetails(GasDetailOffers gasDetailOffers) {
        LinearLayout currentLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.pdr_dati_da_fattura, null);

        TextView tittle = (TextView) currentLayout.findViewById(R.id.tittle);
        TextView annuoSmc = (TextView) currentLayout.findViewById(R.id.consumoKwh1);
        TextView categoriaDuso = (TextView) currentLayout.findViewById(R.id.categoriaDuso);
        TextView classePrelievo = (TextView) currentLayout.findViewById(R.id.classePrelievo);
        LinearLayout categoriaUsoAndClasePrelievoLayout = (LinearLayout) currentLayout.findViewById(R.id.gasDetails);
        LinearLayout consumoAnnuo = (LinearLayout) currentLayout.findViewById(R.id.consumoAnnuoSmc);

        annuoSmc.setText(getString(R.string.gas_value_with_units, gasDetailOffers.getYearlySmc()));
        classePrelievo.setText(new ClassePrelievoGasDAOImpl().getClasseDiPrelievoDescById(gasDetailOffers.getDayClassId()));
        categoriaDuso.setText(new CategorieUsoDAOImpl().getCategorieUsoDescById(gasDetailOffers.getUserTypeId()));
        tittle.setText(gasDetailOffers.getPdr());

        List<GasOfferDateInterval> gasOfferDateIntervals = new GasOfferDateIntervalDAOImpl().getOfferDateIntervalByGasDetailsId(gasDetailOffers.getGasDetailsOfferId());
        if (gasOfferDateIntervals.size() > 0 && gasOfferDateIntervals.get(0).getDateFrom() != null) {
            consumoAnnuo.setVisibility(View.GONE);
            for (GasOfferDateInterval gasOfferDateInterval : gasOfferDateIntervals) {
                LinearLayout intervalItem = (LinearLayout) getLayoutInflater().inflate(R.layout.dati_da_fattura_pdr_interval_item, null);
                LinearLayout intervalLayout = (LinearLayout) currentLayout.findViewById(R.id.intervals_layout);

                TextView intervalKwh1 = (TextView) intervalItem.findViewById(R.id.intervaliKwh1);
                TextView periodoDa = (TextView) intervalItem.findViewById(R.id.periodoDa);
                TextView periodoA = (TextView) intervalItem.findViewById(R.id.periodoA);

                intervalKwh1.setText(gasOfferDateInterval.getSmc() != 0 ? getString(R.string.gas_value_with_units, gasOfferDateInterval.getSmc()) : "");
                periodoDa.setText(gasOfferDateInterval.getDateFrom() != null ? convertDate(gasOfferDateInterval.getDateFrom()) : "");
                periodoA.setText(gasOfferDateInterval.getDateTo() != null ? convertDate(gasOfferDateInterval.getDateTo()) : "");
                categoriaUsoAndClasePrelievoLayout.setVisibility(View.VISIBLE);
                intervalLayout.addView(intervalItem);
            }
        }
        gasDetailsLayout.addView(currentLayout);
    }

    private void drawCpsOfferDetails(TlcDetailsOffer tlcDetailsOffer) {
        LinearLayout currentLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.cps_dati_da_fattura, null);

        TextView linea = (TextView) currentLayout.findViewById(R.id.tittle);
        TextView wlr = (TextView) currentLayout.findViewById(R.id.lineaType);
        TextView numLines = (TextView) currentLayout.findViewById(R.id.numeroLinee);

        linea.setText(tlcDetailsOffer.getTlcName());
        String NO = " NO";
        wlr.setText(NO);
        numLines.setText(String.valueOf(tlcDetailsOffer.getNumLines()));

        tlcDetailsLayout.addView(currentLayout);
    }

    private void drawAdslDetails(InternetDetailOffers internetDetailOffers) {
        LinearLayout currentLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.adsl_dati_da_fattura, null);

        TextView adsl = (TextView) currentLayout.findViewById(R.id.tittle);
//        TextView router = (TextView) currentLayout.findViewById(R.id.router);
        TextView ipAggiuntivi = (TextView) currentLayout.findViewById(R.id.ipAggiuntivi);
        TextView lineaSoloDati = (TextView) currentLayout.findViewById(R.id.lineaSoloDati);
        LinearLayout ipAggiuntiviLayout = (LinearLayout) currentLayout.findViewById(R.id.ipAggiuntiviLayout);

        adsl.setText(internetDetailOffers.getAdsl());
//        router.setText(internetDetailOffers.getRouter() == 1 ? "noleggio" : "acquisto");
        List<Services> servicesList = ServicesDAOImpl.getServicesByIdRange(TextUtils.join(",", internetDetailOffers.getServiceId().split(", ")));
        for (Services service : servicesList) {
            if (service.getFieldName().equals(ServicesDAOImpl.TipoOpzioni.LINEA_SOLO_DATI_LNA.getCtoId())) {
                lineaSoloDati.setText(SI);
                continue;
            }
            if (service.getFieldName().equals(ServicesDAOImpl.TipoOpzioni.IP_AGGIUNTIVI.getCtoId())) {
                ipAggiuntivi.setText(service.getServiceDesc());
                ipAggiuntiviLayout.setVisibility(View.VISIBLE);
            }
        }
        adslDetailsLayout.addView(currentLayout);
    }

    private void drawMobileDetails(MobileDetailsOffer mobileDetailsOffer) {
        LinearLayout currentLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.sim_details_item, null);

        TextView simName = (TextView) currentLayout.findViewById(R.id.simName);
        TextView offertaType = (TextView) currentLayout.findViewById(R.id.offertaType);
        TextView cost = (TextView) currentLayout.findViewById(R.id.cost);

        MobileBundle mobileBundle = MobileBundleDAOImpl.getMobileBundle(mobileDetailsOffer.getBundleId());
        simName.setText(mobileDetailsOffer.getSim());
        offertaType.setText(getString(R.string.voce_e_dati, (int) mobileBundle.getMin(), (int) mobileBundle.getSms(), (int) mobileBundle.getGiga(), mobileBundle.getDescrizioneProdotto()));
        cost.setText(String.valueOf(mobileDetailsOffer.getCost()));
        mobileDetailsLayout.addView(currentLayout);
    }

    private void drawAssicurazioneDetails(AssicurazioneOffer assicurazioneOffer) {
        LinearLayout currentLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.assicurazione_item_for_dettaglio, null);

        TextView targa = (TextView) currentLayout.findViewById(R.id.targa);
        TextView gestioneIvaInclusa = (TextView) currentLayout.findViewById(R.id.gestioneIvaInclusa);
        TextView gestioneIvaEsclusa = (TextView) currentLayout.findViewById(R.id.gestioneIvaEsclusa);
        TextView cost = (TextView) currentLayout.findViewById(R.id.param1);
        TextView costGestione = (TextView) currentLayout.findViewById(R.id.param2);
        TextView costGestioneIva = (TextView) currentLayout.findViewById(R.id.param3);

        targa.setText(assicurazioneOffer.getTarga());
        cost.setText(getString(R.string.value_euro, decimalFormat.format(assicurazioneOffer.getAssicurazioneCost() / 12)));
        costGestione.setText(getString(R.string.value_euro, decimalFormat.format(assicurazioneOffer.getCostoGestioneIntegrata())));
        costGestioneIva.setText(getString(R.string.value_euro, decimalFormat.format(assicurazioneOffer.getCostoGestioneIntegrataIva())));

        if (offer != null && offer.getConfiguratorType() == Constants.CONSUMER_CONFIGURATOR) {
            gestioneIvaEsclusa.setVisibility(View.GONE);
            costGestione.setVisibility(View.GONE);
        } else if (offer != null && offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR) {
            gestioneIvaInclusa.setVisibility(View.GONE);
            costGestioneIva.setVisibility(View.GONE);
        }

        assicurazioneDetailsLayout.addView(currentLayout);
    }

    private void drawWlrOfferDetails(WlrOfferDetails wlrOfferDetails) {
        if (wlrOfferDetails.getRete().equals(Constants.VOIP)) {
            drawVoipSection(wlrOfferDetails);
        } else {
            drawWlrSection(wlrOfferDetails);
        }
    }

    private void drawWlrSection(WlrOfferDetails wlrOfferDetails) {
        LinearLayout currentLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.wlr_dati_da_fattura, null);

        TextView linea = (TextView) currentLayout.findViewById(R.id.tittle);
        TextView wlr = (TextView) currentLayout.findViewById(R.id.lineaType);
        TextView numLines = (TextView) currentLayout.findViewById(R.id.numeroLinee);
        TextView operatore = (TextView) currentLayout.findViewById(R.id.operatore);
        TextView rete = (TextView) currentLayout.findViewById(R.id.rete);
        TextView services = (TextView) currentLayout.findViewById(R.id.services);
        TextView lineDesc = (TextView) currentLayout.findViewById(R.id.lineDesc);
        TextView numerazioneValue = (TextView) currentLayout.findViewById(R.id.numerazioneValue);
        LinearLayout numerazioneLayout = (LinearLayout) currentLayout.findViewById(R.id.numerazioneAggiuntiviLayout);

        int voceIdcampagna = IdCampganaUtils.getOfferIdCampagnaByServiceType(IdCampganaUtils.ServiceType.VOCE,
                offer.getConfiguratorType(), offer.getIsOldTlcOfferUsed());

        linea.setText(wlrOfferDetails.getWlrName());
        wlr.setText(SI);
        numLines.setText(String.valueOf(new LineNumbersDAOImpl().getLineNumbersById(wlrOfferDetails.getNumLines()).getLineNumberDesc()));
        operatore.setText(new CarriersDAOImpl().getCarrierById(wlrOfferDetails.getCarrierId()).getCarrierDesc());
        rete.setText(new NetworksDAOImpl().getNetworkById(wlrOfferDetails.getNetworkId()).getNetworkDesc());
        services.setText(getAllServiceDesc(wlrOfferDetails));
        if (wlrOfferDetails.getServiceAddictionNumber() != null && wlrOfferDetails.getServiceAddictionNumber() != 0) {
            numerazioneValue.setText(String.valueOf(wlrOfferDetails.getServiceAddictionNumber()));
            numerazioneLayout.setVisibility(View.VISIBLE);
        }
        lineDesc.setText(LineTypeDAOImpl.getLineTypeByIdAndCampagnaId(voceIdcampagna, wlrOfferDetails.getLineId()).getLineDet());

        tlcDetailsLayout.addView(currentLayout);
    }

    private void drawVoipSection(WlrOfferDetails wlrOfferDetails) {
        LinearLayout currentLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.voip_dati_da_fattura, null);
        TextView linea = (TextView) currentLayout.findViewById(R.id.tittle);

        linea.setText(wlrOfferDetails.getWlrName());
        tlcDetailsLayout.addView(currentLayout);
    }

    private void drawEnergyDettaglio() {
        if (offerMap.get(ENERGY_OFFER) != null) {
            EnergyOffer energyOffer = new EnergyOfferDAOImpl().getEnergyOfferById(offer.getEnergyOfferId());
            List<EnergyOfferDetails> energyOfferDetailses = energyOfferDetailsDAO.getEnergyOfferDetailsByEnergyOfferId(energyOffer.getEnergyOfferId());
            for (int i = 0; i < energyOfferDetailses.size(); i++) {
                EnergyOfferDetails energyOfferDetails = energyOfferDetailses.get(i);
                drawEnergyOfferDetails(energyOfferDetails);
                drawSogliaTableRow(ENERGY_SOGLIA_LABLE + "POD " + number, energyOfferDetails.getPs(), energyOfferDetails.getPmc());
//                EnergyMeter energyMeter = new EnergyMeterDAOImpl().getEnergiMeterById(energyOfferDetails.isQuestionarioUsing() ? energyOfferDetails.getEnergyMeter2() : energyOfferDetails.getEnergyMeter());
                if (energyOfferDetailses.size() - 1 == i) offerMap.remove(ENERGY_OFFER);
                Map<String, String> prezzoValueMap = new HashMap<>();
                prezzoValueMap.put("codice", energyOfferDetails.getCodicePmcPS());
                prezzoValueMap.put("prezzoMancatoConsumo", energyOfferDetails.getPmc());
                prezzoValueMap.put("prezzoSforamento", energyOfferDetails.getPs());
                drawPercentViewForSeparateService(String.valueOf(energyOfferDetails.getYearlyConsumption()),
                        getFormattedPercent(energyOfferDetails.getPercentage()),
                        energyOfferDetails.isExistingClientOffer() ? energyOfferDetails.getPod() + addForwardSlash(energyOfferDetails) : "POD " + number + addForwardSlash(energyOfferDetails), " kWh",
                        AbstractJarUtil.getTariffTransportoDescription(offer, energyOfferDetails),
                        getPotenzaDescription(energyOfferDetails, true),
                        prezzoValueMap, energyOfferDetails.getWebServiceResult(), podPercentLayout);
                number += energyOfferDetails.isExistingClientOffer() ? 0 : 1;
            }
            number = 1;
        }
    }

    /**
     * Add forward slash if web service was used
     */
    private String addForwardSlash(EnergyOfferDetails energyOfferDetails) {
        return energyOfferDetails.getSumAttiva() != 0 ? "/" : "";
    }

//    private void setPercToView(int viewId, double cost) {
//        TextView existingClientPerc = (TextView) findViewById(viewId);
//        double perc = getPercentOfOfferPod(cost);
//        sumOfPercent += perc;
//        existingClientPerc.setText("[" + perc + "%]");
//    }

    private void drawTlcDettaglioLayout(String name) {
        TextView nameLine = (TextView) getLayoutInflater().inflate(R.layout.linee_name_text_view, null);

        nameLine.setText(name);
        newLineNameLayout.addView(nameLine);
    }

//    private double getLastPercent(double sumOfPercent) {
//        return Math.floor((100 - sumOfPercent) * 100) / 100;
//    }

//    private double getPercentOfOfferPod(double podCost) {
//        String pattern = "0.0";
//        DecimalFormatSymbols fts = new DecimalFormatSymbols();
//        fts.setDecimalSeparator('.');
//        DecimalFormat decimalFormat = new DecimalFormat(pattern, fts);
//        return offerMap.size() == 0 ? Double.valueOf(decimalFormat.format(getLastPercent(sumOfPercent))) :
//                Double.valueOf(decimalFormat.format((podCost / montlyCost) * 100.00));
//    }

    private String getFormattedPercent(float percent) {
//        float formattedPerc = offerMap.isEmpty() ? (float) (100 - sumOfPercent) : percent;
//        sumOfPercent += percent;
        return decimalFormatForPercent.format(percent);
    }

    private void drawPercentViewForSeparateService(String offerValue, String percent, String title, String units,
                                                   String podDetail, String podDetail2, Map<String, String> codiceMap, String exclamationText, LinearLayout targetLayout) {
        LinearLayout row = (LinearLayout) getLayoutInflater().inflate(R.layout.pod_item_for_dettaglio, null);
//        sumOfPercent += percent;
        TextView podName = (TextView) row.findViewById(R.id.podName);
        TextView podValue = (TextView) row.findViewById(R.id.podValue);
        TextView percentView = (TextView) row.findViewById(R.id.percentPod);
        TextView podDett = (TextView) row.findViewById(R.id.podDetail);
        TextView podDett2 = (TextView) row.findViewById(R.id.podDetail2);
        ImageButton exclamation = (ImageButton) row.findViewById(R.id.exclamation);
        if (!TextUtils.isEmpty(exclamationText)) {
            exclamation.setVisibility(View.VISIBLE);
            exclamation.setContentDescription(exclamationText);
            exclamation.setOnClickListener(this);
        }
        LinearLayout percentLayout = (LinearLayout) row.findViewById(R.id.podPercentLayout);
        TextView codiceTextView = (TextView) row.findViewById(R.id.codice);
        TextView codicePSTextView = (TextView) row.findViewById(R.id.codicePS);
        TextView codicePMCTextView = (TextView) row.findViewById(R.id.codicePMC);

        podName.setText(title);
        podValue.setText((offerValue != null ? offerValue : " ") + " " + units);
        if (percent.isEmpty() || percent.equals("0") || offer.getOfferCost().doubleValue() == 0)
            percentLayout.setVisibility(View.INVISIBLE);
        percentView.setText("[" + percent + "%]");
        podDett.setText(podDetail);
        podDett2.setText(podDetail2);

        if (codiceMap != null) {
            String codice = codiceMap.get("codice");
            String prezzoMancatoConsumo = codiceMap.get("prezzoMancatoConsumo");
            String prezzoSforamento = codiceMap.get("prezzoSforamento");

            codiceTextView.setText(codice != null ? getString(R.string.codice_pmc_ps, codice) : "");
            codiceTextView.setVisibility(codice != null ? View.VISIBLE : View.GONE);
            codicePMCTextView.setText(prezzoMancatoConsumo != null ? getString(R.string.codice_prezzo_mancato, prezzoMancatoConsumo) : "");
            codicePMCTextView.setVisibility(prezzoMancatoConsumo != null ? View.VISIBLE : View.GONE);
            codicePSTextView.setText(prezzoSforamento != null ? getString(R.string.codice_prezzo_sforamento, prezzoSforamento) : "");
            codicePSTextView.setVisibility(prezzoSforamento != null ? View.VISIBLE : View.GONE);
        }
        targetLayout.addView(row);
    }

    private void setPercentFieldVisibility(int visibilityStatus) {
//        findViewById(R.id.energiaPercentLayout).setVisibility(visibilityStatus);
//        findViewById(R.id.gasPercentlayout).setVisibility(visibilityStatus);
//        findViewById(R.id.TLCpercentLayout).setVisibility(visibilityStatus);
//        findViewById(R.id.mobilePercentLayout).setVisibility(visibilityStatus);
//        findViewById(R.id.wlrPercentLayout).setVisibility(visibilityStatus);
//        findViewById(R.id.internetPercentLayout).setVisibility(visibilityStatus);
        int visibilityforDetails = visibilityStatus == View.INVISIBLE ? View.GONE : View.VISIBLE;
        energyHeader.setVisibility(visibilityStatus);
        mobileHeader.setVisibility(visibilityStatus);
        gasHeader.setVisibility(visibilityStatus);
        internetHeader.setVisibility(visibilityStatus);
        tlcHeader.setVisibility(visibilityStatus);
        podPercentLayout.setVisibility(visibilityforDetails);
        pdrPercentLayout.setVisibility(visibilityforDetails);
        adslPercentLayout.setVisibility(visibilityforDetails);
        mobileDetailsLayout.setVisibility(visibilityforDetails);
        mobileTotalCostContainer.setVisibility(visibilityforDetails);
        tlcPercentLayout.setVisibility(visibilityforDetails);
        datiDaFatturaLayout.setVisibility(visibilityforDetails);
        devicePercentLayout.setVisibility(visibilityforDetails);
//        int sogliaTableVisibilityStatus = visibilityStatus == View.VISIBLE && (offer.getEnergyOfferId() != null || offer.getGasOfferId() != null) ?
//                VisSDCSectionEnablediew.VISIBLE : View.GONE;
        //sogliaTableLayout.setVisibility(sogliaTableVisibilityStatus);
    }

    private void changePercButtonVisibility(int enabledButtonVisibility, int disabledButtonVisibility) {
        findViewById(R.id.percEnabledLayout).setVisibility(enabledButtonVisibility);
        findViewById(R.id.percDisabledLayout).setVisibility(disabledButtonVisibility);
    }

    private boolean isSDCSectionEnabled() {
//        return offer != null && offer.getConfiguratorType() == Constants.CONSUMER_CONFIGURATOR && (offer.getEnergyOfferId() != null || offer.getGasOfferId() != null);
        return false;
    }

    @Override
    public void onClick(View v) {
        myPopupView.show(v, v.getContentDescription().toString());
    }
}
